package org.walkerljl.toolkit.template.handle.rpc;

import org.walkerljl.toolkit.standard.exception.AppRpcException;
import org.walkerljl.toolkit.template.handle.AbstractHandleTemplate;
import org.walkerljl.toolkit.template.log.model.InvocationInfo;
import org.walkerljl.toolkit.template.log.Logger;
import org.walkerljl.toolkit.template.log.LoggerFactory;
import org.walkerljl.toolkit.template.log.util.LoggerUtil;

/**
 * 抽象的Rpc处理模板
 *
 * @author xingxun
 */
public abstract class AbstractRpcHandleTemplate extends AbstractHandleTemplate {

    /**
     * 本地日志打印对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRpcHandleTemplate.class);

    /**
     * 处理业务
     *
     * @param handler 处理器
     * @param invocationInfo 调用信息
     * @return
     */
    public <PARAM, RESULT> RESULT handle(InvocationInfo<PARAM, RESULT> invocationInfo, RpcHandler<PARAM, RESULT> handler) {
        RESULT result = null;
        try {
            // 参数校验
            RpcAssertUtil.assertParam(invocationInfo != null, "invocationInfo");
            RpcAssertUtil.assertParam(handler != null, "handler");

            // 业务参数校验
            boolean isPassedCheckParams = handler.checkParams(invocationInfo.getParam());
            RpcAssertUtil.assertTrue(isPassedCheckParams, RpcErrorCode.INVALID_PARAM);

            //业务执行
            result = handler.handle(invocationInfo.getParam());

        } catch (Throwable e) {
            if (invocationInfo != null) {
                invocationInfo.markFailure(e);
            }
        } finally {
            try {
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
     * 校验调用信息
     *
     * @param invocationInfo 调用信息
     * @param <PARAM>
     * @param <RESULT>
     */
    private <PARAM, RESULT> void assertInvocationInfo(InvocationInfo<PARAM, RESULT> invocationInfo) {
        RpcAssertUtil.assertParam(invocationInfo != null, "invocationInfo");
        if (invocationInfo.isSuccess()) {
            return;
        }
        Throwable throwable = invocationInfo.getThrowable();
        if (throwable == null) {
            throw new AppRpcException(invocationInfo.getTraceInfo());
        } else {
            if (throwable instanceof AppRpcException) {
                throw new AppRpcException(((AppRpcException) throwable).getCode(), throwable.getMessage(), throwable);
            } else {
                throw new AppRpcException(throwable.getMessage(), throwable);
            }
        }
    }
}