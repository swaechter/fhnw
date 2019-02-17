/*
 * Created on Mar 4, 2016
 */
package ch.fhnw.swa.restaurant;

/**
 * @author Wolfgang Weck
 */
public final class GuestB implements Guest {
    private static enum State {
        notStarted, atSalad, atCashier
    }

    private final int no;
    private final Buffet salad;
    private final Composition cashier;
    private final SimulationStatistics stat;
    private long arrivalTime;
    private State state;

    public GuestB(int no, Buffet salad, Composition cashier, SimulationStatistics stat) {
        this.no = no;
        this.salad = salad;
        this.cashier = cashier;
        this.stat = stat;
        state = State.notStarted;
    }

    @Override
    public void enterRestaurant() {
        Log.event(this + " entered");
        if (state != State.notStarted) throw new IllegalStateException();
        arrivalTime = System.currentTimeMillis();
        state = State.atSalad;
        salad.arrive(this);
    }

    @Override
    public void done() {
        switch (state) {
            case atSalad: {
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
        return "GuestB " + no;
    }
}
