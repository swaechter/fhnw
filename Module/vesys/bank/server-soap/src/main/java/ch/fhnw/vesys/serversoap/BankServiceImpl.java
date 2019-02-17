package ch.fhnw.vesys.serversoap;

import ch.fhnw.vesys.shared.api.InactiveException;
import ch.fhnw.vesys.shared.api.OverdrawException;
import ch.fhnw.vesys.shared.local.LocalDriver;

import javax.jws.WebService;
import java.io.IOException;

@WebService(endpointInterface = "ch.fhnw.vesys.serversoap.BankService")
public class BankServiceImpl implements BankService {

    private final LocalDriver driver;

    public BankServiceImpl() {
        this.driver = new LocalDriver();
    }

    @Override
    public String createAccount(String owner) throws IOException {
        return driver.getBank().createAccount(owner);
    }

    @Override
    public boolean closeAccount(String number) throws IOException {
        return driver.getBank().closeAccount(number);
    }

    @Override
    public String[] getAccountNumbers() throws IOException {
        return driver.getBank().getAccountNumbers().toArray(new String[]{});
    }

    @Override
    public void transfer(String fromnumber, String tonumber, double amount) throws IOException, IllegalNumberException, IllegalBankArgumentException, OverdrawException, InactiveException {
        try {
            driver.getBank().transfer(driver.getBank().getAccount(fromnumber), driver.getBank().getAccount(tonumber), amount);
        } catch (NullPointerException exception) {
            throw new IllegalNumberException(exception.getMessage());
        } catch (IllegalArgumentException exception) {
            throw new IllegalBankArgumentException(exception.getMessage());
        }
    }

    @Override
    public String getOwner(String number) throws IOException, IllegalNumberException {
        try {
            return driver.getBank().getAccount(number).getOwner();
        } catch (NullPointerException exception) {
            throw new IllegalNumberException(exception.getMessage());
        }
    }

    @Override
    public boolean isActive(String number) throws IOException, IllegalNumberException {
        try {
            return driver.getBank().getAccount(number).isActive();
        } catch (NullPointerException exception) {
            throw new IllegalNumberException(exception.getMessage());
        }
    }

    @Override
    public void deposit(String number, double amount) throws IOException, IllegalNumberException, IllegalBankArgumentException, InactiveException {
        try {
            driver.getBank().getAccount(number).deposit(amount);

        } catch (NullPointerException exception) {
            throw new IllegalNumberException(exception.getMessage());
        } catch (IllegalArgumentException exception) {
            throw new IllegalBankArgumentException(exception.getMessage());
        }
    }

    @Override
    public void withdraw(String number, double amount) throws IOException, IllegalNumberException, IllegalBankArgumentException, OverdrawException, InactiveException {
        try {
            driver.getBank().getAccount(number).withdraw(amount);
        } catch (NullPointerException exception) {
            throw new IllegalNumberException(exception.getMessage());
        } catch (IllegalArgumentException exception) {
            throw new IllegalBankArgumentException(exception.getMessage());
        }
    }

    @Override
    public double getBalance(String number) throws IOException, IllegalNumberException {
        try {
            return driver.getBank().getAccount(number).getBalance();
        } catch (NullPointerException exception) {
            throw new IllegalNumberException(exception.getMessage());
        }
    }
}
