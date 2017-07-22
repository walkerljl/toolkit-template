package org.walkerljl.toolkit.template.handle;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.walkerljl.toolkit.standard.exception.AppServiceException;

/**
 *
 * @author junlin.ljl
 * @version $Id: ServiceHandleTemplateTest.java, v 0.1 2017年07月12日 下午4:37 junlin.ljl Exp $
 */
public class ServiceHandleTemplateTest {

    @Test
    public void test() {

        ServiceHandleTemplate<String, Integer> serviceHandleTemplate = ServiceHandleTemplate.getInstance();
        Assert.assertTrue(serviceHandleTemplate instanceof ServiceHandleTemplate);

        String actualErrorMsg = "errorMsg";
        RuntimeException actualException = new RuntimeException();

        boolean actualResult = false;
        try {
            serviceHandleTemplate.rethrowException(actualErrorMsg, actualException);
        } catch (AppServiceException e) {
            if (actualErrorMsg.equals(e.getMessage())) {
                actualResult = true;
            }
        }
        Assert.assertTrue(actualResult);

        Assert.assertNotNull(serviceHandleTemplate.getLogger());
    }
}