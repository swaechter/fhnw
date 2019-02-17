package ch.fhnw.vesys.serverrmi;

import ch.fhnw.vesys.shared.api.Bank;
import ch.fhnw.vesys.shared.local.LocalDriver;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

class RmiServer {

    private RmiServer(int port) throws Exception {
        LocateRegistry.createRegistry(port);
        Bank bank = new LocalDriver.LocalBank();
        Naming.rebind("//localhost:" + port + "/Bank", new RmiBankImpl(bank));
        System.in.read();
    }

    public static void main(String[] args) throws Exception {
        new RmiServer(1234);
    }
}
