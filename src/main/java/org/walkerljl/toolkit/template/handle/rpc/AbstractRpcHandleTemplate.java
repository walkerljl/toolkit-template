package org.walkerljl.toolkit.template.handle.rpc;


import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;
import org.walkerljl.toolkit.standard.exception.AppRpcException;
import org.walkerljl.toolkit.template.log.InvocationInfo;
import org.walkerljl.toolkit.template.log.LoggerDetailUtil;
import org.walkerljl.toolkit.template.log.LoggerDigestUtil;
import org.walkerljl.toolkit.template.log.LoggerUtil;

/**
 * 抽象的Rpc处理模板
 *
 * @author lijunlin
 */
public abstract class AbstractRpcHandleTemplate {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * 处理业务
     *
     * @param param 请求参数
     * @param handler 处理器
     * @return
     */
    public <PARAM, RESULT> RESULT handle(PARAM param, RpcHandler<PARAM, RESULT> handler) {
        InvocationInfo<RESULT> invocationInfo = null;
        try {
            //业务执行
            invocationInfo = handler.handle(param);
        } catch (Throwable e) {
            //如需要重新抛出异常则重新抛出异常
            if (canRethrowException()) {
                //rethrow the exception
                if (e instanceof RuntimeException) {
                    rethrowException(((RuntimeException) e));
                } else {
                    throw new Error(e.getMessage(), e);
                }
            }
        } finally {
            try {
                doLog(invocationInfo);
            } catch (Throwable e) {
                LoggerUtil.error(LOGGER, e);
            }
        }

        if (invocationInfo == null) {
            rethrowException("invocation info is null.");
        }

        if (!invocationInfo.isSuccess()) {
            rethrowException(invocationInfo.getTraceInfo());
        }

        return invocationInfo.getResult();
    }

    private <RESULT> void doLog(InvocationInfo<RESULT> invocationInfo) {
        LoggerDigestUtil.logDigest(invocationInfo, getDigestLogger());
        LoggerDetailUtil.logDetail(invocationInfo, getDetailLogger());
    }

    /**
     * 跟踪异常时，是否重新抛出异常
     *
     * @return
     */
    protected boolean canRethrowException() {
        return true;
    }

    /**
     * 重新抛出异常
     *
     * @param errorMsg
     */
    protected void rethrowException(String errorMsg) {
        throw new AppRpcException(errorMsg);
    }

    /**
     * 重新抛出异常
     *
     * @param runtimeException 运行时异常
     */
    protected void rethrowException(RuntimeException runtimeException) {
        throw new AppRpcException(runtimeException);
    }

    protected abstract Logger getDigestLogger();

    protected abstract Logger getDetailLogger();
}