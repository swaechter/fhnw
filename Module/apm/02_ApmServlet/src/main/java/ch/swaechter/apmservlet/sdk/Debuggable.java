package ch.swaechter.apmservlet.sdk;

import java.util.List;

/**
 * This interface provides the possibility to get the IDs of the cached values.
 *
 * @author Simon Wächter
 */
public interface Debuggable {

    /**
     * Get all IDs of the cached entries.
     *
     * @return List of cached IDs
     */
    List<Integer> getCacheIds();
}
