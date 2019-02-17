/*
 * Copyright (c) 2000-2016 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package ch.fhnw.vesys.client.tests;

import ch.fhnw.vesys.shared.api.Account;
import ch.fhnw.vesys.shared.api.Bank;
import ch.fhnw.vesys.shared.api.InactiveException;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

public class FunctionalityTest implements BankTest {

    @Override
    public String getName() {
        return "Functionality Test";
    }

    @Override
    public boolean isEnabled(int size) {
        return size > 0;
    }

    @Override
    public void runTests(JFrame context, final Bank bank, String currentAccountNumber) throws Exception {
        final Account acc = bank.getAccount(currentAccountNumber);

        String msg = null;

        // can new accounts be created?
        if (msg == null) {
            String nr = bank.createAccount("TestUser1");
            if (nr != null && bank.getAccount(nr) != null) {
                bank.closeAccount(nr);
            } else {
                msg = "your implementation does not support\nthe creation of new accounts.";
            }
        }

        // is active implemented correctly?
        // After closing a deposit on the closed account has to throw an exception
        if (msg == null) {
            String nr = bank.createAccount("TestUser");
            Account a = bank.getAccount(nr);
            bank.closeAccount(nr);
            try {
                a.deposit(100);
                msg = "active is not implemented correctly!\n"
                    + "Transactions are not allowed on a closed account.";
            } catch (InactiveException e) {
            }
        }

        // is it possible to overdraw the account?
        if (msg == null) {
            if (acc.getBalance() < 0) {
                msg = "it is possible to overdraw your account!\n"
                    + "look at the balance of the current account.";
            } else {
                double amount = acc.getBalance();
                acc.withdraw(amount);
                try {
                    double x = Math.floor(Math.sin(100) * 100) / 10;
                    acc.deposit(x);
                } catch (Exception e) {
                    /* ignore the exception */
                }
                if (acc.getBalance() < 0) {
                    msg = "it is possible to overdraw your account!\n"
                        + "look at the balance of the current account.";
                } else {
                    acc.deposit(amount);
                }
            }
        }

        // test of transfer
        if (msg == null) {
            String n1 = currentAccountNumber;
            String n2 = bank.createAccount("Account2");
            Account a1 = bank.getAccount(n1);
            Account a2 = bank.getAccount(n2);
            double a1Balance = a1.getBalance();
            double a2Balance = a2.getBalance();
            a1.withdraw(a1Balance);
            a2.withdraw(a2Balance);
            a1.deposit(50);
            a2.deposit(50);

            try {
                bank.transfer(a1, a2, -100);
                msg = "oops, your implementation of transfer is not correct!";
            } catch (Exception e) {
                double bal1 = a1.getBalance();
                double bal2 = a2.getBalance();
                if (bal1 != 50 || bal2 != 50) {
                    msg = "Although an exception was thrown by transfer, "
                        + "the balances have been changed.\n"
                        + "When an exception is thrown the state must not be changed.";
                }
            }

            if (msg == null) {
                double bal2 = a2.getBalance();
                a2.withdraw(bal2);
                bank.closeAccount(n2);
                a1.withdraw(50);
                a1.deposit(a1Balance);
            }
        }

        // can an account with positive balance be closed?
        if (msg == null) {
            String n = bank.createAccount("Account4");
            Account a = bank.getAccount(n);
            a.deposit(100);
            boolean done = bank.closeAccount(n);
            if (done) {
                msg = "Accounts with a positive balance must not be closed!";
            } else {
                a.withdraw(100);
                bank.closeAccount(n);
            }
        }

        // can an owner open two accounts?
        if (msg == null) {
            String n1 = bank.createAccount("Meier");
            String n2 = bank.createAccount("Meier");
            if (n1.equals(n2)) {
                msg = "A user cannot create two accounts using the same name";
            }
            bank.closeAccount(n1);
            bank.closeAccount(n2);
        }

        // uniqueness of account numbers
        if (msg == null) {
            String n1 = bank.createAccount("Account1");
            String n2 = bank.createAccount("Account54039680");

            if (n1.equals(n2)) {
                msg = "different accounts should have different account numbers!";
            }

            bank.closeAccount(n1);
            bank.closeAccount(n2);
        }

        // are arbitrary names supported
        if (msg == null) {
            String name, id;
            Account a;
            name = "Hans Muster";
            id = bank.createAccount(name);
            a = bank.getAccount(id);
            if (!name.equals(a.getOwner()))
                msg = "not all names are properly supported";
            bank.closeAccount(id);

            name = "Peter Müller;junior";
            id = bank.createAccount(name);
            a = bank.getAccount(id);
            if (!name.equals(a.getOwner()))
                msg = "not all names are properly supported";
            bank.closeAccount(id);

            name = "Peter:Müller";
            id = bank.createAccount(name);
            a = bank.getAccount(id);
            if (!name.equals(a.getOwner()))
                msg = "not all names are properly supported";
            bank.closeAccount(id);
        }

