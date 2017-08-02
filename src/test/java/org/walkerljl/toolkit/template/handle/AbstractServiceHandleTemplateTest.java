package org.walkerljl.toolkit.template.handle;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;
import org.walkerljl.toolkit.standard.Message;
import org.walkerljl.toolkit.standard.exception.AppException;
import org.walkerljl.toolkit.standard.exception.AppServiceException;

/**
 *
 * @author junlin.ljl
 * @version $Id: AbstractServiceHandleTemplateTest.java, v 0.1 2017年07月16日 下午4:41 junlin.ljl Exp $
 */
public class AbstractServiceHandleTemplateTest {

    private String checkParamFailedErrorMsg = ServiceErrorCode.INVALID_PARAM.getDescription();

    @Test
    public void testCaseForNullLogger() {

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
    public void testCaseForValidLogger() {

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
    public void testCaseForCanRethrowException() {

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

    @Test
    public void testCaseForErrorCode() {

        String param = "hello";
        Object result = "world";
        AbstractServiceHandleTemplate serviceHandleTemplate = new AbstractServiceHandleTemplate() {

            @Override
            public Logger getLogger() {
                return null;
            }
        };
        ServiceHandler<String, Object> serviceHandler = new ServiceHandler<String, Object>() {

            @Override
            public boolean checkParams(String s) {
                throw new AppServiceException(ServiceErrorCode.INVALID_PARAM);
            }

            @Override
            public Object handle(String s) {
                return result;
            }
        };
        String customizedErrorMsg = "XX参数未无效";
        Message<Object> message = serviceHandleTemplate.handle(param, serviceHandler);
        Assert.assertEquals(message.getCode(), ServiceErrorCode.INVALID_PARAM.getCode());
        Assert.assertEquals(message.getBody(), ServiceErrorCode.INVALID_PARAM.getDescription());

        serviceHandler = new ServiceHandler<String, Object>() {

            @Override
            public boolean checkParams(String s) {
                throw new AppServiceException(ServiceErrorCode.INVALID_PARAM, customizedErrorMsg);
            }

            @Override
            public Object handle(String s) {
                return result;
            }
        };
        message = serviceHandleTemplate.handle(param, serviceHandler);
        Assert.assertEquals(message.getCode(), ServiceErrorCode.INVALID_PARAM.getCode());
        Assert.assertEquals(message.getBody(), customizedErrorMsg);

        serviceHandler = new ServiceHandler<String, Object>() {

            @Override
            public boolean checkParams(String s) {
                throw new AppException(ServiceErrorCode.INVALID_PARAM);
            }

            @Override
            public Object handle(String s) {
                return result;
            }
        };
        message = serviceHandleTemplate.handle(param, serviceHandler);
        Assert.assertEquals(message.getCode(), ServiceErrorCode.UNKOWN.getCode());
        Assert.assertEquals(message.getBody(), ServiceErrorCode.UNKOWN.getDescription());
    }
}



