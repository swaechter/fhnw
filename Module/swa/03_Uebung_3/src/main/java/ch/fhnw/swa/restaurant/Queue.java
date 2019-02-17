/*
 * Created on Mar 4, 2016
 */
package ch.fhnw.swa.restaurant;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Wolfgang Weck
 */
public final class Queue {
    private final String name;
    private final BlockingQueue<Guest> q;

    public Queue(String name) {
        this.name = name;
        q = new LinkedBlockingQueue<>();
    }

    public void queue(Guest g) {
        try {
            q.put(g);
        } catch (InterruptedException e) {
        }
    }

    public Guest getFirst() {
        Guest g;
        try {
            g = q.take();
        } catch (InterruptedException e) {
            g = null;
        }
        Log.event(this + " length = " + (q.size() + 1));
        return g;
    }

    public int getLength() {
        return q.size();
    }

    @Override
    public String toString() {
        return "Queue " + name;
    }
}
