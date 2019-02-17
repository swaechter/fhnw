/*
 * Copyright (c) 2000-2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package ch.fhnw.vesys.shared.api;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

/**
 * The <code>Bank</code> interface describes the functionality of a bank. Using
 * this interface, new accounts can be created and existing accounts can be
 * closed.
 *
 * @author Dominik Gruntz
 * @version 3.0
 * @see Account
 */
public interface Bank extends Serializable {

    /**
     * Creates a new account. As result, the method returns the account number
     * of the generated account or <code>null</code>, in case that the account
     * could not be generated. An owner may own several accounts.
     *
     * @param owner name of the owner
     * @return account number of generated account or <code>null</code>, if the
     * account could not be generated
     * @throws IOException if a remoting or communication problem occurs
     */
    String createAccount(String owner) throws IOException;

    /**
     * Closes an account. Only accounts with balance zero may be closed.
     * Calling method <code>isActive</code> on a closed account will
     * return <code>false</code>. As result, the method returns whether the
     * account could be closed or not.
     *
     * @param number number of the account to be closed
     * @return true, if the closing was successful, false otherwise
     * @throws IOException if a remoting or communication problem occurs
     */
    boolean closeAccount(String number) throws IOException;

    /**
     * Returns a set of the account numbers (of type String) of all currently
     * active accounts.
     *
     * @return set of account numbers of all active accounts
     * @throws IOException if a remoting or communication problem occurs
     */
    Set<String> getAccountNumbers() throws IOException;

    /**
     * Returns a particular account given the account number. If the account
     * number is not valid, <code>null</code> is returned as result. The returned
     * account may be passive.
     *
     * @param number number of the account
     * @return account with the account number as specified or <code>null</code>,
     * if such an account was never created and does not exist.
     * @throws IOException if a remoting or communication problem occurs
     */
    Account getAccount(String number) throws IOException;

    /**
     * Transfers the given amount from account a to account b.
     *
     * @param a      account to withdraw amount from
     * @param b      account to deposit amount
     * @param amount value to transfer
     * @throws InactiveException        if one of the two accounts is not active
     * @throws OverdrawException        if the amount is greater than the balance of
     *                                  account a
     * @throws IllegalArgumentException if the argument is negative
     * @throws IOException              if a remoting or communication problem occurs
     * @pre amount &ge; 0
     */
    void transfer(Account a, Account b, double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException;
}
