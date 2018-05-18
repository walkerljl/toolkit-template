package org.walkerljl.toolkit.template.service.integration.impl;

import org.junit.Test;
import org.walkerljl.toolkit.template.BaseUnitTest;
import org.walkerljl.toolkit.template.service.facade.HelloFacade;
import org.walkerljl.toolkit.template.service.facade.impl.HelloFacadeImpl;

/**
 * HelloFacadeClientImplTest
 *
 * @author xingxun
 */
public class HelloFacadeClientImplTest extends BaseUnitTest {
    
    @Test
    public void testForNormal() {
        HelloFacade helloFacade = new HelloFacadeImpl();
        HelloFacadeClientImpl helloFacadeClient = new HelloFacadeClientImpl();
        helloFacadeClient.setHelloFacade(helloFacade);
        helloFacadeClient.say("world");
        helloFacadeClient.doSomething("127.0.0.1", "haha");
    }

    @Test
    public void testForException() {
        HelloFacade helloFacade = new HelloFacadeImpl();
        HelloFacadeClientImpl helloFacadeClient = new HelloFacadeClientImpl();
        helloFacadeClient.setHelloFacade(helloFacade);

        helloFacadeClient.say(null);
        helloFacadeClient.doSomething(null, null);
    }
}