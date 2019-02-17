/*
 * Created on Mar 4, 2016
 */
package ch.fhnw.swa.restaurant;

import java.util.PriorityQueue;
import java.util.function.IntUnaryOperator;

/**
 * @author Wolfgang Weck
 */
public final class Buffet {
    private final String name;
    private final IntUnaryOperator serviceDurationFkt;
    private final PriorityQueue<Node> q;
    private final Object lock = new Object();

    public Buffet(String name, IntUnaryOperator serviceDurationFkt) {
        this.name = name;
        this.serviceDurationFkt = serviceDurationFkt;
        q = new PriorityQueue<Node>();
        new Thread(new Runner()) {
        }.start();
    }

    public void arrive(Guest g) {
        synchronized (lock) {
            long t = serviceDurationFkt.applyAsInt(q.size() + 1);
            q.add(new Node(g, t + System.currentTimeMillis()));
            Log.event(this + " serving " + q.size() + " guests, taking " + Log.formatedTime((int) t) + " each");
            lock.notify();
        }
    }

    private class Runner implements Runnable {
        @Override
        public void run() {
            Log.event(Buffet.this + " started");
            while (true) {
                synchronized (lock) {
                    Node head = q.peek();
                    if (head == null) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                        }
                    } else {
                        long remainingTime = head.leavingTime - System.currentTimeMillis();
                        if (remainingTime <= 0) {
                            if (q.remove() != head) throw new IllegalStateException();
                            head.guest.done();
                        } else {
                            try {
                                lock.wait(remainingTime);
                            } catch (InterruptedException e) {
                            }
                        }
                    }
                }
            }
        }
    }

    private static class Node implements Comparable<Node> {
        private Guest guest;
        private long leavingTime;

        @Override
        public int compareTo(Node other) {
            return (int) (leavingTime - other.leavingTime);
        }

        public Node(Guest guest, long leavingTime) {
            this.guest = guest;
            this.leavingTime = leavingTime;
        }
    }

    @Override
    public String toString() {
        return "Buffet " + name;
    }
}
