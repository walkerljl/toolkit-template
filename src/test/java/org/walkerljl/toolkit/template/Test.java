package org.walkerljl.toolkit.template;

import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSON;

import org.walkerljl.toolkit.standard.Result;

/**
 *
 * @author xingxun
 */
public class Test {

    @org.testng.annotations.Test
    public void test() {
        Result<TestResult> result = Result.success();
        TestResult testResult = new TestResult();
        testResult.setBizType("bizType");
        testResult.setUsers(Arrays.asList(new User[]{ new User("id1", "name1"), new User("id2", "name2")}));
        result.setData(testResult);

        System.out.println(JSON.toJSON(result));
    }
}

class TestResult {

    private List<User> users;
    private String bizType;

    /**
     * Getter method for property <tt>users</tt>.
     *
     * @return property value of users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Setter method for property <tt>users</tt>.
     *
     * @param users  value to be assigned to property users
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * Getter method for property <tt>bizType</tt>.
     *
     * @return property value of bizType
     */
    public String getBizType() {
        return bizType;
    }

    /**
     * Setter method for property <tt>bizType</tt>.
     *
     * @param bizType  value to be assigned to property bizType
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
}