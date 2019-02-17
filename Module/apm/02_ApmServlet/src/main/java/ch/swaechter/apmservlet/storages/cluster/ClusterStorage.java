package ch.swaechter.apmservlet.storages.cluster;

import ch.swaechter.apmservlet.sdk.ClusterStatus;
import ch.swaechter.apmservlet.sdk.Storage;
import com.hazelcast.config.Config;
import com.hazelcast.core.Client;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This is a distributed storage for a clustered map.
 * <p>
 * This class requires the hazelcast library to be included
 */
public class ClusterStorage implements Storage {

    private static HazelcastInstance instance = Hazelcast.newHazelcastInstance(new Config());

    private static IMap<Integer, String> storage = instance.getMap("clusterMap");

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
        // return fully functional if we have more than one cluster node
        if (getMemberList().length > 1) return ClusterStatus.CLUSTER_FUNCTIONAL;

        // when only one node is connected return degraded
        if (getMemberList().length == 1) return ClusterStatus.CLUSTER_DEGRADED;

        // consider all other states as illegal
        return ClusterStatus.CLUSTER_ILLEGAL;
    }

    @Override
    public boolean getCurrentNodeStatus(InetAddress ia) {
        // Local node is always cosidered healthy
        if (ia.isAnyLocalAddress()) return true;

        // Check if given address is in cluster
        Collection<Client> l = instance.getClientService().getConnectedClients();
        for (Client c : l) {
            if (c.getSocketAddress().getAddress().equals(ia)) return true;
        }

        // reject all other addresses
        return false;
    }

    public SocketAddress[] getMemberList() {
        List<SocketAddress> v = new ArrayList<>();
        for (Client c : instance.getClientService().getConnectedClients()) {
            v.add(c.getSocketAddress());
        }
        return v.toArray(new SocketAddress[v.size()]);
    }
}
