package org.walkerljl.toolkit.template.handle.service;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.walkerljl.toolkit.template.BaseUnitTest;

/**
 * ServiceErrorCodeTest
 *
 * @author xingxun
 */
public class ServiceErrorCodeTest extends BaseUnitTest {

    @Test
    public void test() {

        Assert.assertEquals(ServiceErrorCode.UNKNOWN.getCode(), "unknown");
        Assert.assertEquals(ServiceErrorCode.UNKNOWN.getDescription(), "网络繁忙，请稍后再试");

        Assert.assertEquals(ServiceErrorCode.INVALID_PARAM.getCode(), "invalid_param");
        Assert.assertEquals(ServiceErrorCode.INVALID_PARAM.getDescription(), "无效的参数");

        Assert.assertEquals(ServiceErrorCode.PERMISSION_DENIED.getCode(), "permission_denied");
        Assert.assertEquals(ServiceErrorCode.PERMISSION_DENIED.getDescription(), "无操作权限");

        Assert.assertEquals(ServiceErrorCode.INVALID_SIGNATURE.getCode(), "invalid_signature");
        Assert.assertEquals(ServiceErrorCode.INVALID_SIGNATURE.getDescription(), "无效的参数签名");

        Assert.assertEquals(ServiceErrorCode.REPETITIVE_OPERATION.getCode(), "repetitive_operation");
        Assert.assertEquals(ServiceErrorCode.REPETITIVE_OPERATION.getDescription(), "重复操作");

    }
}