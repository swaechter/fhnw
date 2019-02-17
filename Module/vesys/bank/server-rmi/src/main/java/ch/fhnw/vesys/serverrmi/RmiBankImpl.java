package ch.fhnw.vesys.serverrmi;

import ch.fhnw.vesys.shared.api.Account;
import ch.fhnw.vesys.shared.api.Bank;
import ch.fhnw.vesys.shared.api.InactiveException;
import ch.fhnw.vesys.shared.api.OverdrawException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class RmiBankImpl extends UnicastRemoteObject implements RmiBank {

    private final Bank bank;

    public RmiBankImpl(Bank bank) throws RemoteException {
        this.bank = bank;
    }

    @Override
    public String createAccount(String owner) throws IOException {
        return bank.createAccount(owner);
    }

    @Override
    public boolean closeAccount(String number) throws IOException {
        return bank.closeAccount(number);
    }

    @Override
    public Set<String> getAccountNumbers() throws IOException {
        return new HashSet<>(bank.getAccountNumbers());
    }

    @Override
    public Account getAccount(String number) throws IOException {
        Account account = bank.getAccount(number);
        return (account != null) ? new RmiAccountImpl(account) : null;
    }

    @Override
    public void transfer(Account a, Account b, double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
        bank.transfer(a, b, amount);
    }
}
