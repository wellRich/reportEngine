package cn.guoyka.simplermybatis.util;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class JSONHelper {



    /**
     * 对象转化为json字符串
     * @param obj 对象
     * @return json
     */
    public static String toJSON(Object obj){
        return JSONObject.toJSONString(obj);
    }

    public <T> T toEntity(String src, Class<T> clazz){
        return JSON.parseObject(src, clazz);
    }

    private static class ParameterizedTypeImpl implements ParameterizedType {
        private final Class raw;
        private final Type[] args;
        private ParameterizedTypeImpl(Class raw, Type[] args) {
            this.raw = raw;
            this.args = args != null ? args : new Type[0];
        }
        @Override
        public Type[] getActualTypeArguments() {
            return args;
        }
        @Override
        public Type getRawType() {
            return raw;
        }
        @Override
        public Type getOwnerType() {return null;}
    }
}
