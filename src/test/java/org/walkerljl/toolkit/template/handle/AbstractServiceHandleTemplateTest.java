package org.walkerljl.toolkit.template.handle;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;
import org.walkerljl.toolkit.standard.Message;
import org.walkerljl.toolkit.standard.exception.AppException;

/**
 *
 * @author junlin.ljl
 * @version $Id: AbstractServiceHandleTemplateTest.java, v 0.1 2017年07月16日 下午4:41 junlin.ljl Exp $
 */
public class AbstractServiceHandleTemplateTest {

    private String checkParamFailedErrorMsg = "参数校验失败";

    @Test
    public void handleCaseForNullLogger() {

        AbstractServiceHandleTemplate serviceHandleTemplate = new AbstractServiceHandleTemplate() {
            @Override
            public Logger getLogger() {
                return null;
            }
        };

        String actualMessagePrefix = null;
        Integer param = null;
        ServiceHandler<String, Integer> serviceHandler = new ServiceHandler<String, Integer>() {
            @Override
            public boolean checkParams(String s) {
                return false;
            }

            @Override
            public Integer handle(String s) {
                return null;
            }
        };

        Message<Integer> expectedMessage = null;
        expectedMessage = serviceHandleTemplate.handle(actualMessagePrefix, param, serviceHandler);
        Assert.assertTrue(expectedMessage.isFailure());
        Assert.assertEquals(expectedMessage.getBody(), checkParamFailedErrorMsg);

        serviceHandler = new ServiceHandler<String, Integer>() {
            @Override
            public boolean checkParams(String s) {
                return true;
            }

            @Override
            public Integer handle(String s) {
                return 1;
            }
        };

        expectedMessage = serviceHandleTemplate.handle(actualMessagePrefix, param, serviceHandler);
        Assert.assertTrue(expectedMessage.isSuccess());
        Assert.assertEquals(expectedMessage.getData(), new Integer(1));
    }

    @Test
    public void handleCaseForValidLogger() {

        AbstractServiceHandleTemplate serviceHandleTemplate = new AbstractServiceHandleTemplate() {
            @Override
            public Logger getLogger() {
                return LoggerFactory.getLogger(AbstractServiceHandleTemplateTest.class);
            }
        };

        String actualMessagePrefix = null;
        Integer param = null;
        ServiceHandler<String, Integer> serviceHandler = new ServiceHandler<String, Integer>() {
            @Override
            public boolean checkParams(String s) {
                return false;
            }

            @Override
            public Integer handle(String s) {
                return null;
            }
        };

        Message<Integer> expectedMessage = null;
        expectedMessage = serviceHandleTemplate.handle(actualMessagePrefix, param, serviceHandler);
        Assert.assertTrue(expectedMessage.isFailure());
        Assert.assertEquals(expectedMessage.getBody(), checkParamFailedErrorMsg);

        serviceHandler = new ServiceHandler<String, Integer>() {
            @Override
            public boolean checkParams(String s) {
                return true;
            }

            @Override
            public Integer handle(String s) {
                return 1;
            }
        };

        expectedMessage = serviceHandleTemplate.handle(actualMessagePrefix, param, serviceHandler);
        Assert.assertTrue(expectedMessage.isSuccess());
        Assert.assertEquals(expectedMessage.getData(), new Integer(1));
    }

    @Test
    public void handleCaseForCanRethrowException() {

        AbstractServiceHandleTemplate serviceHandleTemplate = new AbstractServiceHandleTemplate() {
            @Override
            public Logger getLogger() {
                return LoggerFactory.getLogger(AbstractServiceHandleTemplateTest.class);
            }

            @Override
            protected boolean canRethrowException() {
                return true;
            }
        };

        String actualMessagePrefix = null;
        Integer param = null;
        ServiceHandler<String, Integer> serviceHandler = new ServiceHandler<String, Integer>() {
            @Override
            public boolean checkParams(String s) {
                return false;
            }

            @Override
            public Integer handle(String s) {
                return null;
            }
        };

        boolean expectedResult = false;
        Message<Integer> expectedMessage = null;
        try {
            expectedMessage = serviceHandleTemplate.handle(actualMessagePrefix, param, serviceHandler);
        } catch (AppException e) {
            if (checkParamFailedErrorMsg.equals(e.getMessage())) {
                expectedResult = true;
            }
        }
        Assert.assertTrue(expectedResult);
        expectedResult = false;
        Assert.assertNull(expectedMessage);

        serviceHandler = new ServiceHandler<String, Integer>() {
            @Override
            public boolean checkParams(String s) {
                return true;
            }

            @Override
            public Integer handle(String s) {
                return 1;
            }
        };
        expectedMessage = serviceHandleTemplate.handle(actualMessagePrefix, param, serviceHandler);
        Assert.assertTrue(expectedMessage.isSuccess());
        Assert.assertEquals(expectedMessage.getData(), new Integer(1));
        expectedMessage = null;

        serviceHandler = new ServiceHandler<String, Integer>() {
            @Override
            public boolean checkParams(String s) {
                return true;
            }

            @Override
            public Integer handle(String s) {
                throw new RuntimeException("xx");
            }
        };
        try {
            expectedMessage = serviceHandleTemplate.handle(actualMessagePrefix, param, serviceHandler);
        } catch (AppException e) {
            if ("xx".equals(e.getMessage())) {
                expectedResult = true;
            }
        }
        Assert.assertTrue(expectedResult);
        Assert.assertNull(expectedMessage);
    }
}

