package org.walkerljl.toolkit.template.log.interceptor;

import org.walkerljl.toolkit.template.log.Logger;
import org.walkerljl.toolkit.template.log.LoggerFactory;

/**
 *
 * @author xingxun
 */
public class ServiceLogInterceptor extends AbstractLogInterceptor {

    private static final Logger DIGEST_LOGGER = LoggerFactory.getLogger("SERVICE-DIGEST");
    private static final Logger DETAIL_LOGGER = LoggerFactory.getLogger("SERVICE-DETAIL");

    @Override
    protected Logger getDigestLogger() {
        return DIGEST_LOGGER;
    }

    @Override
    protected Logger getDetailLogger() {
        return DETAIL_LOGGER;
    }
}