package org.walkerljl.toolkit.template.handle.rpc;

import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;
import org.walkerljl.toolkit.standard.exception.AppRpcException;

/**
 * 抽象的Rpc处理模板
 *
 * @author lijunlin
 */
public abstract class AbstractRpcHandleTemplate {

    /**
     * 本地日志打印对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRpcHandleTemplate.class);

    /**
     * 处理业务
     *
     * @param param 请求参数
     * @param rpcHandler rpc处理器
     * @return
     */
    public <Param, Result> Result handle(Param param, RpcHandler<Param, Result> rpcHandler) {
        return handle(null, param, rpcHandler);
    }

    /**
     * 处理业务
     *
     * @param messagePrefix 消息前缀
     * @param param 请求参数
     * @param rpcHandler 业务处理器
     * @return
     */
    public <Param, Result> Result handle(String messagePrefix, Param param, RpcHandler<Param, Result> rpcHandler) {

        Result result = null;
        try {
            //业务执行
            result = rpcHandler.handle(param);

            //日志跟踪打印
            Logger logger = getLogger();
            if (logger != null) {
                if (logger.isInfoEnabled()) {
                    logger.info(wrapTraceMessage(messagePrefix, param, result));
                }
            }
        } catch (Throwable e) {

            //异常处理
            try {
                Logger logger = getLogger();
                if (logger != null) {
                    String messageString = wrapTraceMessage(messagePrefix, param, result);
                    if (e instanceof AppRpcException) {
                        logger.warn(messageString);
                    } else {
                        logger.error(messageString, e);
                    }
                }
            } catch (Throwable inner) {
                LOGGER.error("Fail to trace exception:" + inner.getMessage(), inner);
            }

            //如需要重新抛出异常则重新抛出异常
            if (canRethrowException()) {
                //rethrow the exception
                if (e instanceof RuntimeException) {
                    rethrowException(e.getMessage(), ((RuntimeException) e));
                } else {
                    throw new Error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /**
     * 包装跟踪消息
     *
     * @param messagePrefix 消息前缀
     * @param request       请求参数
     * @param response      响应对象
     * @return
     */
    protected String wrapTraceMessage(String messagePrefix, Object request, Object response) {
        return String.format("%sResponse->%s,Request->%s.", (messagePrefix == null ? "" : (messagePrefix + ":")),
                response, request);
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
     * @param errMsg 错误消息
     * @param runtimeException 运行时异常
     */
    protected void rethrowException(String errMsg, RuntimeException runtimeException) {
        throw new AppRpcException(errMsg, runtimeException);
    }

    /**
     * 获取日志对象
     *
     * @return
     */
    public abstract Logger getLogger();
}