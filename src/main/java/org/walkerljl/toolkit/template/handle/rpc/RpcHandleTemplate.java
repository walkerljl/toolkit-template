package org.walkerljl.toolkit.template.handle.rpc;

import org.walkerljl.toolkit.template.log.Logger;
import org.walkerljl.toolkit.template.log.LoggerFactory;

/**
 * Rpc处理模板
 *
 * @author xingxun
 */
public class RpcHandleTemplate extends AbstractRpcHandleTemplate {

    /** Digest logger*/
    private static final Logger DIGEST_LOGGER = LoggerFactory.getLogger("INTEGRATION-DIGEST");
    /** Detail logger*/
    private static final Logger DETAIL_LOGGER = LoggerFactory.getLogger("INTEGRATION-DETAIL");
    /** Error logger*/
    private static final Logger ERROR_LOGGER  = LoggerFactory.getLogger(RpcHandleTemplate.class);

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