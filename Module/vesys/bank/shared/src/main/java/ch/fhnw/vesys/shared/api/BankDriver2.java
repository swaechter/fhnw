/*
 * Copyright (c) 2000-2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package ch.fhnw.vesys.shared.api;

import java.io.IOException;

/**
 * The BankDriver2 interface allows the launcher to register a listener which
 * informs the launcher about changes.
 *
 * @author Dominik Gruntz
 * @version 3.0
 * @see Bank
 */

public interface BankDriver2 extends BankDriver {

    /**
     * Registers a update handler. Whenever an account changes (is created,
     * closed or if its balance is changed) then the dateChanged method is
     * invoked.
     *
     * @param handler the update handler to be registered
     */
    void registerUpdateHandler(UpdateHandler handler) throws IOException;

    interface UpdateHandler {
        /**
         * The accountChanged method is invoked whenever an account changes
         * on the server side, i.e. if an account is created, closed or if
         * its balance changes.
         *
         * @param id the number of the changed account. If the argument passed
         *           is null, then any account may have changed.
         */
        void accountChanged(String id) throws IOException;
    }
}
