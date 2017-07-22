package org.walkerljl.toolkit.template.handle;

import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;
import org.walkerljl.toolkit.standard.Message;
import org.walkerljl.toolkit.standard.exception.AppException;
import org.walkerljl.toolkit.standard.exception.ErrorCode;

/**
 * 抽象的业务处理模板
 *
 * @author lijunlin
 */
public abstract class AbstractServiceHandleTemplate<Param, Result> {

    /**
     * 本地日志打印对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServiceHandleTemplate.class);

    /**
     * 处理业务
     *
     * @param param 请求参数
     * @param serviceHandler 业务处理器
     * @return
     */
    public Message<Result> handle(Param param, ServiceHandler<Param, Result> serviceHandler) {
        return handle(null, param, serviceHandler);
    }

    /**
     * 处理业务
     *
     * @param messagePrefix 消息前缀
     * @param param 请求参数
     * @param serviceHandler 业务处理器
     * @return
     */
    public Message<Result> handle(String messagePrefix, Param param, ServiceHandler<Param, Result> serviceHandler) {

        Message<Result> message = null;
        try {
            //参数校验
            boolean isPass = serviceHandler.checkParams(param);
            if (!isPass) {
                rethrowException("参数校验失败", null);
            }

            //业务执行
            Result result = serviceHandler.handle(param);

            //构建Success消息
            message = Message.success(result);

            //日志跟踪打印
            Logger logger = getLogger();
            if (logger != null) {
                if (logger.isInfoEnabled()) {
                    logger.info(wrapTraceMessage(messagePrefix, param, message));
                }
            }
        } catch (Throwable e) {
            if (!canRethrowException()) {
                //构建Failure消息
                ErrorCode errorCode = (e instanceof AppException) ? ((AppException) e).getCode() : null;
                if (errorCode != null) {
                    message = Message.failure(errorCode.getCode());
                } else {
                    message = Message.failure(e.getMessage());
                }
            }

            //异常处理
            try {
                Logger logger = getLogger();
                if (logger != null) {
                    String messageString = wrapTraceMessage(messagePrefix, param, message);
                    if (e instanceof AppException) {
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
        return message;
    }

    /**
     * 包装跟踪消息
     *
     * @param messagePrefix 消息前缀
     * @param request       请求参数
     * @param response      响应对象
     * @return
     */
    private String wrapTraceMessage(String messagePrefix, Object request, Object response) {
        return String.format("%sResponse->%s,Request->%s.", (messagePrefix == null ? "" : (messagePrefix + ":")),
                response, request);
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
     * 重新抛出异常
     *
     * @param errMsg 错误消息
     * @param runtimeException 运行时异常
     */
    protected void rethrowException(String errMsg, RuntimeException runtimeException) {
        throw new AppException(errMsg, runtimeException);
    }

    /**
     * 获取日志对象
     *
     * @return
     */
    public abstract Logger getLogger();
}