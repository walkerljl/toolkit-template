package org.walkerljl.toolkit.template.service.facade.impl;

import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;
import org.walkerljl.toolkit.standard.Result;
import org.walkerljl.toolkit.template.handle.service.ServiceAssertUtil;
import org.walkerljl.toolkit.template.handle.service.ServiceHandleTemplate;
import org.walkerljl.toolkit.template.handle.service.ServiceHandler;
import org.walkerljl.toolkit.template.log.model.InvocationInfo;
import org.walkerljl.toolkit.template.service.facade.HelloFacade;

/**
 *
 * @author xingxun
 */
public class HelloFacadeImpl implements HelloFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloFacadeImpl.class);

    @Override
    public Result<String> say(String content) {
        InvocationInfo<String, String> invocationInfo = new InvocationInfo<>(getClass(), "say", content);
        return ServiceHandleTemplate.getInstance().handle(invocationInfo,
                new ServiceHandler<String, String>() {
                    @Override
                    public boolean checkParams(String content) {
                        ServiceAssertUtil.assertParam(content != null, "content");
                        return true;
                    }

                    @Override
                    public String handle(String content) {
                        String sayResult = String.format("Hello %s.", content);
                        LOGGER.info(sayResult);
                        return sayResult;
                    }
                });

    }

    @Override
    public Result<String> doSomething(String address, String something) {
        InvocationInfo<Object[], String> invocationInfo = new InvocationInfo<>(getClass(), "doSomething",
                new Object[] {address, something});
        return ServiceHandleTemplate.getInstance().handle(invocationInfo, new ServiceHandler<Object[], String>() {
                    @Override
                    public boolean checkParams(Object[] params) {
                        ServiceAssertUtil.assertParam(params[0] != null, "address");
                        ServiceAssertUtil.assertParam(params[1] != null, "something");
                        return true;
                    }

                    @Override
                    public String handle(Object[] params) {
                        String doSomethingResult = String.format("Do %s at %s.", params[1], params[0]);
                        LOGGER.info(doSomethingResult);
                        return doSomethingResult;
                    }
                }
        );
    }
}