package org.walkerljl.toolkit.template.log;

/**
 *
 * @author xingxun
 */
public  class AbstractLogUtil extends LogConstants {

    protected static String getString(String value) {
        return value == null ? LOG_DEFAULT : value;
    }

    protected static String getString(boolean value) {
        return value ? YES : NO;
    }
}