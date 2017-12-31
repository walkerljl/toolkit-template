package org.walkerljl.toolkit.template.handle.rpc;

import org.junit.Test;
import org.walkerljl.toolkit.template.service.facade.HelloFacade;
import org.walkerljl.toolkit.template.service.facade.impl.HelloFacadeImpl;
import org.walkerljl.toolkit.template.service.integration.impl.HelloFacadeClientImpl;

/**
 *
 * @author xingxun
 */
public class RpcHandleTemplateTest {

    @Test
    public void test() {
        HelloFacade helloFacade = new HelloFacadeImpl();
        HelloFacadeClientImpl helloFacadeClient = new HelloFacadeClientImpl();
        helloFacadeClient.setHelloFacade(helloFacade);
        helloFacadeClient.say("world");
        helloFacadeClient.doSomething("127.0.0.1", "haha");

        helloFacadeClient.say(null);
        helloFacadeClient.doSomething(null, null);
    }
}