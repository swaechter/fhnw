package ch.fhnw.vesys.shared.local;

import ch.fhnw.vesys.shared.api.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class LocalDriver implements BankDriver {

    private final Bank bank = new LocalBank();

    @Override
    public void connect(String[] args) {
        System.out.println("Bank connected...");
    }

    @Override
    public void disconnect() {
        System.out.println("Bank disconnected...");
    }

    @Override
    public Bank getBank() {
        return bank;
    }

    public static class LocalBank implements Bank {

        private final Map<String, LocalAccount> accounts = new HashMap<>();

        @Override
        public Set<String> getAccountNumbers() throws IOException {
            return Collections.unmodifiableSet(this.accounts.values().stream().filter(LocalAccount::isActive).map(LocalAccount::getNumber).collect(Collectors.toSet()));
        }

        @Override
        public String createAccount(String owner) {
            String uuid = UUID.randomUUID().toString();
            LocalAccount account = new LocalAccount(owner, uuid);
            accounts.put(uuid, account);
            return uuid;
        }

        @Override
        public boolean closeAccount(String number) {
            LocalAccount account = getAccount(number);

            if (account == null) {
                return false;
            }

            if (!account.isActive()) {
                return false;
            }

            if (account.getBalance() != 0) {
                return false;
            }

            account.active = false;
            return true;
        }

        @Override
        public LocalAccount getAccount(String number) {
            return accounts.get(number);
        }

        @Override
        public void transfer(Account from, Account to, double amount) throws IOException, InactiveException, OverdrawException {

            if (!to.isActive()) {
                throw new InactiveException("The account of the receiver is inactive");
            }

            from.withdraw(amount);
            to.deposit(amount);
        }
    }

    public static class LocalAccount implements Account {

        private String number;

        private String owner;

        private double balance;

        public boolean active = true;

        public LocalAccount() {
        }

        public LocalAccount(String owner, String number) {
            this.owner = owner;
            this.number = number;
        }

        @Override
        public double getBalance() {
            return balance;
        }

        @Override
        public String getOwner() {
            return owner;
        }

        @Override
        public String getNumber() {
            return number;
        }

        @Override
        public boolean isActive() {
            return active;
        }

        @Override
        public void deposit(double amount) throws InactiveException {
            if (!active) {
                throw new InactiveException("The current account is not active");
            }

            if (amount < 0) {
                throw new IllegalArgumentException("The deposit amount has to be greater than 0");
            }

            balance += amount;
        }

        @Override
        public void withdraw(double amount) throws InactiveException, OverdrawException {
            if (!active) {
                throw new InactiveException("The current account is not active");
            }

            if (amount < 0) {
                throw new IllegalArgumentException("The withdraw amount has to be greater than 0");
            }

            if (balance - amount < 0) {
                throw new OverdrawException("The current amount would overdraw the account");
            }

            balance -= amount;
        }
    }
}
