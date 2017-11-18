
package org.walkerljl.toolkit.template.handle.rpc;

import org.walkerljl.toolkit.standard.exception.ErrorCode;

/**
 * RpcErrorCode
 *
 * @author lijunlin
 */
public enum RpcErrorCode implements ErrorCode {

    /**
     * 未知异常
     */
    UNKOWN("10000", "未知异常"),
    ;

    /**
     * 构造函数
     *
     * @param code 编码
     * @param description 描述
     */
    RpcErrorCode(String code, String description) {
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