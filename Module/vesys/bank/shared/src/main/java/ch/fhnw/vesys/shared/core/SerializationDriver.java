package ch.fhnw.vesys.shared.core;

import ch.fhnw.vesys.shared.api.*;
import ch.fhnw.vesys.shared.local.LocalDriver;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

public abstract class SerializationDriver implements BankDriver {

    private final SerializationBank bank;

    public SerializationDriver(Sender sender) {
        this.bank = new SerializationBank(sender);
    }

    abstract public void connect(String[] args);

    abstract public void disconnect();

    @Override
    public Bank getBank() {
        return bank;
    }

    private static class SerializationBank implements Bank {

        private final SerializationConverter converter;

        private final Sender sender;

        SerializationBank(Sender sender) {
            this.converter = new SerializationConverter();
            this.sender = sender;
        }

        @Override
        public String createAccount(String owner) throws IOException {
            Task task = sender.sendTask(new Task.CreateAccountTask(owner));
            task.throwPossibleIoException();
            return (String) task.getResult();
        }

        @Override
        public boolean closeAccount(String number) throws IOException {
            Task task = sender.sendTask(new Task.CloseAccountTask(number));
            task.throwPossibleIoException();
            return (boolean) task.getResult();
        }

        @Override
        public Set<String> getAccountNumbers() throws IOException {
            Task task = sender.sendTask(new Task.GetAccountNumbersTask());
            task.throwPossibleIoException();
            return (Set<String>) task.getResult();
        }

        @Override
        public Account getAccount(String number) throws IOException {
            Task task = sender.sendTask(new Task.GetAccountTask(number));
            task.throwPossibleIoException();
            return converter.fromLocalToRemoteAccount(sender, (Account) task.getResult());
        }

        @Override
        public void transfer(Account from, Account to, double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
            Task task = sender.sendTask(new Task.TransferTask(converter.fromRemoteToLocalAccount(from), converter.fromRemoteToLocalAccount(to), amount));
            task.throwPossibleIoException();
            task.throwPossibleIllegalArgumentException();
            task.throwPossibleInactiveEception();
            task.throwPossibleOverdrawException();
        }
    }

    private static class SerializationAccount implements Account, Serializable {

        private final Sender sender;

        private final String number;

        private final String owner;

        SerializationAccount(Sender sender, String number, String owner) {
            this.sender = sender;
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
            Task task = sender.sendTask(new Task.IsActiveTask(number));
            task.throwPossibleIoException();
            return (boolean) task.getResult();
        }

        @Override
        public void deposit(double amount) throws IOException, IllegalArgumentException, InactiveException {
            Task task = sender.sendTask(new Task.DepositTask(number, amount));
            task.throwPossibleIoException();
            task.throwPossibleIllegalArgumentException();
            task.throwPossibleInactiveEception();
        }

        @Override
        public void withdraw(double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
            Task task = sender.sendTask(new Task.WithdrawTask(number, amount));
            task.throwPossibleIoException();
            task.throwPossibleIllegalArgumentException();
            task.throwPossibleOverdrawException();
            task.throwPossibleInactiveEception();
        }

        @Override
        public double getBalance() throws IOException {
            Task task = sender.sendTask(new Task.GetBalanceTask(number));
            task.throwPossibleIoException();
            return (double) task.getResult();
        }
    }

    private static class SerializationConverter {

        public Account fromLocalToRemoteAccount(Sender sender, Account localaccount) throws IOException {
            if (localaccount == null) {
                return null;
            }
            return new SerializationAccount(sender, localaccount.getNumber(), localaccount.getOwner());
        }

        public Account fromRemoteToLocalAccount(Account remoteaccount) throws IOException, InactiveException {
            if (remoteaccount == null) {
                return null;
            }

            LocalDriver.LocalAccount localaccount = new LocalDriver.LocalAccount(remoteaccount.getOwner(), remoteaccount.getNumber());

            localaccount.active = remoteaccount.isActive();

            if (localaccount.active) {
                localaccount.deposit(remoteaccount.getBalance());
            }

            return localaccount;
        }
    }
}
