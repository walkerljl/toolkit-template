package org.walkerljl.toolkit.template.handle.rpc;

import org.junit.Test;
import org.walkerljl.toolkit.template.BaseUnitTest;
import org.walkerljl.toolkit.template.service.facade.HelloFacade;
import org.walkerljl.toolkit.template.service.facade.impl.HelloFacadeImpl;
import org.walkerljl.toolkit.template.service.integration.impl.HelloFacadeClientImpl;

/**
 *
 * @author xingxun
 */
public class RpcHandleTemplateTest extends BaseUnitTest {

    @Test
    public void testForLogger() {

        RpcHandleTemplate.getInstance().getDigestLogger();
        RpcHandleTemplate.getInstance().getDetailLogger();
        RpcHandleTemplate.getInstance().getErrorLogger();
    }
}