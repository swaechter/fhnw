package ch.swaechter.apmservlet.storages.strategies;

import ch.swaechter.apmservlet.sdk.CacheElement;
import ch.swaechter.apmservlet.sdk.ClusterStatus;
import ch.swaechter.apmservlet.sdk.Debuggable;
import ch.swaechter.apmservlet.sdk.Storage;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalMruCache implements Storage, Debuggable {

    private final Storage backendStorage;

    private final Map<Integer, CacheElement> cacheStorage;

    private final int cacheSize;

    public LocalMruCache(Storage backendStore, int cacheSize) {
        this.backendStorage = backendStore;
        this.cacheStorage = new ConcurrentHashMap<>();
        this.cacheSize = cacheSize;
    }

    @Override
    public boolean store(int id, String value) {
        return backendStorage.store(id, value);
    }

    @Override
    public String load(int id) {
        CacheElement cacheElement = cacheStorage.get(id);
        if (cacheElement == null) {
            cacheElement = new CacheElement(backendStorage.load(id));
            while (cacheStorage.size() >= cacheSize) {
                int deleteIndex = -1;
                long lastAccessed = 0;
                for (Map.Entry<Integer, CacheElement> element : cacheStorage.entrySet()) {
                    if (element.getValue().getLastAccessed() > lastAccessed) {
                        lastAccessed = element.getValue().getLastAccessed();
                        deleteIndex = element.getKey();
                    }
                }
                cacheStorage.remove(deleteIndex);
            }
            cacheStorage.put(id, cacheElement);
        } else {
            cacheElement.updateLastAccessed();
        }
        return cacheElement.getValue();
    }

    @Override
    public ClusterStatus getCurrentClusterStatus() {
        return ClusterStatus.CLUSTER_FUNCTIONAL;
    }

    @Override
    public boolean getCurrentNodeStatus(InetAddress ia) {
        if (ia.isAnyLocalAddress()) return true;
        return false;
    }

    @Override
    public List<Integer> getCacheIds() {
        return new ArrayList<>(cacheStorage.keySet());
    }
}
