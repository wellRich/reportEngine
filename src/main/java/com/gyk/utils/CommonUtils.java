package com.gyk.utils;

public class CommonUtils {

    //模仿groovy的二元操作符 ?:
    public static Object binaryOperation(Object first, Object second){
        if(first.equals(false) || first == null){
            return second;
        }else {
            return first;
        }
    }
}
