package me.solby.xconfig.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;

/**
 * Redis缓存配置
 *
 * @author majhdk
 * @date 2019-06-05
 */
@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 默认使用lettuce
     */
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 自定义RedisTemplate，定义其序列化方式
     *
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redis = new RedisTemplate<>();
        redis.setConnectionFactory(redisConnectionFactory);

        // 设置redis的String/Value的默认序列化方式
        redis.setKeySerializer(new StringRedisSerializer());
        redis.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redis.setHashKeySerializer(new StringRedisSerializer());
        redis.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        redis.afterPropertiesSet();
        return redis;
    }

    /**
     * 定义缓存管理器
     *
     * @return
     */
    @Bean
    @Override
    public CacheManager cacheManager() {
        RedisCacheConfiguration cacheConfiguration = defaultCacheConfig()
                //排除掉null的缓存
                .disableCachingNullValues()
                .serializeValuesWith(
                        //配置序列化方式
                        //主要是注解使用时
                        RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer())
                );
        //配置连接，并设置默认的缓存配置
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(cacheConfiguration).build();
    }

    /**
     * 缓存Resolver
     *
     * @return
     */
    @Bean
    @Override
    public CacheResolver cacheResolver() {
        return new SimpleCacheResolver(cacheManager());
    }

    /**
     * 缓存出错处理
     *
     * @return
     */
    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        // 用于捕获从Cache中进行CRUD时的异常的回调处理器。
        return new SimpleCacheErrorHandler();
    }

    /**
     * 自定义Key生成器
     *
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

}
