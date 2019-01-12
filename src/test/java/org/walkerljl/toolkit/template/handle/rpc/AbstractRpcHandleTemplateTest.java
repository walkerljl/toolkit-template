package org.walkerljl.toolkit.template.handle.rpc;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.walkerljl.toolkit.standard.exception.AppRpcException;
import org.walkerljl.toolkit.template.log.model.InvocationInfo;
import org.walkerljl.toolkit.template.log.Logger;
import org.walkerljl.toolkit.template.log.LoggerFactory;

/**
 * AbstractRpcHandleTemplateTest
 *
 * @author xingxun
 */
public class AbstractRpcHandleTemplateTest {

    private AbstractRpcHandleTemplate rpcHandleTemplate = new DefaultRpcHandleTemplate();

    @Test
    public void handleForInvalidParam() {
        InvocationInfo<String, Object> invocationInfo = null;
        RpcHandler<String, Object> handler = null;
        boolean actual = false;
        try {
            rpcHandleTemplate.handle(invocationInfo, handler);
        } catch (AppRpcException e) {
            actual = (e.getCode() == RpcErrorCode.INVALID_PARAM
                    && e.getMessage().equals(String.format("%s:%s",
                    RpcErrorCode.INVALID_PARAM.getDescription(), "invocationInfo")));
        }
        Assert.assertTrue(actual);
        actual = false;

        invocationInfo = new InvocationInfo<>(getClass(), "handle", "testParam");
        try {
            rpcHandleTemplate.handle(invocationInfo, handler);
        } catch (AppRpcException e) {
            String errorMsg = e.getMessage();
            actual = (e.getCode() == RpcErrorCode.INVALID_PARAM
                    && errorMsg.equals(String.format("%s:%s", RpcErrorCode.INVALID_PARAM.getDescription(), "handler")));
        }
        Assert.assertTrue(actual);

        invocationInfo = new InvocationInfo<>(getClass(), "handle", "testParam");
        handler = new DefaultRpcHandler(invocationInfo);
        rpcHandleTemplate.handle(invocationInfo, handler);
    }

    @Test
    public void handleForInvalidBizParam() {
        InvocationInfo<String, Object> invocationInfo =
                new InvocationInfo<>(getClass(), "handle", "testParam");
        RpcHandler<String, Object> rpcHandler = new RpcHandler<String, Object>() {
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
            rpcHandleTemplate.handle(invocationInfo, rpcHandler);
        } catch (AppRpcException e) {
            String errorMsg = e.getMessage();
            actual = (e.getCode() == RpcErrorCode.INVALID_PARAM
                    && errorMsg.equals(RpcErrorCode.INVALID_PARAM.getDescription()));
        }
        Assert.assertTrue(actual);
    }

    @Test
    public void handleForNormal() {
        InvocationInfo<String, Object> invocationInfo =
                new InvocationInfo<>(getClass(), "handle", "testParam");
        final Object expected = "expected";
        RpcHandler<String, Object> rpcHandler = new RpcHandler<String, Object>() {
            @Override
            public boolean checkParams(String s) {
                return true;
            }

            @Override
            public Object handle(String s) {

                invocationInfo.markResult(true,
                        expected,
                        expected);

                return expected;
            }
        };
        Object actual = rpcHandleTemplate.handle(invocationInfo, rpcHandler);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void handleForBizHandleError() {
        InvocationInfo<String, Object> invocationInfo =
                new InvocationInfo<>(getClass(), "handle", "testParam");
        final Object expected = "expected";
        RpcHandler<String, Object> rpcHandler = new RpcHandler<String, Object>() {
            @Override
            public boolean checkParams(String s) {
                return true;
            }

            @Override
            public Object handle(String s) {

                invocationInfo.markResult(false,
                        expected,
                        expected);

                return expected;
            }
        };
        boolean actual = false;
        try {
            rpcHandleTemplate.handle(invocationInfo, rpcHandler);
        } catch (AppRpcException e) {
            String errorMsg = e.getMessage();
            actual = errorMsg.equals(invocationInfo.getTraceInfo());
        }
        Assert.assertTrue(actual);
        actual = false;

        rpcHandler = new RpcHandler<String, Object>() {
            @Override
            public boolean checkParams(String s) {
                return true;
            }

            @Override
            public Object handle(String s) {

                throw new RuntimeException("测试异常");
            }
        };
        try {
            rpcHandleTemplate.handle(invocationInfo, rpcHandler);
        } catch (AppRpcException e) {
            String errorMsg = e.getMessage();
            actual = errorMsg.equals("测试异常");
        }
        Assert.assertTrue(actual);
    }
}

class DefaultRpcHandleTemplate extends AbstractRpcHandleTemplate {

    /** Digest logger*/
    private static final Logger DIGEST_LOGGER = LoggerFactory.getLogger("INTEGRATION-DIGEST");
    /** Detail logger*/
    private static final Logger DETAIL_LOGGER = LoggerFactory.getLogger("INTEGRATION-DETAIL");
    /** Error logger*/
    private static final Logger ERROR_LOGGER  = LoggerFactory.getLogger(RpcHandleTemplate.class);

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

class DefaultRpcHandler implements RpcHandler<String, Object> {

    private InvocationInfo<String, Object> invocationInfo;

    public DefaultRpcHandler(InvocationInfo<String, Object> invocationInfo) {
        this.invocationInfo = invocationInfo;
    }

    @Override
    public boolean checkParams(String s) {
        return true;
    }

    @Override
    public Object handle(String s) {

        invocationInfo.markResult(true,
                null,
                null);

        return null;
    }
}