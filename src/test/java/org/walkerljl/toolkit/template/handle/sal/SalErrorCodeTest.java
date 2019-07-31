package org.walkerljl.toolkit.template.handle.sal;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.walkerljl.toolkit.template.BaseUnitTest;

/**
 * RpcErrorCodeTest
 *
 * @author xingxun
 */
public class SalErrorCodeTest extends BaseUnitTest {

    @Test
    public void test() {

        Assert.assertEquals(SalErrorCode.UNKNOWN.getCode(), "unknown");
        Assert.assertEquals(SalErrorCode.UNKNOWN.getDescription(), "网络繁忙，请稍后再试");

        Assert.assertEquals(SalErrorCode.INVALID_PARAM.getCode(), "invalid_param");
        Assert.assertEquals(SalErrorCode.INVALID_PARAM.getDescription(), "无效的参数");
    }
}