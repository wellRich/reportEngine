package cn.guoyka.simplermybatis.util.redis;

import cn.guoyka.simplermybatis.util.JSONHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisClient {
    @Autowired
    private RedisTemplate redisTemplate;

    //@Value("${spring.redis.timeout}")
    private int defaultTime = 0;

    /**
     * 添加key到redis数据库中
     */
    public void set(String key, String value) throws Exception {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(key, value);
    }
    public void set(String key, String value,int timeOut) throws Exception {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        int times = 0;
        if(timeOut>0){
            times = timeOut * 60;
        }else {
            times = defaultTime;
        }
        operations.set(key, value, times, TimeUnit.SECONDS);
    }
    /**
     * 取值key到redis数据库中
     */
    public String get(String key) throws Exception  {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }
    /**
     * 删除指定key
     */
    public void del(String key){
        redisTemplate.delete(key);
    }
    /**
     * 保存obj对象到redis数据库
     */
    public void setObj(Object obj){
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        operations.set(obj.getClass().getName(),obj);
    }
    public void setObj(Object obj,int timeOut) throws Exception {
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        int times = 0;
        if(timeOut>0){
            times = timeOut * 60;
        }else {
            times = defaultTime;
        }
        operations.set(obj.getClass().getName(),obj, times, TimeUnit.SECONDS);
    }
    /**
     * 根据指定o获取Object
     */
    public <T> T getObj(Object obj,Class<T> clazz){

        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        return (T)operations.get(obj.getClass().getName());
    }

    /**
     * 删除obj对象在redis数据库
     */
    public void delObj(Object o){
        redisTemplate.delete(o);
    }
    /**
     * Set集合的赋值去取
     */
    public void setSetCollections(String key,Set value){
        redisTemplate.opsForSet().add(key,value);
    }
    public String getSetCollections(String key){
        String result = JSONHelper.toJSON(redisTemplate.opsForSet().members(key));
        return result.substring(1,result.length()-1);
    }
    public Set<String> getMapKeys(String key){
        Set<String>resultMapSet=redisTemplate.opsForHash().keys(key);
        return resultMapSet;
    }

    /**
     * Map集合的赋值去取
     */
    public void setMapCollections(String key,Map<String,Object> value){
        redisTemplate.opsForHash().putAll(key,value);
    }
    public String getMapCollections(String key){
        return JSONHelper.toJSON(redisTemplate.opsForHash().entries(key));
    }

    /**
     * List集合的赋值去取
     */
    public void setLists(String key,List list){
        redisTemplate.opsForList().leftPush(key, list);
    }

    public String getListStartEnd(String key,int start,int end){
        String result =JSONHelper.toJSON(redisTemplate.opsForList().range(key, start, end));
        return result.substring(1,result.length()-1);
    }
    /**查询key的剩余存活时间*/
    public long getKeyExpireTime(String key){
        return  redisTemplate.getExpire(key);
    }

    /**设置key的剩余存活时间*/
    public boolean setKeyExpireTime(String key,int timeOut){
        long times = 0;
        if(timeOut>0){
            times = timeOut * 60;
        }else {
            times = defaultTime;
        }
        return  redisTemplate.expire(key,times,TimeUnit.SECONDS);
    }
    /**判断key是否存在*/
    public boolean exitsKey(String key){
        Object obj = redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
        boolean flag=true;
        if (obj.toString().equals("false")){
            return false;
        }
        return flag;
    }
}