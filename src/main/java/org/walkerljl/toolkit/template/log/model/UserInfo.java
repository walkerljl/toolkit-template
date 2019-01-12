package org.walkerljl.toolkit.template.log.model;

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
    /** 访问渠道*/
    private String channel;

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id  value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name  value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>ip</tt>.
     *
     * @return property value of ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * Setter method for property <tt>ip</tt>.
     *
     * @param ip  value to be assigned to property ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Getter method for property <tt>userAgent</tt>.
     *
     * @return property value of userAgent
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * Setter method for property <tt>userAgent</tt>.
     *
     * @param userAgent  value to be assigned to property userAgent
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * Getter method for property <tt>channel</tt>.
     *
     * @return property value of channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Setter method for property <tt>channel</tt>.
     *
     * @param channel  value to be assigned to property channel
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }
}