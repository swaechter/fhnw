/*
 * Created on 05.03.2016
 */
package ch.fhnw.swa.restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;

/**
 * @author Wolfgang Weck
 */
public class SimulationScheduler {
    /*
     * simulation parameters:
     *
     * SEC: duration of a simulated second in microseconds (10 is speed-up 100)
     *
     * OPENING_TIME: simulated duration in simulated seconds (roughly) - used by
     * sendGuests to determine number of Guests of each type to be send
     */
    private static final int SEC = 10, OPENING_TIME = 3600;
    private Queue hitQ;
    private Buffet saladBuffet, soupBuffet;
    private Composition cashier;
    private SimulationStatistics stat;

    public static void main(String[] args) {
        Log.init(SEC);
        SimulationScheduler schedule = new SimulationScheduler();
        schedule.initRestaurant();
        schedule.sendGuests();
    }

    private void initRestaurant() {
        stat = new SimulationStatistics();
        hitQ = new Queue("Hit");
        new Counter("Hit", hitQ, 45 * SEC);

        saladBuffet = new Buffet("Salad", n -> (60 + 10 * n) * SEC);
        soupBuffet = new Buffet("Soup", n -> (60 + 10 * n) * SEC);

        Queue queue1 = new Queue("Cashier queue 1");
        Queue queue2 = new Queue("Cashier queue 2");
        Counter counter1 = new Counter("Cashier 1", queue1, 30 * SEC);
        Counter counter2 = new Counter("Cashier 2", queue2, 30 * SEC);
        cashier = new Composition(Arrays.asList(counter1, counter2));
    }

    private void sendGuests() {
        final int nofGuestA = 45, nofGuestB = 100, nofGuestC = 50;
        final IntFunction<Guest> guestAFactory = no -> new GuestA(no, hitQ, cashier, stat);
        final IntFunction<Guest> guestBFactory = no -> new GuestB(no, saladBuffet, cashier, stat);
        final IntFunction<Guest> guestCFactory = no -> new GuestC(no, saladBuffet, soupBuffet, cashier, stat);
        new GuestFactory(OPENING_TIME / nofGuestA, nofGuestA * SEC, guestAFactory);
        new GuestFactory(OPENING_TIME / nofGuestB, nofGuestB * SEC, guestBFactory);
        new GuestFactory(OPENING_TIME / nofGuestC, nofGuestC * SEC, guestCFactory);
    }

    private static class GuestFactory extends Thread {
        private final int nofGuests, meanTime;
        private final IntFunction<Guest> factory;

        private GuestFactory(int nofGuests, int meanTime, IntFunction<Guest> factory) {
            this.nofGuests = nofGuests;
            this.meanTime = meanTime;
            this.factory = factory;
            start();
        }

        private static int expRand(double mean) {
            return (int) (-mean * Math.log(1 - Math.random()) + 0.5);
        }

        @Override
        public synchronized void run() {
            for (int i = 0; i < nofGuests; i++) {
                factory.apply(i + 1).enterRestaurant();
                try {
                    sleep(expRand(meanTime));
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
