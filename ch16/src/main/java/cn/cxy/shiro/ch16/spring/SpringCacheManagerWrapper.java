package cn.cxy.shiro.ch16.spring;

import lombok.Setter;
import net.sf.ehcache.Ehcache;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import java.util.*;

/**
 * Function: 包装Spring cache抽象
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/27 18:56 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */

@Setter
public class SpringCacheManagerWrapper implements CacheManager {

    private org.springframework.cache.CacheManager cacheManager;

    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        org.springframework.cache.Cache springCache = cacheManager.getCache(name);
        return new SpringCacheWrapper(springCache);
    }

    /**
     * 自定义包装cache
     */
    static class SpringCacheWrapper implements Cache {

        private org.springframework.cache.Cache springCache;

        public SpringCacheWrapper(org.springframework.cache.Cache springCache) {
            this.springCache = springCache;
        }

        public Object get(Object key) throws CacheException {
            org.springframework.cache.Cache.ValueWrapper valueWrapper = springCache.get(key);
            return valueWrapper;
        }

        public Object put(Object key, Object value) throws CacheException {
            springCache.put(key, value);
            return value;
        }

        public Object remove(Object key) throws CacheException {
            springCache.evict(key);
            return null;
        }

        public void clear() throws CacheException {
            springCache.clear();
        }

        public int size() {
            if (springCache.getNativeCache() instanceof Ehcache) {
                Ehcache ehcache = (Ehcache) springCache.getNativeCache();
                return ehcache.getSize();
            }
            throw new UnsupportedOperationException("invoke spring cache abstract size method not supported");
        }

        public Set keys() {
            if (springCache.getNativeCache() instanceof Ehcache) {
                Ehcache ehcache = (Ehcache) springCache.getNativeCache();
                return new HashSet(ehcache.getKeys());
            }
            throw new UnsupportedOperationException("invoke spring cache abstract keys method not supported");
        }

        public Collection values() {
            if (springCache.getNativeCache() instanceof Ehcache) {
                Ehcache ehcache = (Ehcache) springCache.getNativeCache();
                List keys = ehcache.getKeys();
                if (!CollectionUtils.isEmpty(keys)) {
                    List values = new ArrayList(keys.size());
                    for (Object key : keys) {
                        Object value = get(key);
                        if (null != value) {
                            values.add(value);
                        }
                    }
                    return Collections.unmodifiableList(values);
                } else {
                    return Collections.emptyList();
                }
            }
            throw new UnsupportedOperationException("invoke spring cache abstract keys method not supported");
        }
    }
}
