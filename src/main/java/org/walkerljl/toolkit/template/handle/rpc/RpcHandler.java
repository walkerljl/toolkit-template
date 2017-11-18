package org.walkerljl.toolkit.template.handle.rpc;

import org.walkerljl.toolkit.template.log.InvocationInfo;

/**
 * Rpc处理器
 *
 * @author lijunlin
 */
public interface RpcHandler<PARAM, RESULT> {

    /**
     * 业务处理
     *
     * @param param 参数
     * @return
     */
    InvocationInfo<RESULT> handle(PARAM param);
}