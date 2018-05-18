package org.walkerljl.toolkit.template.handle.rpc;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.walkerljl.toolkit.template.BaseUnitTest;
import org.walkerljl.toolkit.template.handle.service.ServiceErrorCode;

/**
 * RpcErrorCodeTest
 *
 * @author xingxun
 */
public class RpcErrorCodeTest extends BaseUnitTest {

    @Test
    public void test() {

        Assert.assertEquals(RpcErrorCode.UNKNOWN.getCode(), "unknown");
        Assert.assertEquals(RpcErrorCode.UNKNOWN.getDescription(), "网络繁忙，请稍后再试");

        Assert.assertEquals(RpcErrorCode.INVALID_PARAM.getCode(), "invalid_param");
        Assert.assertEquals(RpcErrorCode.INVALID_PARAM.getDescription(), "无效的参数");
    }
}