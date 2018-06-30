package cn.guoyka.simplermybatis.conf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Configuration
@EnableCaching
public class RedisConfig  extends CachingConfigurerSupport {
    private Logger log = LoggerFactory.getLogger(RedisConfig.class);



    /**
     * 缓存管理器
     * @return CacheManager
     */
    /*@Bean
    public CacheManager cacheManager() {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory);
        Set<String> cacheNames = new HashSet<String>() {{
            add("codeNameCache");
            add("findAll");
        }};
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration.entryTtl(Duration.ofSeconds(15));
        builder.cacheDefaults(redisCacheConfiguration);
        Duration duration = redisCacheConfiguration.getTtl();
        log.info("cacheManager----> " + duration);
        builder.initialCacheNames(cacheNames);
        RedisCacheManager cacheManager =  builder.build();
        log.info("cacheManager.getCacheConfigurations()---------------> " + cacheManager.getCacheConfigurations());
        return cacheManager;
    }*/

    @Bean
    CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        log.info("RedisConfig.cacheManager--------->");

        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);

        //初始化一个redis配置类
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();

        //设置默认超过期时间是30秒
        defaultCacheConfig.entryTtl(Duration.ofSeconds(15));
        log.info("defaultCacheConfig----------> " + defaultCacheConfig);

        Set<String> cacheNames = new HashSet<String>() {{
            add("codeNameCache");
        }};

        //初始化RedisCacheManager
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig);

        log.info("cacheManager.getCacheConfigurations()---------------> " + cacheManager.getCacheConfigurations());
        return cacheManager;
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
    {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(jackson2JsonRedisSerializer);
        template.setValueSerializer(FastJsonSerializer.INSTANCE.new ValueSerializer<>(Object.class));
        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        template.getConnectionFactory().getConnection();
        return template;
    }



    private enum  FastJsonSerializer{
        INSTANCE;

        private final static Charset DEFAULT_CHARSET = Charset.forName("UTF-8");


        private class ValueSerializer<T> implements RedisSerializer<T>{
            private Class<T>  clazz;


            public ValueSerializer(Class<T> clazz) {
                super();
                this.clazz = clazz;
            }

            @Override
            public byte[] serialize(T t) throws SerializationException {
                if(Objects.equals(t, null)){
                    return new byte[0];
                }else {
                    return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
                }
            }

            @Override
            public T deserialize(byte[] bytes) throws SerializationException {
                if (Objects.equals(bytes, null) || bytes.length <= 0) {
                    return null;
                }
                String str = new String(bytes, DEFAULT_CHARSET);
                return  JSON.parseObject(str, clazz);
            }
        }




    }

}
