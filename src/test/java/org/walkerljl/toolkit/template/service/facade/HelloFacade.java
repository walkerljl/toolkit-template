package org.walkerljl.toolkit.template.service.facade;

import org.walkerljl.toolkit.standard.Result;

/**
 *
 * @author xingxun
 */
public interface HelloFacade {

    Result<String> say(String content);

    Result<String> doSomething(String address, String something);

}