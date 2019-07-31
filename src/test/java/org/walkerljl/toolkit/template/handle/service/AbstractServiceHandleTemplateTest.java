package org.walkerljl.toolkit.template.handle.service;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.walkerljl.toolkit.standard.Result;
import org.walkerljl.toolkit.standard.exception.AppServiceException;
import org.walkerljl.toolkit.template.handle.sal.SalErrorCode;
import org.walkerljl.toolkit.template.log.model.InvocationInfo;
import org.walkerljl.toolkit.template.log.Logger;
import org.walkerljl.toolkit.template.log.LoggerFactory;

/**
 * AbstractServiceHandleTemplateTest
 *
 * @author xingxun
 */
public class AbstractServiceHandleTemplateTest {

    private AbstractServiceHandleTemplate serviceHandleTemplate = new DefaultServiceHandleTemplate();
    private AbstractServiceHandleTemplate canRethrowExceptionServiceHandleTemplate = new CanRethrowExceptionServiceHandleTemplate();

    @Test
    public void handleForInvalidParam() {
        InvocationInfo<String, Object> invocationInfo = null;
        ServiceHandler<String, Object> serviceHandler = null;
        Result<Object> actual = serviceHandleTemplate.handle(invocationInfo, serviceHandler);
        Assert.assertFalse(actual.isSuccess());
        Assert.assertEquals(actual.getCode(), SalErrorCode.INVALID_PARAM.getCode());
        Assert.assertEquals(actual.getMessage(), SalErrorCode.INVALID_PARAM.getDescription());

        invocationInfo = new InvocationInfo<>(getClass(), "handle", "testParam");
        actual = serviceHandleTemplate.handle(invocationInfo, serviceHandler);
        Assert.assertFalse(actual.isSuccess());
        Assert.assertEquals(actual.getCode(), SalErrorCode.INVALID_PARAM.getCode());
        Assert.assertEquals(actual.getMessage(), SalErrorCode.INVALID_PARAM.getDescription());

        serviceHandler = new DefaultServiceHandler();
        actual = serviceHandleTemplate.handle(invocationInfo, serviceHandler);
        Assert.assertTrue(actual.isSuccess());
    }

    @Test
    public void handleForInvalidBizParam() {
        InvocationInfo<String, Object> invocationInfo =
                new InvocationInfo<>(getClass(), "handle", "testParam");
        ServiceHandler<String, Object> handler = new ServiceHandler<String, Object>() {
            @Override
            public boolean checkParams(String s) {
                return false;
            }

            @Override
            public Object handle(String s) {
                return null;
            }
        };

        Result<Object> actual = serviceHandleTemplate.handle(invocationInfo, handler);
        Assert.assertFalse(actual.isSuccess());
        Assert.assertEquals(actual.getCode(), SalErrorCode.INVALID_PARAM.getCode());
        Assert.assertEquals(actual.getMessage(), SalErrorCode.INVALID_PARAM.getDescription());
    }

    @Test
    public void handleForNormal() {
        InvocationInfo<String, Object> invocationInfo =
                new InvocationInfo<>(getClass(), "handle", "testParam");
        final Object expected = "expected";

        ServiceHandler<String, Object> serviceHandler = new ServiceHandler<String, Object>() {
            @Override
            public boolean checkParams(String s) {
                return true;
            }

            @Override
            public Object handle(String s) {
                return expected;
            }
        };
        Result<Object> actual = serviceHandleTemplate.handle(invocationInfo, serviceHandler);
        Assert.assertTrue(actual.isSuccess());
        Assert.assertEquals(actual.getCode(), Result.DEFAULT_SUCCESS_CODE);
        Assert.assertEquals(actual.getMessage(), Result.DEFAULT_SUCCESS_MESSAGE);
        Assert.assertEquals(actual.getData(), expected);
        Assert.assertEquals(actual, invocationInfo.getDirectResultData());
    }

