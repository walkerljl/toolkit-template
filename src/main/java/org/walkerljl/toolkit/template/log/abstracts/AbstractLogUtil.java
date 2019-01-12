package org.walkerljl.toolkit.template.log.abstracts;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import org.walkerljl.toolkit.template.log.util.LogConstants;

/**
 * Abstract log util
 *
 * @author xingxun
 */
public abstract class AbstractLogUtil extends LogConstants {

    /**
     * fastjson 统一配置
     */
    private static final SerializerFeature[] FASTJSON_CONFIG = new SerializerFeature[] {
            //SerializerFeature.DisableCircularReferenceDetect,
            //SerializerFeature.WriteDateUseDateFormat,
            //SerializerFeature.WriteMapNullValue,
            //SerializerFeature.WriteNullStringAsEmpty};
            //SerializerFeature.PrettyFormat
    };

    /**
     * 将对象转成JSON字符串
     *
     * @param object
     * @return
     */
    public static String toJSONString(Object object) {
        if (object == null) {
            return null;
        }
        return JSON.toJSONString(object, FASTJSON_CONFIG);
    }

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
