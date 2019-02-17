/*
 * Created on Mar 15, 2017
 */
package ch.fhnw.swa.restaurant;

/**
 * @author Simon Wächter
 */
public final class GuestC implements Guest {
    private static enum State {
        notStarted, aSalad, aSoup, atCashier
    }

    private final int no;
    private final Buffet salad, soup;
    private final Composition cashier;
    private final SimulationStatistics stat;
    private long arrivalTime;
    private State state;

    public GuestC(int no, Buffet salad, Buffet soup, Composition cashier, SimulationStatistics stat) {
        this.no = no;
        this.salad = salad;
        this.soup = soup;
        this.cashier = cashier;
        this.stat = stat;
        state = State.notStarted;
    }

    @Override
    public void enterRestaurant() {
        Log.event(this + " entered");
        if (state != State.notStarted) throw new IllegalStateException();
        arrivalTime = System.currentTimeMillis();
        state = State.aSalad;
        salad.arrive(this);
    }

    @Override
    public void done() {
        switch (state) {
            case aSalad: {
                soup.arrive(this);
                state = State.aSoup;
                break;
            }
            case aSoup: {
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
        return "GuestC " + no;
    }
}
