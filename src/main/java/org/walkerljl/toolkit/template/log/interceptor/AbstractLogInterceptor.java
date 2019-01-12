package org.walkerljl.toolkit.template.log.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.walkerljl.toolkit.template.log.model.InvocationInfo;
import org.walkerljl.toolkit.template.log.Logger;
import org.walkerljl.toolkit.template.log.util.LoggerDetailUtil;
import org.walkerljl.toolkit.template.log.util.LoggerDigestUtil;
import org.walkerljl.toolkit.template.log.LoggerFactory;

/**
 * 抽象的日志过滤器
 *
 * @author xingxun
 */
public abstract class AbstractLogInterceptor implements MethodInterceptor {

    /** 日志对象*/
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLogInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        InvocationInfo invocationInfo = new InvocationInfo(invocation.getMethod().getDeclaringClass(),
                invocation.getMethod().getName(), invocation.getArguments());
        try {
            Object result = invocation.proceed();
            //标注成功的调用
            invocationInfo.markSuccess(result);
        } catch (Throwable e) {
            //标注失败的调用
            invocationInfo.markFailure(e);
            throw e;
        } finally {
            //记录日志
            try {
                doLog(invocationInfo);
            } catch (Throwable e) {
                LOGGER.error(e);
            }
        }
        return invocationInfo.getResultData();
    }

    /**
     * 记录日志
     *
     * @param invocationInfo 调用信息
     */
    private void doLog(InvocationInfo invocationInfo) {
        //记录日志摘要
        LoggerDigestUtil.logDigest(invocationInfo, getDigestLogger());
        //记录日志详情
        LoggerDetailUtil.logDetail(invocationInfo, getDetailLogger());
    }

    /**
     * 获取记录摘要日志对象
     *
     * @return
     */
    protected abstract Logger getDigestLogger();

    /**
     * 获取记录详情日志对象
     *
     * @return
     */
    protected abstract Logger getDetailLogger();

}