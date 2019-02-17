package ch.swaechter.eaf.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache {

    private Map<Long, Object> elements;

    public Cache() {
        this.elements = new HashMap();
    }

    public void addObjectToCache(Object object, long key) {
        this.elements.put(key, object);
    }

    public Object getObjectFromCache(long key) {
        return this.elements.get(key);
    }

    public List<Object> getAll() {
        return new ArrayList<>(elements.values());
    }

    public void removeObject(Long id) {
        this.elements.remove(id);
    }

    public void removeAll() {
        this.elements.clear();
    }

    public int getNumberOfObjects() {
        return elements.size();
    }
}
