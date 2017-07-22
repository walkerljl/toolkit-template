package org.walkerljl.toolkit.template.handle;

import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;
import org.walkerljl.toolkit.standard.exception.AppRpcException;

/**
 * RPC业务处理模板
 *
 * @author lijunlin
 */
public class RpcHandleTemplate<Param, Result> extends AbstractServiceHandleTemplate<Param, Result> {

    private static Logger LOGGER = LoggerFactory.getLogger(RpcHandleTemplate.class);

    /**
     * 私有构造函数
     */
    private RpcHandleTemplate() {}

    /**
     * 获取实例
     *
     * @return
     */
    public static RpcHandleTemplate getInstance() {
        return RpcHandleTemplateHolder.instance;
    }

    /**
     * 单列容器
     */
    private static class RpcHandleTemplateHolder {
        private static RpcHandleTemplate instance = new RpcHandleTemplate();
    }

    @Override
    protected void rethrowException(String errMsg, RuntimeException runtimeException) {
        throw new AppRpcException(errMsg, runtimeException);
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }
}