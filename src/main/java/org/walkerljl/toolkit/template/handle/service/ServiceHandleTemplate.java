package org.walkerljl.toolkit.template.handle.service;

import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;

/**
 * 业务处理模板
 * 
 * @author lijunlin
 */
public class ServiceHandleTemplate extends AbstractServiceHandleTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceHandleTemplate.class);

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
        return null;
    }

    @Override
    protected Logger getDetailLogger() {
        return null;
    }

    @Override
    protected Logger getErrorLogger() {
        return null;
    }

    /**
     * 单列容器
     */
    private static class ServiceHandleTemplateHolder {
        private static ServiceHandleTemplate instance = new ServiceHandleTemplate();
    }
}