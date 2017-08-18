package org.walkerljl.toolkit.template;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;

/**
 * @author lijunlin
 */
public class BaseUnitTest {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @BeforeMethod
    public void before() {
        LOGGER.debug("Initialized some resoruces.");
    }

    @AfterMethod
    public void after() {
        LOGGER.debug("Released some resources.");
    }
}