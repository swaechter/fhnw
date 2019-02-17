/*
 * Copyright (c) 2000-2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package ch.fhnw.vesys.shared.api;

/**
 * The InactiveException is thrown when a bank transaction is called on an
 * closed account.
 *
 * @author Dominik Gruntz
 * @version 3.0
 * @see Account
 * @see Bank
 */
public class InactiveException extends Exception {

    public InactiveException() {
        super();
    }

    public InactiveException(String reason) {
        super(reason);
    }

    public InactiveException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public InactiveException(Throwable cause) {
        super(cause);
    }
}
