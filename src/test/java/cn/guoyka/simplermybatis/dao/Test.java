package cn.guoyka.simplermybatis.dao;

import cn.guoyka.simplermybatis.api.ReportApi;
import cn.guoyka.simplermybatis.api.TestProxy;
import cn.guoyka.simplermybatis.service.MyInterceptor;
import cn.guoyka.simplermybatis.service.ReportApiImpl;
import cn.guoyka.simplermybatis.service.TestProxyImpl;
import javafx.scene.input.DataFormat;

import java.lang.reflect.Proxy;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;

public class Test {

    @org.junit.Test
    public void dateFormat(){
        DateFormat format = DateFormat.getDateInstance(DateFormat.LONG);
        String re = format.format(new Date());
        System.out.println("re---------> " + re);
    }

    @org.junit.Test
    public void testProxy(){
        TestProxy reportApi = new TestProxyImpl();
        MyInterceptor interceptor = new MyInterceptor(reportApi);
        TestProxy s = (TestProxy) Proxy.newProxyInstance(reportApi.getClass().getClassLoader(), reportApi.getClass().getInterfaces(), interceptor);
        s.hi();
        Arrays.stream(s.getClass().getInterfaces()).peek(e-> System.out.println(e.getSuperclass())).forEach(System.out::println);
    }
}
