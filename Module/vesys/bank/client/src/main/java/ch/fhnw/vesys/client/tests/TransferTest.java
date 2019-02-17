/*
 * Copyright (c) 2000-2016 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package ch.fhnw.vesys.client.tests;

import ch.fhnw.vesys.shared.api.Account;
import ch.fhnw.vesys.shared.api.Bank;
import ch.fhnw.vesys.shared.api.InactiveException;
import ch.fhnw.vesys.shared.api.OverdrawException;

import javax.swing.*;
import java.util.Iterator;
import java.util.Set;

public class TransferTest implements BankTest {

    @Override
    public String getName() {
        return "Transfer Test";
    }

    @Override
    public boolean isEnabled(int size) {
        return size >= 2;
    }

    @Override
    public void runTests(JFrame context, final Bank bank,
                         String currentAccountNumber) throws Exception {
        Set<String> s = bank.getAccountNumbers();
        Iterator<String> it = s.iterator();
        final Account a1 = bank.getAccount(it.next());
        final Account a2 = bank.getAccount(it.next());

        String msg = null;

        if (msg == null) {
            double bal1 = a1.getBalance();
            double bal2 = a2.getBalance();

            try {
                bank.transfer(a1, a2, bal1 + 1);
            } catch (InactiveException e) {
                /* should not be thrown */
                msg = "an InactiveException should not be thrown; probably method getAccountNumbers returns numbers of inactive accounts.";
            } catch (OverdrawException e) {
                /* expected */
            }

            if (bal1 != a1.getBalance() || bal2 != a2.getBalance()) {
                msg = "your implementation of transfer is not correct";
            }
        }

        if (msg == null) {
            String id = bank.createAccount("Test");
            Account a = a1;
            Account b = bank.getAccount(id);
            a.deposit(1);
            double bal = a.getBalance();
            bank.closeAccount(id);
            try {
                bank.transfer(a, b, 1);
                msg = "an InactiveException was expected";
            } catch (InactiveException e) {
                // OK
            } catch (Exception e) {
                msg = "an InactiveException was expected but an exception of type " + e.getClass().getSimpleName() + " was thrown.";
            }
            if (msg == null && bal != a.getBalance()) {
                msg = "money got lost during a transfer operation";
            }
            if (msg == null) {
                a.withdraw(1);
            }
        }

        if (msg == null) {
            msg = "found no errors in the implementation of transfer";
        }

        JOptionPane.showMessageDialog(context, msg, "Test Result", JOptionPane.INFORMATION_MESSAGE);
    }
}
