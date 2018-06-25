package cn.guoyka.simplermybatis.dao;

import javafx.scene.input.DataFormat;

import java.text.DateFormat;
import java.util.Date;

public class Test {

    @org.junit.Test
    public void dateFormat(){
        DateFormat format = DateFormat.getDateInstance(DateFormat.LONG);
        String re = format.format(new Date());
        System.out.println("re---------> " + re);
    }
}
