package ch.fhnw.vesys.serverrmi;

import ch.fhnw.vesys.shared.api.Account;
import ch.fhnw.vesys.shared.api.InactiveException;
import ch.fhnw.vesys.shared.api.OverdrawException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiAccountImpl extends UnicastRemoteObject implements RmiAccount {

    private final Account account;

    public RmiAccountImpl(Account account) throws RemoteException {
        this.account = account;
    }

    @Override
    public String getNumber() throws IOException {
        return account.getNumber();
    }

    @Override
    public String getOwner() throws IOException {
        return account.getOwner();
    }

    @Override
    public boolean isActive() throws IOException {
        return account.isActive();
    }

    @Override
    public void deposit(double amount) throws IOException, IllegalArgumentException, InactiveException {
        account.deposit(amount);
    }

    @Override
    public void withdraw(double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
        account.withdraw(amount);
    }

    @Override
    public double getBalance() throws IOException {
        return account.getBalance();
    }
}
