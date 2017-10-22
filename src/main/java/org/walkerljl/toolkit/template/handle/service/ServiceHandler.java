package org.walkerljl.toolkit.template.handle.service;

/**
 * 业务处理器
 *
 * @author lijunlin
 */
public interface ServiceHandler<Param, Result> {

    /**
     * 参数校验
     *
     * @param param 参数
     * @return
     */
    boolean checkParams(Param param);

    /**
     * 业务处理
     *
     * @param param 参数
     * @return
     */
    Result handle(Param param);
}