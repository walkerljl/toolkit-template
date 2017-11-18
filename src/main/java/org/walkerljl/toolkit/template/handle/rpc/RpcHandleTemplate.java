package org.walkerljl.toolkit.template.handle.rpc;

/**
 * Rpc处理模板
 * 
 * @author lijunlin
 */
public class RpcHandleTemplate extends AbstractRpcHandleTemplate {

    /**
     * 私有构造函数
     */
    private RpcHandleTemplate() {}

    /**
     * 获取实例
     *
     * @return
     */
    public static RpcHandleTemplate getInstance() {
        return RpcHandleTemplate.RpcHandleTemplateHolder.instance;
    }

    /**
     * 单列容器
     */
    private static class RpcHandleTemplateHolder {
        private static RpcHandleTemplate instance = new RpcHandleTemplate();
    }
}