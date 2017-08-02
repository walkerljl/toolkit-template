
package org.walkerljl.toolkit.template.handle;

import org.walkerljl.toolkit.standard.exception.ErrorCode;

/**
 *
 * @author junlin.ljl
 * @version $Id: ServiceErrorCode.java, v 0.1 2017年07月18日 下午1:51 junlin.ljl Exp $
 */
public enum ServiceErrorCode implements ErrorCode {

    /**
     * 未知异常
     */
    UNKOWN("10001", "未知异常"),

    /**
     * 无效的参数
     */
    INVALID_PARAM("10002", "无效的参数"),
    ;

    /**
     * 构造函数
     *
     * @param code 编码
     * @param description 描述
     */
    ServiceErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /** 编码*/
    private String code;
    /** 描述*/
    private String description;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}