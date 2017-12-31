package org.walkerljl.toolkit.template.handle.service;

import org.walkerljl.toolkit.standard.exception.AppServiceException;
import org.walkerljl.toolkit.standard.exception.code.ErrorCode;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author xingxun
 */
public class ServiceAssertUtilTest {

    @Test
    public void assertTrue() {
        String actualErrorMsg = "actualErrorMsg";
        ServiceAssertUtil.assertTrue(true, actualErrorMsg);
        boolean result = false;
        try {
            ServiceAssertUtil.assertTrue(false, actualErrorMsg);
        } catch (AppServiceException e) {
            if (e.getCode() == null && actualErrorMsg.equals(e.getMessage())) {
                result = true;
            }
        }
        Assert.assertTrue(result);
        result = false;

        ErrorCode actualErrorCode = ServiceErrorCode.INVALID_PARAM;
        try {
            ServiceAssertUtil.assertTrue(false, actualErrorCode);
        } catch (AppServiceException e) {
            if (e.getCode() == actualErrorCode && actualErrorCode.getDescription().equals(e.getMessage())) {
                result = true;
            }
        }
        Assert.assertTrue(result);
        result = false;

        try {
            ServiceAssertUtil.assertTrue(false, actualErrorCode, actualErrorMsg);
        } catch (AppServiceException e) {
            if (e.getCode() == actualErrorCode && actualErrorMsg.equals(e.getMessage())) {
                result = true;
            }
        }
        Assert.assertTrue(result);
    }

    @Test
    public void assertParam() {
        String propertName = "propertName";
        ServiceAssertUtil.assertParam(true, "propertName");
        boolean result = false;
        try {
            ServiceAssertUtil.assertParam(false, propertName);
        } catch (AppServiceException e) {
            String errorMsg = String.format("%s:%s", ServiceErrorCode.INVALID_PARAM.getDescription(), propertName);
            if (e.getCode() == ServiceErrorCode.INVALID_PARAM && (errorMsg).equals(e.getMessage())) {
                result = true;
            }
        }
        Assert.assertTrue(result);
        result = false;

        ServiceAssertUtil.assertParam(true, ServiceErrorCode.UNKOWN, "propertName");
        try {
            ServiceAssertUtil.assertParam(false, ServiceErrorCode.UNKOWN, propertName);
        } catch (AppServiceException e) {
            if (e.getCode() == ServiceErrorCode.UNKOWN && (String.format("%s:%s", ServiceErrorCode.UNKOWN.getDescription(), propertName)).equals(e.getMessage())) {
                result = true;
            }
        }
        Assert.assertTrue(result);
    }
}