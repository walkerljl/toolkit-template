package org.walkerljl.toolkit.template.log;

import org.walkerljl.toolkit.standard.model.BaseEntity;

/**
 * 用户信息
 *
 * @author xingxun
 */
public class UserInfo extends BaseEntity {

    /** ID*/
    private String id;
    /** 姓名*/
    private String name;
    /** 访问IP*/
    private String ip;
    /** 用户访问终端*/
    private String userAgent;
    /** 访问取代哦*/
    private String channel;

}