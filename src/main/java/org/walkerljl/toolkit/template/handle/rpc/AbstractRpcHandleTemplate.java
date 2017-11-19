package org.walkerljl.toolkit.template.handle.rpc;


import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;
import org.walkerljl.toolkit.template.handle.AbstractHandleTemplate;
import org.walkerljl.toolkit.template.handle.Handler;
import org.walkerljl.toolkit.template.log.InvocationInfo;
import org.walkerljl.toolkit.template.log.LoggerUtil;

/**
 * 抽象的Rpc处理模板
 *
 * @author lijunlin
 */
public abstract class AbstractRpcHandleTemplate extends AbstractHandleTemplate {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * 处理业务
     *
     * @param param 请求参数
     * @param handler 处理器
     * @return
     */
    public <PARAM, RESULT> RESULT handle(PARAM param, Handler<PARAM, RESULT> handler) {
        InvocationInfo<RESULT> invocationInfo = null;
        try {
            // 参数校验
            invocationInfo = handler.checkParams(param);
            assertInvocationInfo(invocationInfo);

            // 业务执行
            invocationInfo = handler.handle(param);
            assertInvocationInfo(invocationInfo);
        } catch (Throwable e) {
            // 设置异常对象
            invocationInfo.setThrowable(e);
            // 尝试重新抛出异常
            tryRethrowException(e);

        } finally {
            try {
                doLog(invocationInfo);
            } catch (Throwable e) {
                LoggerUtil.error(LOGGER, e);
            }
        }

        return invocationInfo.getResult();
    }
}