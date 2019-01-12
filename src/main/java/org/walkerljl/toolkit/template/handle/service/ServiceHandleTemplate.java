package org.walkerljl.toolkit.template.handle.service;

import org.walkerljl.toolkit.template.log.Logger;
import org.walkerljl.toolkit.template.log.LoggerFactory;

/**
 * 业务处理模板
 *
 * @author xingxun
 */
public class ServiceHandleTemplate extends AbstractServiceHandleTemplate {

    /** Digest logger*/
    private static final Logger DIGEST_LOGGER = LoggerFactory.getLogger("SERVICE-DIGEST");
    /** Detail logger*/
    private static final Logger DETAIL_LOGGER = LoggerFactory.getLogger("SERVICE-DETAIL");
    /** Logger*/
    private static final Logger LOGGER        = LoggerFactory.getLogger(ServiceHandleTemplate.class);

    /**
     * 私有构造函数
     */
    private ServiceHandleTemplate() {}

    /**
     * 获取实例
     *
     * @return
     */
    public static ServiceHandleTemplate getInstance() {
        return ServiceHandleTemplate.ServiceHandleTemplateHolder.instance;
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
        return LOGGER;
    }

    /**
     * 单列容器
     */
    private static class ServiceHandleTemplateHolder {
        private static ServiceHandleTemplate instance = new ServiceHandleTemplate();
    }
}