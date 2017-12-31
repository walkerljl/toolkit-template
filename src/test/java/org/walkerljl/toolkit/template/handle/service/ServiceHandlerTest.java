//package org.walkerljl.toolkit.template.handle.service;
//
//import org.testng.annotations.Test;
//import org.walkerljl.toolkit.template.handle.service.ServiceHandler;
//
///**
// * @author lijunlin
// */
//public class ServiceHandlerTest {
//
//    @Test
//    public void test() {
//
//        ServiceHandler<String, Integer> serviceHandler = new ServiceHandler<String, Integer>() {
//            @Override
//            public boolean checkParams(String s) {
//                return false;
//            }
//
//            @Override
//            public Integer handle(String s) {
//                return null;
//            }
//        };
//
//        boolean checkResult = serviceHandler.checkParams(null);
//
//        Integer handleResult = serviceHandler.handle(null);
//    }
//}