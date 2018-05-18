package org.walkerljl.toolkit.template.handle.service;

import org.junit.Test;
import org.walkerljl.toolkit.template.service.facade.HelloFacade;
import org.walkerljl.toolkit.template.service.facade.impl.HelloFacadeImpl;

/**
 * @author xingxun
 */
public class ServiceHandleTemplateTest {

    @Test
    public void test() {

        ServiceHandleTemplate serviceHandleTemplate = ServiceHandleTemplate.getInstance();

        //HelloFacade helloFacade = new HelloFacadeImpl();
        //helloFacade.say("world");
        //helloFacade.doSomething("127.0.0.1", "haha");
        //
        //helloFacade.say(null);
        //helloFacade.doSomething(null, null);
    }
}