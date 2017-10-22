package org.walkerljl.toolkit.template.handle.rpc;

/**
 * Rpc处理器
 *
 * @author lijunlin
 */
public interface RpcHandler<Param, Result> {

    /**
     * 业务处理
     *
     * @param param 参数
     * @return
     */
    Result handle(Param param);
}