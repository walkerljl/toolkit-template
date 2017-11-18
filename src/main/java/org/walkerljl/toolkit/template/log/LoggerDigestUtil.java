package org.walkerljl.toolkit.template.log;

import org.walkerljl.toolkit.logging.Logger;

/**
 *
 * @author xingxun
 */
public class LoggerDigestUtil extends AbstractLogUtil {

    public static void logDigest(InvocationInfo invocationInfo, Logger logger) {
        StringBuilder sb = new StringBuilder();
        sb.append(LOG_PARAM_PREFIX);
        sb.append(getString(invocationInfo.getServiceName()));
        sb.append(LOG_SEPERATOR);
        sb.append(getString(invocationInfo.isSuccess()));
        sb.append(LOG_SEPERATOR);
        sb.append(getString(null));
        sb.append(LOG_SEPERATOR);
        sb.append(invocationInfo.getCostTime());
        sb.append(TIME_UNIT);
        sb.append(LOG_PARAM_SUFFIX);

        sb.append(LOG_PARAM_PREFIX);
        sb.append(getString(invocationInfo.getAppName()));
        sb.append(LOG_PARAM_SUFFIX);

        LoggerUtil.info(logger, sb.toString());
    }
}