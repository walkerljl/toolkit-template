package org.walkerljl.toolkit.template.service.integration;

import org.walkerljl.toolkit.standard.Result;
import org.walkerljl.toolkit.standard.exception.SwallowedAppException;

/**
 *
 * @author xingxun
 */
public class ResultUtil {

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