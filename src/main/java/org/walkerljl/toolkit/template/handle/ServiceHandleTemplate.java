package org.walkerljl.toolkit.template.handle;

import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;
import org.walkerljl.toolkit.standard.exception.AppServiceException;

/**
 * 业务处理模板
 * 
 * @author lijunlin
 */
public class ServiceHandleTemplate<Param, Result> extends AbstractServiceHandleTemplate<Param, Result> {

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

    /**
     * 单列容器
     */
    private static class ServiceHandleTemplateHolder {
        private static ServiceHandleTemplate instance = new ServiceHandleTemplate();
    }

    @Override
    protected void rethrowException(String errMsg, RuntimeException runtimeException) {
        throw new AppServiceException(errMsg, runtimeException);
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }
}