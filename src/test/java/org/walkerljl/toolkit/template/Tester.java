/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package org.walkerljl.toolkit.template;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

/**
 *
 * @author junlin.ljl
 * @version $Id: Tester.java, v 0.1 2017年07月29日 下午6:49 junlin.ljl Exp $
 */
public class Tester {

    @Test
    public void test() {
        List<String> list = new ArrayList<>(5);
        for (String ele : list) {
            System.out.println("1");
        }
    }
}