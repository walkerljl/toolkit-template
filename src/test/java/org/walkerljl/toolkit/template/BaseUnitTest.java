package org.walkerljl.toolkit.template;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.walkerljl.toolkit.template.log.Logger;
import org.walkerljl.toolkit.template.log.LoggerFactory;
import org.walkerljl.toolkit.template.log.defaults.DefaultLoggerRepository;

/**
 * @author xingxun
 */
public class BaseUnitTest {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    static {
        LoggerFactory.bindLoggerRepository(new DefaultLoggerRepository());
    }

    @BeforeMethod
    public void before() {
        LOGGER.debug("Initialized some resoruces.");
    }

    @AfterMethod
    public void after() {
        LOGGER.debug("Released some resources.");
    }
}