package cn.guoyka.simplermybatis.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInterceptor implements InvocationHandler {
    private Object target;

    public MyInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理调用被代理方法之前的动作");
        Object re = method.invoke(target, args);
        System.out.println("代理调用被代理对象的方法之后的动作");
        return re;
    }
}
