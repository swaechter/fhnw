package ch.fhnw.vesys.shared.core;

import ch.fhnw.vesys.shared.api.Account;
import ch.fhnw.vesys.shared.api.BankDriver;
import ch.fhnw.vesys.shared.api.InactiveException;
import ch.fhnw.vesys.shared.api.OverdrawException;

import java.io.IOException;
import java.io.Serializable;

public abstract class Task implements Serializable {

    Object result;

    Exception exception;

    public abstract void executeTask(BankDriver driver) throws Exception;

    public void executeHandledTask(BankDriver driver) {
        try {
            executeTask(driver);
        } catch (Exception exception) {
            this.exception = exception;
        }
    }

    public Object getResult() {
        return result;
    }

    void setResult(Object result) {
        this.result = result;
    }

    void throwPossibleIoException() throws IOException {
        if (exception != null && exception instanceof IOException) {
            throw new IOException(exception.getMessage(), exception);
        }
    }

    void throwPossibleIllegalArgumentException() throws IllegalArgumentException {
        if (exception != null && exception instanceof IllegalArgumentException) {
            throw new IllegalArgumentException(exception.getMessage(), exception);
        }
    }

    void throwPossibleInactiveEception() throws InactiveException {
        if (exception != null && exception instanceof InactiveException) {
            throw new InactiveException(exception.getMessage(), exception);
        }
    }

    void throwPossibleOverdrawException() throws OverdrawException {
        if (exception != null && exception instanceof OverdrawException) {
            throw new OverdrawException(exception.getMessage(), exception);
        }
    }

    public static class InvalidTask extends Task {

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            // Do nothing and return a null pointer in the result cast. This will trigger a null pointer exception.
        }
    }

    static class CreateAccountTask extends Task {

        private final String owner;

        CreateAccountTask(String owner) {
            this.owner = owner;
        }

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            result = driver.getBank().createAccount(owner);
        }
    }

    static class CloseAccountTask extends Task {

        private final String number;

        CloseAccountTask(String number) {
            this.number = number;
        }

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            result = driver.getBank().closeAccount(number);
        }
    }

    static class GetAccountNumbersTask extends Task {

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            result = driver.getBank().getAccountNumbers();
        }
    }

    static class GetAccountTask extends Task {

        private final String number;

        GetAccountTask(String number) {
            this.number = number;
        }

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            result = driver.getBank().getAccount(number);
        }
    }

    static class TransferTask extends Task {

        private final Account from;

        private final Account to;

        private final double amount;

        TransferTask(Account from, Account to, double amount) {
            this.from = from;
            this.to = to;
            this.amount = amount;
        }

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            driver.getBank().transfer(from, to, amount);
        }
    }

    static class IsActiveTask extends Task {

        private final String number;

        IsActiveTask(String number) {
            this.number = number;
        }

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            result = driver.getBank().getAccount(number).isActive();
        }
    }

    static class DepositTask extends Task {

        private final String number;

        private final double amount;

        DepositTask(String number, double amount) {
            this.number = number;
            this.amount = amount;
        }

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            driver.getBank().getAccount(number).deposit(amount);
        }
    }

    static class WithdrawTask extends Task {

        private final String number;

        private final double amount;

        WithdrawTask(String number, double amount) {
            this.number = number;
            this.amount = amount;
        }

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            driver.getBank().getAccount(number).withdraw(amount);
        }
    }

    static class GetBalanceTask extends Task {

        private final String number;

        GetBalanceTask(String number) {
            this.number = number;
        }

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            result = driver.getBank().getAccount(number).getBalance();
        }
    }
}
