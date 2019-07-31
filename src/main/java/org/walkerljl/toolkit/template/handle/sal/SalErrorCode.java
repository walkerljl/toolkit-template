package org.walkerljl.toolkit.template.handle.sal;

import org.walkerljl.toolkit.standard.enums.IEnum;
import org.walkerljl.toolkit.standard.exception.code.ErrorCode;

/**
 * SalErrorCode
 *
 * @author xingxun
 */
public enum SalErrorCode implements ErrorCode {

    /**
     * 网络繁忙，请稍后再试。
     */
    UNKNOWN("unknown", "网络繁忙，请稍后再试"),

    /**
     * 无效的参数
     */
    INVALID_PARAM("invalid_param", "无效的参数"),

    ;

    /**
     * 构造函数
     *
     * @param code 编码
     * @param description 描述
     */
    SalErrorCode(String code, String description) {
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

    @Override
    public IEnum getEnumObject(String code) {
        for (IEnum ele : values()) {
            if (ele.getCode().equalsIgnoreCase(code)) {
                return ele;
            }
        }
        return null;
    }
}