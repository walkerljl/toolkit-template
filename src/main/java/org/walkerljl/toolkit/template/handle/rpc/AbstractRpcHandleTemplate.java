package org.walkerljl.toolkit.template.handle.rpc;

import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;
import org.walkerljl.toolkit.standard.exception.AppRpcException;
import org.walkerljl.toolkit.template.handle.AbstractHandleTemplate;
import org.walkerljl.toolkit.template.log.InvocationInfo;
import org.walkerljl.toolkit.template.log.LoggerUtil;

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
            //校验调用信息
            assertInvocationInfoNotNull(invocationInfo);
            // 参数校验
            boolean isPassedCheckParams = handler.checkParams(invocationInfo.getParam());
            if (!isPassedCheckParams) {
                throw new AppRpcException(RpcErrorCode.INVALID_PARAM);
            }
            //业务执行
            result = handler.handle(invocationInfo.getParam());
            //记录结果到调用信息
            invocationInfo.markSuccess(result);
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
            assertInvocationInfo(invocationInfo);
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
        if (!canRethrowException()) {
            return;
        }
        //不为空
        assertInvocationInfoNotNull(invocationInfo);
        if (!invocationInfo.isSuccess()) {
            String errorMsg = String.format("Fail to invocation:%s.", invocationInfo.getTraceInfo());
            if (invocationInfo.getThrowable() == null) {
                throw new AppRpcException(errorMsg);
            } else {
                throw new AppRpcException(errorMsg, invocationInfo.getThrowable());
            }
        }
    }

    /**
     * 校验调用信息不为空
     *
     * @param invocationInfo
     * @param <PARAM>
     * @param <RESULT>
     */
    private <PARAM, RESULT> void assertInvocationInfoNotNull(InvocationInfo<PARAM, RESULT> invocationInfo) {
        if (invocationInfo == null) {
            throw new AppRpcException(RpcErrorCode.UNKOWN, "invocation info is null.");
        }
    }
}