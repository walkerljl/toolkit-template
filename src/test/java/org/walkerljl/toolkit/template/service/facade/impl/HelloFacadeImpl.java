package org.walkerljl.toolkit.template.service.facade.impl;

import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;
import org.walkerljl.toolkit.standard.Result;
import org.walkerljl.toolkit.standard.exception.util.ServiceAssertUtils;
import org.walkerljl.toolkit.template.handle.service.ServiceErrorCode;
import org.walkerljl.toolkit.template.handle.service.ServiceHandleTemplate;
import org.walkerljl.toolkit.template.handle.service.ServiceHandler;
import org.walkerljl.toolkit.template.service.facade.HelloFacade;

/**
 *
 * @author xingxun
 */
public class HelloFacadeImpl implements HelloFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloFacadeImpl.class);

    @Override
    public Result<String> say(String content) {

        Result<String> result = ServiceHandleTemplate.getInstance().handle(content,
                new ServiceHandler<String, String>() {
                    @Override
                    public boolean checkParams(String content) {
                        ServiceAssertUtils.assertTrue(content != null,
                                ServiceErrorCode.INVALID_PARAM);
                        return true;
                    }

                    @Override
                    public String handle(String content) {
                        String sayContent = String.format("Hello %s.", content);
                        LOGGER.info(sayContent);
                        return sayContent;
                    }
                });

        return result;
    }

    @Override
    public Result<String> doSomething(String address, String something) {
        Result<String> result = ServiceHandleTemplate.getInstance().handle(new Object[]{address, something},
                new ServiceHandler<Object[], String>() {
                    @Override
                    public boolean checkParams(Object[] params) {
                        return true;
                    }

                    @Override
                    public String handle(Object[] params) {
                        String doSomethingResult = String.format("Do % at %s.", params[1], params[0]);
                        LOGGER.info(doSomethingResult);
                        return doSomethingResult;
                    }
                });

        return result;    }
}