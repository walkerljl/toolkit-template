package org.walkerljl.toolkit.template.log;

import java.util.Arrays;

import org.walkerljl.toolkit.logging.Logger;

/**
 *
 * @author xingxun
 */
public class LoggerDetailUtil extends AbstractLogUtil {

    public static void logDetail(InvocationInfo invocationInfo, Logger logger) {
        StringBuilder sb = new StringBuilder();
        sb.append(LOG_PARAM_PREFIX);
        sb.append(getString(invocationInfo.getServiceName()));
        sb.append(LOG_SEPERATOR);
        sb.append(getString(invocationInfo.isSuccess()));
        sb.append(LOG_PARAM_SUFFIX);

        sb.append(LOG_PARAM_PREFIX).append(Arrays.toString(invocationInfo.getParams()));

        sb.append(LOG_PARAM_SUFFIX).append(LOG_PARAM_PREFIX);
        sb.append(null == invocationInfo.getResult() ? LOG_DEFAULT : invocationInfo.getResult()).append(LOG_PARAM_SUFFIX);
        //异常
        sb.append(LOG_PARAM_PREFIX).append(THROWABLE);
        String message = LOG_DEFAULT;
        if(invocationInfo.getThrowable() != null){
            message = invocationInfo.getThrowable().getMessage();
        }
        sb.append(message).append(LOG_PARAM_SUFFIX);

        LoggerUtil.info(logger, sb.toString());
    }
}