    @Test
    public void handleForBizHandleError() {
        InvocationInfo<String, Object> invocationInfo =
                new InvocationInfo<>(getClass(), "handle", "testParam");

        ServiceHandler<String, Object> serviceHandler = new ServiceHandler<String, Object>() {
            @Override
            public boolean checkParams(String s) {
                return true;
            }

            @Override
            public Object handle(String s) {
                throw new RuntimeException("测试异常");
            }
        };
        Result<Object> actual = serviceHandleTemplate.handle(invocationInfo, serviceHandler);
        Assert.assertFalse(actual.isSuccess());
        Assert.assertEquals(actual.getCode(), ServiceErrorCode.UNKNOWN.getCode());
        Assert.assertEquals(actual.getMessage(), ServiceErrorCode.UNKNOWN.getDescription());

        serviceHandler = new ServiceHandler<String, Object>() {
            @Override
            public boolean checkParams(String s) {
                return true;
            }

            @Override
            public Object handle(String s) {
                throw new AppServiceException("测试异常");
            }
        };
        actual = serviceHandleTemplate.handle(invocationInfo, serviceHandler);
        Assert.assertFalse(actual.isSuccess());
        Assert.assertEquals(actual.getCode(), ServiceErrorCode.UNKNOWN.getCode());
        Assert.assertEquals(actual.getMessage(), ServiceErrorCode.UNKNOWN.getDescription());

        serviceHandler = new ServiceHandler<String, Object>() {
            @Override
            public boolean checkParams(String s) {
                return true;
            }

            @Override
            public Object handle(String s) {
                throw new AppServiceException(ServiceErrorCode.PERMISSION_DENIED);
            }
        };
        actual = serviceHandleTemplate.handle(invocationInfo, serviceHandler);
        Assert.assertFalse(actual.isSuccess());
        Assert.assertEquals(actual.getCode(), ServiceErrorCode.PERMISSION_DENIED.getCode());
        Assert.assertEquals(actual.getMessage(), ServiceErrorCode.PERMISSION_DENIED.getDescription());
    }

    @Test
    public void handleForInvalidParamAndCanRethrowException() {
        InvocationInfo<String, Object> invocationInfo = null;
        ServiceHandler<String, Object> serviceHandler = null;
        boolean actual = false;
        try {
            canRethrowExceptionServiceHandleTemplate.handle(invocationInfo, serviceHandler);
        } catch (AppServiceException e) {
            actual = (e.getCode() == ServiceErrorCode.INVALID_PARAM
                    && e.getMessage().equals(String.format("%s:%s",
                    ServiceErrorCode.INVALID_PARAM.getDescription(), "invocationInfo")));
        }
        Assert.assertTrue(actual);
        actual = false;

        invocationInfo = new InvocationInfo<>(getClass(), "handle", "testParam");
        try {
            canRethrowExceptionServiceHandleTemplate.handle(invocationInfo, serviceHandler);
        } catch (AppServiceException e) {
            String errorMsg = e.getMessage();
            actual = (e.getCode() == ServiceErrorCode.INVALID_PARAM
                    && errorMsg.equals(String.format("%s:%s", SalErrorCode.INVALID_PARAM.getDescription(), "handler")));
        }
        Assert.assertTrue(actual);

        invocationInfo = new InvocationInfo<>(getClass(), "handle", "testParam");
        serviceHandler = new DefaultServiceHandler();
        canRethrowExceptionServiceHandleTemplate.handle(invocationInfo, serviceHandler);
    }

