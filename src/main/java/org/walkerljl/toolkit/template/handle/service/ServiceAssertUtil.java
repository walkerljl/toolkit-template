package org.walkerljl.toolkit.template.handle.service;

import org.walkerljl.toolkit.standard.exception.AppServiceException;
import org.walkerljl.toolkit.standard.exception.code.ErrorCode;

/**
 * ServiceAssertUtil
 *
 * @author xingxun
 */
public class ServiceAssertUtil {

    /**
     * 断言为真
     *
     * @param expression 表达式
     * @param message 消息
     */
    public static void assertTrue(boolean expression, String message) {
        if (!expression) {
            throw new AppServiceException(message);
        }
    }

    /**
     * 断言为真
     *
     * @param expression 表达式
     * @param errorCode 错误码
     */
    public static void assertTrue(boolean expression, ErrorCode errorCode) {
        if (!expression) {
            throw new AppServiceException(errorCode);
        }
    }

    /**
     * 断言为真
     *
     * @param expression 表达式
     * @param errorCode 错误码
     * @param message 消息
     */
    public static void assertTrue(boolean expression, ErrorCode errorCode, String message) {
        if (!expression) {
            throw new AppServiceException(errorCode, message);
        }
    }

    /**
     * 断言参数
     *
     * @param expression 表达式
     * @param paramName 参数名称
     */
    public static void assertParam(boolean expression, String paramName) {
        assertParam(expression, ServiceErrorCode.INVALID_PARAM, paramName);
    }

    /**
     * 断言参数
     *
     * @param expression 表达式
     * @param errorCode 错误码
     * @param paramName 参数名称
     */
    public static void assertParam(boolean expression, ErrorCode errorCode, String paramName) {
        if (!expression) {
            String message = String.format("%s:%s", errorCode.getDescription(), paramName);
            throw new AppServiceException(errorCode, message);
        }
    }
}