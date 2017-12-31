package org.walkerljl.toolkit.template.handle;

/**
 * Handler
 *
 * @author xingxun
 */
public interface Handler<PARAM, RESULT> {

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