//		if (msg == null) {
//			Bank bank = driver.getBank();
//			if (bank != this.bank)
//				msg = "getBank should be implemented as singleton";
//		}

        if (msg == null) {
            String n = bank.createAccount("Account5");
            Set<String> s1 = new HashSet<String>(bank.getAccountNumbers());
            bank.closeAccount(n);
            Set<String> s2 = new HashSet<String>(bank.getAccountNumbers());
            if (s1.equals(s2))
                msg = "method getAccountNumbers should only return the numbers of active accounts.";
        }

        if (msg == null) {
            String n = bank.createAccount("Account6");
            Account a = bank.getAccount(n);
            bank.closeAccount(n);
            try {
                double balance = a.getBalance();
                if (balance != 0.0)
                    msg = "balance of a closed account should be zero.";
            } catch (Exception e) {
                msg = "method getBalance should not throw an Exception.";
            }
        }

        if (msg == null) {
            Account a = bank.getAccount(""); // assume that this is not a valid number
            if (a != null) {
                msg = "method getAccount must return null if the account does not exist";
            }
        }

        if (msg == null) {
            String a1 = bank.createAccount("a1");
            String a2 = bank.createAccount("a2");
            String a3 = bank.createAccount("a3");
            String a4 = bank.createAccount("a4");
            boolean close2 = bank.closeAccount(a2);
            boolean close4 = bank.closeAccount(a4);
            if (!close2 || !close4) {
                msg = "accounts could not be closed although their balance is 0";
            } else {
                Set<String> accountNumbers = bank.getAccountNumbers();
                if (accountNumbers.contains(a2) || accountNumbers.contains(a4)) {
                    msg = "method getAccountNumbers should only contain active accounts";
                } else if (!accountNumbers.contains(a1) || !accountNumbers.contains(a3)) {
                    msg = "method getAccountNumbers should contain all active accounts";
                }
            }
            if (msg == null) {
                Account a = bank.getAccount(a2);
                if (a == null) {
                    msg = "method getAccount must return all created accounts,\n" +
                        "even if they are closed.";
                }
            }
            bank.closeAccount(a1);
            bank.closeAccount(a3);
        }

        if (msg == null) {
            try {
                Account a = bank.getAccount("xxx");
                if (a != null) {
                    msg = "if bank.getAccount is called with an invalid account number then null must be returned.";
                }
            } catch (Exception e) {
                msg = "if bank.getAccount is called with an invalid account number then null must be returned,\n" +
                    "no exception must be thrown.";
            }
        }

        if (msg == null) {
            try {
                String id = bank.createAccount("a5");
                boolean close1 = bank.closeAccount(id);
                boolean close2 = bank.closeAccount(id);
                if (!close1) {
                    msg = "An account which has just been created must be closeable. \n" +
                        "Your implementation returned false when invoking closeAccount.";
                } else if (close2) {
                    msg = "If bank.closeAccount(id) is invoked on an account which is already closed,\n" +
                        "then false must be returned (as the close operation was not successful).";
                }
            } catch (Exception e) {
                msg = "closing a closed account must not end in an exception.";
            }
        }

        if (msg == null) {
            try {
                String id = bank.createAccount("a6");
                Account a1 = bank.getAccount(id);
                Account a2 = bank.getAccount(id);
                a1.deposit(100);
                if (a2.getBalance() != 100) {
                    msg = "if an account is accessed twice with getAccount(id), and if on the first reference\n" +
                        "its value is changed, then this change must also be visible over the second refereence";
                }
                a1.withdraw(100);
                bank.closeAccount(id);
            } catch (Exception e) {
                msg = "accessing an account twice with getAccount(id), and then invoking deposit on the first reference\n" +
                    "and getBalance on the second reference, this lead to an exception.";
            }
        }

        if (msg == null)
            msg = "Your implementation passed all unit tests";

        JOptionPane.showMessageDialog(context, msg, "Test Result", JOptionPane.INFORMATION_MESSAGE);
    }
}
