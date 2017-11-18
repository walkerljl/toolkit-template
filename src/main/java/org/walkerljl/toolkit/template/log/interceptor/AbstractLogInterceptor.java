package org.walkerljl.toolkit.template.log.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;
import org.walkerljl.toolkit.standard.Result;
import org.walkerljl.toolkit.template.log.InvocationInfo;
import org.walkerljl.toolkit.template.log.LogConstants;
import org.walkerljl.toolkit.template.log.LoggerDetailUtil;
import org.walkerljl.toolkit.template.log.LoggerDigestUtil;
import org.walkerljl.toolkit.template.log.LoggerUtil;

/**
 *
 * @author xingxun
 */
public abstract class AbstractLogInterceptor implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLogInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        InvocationInfo invocationInfo = new InvocationInfo(invocation.getMethod().getDeclaringClass(),
                invocation.getMethod().getName(), invocation.getArguments());
        try {
            Object result = invocation.proceed();
            invocationInfo.setSuccess(result);
        } catch (Throwable e) {
            invocationInfo.setFailure(e);
            throw e;
        } finally {
           try {
               doLog(invocationInfo);
           } catch (Throwable e) {
               LOGGER.error(e);
           }
        }
        return invocationInfo.getResult();
    }

    private void doLog(InvocationInfo invocationInfo) {
        LoggerDigestUtil.logDigest(invocationInfo, getDigestLogger());
        LoggerDetailUtil.logDetail(invocationInfo, getDetailLogger());
    }

    protected abstract Logger getDigestLogger();

    protected abstract Logger getDetailLogger();

}