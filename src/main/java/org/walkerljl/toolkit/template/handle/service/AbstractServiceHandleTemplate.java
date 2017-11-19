package org.walkerljl.toolkit.template.handle.service;

import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;
import org.walkerljl.toolkit.standard.exception.AppServiceException;
import org.walkerljl.toolkit.standard.exception.ErrorCode;
import org.walkerljl.toolkit.template.handle.AbstractHandleTemplate;
import org.walkerljl.toolkit.template.log.InvocationInfo;
import org.walkerljl.toolkit.template.log.LoggerUtil;

/**
 * 抽象的业务处理模板
 *
 * @author lijunlin
 */
public abstract class AbstractServiceHandleTemplate extends AbstractHandleTemplate {

    /**
     * 本地日志打印对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServiceHandleTemplate.class);

    /**
     * 处理业务
     *
     * @param param 请求参数
     * @param handler 业务处理器
     * @return
     */
    public <PARAM, RESULT> org.walkerljl.toolkit.standard.Result<RESULT> handle(PARAM param, ServiceHandler<PARAM, RESULT> handler) {
        org.walkerljl.toolkit.standard.Result<RESULT> result = null;
        InvocationInfo<RESULT> invocationInfo = null;
        try {
            // 参数校验
            invocationInfo = handler.checkParams(param);
            assertInvocationInfo(invocationInfo);

            //业务执行
            invocationInfo = handler.handle(param);
            assertInvocationInfo(invocationInfo);

            //构建Success消息
            result = org.walkerljl.toolkit.standard.Result.success(invocationInfo.getResult());
        } catch (Throwable e) {
            if (!canRethrowException()) {
                //构建Failure消息
                ErrorCode errorCode = (e instanceof AppServiceException) ? ((AppServiceException) e).getCode() : null;
                if (errorCode != null) {
                    result = org.walkerljl.toolkit.standard.Result.failure(errorCode.getCode(), e.getMessage());
                } else {
                    result = org.walkerljl.toolkit.standard.Result.failure(ServiceErrorCode.UNKOWN.getCode(),
                            ServiceErrorCode.UNKOWN.getDescription());
                    result.setRemark(e.getMessage());
                }
            }

            tryRethrowException(e);
        } finally {
            try {
                doLog(invocationInfo);
            } catch (Throwable e) {
                LoggerUtil.error(LOGGER, e);
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
}