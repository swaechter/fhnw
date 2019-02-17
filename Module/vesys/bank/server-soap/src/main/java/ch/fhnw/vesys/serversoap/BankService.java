package ch.fhnw.vesys.serversoap;

import ch.fhnw.vesys.shared.api.InactiveException;
import ch.fhnw.vesys.shared.api.OverdrawException;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.io.IOException;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface BankService {

    String createAccount(String owner) throws IOException;

    boolean closeAccount(String number) throws IOException;

    String[] getAccountNumbers() throws IOException;

    void transfer(String fromnumber, String tonumber, double amount) throws IOException, IllegalNumberException, IllegalBankArgumentException, OverdrawException, InactiveException;

    String getOwner(String number) throws IOException, IllegalNumberException;

    boolean isActive(String number) throws IOException, IllegalNumberException;

    void deposit(String number, double amount) throws IOException, IllegalNumberException, IllegalBankArgumentException, InactiveException;

    void withdraw(String number, double amount) throws IOException, IllegalNumberException, IllegalBankArgumentException, OverdrawException, InactiveException;

    double getBalance(String number) throws IOException, IllegalNumberException;
}
