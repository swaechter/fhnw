/*
 * Copyright (c) 2000-2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package ch.fhnw.vesys.shared.api;

import java.io.IOException;

/**
 * The BankDriver interface is used to access a particular bank. The launcher
 * program first calls connect over this interface and then requests the bank.
 *
 * @author Dominik Gruntz
 * @version 3.0
 * @see Bank
 */

public interface BankDriver {

    /**
     * Connects to an implementation of a bank. Parameters which designate e.g.
     * the name or number of the server and possibly other arguments may be
     * passed.
     *
     * @param args array of implementation specific arguments
     * @throws IOException if a remoting or communication problem occurs
     */
    void connect(String[] args) throws IOException;

    /**
     * Disconnects from the server which is serving the bank.
     *
     * @throws IOException if a remoting or communication problem occurs
     */
    void disconnect() throws IOException;

    /**
     * Return the bank which is served by that server. Before getBank can be
     * invoked, connect must be called. getBank must have singleton semantics,
     * i.e. it should return the same instance upon subsequent calls. After
     * disconnect has been called getBank returns null.
     *
     * @return the bank to which this driver instance has been connected.
     * @see #connect
     * @see Bank
     */
    Bank getBank();
}
