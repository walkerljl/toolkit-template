package org.walkerljl.toolkit.template.log;

/**
 * Abstract log util
 *
 * @author xingxun
 */
public abstract class AbstractLogUtil extends LogConstants {

    /**
     * 获取String值
     *
     * @param value String类型的值
     * @return
     */
    protected static String getString(String value) {
        return value == null ? LOG_DEFAULT : value;
    }

    /**
     * 获取String值
     *
     * @param value boolean类型的值
     * @return
     */
    protected static String getString(boolean value) {
        return value ? YES : NO;
    }
}
