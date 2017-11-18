package org.walkerljl.toolkit.template.service.integration;

/**
 *
 * @author xingxun
 */
public interface HelloFacadeClient {

    String say(String content);

    String doSomething(String address, String something);
}