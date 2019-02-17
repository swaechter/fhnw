package ch.swaechter.apmservlet.sdk;

import java.net.InetAddress;

/**
 * Created by martin.gwerder on 25.04.2017.
 */
public interface Storage {

    /***
     * Stores the value in the Cluster.
     *
     * @param id     The id-slot in shich the value is stored
     * @param value  The value to store. Use null to remove a value from the store.
     * @return True if the value has been successfully stored
     */
    public boolean store(int id, String value);

    /***
     * Loads the value  from the cluster.
     *
     * @param id The id to be retrieved
     * @return The value requested or null if it does not exist
     */
    public String load(int id);

    /***
     * Retrieves the current status of the storage cluster.
     *
     * The status is refreshed at least every 5s.
     *
     * @return The Cluster status
     */
    public ClusterStatus getCurrentClusterStatus();

    /***
     * The status of a specific cluster node.
     *
     * @param ia The address of a cluster node to be checked
     * @return True if the storage cluster node is running
     */
    public boolean getCurrentNodeStatus(InetAddress ia);

}
