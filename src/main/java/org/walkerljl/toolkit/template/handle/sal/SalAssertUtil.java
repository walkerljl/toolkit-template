package org.walkerljl.toolkit.template.handle.sal;

import org.walkerljl.toolkit.standard.exception.AppRpcException;
import org.walkerljl.toolkit.standard.exception.code.ErrorCode;

/**
 * SalAssertUtil
 *
 * @author xingxun
 */
public class SalAssertUtil {

    /**
     * 断言为真
     *
     * @param expression 表达式
     * @param message 消息
     */
    public static void assertTrue(boolean expression, String message) {
        if (!expression) {
            throw new AppSalException(message);
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
            throw new AppSalException(errorCode);
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
            throw new AppSalException(errorCode, message);
        }
    }

    /**
     * 断言参数
     *
     * @param expression 表达式
     * @param paramName 参数名称
     */
    public static void assertParam(boolean expression, String paramName) {
        assertParam(expression, SalErrorCode.INVALID_PARAM, paramName);
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
            throw new AppSalException(errorCode, message);
        }
    }
}