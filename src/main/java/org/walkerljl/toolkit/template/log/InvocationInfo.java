package org.walkerljl.toolkit.template.log;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 调用信息
 *
 * @author lijunlin
 * @Date 2016/11/25
 */
public class InvocationInfo<RESULT> implements Serializable {

    public static final int STATE_SUCCESS = 1;
    public static final int STATE_FAILURE = 0;

    private String appName;
    private Class<?> objectClass;
    private String methodName;
    private Object[] params;
    private RESULT result;
    private Object directResult;
    private String description;
    private int state = STATE_SUCCESS;
    private Throwable throwable;
    private long beginTime = System.currentTimeMillis();
    private long endTime = 0;
    private String traceId;
    private Map<String, Object> parametersMap = new HashMap<String, Object>(0);

    public InvocationInfo(Class<?> objectClass, String methodName, Object[] params, Object directResult, RESULT result, boolean isSuccess) {
        this.objectClass = objectClass;
        this.methodName = methodName;
        this.params = params;
        this.directResult = directResult;
        this.result = result;
        this.state = (isSuccess ? STATE_SUCCESS : STATE_FAILURE);
    }

    public InvocationInfo(Class<?> objectClass, String methodName, Object[] params) {
        this(objectClass, methodName, params, null, null, true);
    }

    public void setFailure() {
        this.setFailure(null);
    }

    public void setFailure(Throwable throwable) {
        this.state = STATE_FAILURE;
        this.throwable = throwable;
        this.endTime = System.currentTimeMillis();
    }

    public void setSuccess(RESULT result) {
        this.state = STATE_SUCCESS;
        this.endTime = System.currentTimeMillis();
        this.result = result;
    }

    public String getServiceName() {
        return String.format("%s.%s", objectClass.getSimpleName(), methodName);
    }

    public String getTraceInfo() {
        return String.format("serviceName=%s,params=%s,result=%s.", getServiceName(), Arrays.toString(params), result);
    }

    public boolean isSuccess() {
        return STATE_SUCCESS == state;
    }

    public long getCostTime() {
        return getEndTime() - getBeginTime();
    }

    public void addParameter(String key, Object value) {
        parametersMap.put(key, value);
    }

    /**
     * Getter method for property <tt>appName</tt>.
     *
     * @return property value of appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     * Setter method for property <tt>appName</tt>.
     *
     * @param appName  value to be assigned to property appName
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * Getter method for property <tt>objectClass</tt>.
     *
     * @return property value of objectClass
     */
    public Class<?> getObjectClass() {
        return objectClass;
    }

    /**
     * Setter method for property <tt>objectClass</tt>.
     *
     * @param objectClass  value to be assigned to property objectClass
     */
    public void setObjectClass(Class<?> objectClass) {
        this.objectClass = objectClass;
    }

    /**
     * Getter method for property <tt>methodName</tt>.
     *
     * @return property value of methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Setter method for property <tt>methodName</tt>.
     *
     * @param methodName  value to be assigned to property methodName
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Getter method for property <tt>params</tt>.
     *
     * @return property value of params
     */
    public Object[] getParams() {
        return params;
    }

    /**
     * Setter method for property <tt>params</tt>.
     *
     * @param params  value to be assigned to property params
     */
    public void setParams(Object[] params) {
        this.params = params;
    }

    /**
     * Getter method for property <tt>result</tt>.
     *
     * @return property value of result
     */
    public RESULT getResult() {
        return result;
    }

    /**
     * Setter method for property <tt>result</tt>.
     *
     * @param result  value to be assigned to property result
     */
    public void setResult(RESULT result) {
        this.result = result;
    }

    /**
     * Getter method for property <tt>directResult</tt>.
     *
     * @return property value of directResult
     */
    public Object getDirectResult() {
        return directResult;
    }

    /**
     * Setter method for property <tt>directResult</tt>.
     *
     * @param directResult  value to be assigned to property directResult
     */
    public void setDirectResult(Object directResult) {
        this.directResult = directResult;
    }

    /**
     * Getter method for property <tt>description</tt>.
     *
     * @return property value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for property <tt>description</tt>.
     *
     * @param description  value to be assigned to property description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method for property <tt>state</tt>.
     *
     * @return property value of state
     */
    public int getState() {
        return state;
    }

    /**
     * Setter method for property <tt>state</tt>.
     *
     * @param state  value to be assigned to property state
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Getter method for property <tt>throwable</tt>.
     *
     * @return property value of throwable
     */
    public Throwable getThrowable() {
        return throwable;
    }

    /**
     * Setter method for property <tt>throwable</tt>.
     *
     * @param throwable  value to be assigned to property throwable
     */
    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    /**
     * Getter method for property <tt>beginTime</tt>.
     *
     * @return property value of beginTime
     */
    public long getBeginTime() {
        return beginTime;
    }

    /**
     * Setter method for property <tt>beginTime</tt>.
     *
     * @param beginTime  value to be assigned to property beginTime
     */
    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * Getter method for property <tt>endTime</tt>.
     *
     * @return property value of endTime
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * Setter method for property <tt>endTime</tt>.
     *
     * @param endTime  value to be assigned to property endTime
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * Getter method for property <tt>traceId</tt>.
     *
     * @return property value of traceId
     */
    public String getTraceId() {
        return traceId;
    }

    /**
     * Setter method for property <tt>traceId</tt>.
     *
     * @param traceId  value to be assigned to property traceId
     */
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    /**
     * Getter method for property <tt>parametersMap</tt>.
     *
     * @return property value of parametersMap
     */
    public Map<String, Object> getParametersMap() {
        return parametersMap;
    }

    /**
     * Setter method for property <tt>parametersMap</tt>.
     *
     * @param parametersMap  value to be assigned to property parametersMap
     */
    public void setParametersMap(Map<String, Object> parametersMap) {
        this.parametersMap = parametersMap;
    }
}
