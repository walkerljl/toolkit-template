package org.walkerljl.toolkit.template.service.integration.impl;

import org.walkerljl.toolkit.standard.Result;
import org.walkerljl.toolkit.template.handle.rpc.RpcAssertUtil;
import org.walkerljl.toolkit.template.handle.rpc.RpcHandleTemplate;
import org.walkerljl.toolkit.template.handle.rpc.RpcHandler;
import org.walkerljl.toolkit.template.log.InvocationInfo;
import org.walkerljl.toolkit.template.service.facade.HelloFacade;
import org.walkerljl.toolkit.template.service.integration.HelloFacadeClient;

/**
 * HelloFacadeClientImpl
 *
 * @author xingxun
 */
public class HelloFacadeClientImpl implements HelloFacadeClient {

    private HelloFacade helloFacade;

    @Override
    public String say(String content) {
        InvocationInfo<String, String> invocationInfo = new InvocationInfo<>(getClass(), "say", content);
        return RpcHandleTemplate.getInstance().handle(invocationInfo, new RpcHandler<String, String>() {
            @Override
            public boolean checkParams(String content) {
                RpcAssertUtil.assertParam(content != null, "content");
                return true;
            }

            @Override
            public String handle(String content) {
                Result<String> remotingResult = helloFacade.say(content);

                invocationInfo.markResult(true,
                        remotingResult,
                        remotingResult.getData());

                return remotingResult.getData();
            }
        });
    }

    @Override
    public String doSomething(String address, String something) {
        InvocationInfo<Object[], String> invocationInfo = new InvocationInfo<>(getClass(), "say", new Object[] {address, something});
        return RpcHandleTemplate.getInstance().handle(invocationInfo, new RpcHandler<Object[], String>() {
            @Override
            public boolean checkParams(Object[] params) {
                RpcAssertUtil.assertParam(params[0] != null, "address");
                RpcAssertUtil.assertParam(params[1] != null, "something");
                return true;
            }

            @Override
            public String handle(Object[] params) {
                Result<String> remotingResult = helloFacade.doSomething(String.valueOf(params[0]),
                        String.valueOf(params[1]));

                invocationInfo.markResult(true,
                        remotingResult,
                        remotingResult.getData());

                return remotingResult.getData();
            }
        });
    }

    /**
     * Setter method for property <tt>helloFacade</tt>.
     *
     * @param helloFacade  value to be assigned to property helloFacade
     */
    public void setHelloFacade(HelloFacade helloFacade) {
        this.helloFacade = helloFacade;
    }
}