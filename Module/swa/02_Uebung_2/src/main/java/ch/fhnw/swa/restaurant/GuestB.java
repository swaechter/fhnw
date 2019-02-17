package ch.fhnw.swa.restaurant;

public interface GuestB extends Guest {

    void enterRestaurant(Restaurant restaurant);

    void queueInSaladQueue(SaladQueue queue);

    void takePlateFromSaladCounter(Plate plate);

    void queueInBestCashierQueue(CashierQueue queue1, CashierQueue queue2);

    void relocateToOtherCashierQueue(CashierQueue queue);

    void buyMeal();

    void eatMeal();

    void rateAndLeaveRestaurant();
}
