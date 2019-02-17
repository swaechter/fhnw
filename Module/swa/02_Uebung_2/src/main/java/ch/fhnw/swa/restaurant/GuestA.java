package ch.fhnw.swa.restaurant;

public interface GuestA extends Guest {

    void enterRestaurant(Restaurant restaurant);

    void queueInBestHitQueue(HitQueue queue1, HitQueue queue2);

    void relocatToOtherHitQueue(HitQueue otherqueue);

    void takePlateFromHitCounter(Plate plate);

    void queueInBestCashierQueue(CashierQueue queue1, CashierQueue queue2);

    void relocateToOtherCashierQueue(CashierQueue otherqueue);

    void buyMeal();

    void eatMeal();

    void rateAndLeaveRestaurant();
}
