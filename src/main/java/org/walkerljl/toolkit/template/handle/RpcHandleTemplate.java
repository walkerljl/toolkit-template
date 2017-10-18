package org.walkerljl.toolkit.template.handle;

import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;

/**
 * Rpc处理模板
 * 
 * @author lijunlin
 */
public class RpcHandleTemplate extends AbstractRpcHandleTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcHandleTemplate.class);

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
        return RpcHandleTemplate.RpcHandleTemplateHolder.instance;
    }

    /**
     * 单列容器
     */
    private static class RpcHandleTemplateHolder {
        private static RpcHandleTemplate instance = new RpcHandleTemplate();
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }
}