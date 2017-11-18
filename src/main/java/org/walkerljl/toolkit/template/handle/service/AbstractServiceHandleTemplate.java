package org.walkerljl.toolkit.template.handle.service;

import com.alibaba.fastjson.JSON;

import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;
import org.walkerljl.toolkit.standard.exception.AppException;
import org.walkerljl.toolkit.standard.exception.AppServiceException;
import org.walkerljl.toolkit.standard.exception.ErrorCode;

/**
 * 抽象的业务处理模板
 *
 * @author lijunlin
 */
public abstract class AbstractServiceHandleTemplate {

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
    public <PARAM, RESULT> org.walkerljl.toolkit.standard.Result<RESULT> handle(PARAM param, ServiceHandler<PARAM, RESULT> serviceHandler) {
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
    public <PARAM, RESULT> org.walkerljl.toolkit.standard.Result<RESULT> handle(String messagePrefix, PARAM param, ServiceHandler<PARAM, RESULT> serviceHandler) {

        org.walkerljl.toolkit.standard.Result<RESULT> result = null;
        try {
            //参数校验
            boolean isPass = serviceHandler.checkParams(param);
            if (!isPass) {
                rethrowException(ServiceErrorCode.INVALID_PARAM, null);
            }

            //业务执行
            RESULT handlerResult = serviceHandler.handle(param);

            //构建Success消息
            result = org.walkerljl.toolkit.standard.Result.success(handlerResult);

            //日志跟踪打印
            Logger logger = getLogger();
            if (logger != null) {
                if (logger.isInfoEnabled()) {
                    logger.info(wrapTraceMessage(messagePrefix, param, result));
                }
            }
        } catch (Throwable e) {
            if (!canRethrowException()) {
                //构建Failure消息
                ErrorCode errorCode = (e instanceof AppServiceException) ? ((AppServiceException) e).getCode() : null;
                if (errorCode != null) {
                    result = org.walkerljl.toolkit.standard.Result.failure(errorCode.getCode(), e.getMessage());
                } else {
                    result = org.walkerljl.toolkit.standard.Result.failure(ServiceErrorCode.UNKOWN.getCode(), ServiceErrorCode.UNKOWN.getDescription());
                    result.setRemark(e.getMessage());
                }
            }

            //异常处理
            try {
                Logger logger = getLogger();
                if (logger != null) {
                    String messageString = wrapTraceMessage(messagePrefix, param, result);
                    if (e instanceof AppException && ((AppException)e).getCode() instanceof ErrorCode) {
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
                    rethrowException(null, ((RuntimeException) e));
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
                JSON.toJSONString(response), JSON.toJSONString(request));
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
     * @param exception 异常
     */
    protected void rethrowException(ErrorCode errorCode, RuntimeException exception) {
        if (errorCode == null) {
            throw new AppServiceException(exception.getMessage(), exception);
        } else {
            throw new AppServiceException(errorCode, exception);
        }
    }

    /**
     * 获取日志对象
     *
     * @return
     */
    public abstract Logger getLogger();
}