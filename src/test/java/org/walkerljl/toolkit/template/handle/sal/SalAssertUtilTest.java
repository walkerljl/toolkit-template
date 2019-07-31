package org.walkerljl.toolkit.template.handle.sal;

import org.testng.Assert;
import org.testng.annotations.Test;


import org.walkerljl.toolkit.standard.exception.code.ErrorCode;

/**
 * SalAssertUtilTest
 *
 * @author xingxun
 */
public class SalAssertUtilTest {

    @Test
    public void assertTrue() {
        String actualErrorMsg = "actualErrorMsg";
        SalAssertUtil.assertTrue(true, actualErrorMsg);
        boolean result = false;
        try {
            SalAssertUtil.assertTrue(false, actualErrorMsg);
        } catch (AppSalException e) {
            if (e.getCode() == null && actualErrorMsg.equals(e.getMessage())) {
                result = true;
            }
        }
        Assert.assertTrue(result);
        result = false;

        ErrorCode actualErrorCode = SalErrorCode.INVALID_PARAM;
        try {
            SalAssertUtil.assertTrue(false, actualErrorCode);
        } catch (AppSalException e) {
            if (e.getCode() == actualErrorCode && actualErrorCode.getDescription().equals(e.getMessage())) {
                result = true;
            }
        }
        Assert.assertTrue(result);
        result = false;

        try {
            SalAssertUtil.assertTrue(false, actualErrorCode, actualErrorMsg);
        } catch (AppSalException e) {
            if (e.getCode() == actualErrorCode && actualErrorMsg.equals(e.getMessage())) {
                result = true;
            }
        }
        Assert.assertTrue(result);
    }

    @Test
    public void assertParam() {
        String paramName = "paramName";
        SalAssertUtil.assertParam(true, "paramName");
        boolean result = false;
        try {
            SalAssertUtil.assertParam(false, paramName);
        } catch (AppSalException e) {
            String errorMsg = String.format("%s:%s", SalErrorCode.INVALID_PARAM.getDescription(), paramName);
            if (e.getCode() == SalErrorCode.INVALID_PARAM && (errorMsg).equals(e.getMessage())) {
                result = true;
            }
        }
        Assert.assertTrue(result);
        result = false;

        SalAssertUtil.assertParam(true, SalErrorCode.UNKNOWN, "paramName");
        try {
            SalAssertUtil.assertParam(false, SalErrorCode.UNKNOWN, paramName);
        } catch (AppSalException e) {
            if (e.getCode() == SalErrorCode.UNKNOWN && (String.format("%s:%s", SalErrorCode.UNKNOWN.getDescription(), paramName)).equals(e.getMessage())) {
                result = true;
            }
        }
        Assert.assertTrue(result);
    }
}