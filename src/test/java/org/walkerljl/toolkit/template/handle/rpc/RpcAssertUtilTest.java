package org.walkerljl.toolkit.template.handle.rpc;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.walkerljl.toolkit.standard.exception.AppRpcException;
import org.walkerljl.toolkit.standard.exception.code.ErrorCode;

/**
 *
 * @author xingxun
 */
public class RpcAssertUtilTest {

    @Test
    public void assertTrue() {
        String actualErrorMsg = "actualErrorMsg";
        RpcAssertUtil.assertTrue(true, actualErrorMsg);
        boolean result = false;
        try {
            RpcAssertUtil.assertTrue(false, actualErrorMsg);
        } catch (AppRpcException e) {
            if (e.getCode() == null && actualErrorMsg.equals(e.getMessage())) {
                result = true;
            }
        }
        Assert.assertTrue(result);
        result = false;

        ErrorCode actualErrorCode = RpcErrorCode.INVALID_PARAM;
        try {
            RpcAssertUtil.assertTrue(false, actualErrorCode);
        } catch (AppRpcException e) {
            if (e.getCode() == actualErrorCode && actualErrorCode.getDescription().equals(e.getMessage())) {
                result = true;
            }
        }
        Assert.assertTrue(result);
        result = false;

        try {
            RpcAssertUtil.assertTrue(false, actualErrorCode, actualErrorMsg);
        } catch (AppRpcException e) {
            if (e.getCode() == actualErrorCode && actualErrorMsg.equals(e.getMessage())) {
                result = true;
            }
        }
        Assert.assertTrue(result);
    }

    @Test
    public void assertParam() {
        String propertName = "propertName";
        RpcAssertUtil.assertParam(true, "propertName");
        boolean result = false;
        try {
            RpcAssertUtil.assertParam(false, propertName);
        } catch (AppRpcException e) {
            String errorMsg = String.format("%s:%s", RpcErrorCode.INVALID_PARAM.getDescription(), propertName);
            if (e.getCode() == RpcErrorCode.INVALID_PARAM && (errorMsg).equals(e.getMessage())) {
                result = true;
            }
        }
        Assert.assertTrue(result);
        result = false;

        RpcAssertUtil.assertParam(true, RpcErrorCode.UNKOWN, "propertName");
        try {
            RpcAssertUtil.assertParam(false, RpcErrorCode.UNKOWN, propertName);
        } catch (AppRpcException e) {
            if (e.getCode() == RpcErrorCode.UNKOWN && (String.format("%s:%s", RpcErrorCode.UNKOWN.getDescription(), propertName)).equals(e.getMessage())) {
                result = true;
            }
        }
        Assert.assertTrue(result);
    }
}