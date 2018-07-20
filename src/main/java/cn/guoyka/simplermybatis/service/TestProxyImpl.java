package cn.guoyka.simplermybatis.service;

import cn.guoyka.simplermybatis.api.TestProxy;

public class TestProxyImpl implements TestProxy {
    @Override
    public String hi() {
        System.out.println("hello");
        return "hello";
    }
}
