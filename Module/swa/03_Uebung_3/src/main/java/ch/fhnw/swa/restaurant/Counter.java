/*
 * Created on Mar 4, 2016
 */
package ch.fhnw.swa.restaurant;

/**
 * @author Wolfgang Weck
 */
public final class Counter {
    private final String name;

    private final Queue queue;

    public Counter(String name, Queue q, int serviceDuration) {
        this.name = name;
        this.queue = q;
        new Thread() {
            @Override
            public void run() {
                Log.event(Counter.this + " started.");
                while (true) {
                    Guest g = q.getFirst();
                    try {
                        sleep(serviceDuration);
                    } catch (InterruptedException e) {
                    }
                    g.done();
                }
            }
        }.start();
    }

    public Queue getQueue() {
        return queue;
    }

    @Override
    public String toString() {
        return "Counter " + name;
    }
}
