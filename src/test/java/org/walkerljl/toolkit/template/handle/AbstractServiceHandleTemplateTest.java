package org.walkerljl.toolkit.template.handle;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;
import org.walkerljl.toolkit.standard.Result;
import org.walkerljl.toolkit.standard.exception.AppException;
import org.walkerljl.toolkit.standard.exception.AppServiceException;

/**
 * @author lijunlin
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
        String param = null;
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

        Result<Integer> expectedResult = null;
        expectedResult = serviceHandleTemplate.handle(actualMessagePrefix, param, serviceHandler);
        Assert.assertTrue(expectedResult.isFailure());
        Assert.assertEquals(expectedResult.getMessage(), checkParamFailedErrorMsg);

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

        expectedResult = serviceHandleTemplate.handle(actualMessagePrefix, param, serviceHandler);
        Assert.assertTrue(expectedResult.isSuccess());
        Assert.assertEquals(expectedResult.getData(), new Integer(1));
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
        String param = null;
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

        Result<Integer> expectedResult = null;
        expectedResult = serviceHandleTemplate.handle(actualMessagePrefix, param, serviceHandler);
        Assert.assertTrue(expectedResult.isFailure());
        Assert.assertEquals(expectedResult.getMessage(), checkParamFailedErrorMsg);

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

        expectedResult = serviceHandleTemplate.handle(actualMessagePrefix, param, serviceHandler);
        Assert.assertTrue(expectedResult.isSuccess());
        Assert.assertEquals(expectedResult.getData(), new Integer(1));
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
        String param = null;
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

        boolean expectedIsSuccess = false;
        Result<Integer> expectedResult = null;
        try {
            expectedResult = serviceHandleTemplate.handle(actualMessagePrefix, param, serviceHandler);
        } catch (AppException e) {
            if (checkParamFailedErrorMsg.equals(e.getMessage())) {
                expectedIsSuccess = true;
            }
        }
        Assert.assertTrue(expectedIsSuccess);
        expectedIsSuccess = false;
        Assert.assertNull(expectedResult);

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
        expectedResult = serviceHandleTemplate.handle(actualMessagePrefix, param, serviceHandler);
        Assert.assertTrue(expectedResult.isSuccess());
        Assert.assertEquals(expectedResult.getData(), new Integer(1));
        expectedResult = null;

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
            expectedResult = serviceHandleTemplate.handle(actualMessagePrefix, param, serviceHandler);
        } catch (AppException e) {
            if ("xx".equals(e.getMessage())) {
                expectedIsSuccess = true;
            }
        }
        Assert.assertTrue(expectedIsSuccess);
        Assert.assertNull(expectedResult);
    }

    @Test
    public void testCaseForErrorCode() {

        String handlerParam = "hello";
        Object handlerResult = "world";
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
                return handlerResult;
            }
        };
        String customizedErrorMsg = "XX参数未无效";
        Result<Object> result = serviceHandleTemplate.handle(handlerParam, serviceHandler);
        Assert.assertEquals(result.getCode(), ServiceErrorCode.INVALID_PARAM.getCode());
        Assert.assertEquals(result.getMessage(), ServiceErrorCode.INVALID_PARAM.getDescription());

        serviceHandler = new ServiceHandler<String, Object>() {

            @Override
            public boolean checkParams(String s) {
                throw new AppServiceException(ServiceErrorCode.INVALID_PARAM, customizedErrorMsg);
            }

            @Override
            public Object handle(String s) {
                return handlerResult;
            }
        };
        result = serviceHandleTemplate.handle(handlerParam, serviceHandler);
        Assert.assertEquals(result.getCode(), ServiceErrorCode.INVALID_PARAM.getCode());
        Assert.assertEquals(result.getMessage(), customizedErrorMsg);

        serviceHandler = new ServiceHandler<String, Object>() {

            @Override
            public boolean checkParams(String s) {
                throw new AppException(ServiceErrorCode.INVALID_PARAM);
            }

            @Override
            public Object handle(String s) {
                return handlerResult;
            }
        };
        result = serviceHandleTemplate.handle(handlerParam, serviceHandler);
        Assert.assertEquals(result.getCode(), ServiceErrorCode.UNKOWN.getCode());
        Assert.assertEquals(result.getMessage(), ServiceErrorCode.UNKOWN.getDescription());
    }
}



