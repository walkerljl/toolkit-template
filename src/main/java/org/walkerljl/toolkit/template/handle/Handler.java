package org.walkerljl.toolkit.template.handle;

import org.walkerljl.toolkit.template.log.InvocationInfo;

/**
 * Handler
 *
 * @author lijunlin
 */
public interface Handler<PARAM, RESULT> {

    /**
     * 参数校验
     *
     * @param param 参数
     * @return
     */
    InvocationInfo<RESULT> checkParams(PARAM param);

    /**
     * 业务处理
     *
     * @param param 参数
     * @return
     */
    InvocationInfo<RESULT> handle(PARAM param);
}