/*
 * Copyright (c) 2000-2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package ch.fhnw.vesys.shared.api;

/**
 * The OverdrawException is thrown when a bank transaction is called which would
 * overdraw an account, i.e. which tries to withdraw more money from an account
 * than its balance. This exception is declared on the methods withdraw and
 * transfer.
 *
 * @author Dominik Gruntz
 * @version 3.0
 * @see Account
 * @see Bank
 */
public class OverdrawException extends Exception {

    public OverdrawException() {
        super();
    }

    public OverdrawException(String reason) {
        super(reason);
    }

    public OverdrawException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public OverdrawException(Throwable cause) {
        super(cause);
    }
}
