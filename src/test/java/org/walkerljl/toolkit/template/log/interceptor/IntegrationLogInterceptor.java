package org.walkerljl.toolkit.template.log.interceptor;

import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;

/**
 *
 * @author xingxun
 */
public class IntegrationLogInterceptor extends AbstractLogInterceptor {

    private static final Logger DIGEST_LOGGER = LoggerFactory.getLogger("INTEGRATION-DIGEST");
    private static final Logger DETAIL_LOGGER = LoggerFactory.getLogger("INTEGRATION-DETAIL");

    @Override
    protected Logger getDigestLogger() {
        return DIGEST_LOGGER;
    }

    @Override
    protected Logger getDetailLogger() {
        return DETAIL_LOGGER;
    }
}