package org.walkerljl.toolkit.template.handle.service;

import org.walkerljl.toolkit.standard.Result;
import org.walkerljl.toolkit.standard.exception.AppServiceException;
import org.walkerljl.toolkit.standard.exception.code.ErrorCode;
import org.walkerljl.toolkit.template.handle.AbstractHandleTemplate;
import org.walkerljl.toolkit.template.log.model.InvocationInfo;
import org.walkerljl.toolkit.template.log.Logger;
import org.walkerljl.toolkit.template.log.LoggerFactory;
import org.walkerljl.toolkit.template.log.util.LoggerUtil;

/**
 * 抽象的业务处理模板
 *
 * @author xingxun
 */
public abstract class AbstractServiceHandleTemplate extends AbstractHandleTemplate {

    /**
     * 本地日志打印对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServiceHandleTemplate.class);

    /**
     * 处理业务
     *
     * @param handler 业务处理器
     * @return
     */
    public <PARAM, RESULT> Result<RESULT> handle(InvocationInfo<PARAM, RESULT> invocationInfo,
                                                                                ServiceHandler<PARAM, RESULT> handler) {
        Result<RESULT> result = null;
        try {
            // 参数校验
            ServiceAssertUtil.assertParam(invocationInfo != null, "invocationInfo");
            ServiceAssertUtil.assertParam(handler != null, "handler");

            // 业务参数校验
            boolean isPassedCheckParams = handler.checkParams(invocationInfo.getParam());
            ServiceAssertUtil.assertTrue(isPassedCheckParams, ServiceErrorCode.INVALID_PARAM);

            // 业务执行
            RESULT originResult = handler.handle(invocationInfo.getParam());
            // 构建Success消息
            result = Result.success(originResult);

            // 标注是否成功
            invocationInfo.markSuccess(result, (result == null ? null : result.getData()));
        } catch (Throwable e) {
            if (invocationInfo != null) {
                invocationInfo.markFailure(e);
            }
            //构建Failure消息
            if (!canRethrowException()) {
                ErrorCode errorCode = (e instanceof AppServiceException) ? ((AppServiceException) e).getCode() : null;
                if (errorCode != null) {
                    result = Result.failure(errorCode.getCode(), errorCode.getDescription());
                } else {
                    result = Result.failure(ServiceErrorCode.UNKNOWN.getCode(),
                            ServiceErrorCode.UNKNOWN.getDescription());
                }
                result.setRemark(e.getMessage());
            }
        } finally {
            try {
                //设置原始数据
                invocationInfo.setDirectResultData(result);
                doLog(invocationInfo);
            } catch (Throwable e) {
                LoggerUtil.error(LOGGER, e);
            }
            if (canRethrowException()) {
                assertInvocationInfo(invocationInfo);
            }
        }
        return result;
    }

    /**
     * 跟踪异常时，是否重新抛出异常
     *
     * @return
     */
    protected boolean canRethrowException() {
        return false;
    }

    /**
     * 校验调用信息
     *
     * @param invocationInfo 调用信息
     * @param <PARAM>
     * @param <RESULT>
     */
    private <PARAM, RESULT> void assertInvocationInfo(InvocationInfo<PARAM, RESULT> invocationInfo) {
        ServiceAssertUtil.assertParam(invocationInfo != null, "invocationInfo");
        if (invocationInfo.isSuccess()) {
            return;
        }

        Throwable throwable = invocationInfo.getThrowable();
        if (throwable == null) {
            throw new AppServiceException(invocationInfo.getTraceInfo());
        } else {
            if (throwable instanceof AppServiceException) {
                throw new AppServiceException(((AppServiceException) throwable).getCode(), throwable.getMessage(), throwable);
            } else {
                throw new AppServiceException(throwable.getMessage(), throwable);
            }
        }
    }
}