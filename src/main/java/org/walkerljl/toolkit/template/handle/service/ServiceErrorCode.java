package org.walkerljl.toolkit.template.handle.service;

import org.walkerljl.toolkit.standard.exception.code.ErrorCode;

/**
 * ServiceErrorCode
 *
 * @author xingxun
 */
public enum ServiceErrorCode implements ErrorCode {

    /**
     * 未知异常
     */
    UNKNOWN("unknown", "未知异常"),

    /**
     * 无效的参数
     */
    INVALID_PARAM("param-invalid", "无效的参数"),;

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