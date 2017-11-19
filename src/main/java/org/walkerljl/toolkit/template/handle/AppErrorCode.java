
package org.walkerljl.toolkit.template.handle;

import org.walkerljl.toolkit.standard.exception.ErrorCode;

/**
 * RpcErrorCode
 *
 * @author xingxun
 */
public enum AppErrorCode implements ErrorCode {

    /**
     * 未知异常
     */
    UNKOWN("10000", "未知异常"),

    /**
     * 无效的参数
     */
    INVALID_PARAM("10001", "无效的参数"),

    ;

    /**
     * 构造函数
     *
     * @param code 编码
     * @param description 描述
     */
    AppErrorCode(String code, String description) {
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