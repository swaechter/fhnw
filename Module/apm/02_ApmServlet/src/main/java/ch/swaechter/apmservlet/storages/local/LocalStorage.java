package ch.swaechter.apmservlet.storages.local;

import ch.swaechter.apmservlet.sdk.ClusterStatus;
import ch.swaechter.apmservlet.sdk.Storage;

import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by martin.gwerder on 25.04.2017.
 */
public class LocalStorage implements Storage {

    private static Map<Integer, String> storage = new ConcurrentHashMap<>();

    @Override
    public boolean store(int id, String value) {
        if (value == null) {
            storage.remove(id);
        } else {
            storage.put(id, value);
        }
        return true;
    }

    @Override
    public String load(int id) {
        return storage.get(id);
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
}
