/*
 * Created on Mar 15, 2017
 */
package ch.fhnw.swa.restaurant;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * @author Simon Wächter
 */
public class Composition {

    private final List<Counter> counters;

    public Composition(List<Counter> counters) {
        if (counters.size() == 0) {
            throw new InvalidParameterException("Please provide at least one counter");
        }

        this.counters = counters;
    }

    public void addCounter(Counter counter) {
        if (counter == null) {
            throw new InvalidParameterException("Please provide a valid counter");
        }

        // TODO: Improve the efficiency

        counters.add(counter);
    }

    public void removeCounter(Counter counter) {
        if (counter == null) {
            throw new InvalidParameterException("Please provide a valid counter");
        }

        if (counters.size() == 1) {
            throw new InvalidParameterException("You can't remove the last counter");
        }

        Counter bestcounter = getBestCounter();
        Queue newqueue = bestcounter.getQueue();
        Queue oldqueue = counter.getQueue();

        while (oldqueue.getLength() != 0) {
            newqueue.queue(oldqueue.getFirst());
        }

        // TODO: Decrease the efficiency

        counters.remove(counter);
    }

    public void queue(Guest guest) {
        Counter bestcounter = getBestCounter();
        bestcounter.getQueue().queue(guest);
    }

    private Counter getBestCounter() {
        Counter bestcounter = counters.get(0);
        for (Counter counter : counters) {
            if (counter.getQueue().getLength() < bestcounter.getQueue().getLength()) {
                bestcounter = counter;
            }
        }
        return bestcounter;
    }
}
