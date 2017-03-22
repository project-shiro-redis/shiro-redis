package org.apache.shrio.cache.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Simon on 2017/3/23.
 */
public class RedisCacheManager implements CacheManager{

    private String cacheNamePrefix = "SHIRO_CACHE_";

    private RedisTemplate<String,Cache> redisTemplate;

    private ValueOperations<String, Cache> valueOperations;

    public RedisCacheManager() {

    }

    public RedisCacheManager(String cacheNamePrefix) {
        this.cacheNamePrefix = cacheNamePrefix;
    }

    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        if(!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Cache name cannot be null or empty.");
        }

        Cache cache = valueOperations.get(getCacheName(name));

        if(cache == null){
            cache = createCache(getCacheName(name));
        }

        return cache;
    }

    protected  Cache createCache(String name) throws CacheException{
        return new RedisCache<Object, Object>(name, new ConcurrentHashMap<Object, Object>());
    }

    private String getCacheName(String name) {
        return cacheNamePrefix + name;
    }

    public void setCacheNamePrefix(String cacheNamePrefix) {
        this.cacheNamePrefix = cacheNamePrefix;
    }

    public void setRedisTemplate(RedisTemplate<String, Cache> redisTemplate) {
        if(redisTemplate == null){
            throw new IllegalArgumentException("redisTemplate argument cannot be null.");
        }
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }
}
