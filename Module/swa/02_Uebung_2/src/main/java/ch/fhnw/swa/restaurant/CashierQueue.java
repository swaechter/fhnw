package ch.fhnw.swa.restaurant;

import java.util.List;

public interface CashierQueue {

    void getGuest(Guest guest);

    void relocateAndCloseQueue(List<Guest> guests, CashierQueue otherqueue);
}
