package ch.fhnw.vesys.clientrmi;

import ch.fhnw.vesys.shared.api.Bank;
import ch.fhnw.vesys.shared.api.BankDriver;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class RmiDriver implements BankDriver {

    private Bank bank;

    @Override
    public void connect(String[] args) throws IOException {
        try {
            bank = (Bank) Naming.lookup("rmi://" + args[0] + ":" + args[1] + "/Bank");
            System.out.println("RMI driver connected");
        } catch (NotBoundException exception) {
            throw new IOException("Unable to bind the server");
        }
    }

    @Override
    public void disconnect() throws IOException {
        bank = null;
        System.out.println("RMI driver disconnected");
    }

    @Override
    public Bank getBank() {
        return bank;
    }
}
