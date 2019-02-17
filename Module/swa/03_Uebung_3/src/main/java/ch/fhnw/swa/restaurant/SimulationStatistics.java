/*
 * Created on Mar 4, 2016
 */
package ch.fhnw.swa.restaurant;

/**
 * @author Wolfgang Weck
 */
public final class SimulationStatistics {
    public void storeResults(String type, int durationOfStay) {
        Log.event(type + " left after " + Log.formatedTime(durationOfStay));
    }
}
