package ch.swaechter.apmservlet.storages.strategies;

import ch.swaechter.apmservlet.sdk.CacheElement;
import ch.swaechter.apmservlet.sdk.ClusterStatus;
import ch.swaechter.apmservlet.sdk.Debuggable;
import ch.swaechter.apmservlet.sdk.Storage;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocalFifoCache implements Storage, Debuggable {

    private final Storage backendStorage;

    private final List<CacheElement> cacheStorage;

    private final int cacheSize;

    private int writeIndex;

    public LocalFifoCache(Storage backendStore, int cacheSize) {
        this.backendStorage = backendStore;
        this.cacheStorage = new ArrayList<>();
        this.cacheSize = cacheSize;
        this.writeIndex = 0;
    }

    @Override
    public boolean store(int id, String value) {
        return backendStorage.store(id, value);
    }

    @Override
    public String load(int id) {
        Optional<CacheElement> cacheElement = cacheStorage.stream().filter(element -> element.getId() == id).findFirst();
        if (!cacheElement.isPresent()) {
            cacheElement = Optional.of(new CacheElement(id, backendStorage.load(id)));
            if (cacheStorage.size() == cacheSize) {
                cacheStorage.set(writeIndex, cacheElement.get());
            } else {
                cacheStorage.add(cacheElement.get());
            }
            writeIndex = (writeIndex + 1 < cacheSize) ? writeIndex + 1 : 0;
        }
        return cacheElement.get().getValue();
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
        List<Integer> ids = new ArrayList<>();
        for (CacheElement cacheElement : cacheStorage) {
            if (cacheElement != null) {
                ids.add(cacheElement.getId());
            }
        }
        return ids;
    }
}
