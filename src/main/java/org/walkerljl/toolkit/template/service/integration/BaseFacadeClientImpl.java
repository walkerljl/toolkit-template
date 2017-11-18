package org.walkerljl.toolkit.template.service.integration;

import org.walkerljl.toolkit.standard.Result;
import org.walkerljl.toolkit.standard.exception.AppRpcException;
import org.walkerljl.toolkit.template.handle.rpc.RpcErrorCode;

/**
 *
 * @author xingxun
 */
public abstract class BaseFacadeClientImpl {

    /**
     * 包装调用消息
     *
     * @param serviceName 服务名称
     * @param invokingParams 调用参数
     * @return
     */
    protected String wrapInvokingMsg(String serviceName, Object... invokingParams) {
        return ResultUtil.wrapInvokingMsg(serviceName, invokingParams);
    }

    /**
     * 获取服务名称
     *
     * @param className 类名
     * @param methodName 方法名称
     * @return
     */
    protected String getServiceName(String className, String methodName) {
        return String.format("%s.%s", className, methodName);
    }

    /**
     * 断言RPC结果<br/>
     *<b><font color="red">注意：</font></b>此方法仅用于判断远程调用的结果（内部固定抛出一个结果调用失败的异常）
     * @param expression 表达式，为false时抛出异常
     * @param message 消息
     */
    protected void assertRemotingResult(boolean expression, String message) {
        if (!expression) {
            throw new AppRpcException(RpcErrorCode.UNKOWN, message);
        }
    }

    /**
     * 获取结果对象
     *
     * <p>结果对象为null或调用不成功抛出{@link AppRpcException}<p/>
     *
     * @param remotingResult 远程结果对象
     * @param serviceName 服务名称
     * @param invokingParams 调用参数
     * @param <T>
     * @return
     */
    protected <T> T getRemotingResultData(Result<T> remotingResult, String serviceName,
                                 Object... invokingParams) {

        //断言结果对象
        assertRemotingResult(remotingResult, serviceName, invokingParams);

        return remotingResult.getData();
    }

    /**
     * 断言结果对象
     *
     * <p>结果对象为null或调用不成功抛出{@link AppRpcException}<p/>
     *
     * @param remotingResult 远程结果对象
     * @param serviceName 服务名称
     * @param invokingParams 调用参数
     * @param <T>
     */
    protected <T> void assertRemotingResult(Result<T> remotingResult, String serviceName,
                                       Object... invokingParams) {

        if (remotingResult == null || !remotingResult.isSuccess()) {
            //包装调用消息
            String invokingMsg = wrapInvokingMsg(serviceName, invokingParams);

            //吃掉预期的异常码
            ResultUtil.swallowExpectedErrorCodes(remotingResult, getExpectedErrorCodes(), invokingMsg);

            //抛出转译异常
            throw new AppRpcException(RpcErrorCode.UNKOWN, invokingMsg);
        }
    }

    /**
     * 获取预期的异常码
     *
     * @return
     */
    protected String[] getExpectedErrorCodes() {
        return null;
    }
}