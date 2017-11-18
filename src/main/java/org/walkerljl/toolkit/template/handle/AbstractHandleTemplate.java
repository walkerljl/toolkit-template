package org.walkerljl.toolkit.template.handle;

import java.util.Arrays;

/**
 *
 * @author xingxun
 */
public abstract class AbstractHandleTemplate {

    protected String getServiceName(Class<?> serviceClass, String methodName) {
        return String.format(serviceClass.getSimpleName(), methodName);
    }

    /**
     * 包装调用消息
     *
     * @param serviceName 服务名称
     * @param invokingParams 调用参数
     * @return
     */
    public static String wrapInvocationMsg(String serviceName, Object... invokingParams) {
        return String.format("invoking:%s,params:%s", serviceName, Arrays.toString(invokingParams));
    }
}