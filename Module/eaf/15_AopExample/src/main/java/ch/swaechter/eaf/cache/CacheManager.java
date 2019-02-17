package ch.swaechter.eaf.cache;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CacheManager {

    private Map<Class, Cache> elements;

    public CacheManager() {
        this.elements = new HashMap<>();
    }

    public Cache createCacheForClass(Class clazz) {
        if (elements.get(clazz) == null) {
            Cache cache = new Cache();
            elements.put(clazz, cache);
            return cache;
        } else {
            return elements.get(clazz);
        }
    }

    public Cache getCacheByClass(Class clazz) {
        return elements.get(clazz);
    }

    public int getNumberOfCaches() {
        return elements.size();
    }

    public int getTotalNumberOfCachedObjects() {
        int result = 0;
        for (Map.Entry entry : elements.entrySet()) {
            Cache cache = (Cache) entry.getValue();
            result += cache.getNumberOfObjects();
        }
        return result;
    }
}