    @Test
    public void handleForInvalidBizParamAndCanRethrowException() {
        InvocationInfo<String, Object> invocationInfo =
                new InvocationInfo<>(getClass(), "handle", "testParam");
        ServiceHandler<String, Object> serviceHandler = new ServiceHandler<String, Object>() {
            @Override
            public boolean checkParams(String s) {
                return false;
            }

            @Override
            public Object handle(String s) {
                return null;
            }
        };

        boolean actual = false;
        try {
            canRethrowExceptionServiceHandleTemplate.handle(invocationInfo, serviceHandler);
        } catch (AppServiceException e) {
            String errorMsg = e.getMessage();
            actual = (e.getCode() == ServiceErrorCode.INVALID_PARAM
                    && errorMsg.equals(ServiceErrorCode.INVALID_PARAM.getDescription()));
        }
        Assert.assertTrue(actual);
    }

    @Test
    public void handleForNormalAndCanRethrowException() {
        InvocationInfo<String, Object> invocationInfo =
                new InvocationInfo<>(getClass(), "handle", "testParam");
        final Object expected = "expected";
        ServiceHandler<String, Object> serviceHandler = new ServiceHandler<String, Object>() {
            @Override
            public boolean checkParams(String s) {
                return true;
            }

            @Override
            public Object handle(String s) {
                return expected;
            }
        };
        Result<Object> actual = canRethrowExceptionServiceHandleTemplate.handle(invocationInfo, serviceHandler);
        Assert.assertTrue(actual.isSuccess());
        Assert.assertEquals(actual.getData(), expected);
    }

    @Test
    public void handleForBizHandleErrorAndCanRethrowException() {
        InvocationInfo<String, Object> invocationInfo =
                new InvocationInfo<>(getClass(), "handle", "testParam");
        ServiceHandler<String, Object> serviceHandler = new ServiceHandler<String, Object>() {
            @Override
            public boolean checkParams(String s) {
                return true;
            }

            @Override
            public Object handle(String s) {
                throw new RuntimeException("测试异常");
            }
        };
        boolean actual = false;
        try {
            canRethrowExceptionServiceHandleTemplate.handle(invocationInfo, serviceHandler);
        } catch (AppServiceException e) {
            String errorMsg = e.getMessage();
            actual = errorMsg.equals("测试异常");
        }
        Assert.assertTrue(actual);
    }
}

class DefaultServiceHandleTemplate extends AbstractServiceHandleTemplate {

    /** Digest logger*/
    private static final Logger DIGEST_LOGGER = LoggerFactory.getLogger("SERVICE-DIGEST");
    /** Detail logger*/
    private static final Logger DETAIL_LOGGER = LoggerFactory.getLogger("SERVICE-DETAIL");
    /** Error logger*/
    private static final Logger ERROR_LOGGER  = LoggerFactory.getLogger(ServiceHandleTemplate.class);

    @Override
    protected Logger getDigestLogger() {
        return DIGEST_LOGGER;
    }

    @Override
    protected Logger getDetailLogger() {
        return DETAIL_LOGGER;
    }

    @Override
    protected Logger getErrorLogger() {
        return ERROR_LOGGER;
    }
}

class CanRethrowExceptionServiceHandleTemplate extends AbstractServiceHandleTemplate {

    /** Digest logger*/
    private static final Logger DIGEST_LOGGER = LoggerFactory.getLogger("SERVICE-DIGEST");
    /** Detail logger*/
    private static final Logger DETAIL_LOGGER = LoggerFactory.getLogger("SERVICE-DETAIL");
    /** Error logger*/
    private static final Logger ERROR_LOGGER  = LoggerFactory.getLogger(ServiceHandleTemplate.class);

    @Override
    protected Logger getDigestLogger() {
        return DIGEST_LOGGER;
    }

    @Override
    protected Logger getDetailLogger() {
        return DETAIL_LOGGER;
    }

    @Override
    protected Logger getErrorLogger() {
        return ERROR_LOGGER;
    }

    @Override
    protected boolean canRethrowException() {
        return true;
    }
}

class DefaultServiceHandler implements ServiceHandler<String, Object> {

    @Override
    public boolean checkParams(String s) {
        return true;
    }

    @Override
    public Object handle(String s) {

        return null;
    }
}




