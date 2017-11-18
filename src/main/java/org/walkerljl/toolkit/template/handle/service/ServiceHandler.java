package org.walkerljl.toolkit.template.handle.service;

/**
 * 业务处理器
 *
 * @author lijunlin
 */
public interface ServiceHandler<PARAM, RESULT> {

    /**
     * 参数校验
     *
     * @param param 参数
     * @return
     */
    boolean checkParams(PARAM param);

    /**
     * 业务处理
     *
     * @param param 参数
     * @return
     */
    RESULT handle(PARAM param);
}