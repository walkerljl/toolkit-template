package org.walkerljl.toolkit.template.service.integration;

import java.util.Arrays;

import org.walkerljl.toolkit.standard.Result;
import org.walkerljl.toolkit.standard.exception.SwallowedAppException;

/**
 *
 * @author xingxun
 */
public class ResultUtil {


    public static String buildInvoactionInfo(Class<?> serviceClass, String methodName, Object[] params, Object result) {
        return String.format("serviceName=%s.%s,params=%s,result=%s.", serviceClass, methodName, Arrays.toString(params), result);
    }

    /**
     * 包装调用消息
     *
     * @param serviceName 服务名称
     * @param invokingParams 调用参数
     * @return
     */
    public static String wrapInvokingMsg(String serviceName, Object... invokingParams) {
        return String.format("invoking:%s,params:%s", serviceName, Arrays.toString(invokingParams));
    }

    /**
     * 吃掉预期的异常码
     *
     * @param remotingResult
     * @param expectedErrorCodes
     * @param errorMsg
     * @param <T>
     */
    public static <T> void swallowExpectedErrorCodes(Result<T> remotingResult, String[] expectedErrorCodes, String errorMsg) {
        if (remotingResult == null || remotingResult.isSuccess()) {
            return;
        }
        String actualErrorCode = remotingResult.getCode();
        if (actualErrorCode == null || "".equals(actualErrorCode)) {
            return;
        }
        for (String expectedErrorCode : expectedErrorCodes) {
            if (expectedErrorCode.equalsIgnoreCase(actualErrorCode)) {
                throw new SwallowedAppException(errorMsg);
            }
        }
    }

}