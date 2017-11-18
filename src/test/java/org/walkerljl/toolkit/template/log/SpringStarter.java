package org.walkerljl.toolkit.template.log;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;
import org.walkerljl.toolkit.template.service.facade.HelloFacade;
import org.walkerljl.toolkit.template.service.integration.HelloFacadeClient;

/**
 *
 * @author xingxun
 */
public class SpringStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringStarter.class);

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        HelloFacadeClient helloFacadeClient = ctx.getBean("helloFacadeClient", HelloFacadeClient.class);
        try {
            helloFacadeClient.say("world");
        } catch (Throwable e) {
            LOGGER.error(e);
        }

        try {
            helloFacadeClient.doSomething("127.0.0.1", "haha");
        } catch (Throwable e) {
            LOGGER.error(e);
        }

    }
}