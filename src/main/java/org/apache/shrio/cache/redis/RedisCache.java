package org.apache.shrio.cache.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Simon on 2017/3/23.
 */
public class RedisCache<K, V> implements Cache<K, V> {

    private String cacheName;

    private final ConcurrentMap<K, V> caches;

    public RedisCache(String name, ConcurrentMap<K, V> caches) {
        if (name == null) {
            throw new IllegalArgumentException("Cache name cannot be null.");
        }
        if (caches == null) {
            throw new IllegalArgumentException("Backing caches cannot be null.");
        }
        this.cacheName = name;
        this.caches = caches;
    }

    public V get(K key) throws CacheException {
        return caches.get(key);
    }

    public V put(K key, V value) throws CacheException {
        return caches.put(key, value);
    }

    public V remove(K key) throws CacheException {
        return caches.remove(key);
    }

    public void clear() throws CacheException {
        caches.clear();
    }

    public int size() {
        return caches.size();
    }

    public Set<K> keys() {
        Set<K> keys = caches.keySet();
        if (!keys.isEmpty()) {
            return Collections.unmodifiableSet(keys);
        }
        return Collections.emptySet();
    }

    public Collection<V> values() {
        Collection<V> values = caches.values();
        if (!CollectionUtils.isEmpty(values)) {
            return Collections.unmodifiableCollection(values);
        }
        return Collections.emptySet();
    }
}