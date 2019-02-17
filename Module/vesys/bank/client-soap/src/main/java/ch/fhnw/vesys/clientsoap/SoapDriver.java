package ch.fhnw.vesys.clientsoap;

import ch.fhnw.vesys.serversoap.BankService;
import ch.fhnw.vesys.serversoap.IllegalBankArgumentException;
import ch.fhnw.vesys.serversoap.IllegalNumberException;
import ch.fhnw.vesys.shared.api.*;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SoapDriver implements BankDriver {

    private SoapBank bank;

    @Override
    public void connect(String[] args) throws IOException {
        try {
            URL wsdlurl = new URL("http://" + args[0] + ":" + args[1] + "/bank?wsdl");
            QName qname = new QName("http://serversoap.vesys.fhnw.ch/", "BankServiceImplService");
            Service service = Service.create(wsdlurl, qname);
            BankService bankservice = service.getPort(BankService.class);
            bank = new SoapBank(bankservice);
            System.out.println("Soap driver connected");
        } catch (Exception exception) {
            throw new IOException("Unable to parse the given parameters");
        }
    }

    @Override
    public void disconnect() throws IOException {
        bank = null;
        System.out.println("Soap driver disconnected");
    }

    @Override
    public Bank getBank() {
        return bank;
    }

    private class SoapBank implements Bank {

        private final BankService bankservice;

        private SoapBank(BankService bankservice) {
            this.bankservice = bankservice;
        }

        @Override
        public String createAccount(String owner) throws IOException {
            return bankservice.createAccount(owner);
        }

        @Override
        public boolean closeAccount(String number) throws IOException {
            return bankservice.closeAccount(number);
        }

        @Override
        public Set<String> getAccountNumbers() throws IOException {
            return new HashSet<>(Arrays.asList(bankservice.getAccountNumbers()));
        }

        @Override
        public Account getAccount(String number) throws IOException {
            try {
                String owner = bankservice.getOwner(number);
                return new SoapAccount(bankservice, number, owner);
            } catch (IllegalNumberException exception) {
                return null;
            }
        }

        @Override
        public void transfer(Account a, Account b, double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
            try {
                bankservice.transfer(a.getNumber(), b.getNumber(), amount);
            } catch (IllegalNumberException exception) {
                throw new IOException(exception.getMessage());
            } catch (IllegalBankArgumentException exception) {
                throw new IllegalArgumentException(exception.getMessage());
            }
        }
    }

    private class SoapAccount implements Account {

        private final BankService bankservice;

        private final String number;

        private final String owner;

        private SoapAccount(BankService bankservice, String number, String owner) {
            this.bankservice = bankservice;
            this.number = number;
            this.owner = owner;
        }

        @Override
        public String getNumber() throws IOException {
            return number;
        }

        @Override
        public String getOwner() throws IOException {
            return owner;
        }

        @Override
        public boolean isActive() throws IOException {
            try {
                return bankservice.isActive(number);
            } catch (IllegalNumberException exception) {
                throw new IOException(exception.getMessage());
            }
        }

        @Override
        public void deposit(double amount) throws IOException, IllegalArgumentException, InactiveException {
            try {
                bankservice.deposit(number, amount);
            } catch (IllegalNumberException exception) {
                throw new IOException(exception.getMessage());
            } catch (IllegalBankArgumentException exception) {
                throw new IllegalArgumentException(exception.getMessage());
            }
        }

        @Override
        public void withdraw(double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
            try {
                bankservice.withdraw(number, amount);
            } catch (IllegalNumberException exception) {
                throw new IOException(exception.getMessage());
            } catch (IllegalBankArgumentException exception) {
                throw new IllegalArgumentException(exception.getMessage());
            }
        }

        @Override
        public double getBalance() throws IOException {
            try {
                return bankservice.getBalance(number);
            } catch (IllegalNumberException exception) {
                throw new IOException(exception.getMessage());
            }
        }
    }
}
