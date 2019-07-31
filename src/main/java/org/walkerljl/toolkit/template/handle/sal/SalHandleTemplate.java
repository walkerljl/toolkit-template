package org.walkerljl.toolkit.template.handle.sal;

import org.walkerljl.toolkit.template.log.Logger;
import org.walkerljl.toolkit.template.log.LoggerFactory;

/**
 * SAL 处理模板
 *
 * @author xingxun
 */
public class SalHandleTemplate extends AbstractSalHandleTemplate {

    /** Digest logger*/
    private static final Logger DIGEST_LOGGER = LoggerFactory.getLogger("SAL-DIGEST");
    /** Detail logger*/
    private static final Logger DETAIL_LOGGER = LoggerFactory.getLogger("SAL-DETAIL");
    /** Error logger*/
    private static final Logger ERROR_LOGGER  = LoggerFactory.getLogger(SalHandleTemplate.class);

    /**
     * 私有构造函数
     */
    private SalHandleTemplate() {}

    /**
     * 获取实例
     *
     * @return
     */
    public static SalHandleTemplate getInstance() {
        return SalHandleTemplate.SalHandleTemplateHolder.instance;
    }

    /**
     * 单列容器
     */
    private static class SalHandleTemplateHolder {
        private static SalHandleTemplate instance = new SalHandleTemplate();
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