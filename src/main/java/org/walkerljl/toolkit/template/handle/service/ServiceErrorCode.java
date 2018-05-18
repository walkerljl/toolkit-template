package org.walkerljl.toolkit.template.handle.service;

import org.walkerljl.toolkit.standard.exception.code.ErrorCode;

/**
 * ServiceErrorCode
 *
 * @author xingxun
 */
public enum ServiceErrorCode implements ErrorCode {

    /**
     * 网络繁忙，请稍后再试。
     */
    UNKNOWN("unknown", "网络繁忙，请稍后再试"),

    /**
     * 无效的参数
     */
    INVALID_PARAM("invalid_param", "无效的参数"),

    /**
     * 无效的参数签名
     */
    INVALID_SIGNATURE("invalid_signature", "无效的参数签名"),

    /**
     * 无操作权限
     */
    PERMISSION_DENIED("permission_denied", "无操作权限"),

    /**
     * 重复操作
     */
    REPETITIVE_OPERATION("repetitive_operation", "重复操作"),;

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