package com.gyk.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.util.JAXBSource;
import java.io.IOException;

public class JsonHelper<T> {
    private static final ObjectMapper mapper = new ObjectMapper();

    //解析json字符串
    public static JsonNode parseJSON(String json) throws IOException{
        return mapper.readTree(json);
    }


    //将json字符串转化为对象
    public static <T> T read(String js, Class<T> clazz){

        return null;
    }
}
