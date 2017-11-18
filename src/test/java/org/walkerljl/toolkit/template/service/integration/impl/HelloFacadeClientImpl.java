package org.walkerljl.toolkit.template.service.integration.impl;

import org.walkerljl.toolkit.standard.Result;
import org.walkerljl.toolkit.template.handle.rpc.RpcHandleTemplate;
import org.walkerljl.toolkit.template.handle.rpc.RpcHandler;
import org.walkerljl.toolkit.template.log.InvocationInfo;
import org.walkerljl.toolkit.template.service.facade.HelloFacade;
import org.walkerljl.toolkit.template.service.integration.BaseFacadeClientImpl;
import org.walkerljl.toolkit.template.service.integration.HelloFacadeClient;

/**
 *
 * @author xingxun
 */
public class HelloFacadeClientImpl extends BaseFacadeClientImpl implements HelloFacadeClient {

    private HelloFacade helloFacade;

    @Override
    public String say(String content) {

        return RpcHandleTemplate.getInstance().handle(content, new RpcHandler<String, String>() {

            @Override
            public InvocationInfo<String> handle(String content) {
                Result<String> remotingResult = helloFacade.say(content);
                InvocationInfo<String> invocationInfo = new InvocationInfo<>(HelloFacade.class,
                        "say",
                        new Object[]{content},
                        remotingResult,
                        remotingResult.getData(),
                        !remotingResult.isSuccess());
                return invocationInfo;
            }
        });
    }

    @Override
    public String doSomething(String address, String something) {
        return RpcHandleTemplate.getInstance().handle(new Object[]{address, something}, new RpcHandler<Object[], String>() {

            @Override
            public InvocationInfo<String> handle(Object[] params) {
                Result<String> remotingResult = helloFacade.doSomething((String)params[0], (String)params[1]);
                InvocationInfo<String> invocationInfo = new InvocationInfo<>(HelloFacade.class,
                        "doSomething",
                        params,
                        remotingResult,
                        remotingResult.getData(),
                        remotingResult.isSuccess());
                return invocationInfo;
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