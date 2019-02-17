/*
 * Created on Mar 4, 2016
 */
package ch.fhnw.swa.restaurant;

/**
 * @author Wolfgang Weck
 */
public final class GuestA implements Guest {
    private static enum State {
        notStarted, atHit, atCashier
    }

    private final int no;
    private final Queue hit;
    private final Composition cashier;
    private final SimulationStatistics stat;
    private long arrivalTime;
    private State state;

    public GuestA(int no, Queue hit, Composition cashier, SimulationStatistics stat) {
        this.no = no;
        this.hit = hit;
        this.cashier = cashier;
        this.stat = stat;
        state = State.notStarted;
    }

    @Override
    public void enterRestaurant() {
        Log.event(this + " entered");
        if (state != State.notStarted) throw new IllegalStateException();
        arrivalTime = System.currentTimeMillis();
        state = State.atHit;
        hit.queue(this);
    }

    @Override
    public void done() {
        switch (state) {
            case atHit: {
                cashier.queue(this);
                state = State.atCashier;
                break;
            }
            case atCashier: {
                state = State.notStarted;
                stat.storeResults(toString(), (int) (System.currentTimeMillis() - arrivalTime));
                break;
            }
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public String toString() {
        return "GuestA " + no;
    }
}
