package ch.fhnw.vesys.clientrest;

import ch.fhnw.vesys.shared.api.*;
import ch.fhnw.vesys.shared.local.LocalDriver;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RestDriver implements BankDriver {

    private RestBank bank;

    @Override
    public void connect(String[] args) throws IOException {
        try {
            URI uri = new URI("http://" + args[0] + ":" + Integer.parseInt(args[1]) + "/accounts");
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(uri);
            bank = new RestBank(target);
            System.out.println("Rest driver connected");

        } catch (Exception exception) {
            throw new IOException("Unable to parse the given parameters");
        }
    }

    @Override
    public void disconnect() throws IOException {
        bank = null;
        System.out.println("Rest driver disconnected");
    }

    @Override
    public Bank getBank() {
        return bank;
    }

    private class RestBank implements Bank {

        private final WebTarget target;

        RestBank(WebTarget target) {
            this.target = target;
        }

        @Override
        public String createAccount(String owner) throws IOException {
            Form form = new Form();
            form.param("owner", owner);

            Response response = target.request().post(Entity.form(form));
            if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
                return response.readEntity(String.class);
            } else {
                throw new IOException("Unable to create the account");
            }
        }

        @Override
        public boolean closeAccount(String number) throws IOException {
            if (number == null || number.isEmpty()) {
                return false;
            }

            Response response = target.path("/" + number).request().accept(MediaType.APPLICATION_JSON).delete();
            return response.getStatus() == Response.Status.OK.getStatusCode();
        }

        @Override
        public Set<String> getAccountNumbers() throws IOException {
            Response response = target.request().accept(MediaType.APPLICATION_JSON).get();
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                return new HashSet<>(Arrays.asList(response.readEntity(String[].class)));
            } else {
                throw new IOException("Unable to read the accounts");
            }
        }

        @Override
        public Account getAccount(String number) throws IOException {
            if (number == null || number.isEmpty()) {
                return null;
            }

            Response response = target.path("/" + number).request().accept(MediaType.APPLICATION_JSON).get();
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                LocalDriver.LocalAccount account = response.readEntity(LocalDriver.LocalAccount.class);
                return new RestAccount(target, account.getNumber(), account.getOwner());
            } else {
                return null;
            }
            /*LocalDriver.LocalAccount account = target.path("/" + number).request().accept(MediaType.APPLICATION_JSON).get(LocalDriver.LocalAccount.class);
            if (account != null) {
                return new RestAccount(target, account.getNumber(), account.getOwner());
            } else {
                return null;
            }*/
        }

        @Override
        public void transfer(Account a, Account b, double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
            RestAccount resta = new RestAccount(target, a.getNumber(), a.getOwner());
            RestAccount restb = new RestAccount(target, b.getNumber(), b.getOwner());

            if (!resta.isActive() || !restb.isActive()) {
                throw new InactiveException();
            }

            resta.withdraw(amount);
            restb.deposit(amount);
        }
    }

    private class RestAccount implements Account {

        private final String number;

        private final String owner;

        private final WebTarget target;

        RestAccount(WebTarget target, String number, String owner) {
            this.target = target;
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
            Response response = target.path("/" + number).request().head();
            return response.getStatus() != Response.Status.NOT_FOUND.getStatusCode() && response.getStatus() == Response.Status.OK.getStatusCode();
        }

        @Override
        public void deposit(double amount) throws IOException, IllegalArgumentException, InactiveException {
            if (amount < 0) {
                throw new IllegalArgumentException();
            }

            Form form = new Form();
            form.param("amount", Double.toString(amount));

            Response response = target.path("/" + number).request().put(Entity.form(form));
            if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                throw new InactiveException();
            } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
                throw new InactiveException();
            }
        }

        @Override
        public void withdraw(double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
            if (amount < 0) {
                throw new IllegalArgumentException();
            }

            if (getBalance() - amount < 0) {
                throw new OverdrawException();
            }

            Form form = new Form();
            form.param("amount", Double.toString(-amount));

            Response response = target.path("/" + number).request().put(Entity.form(form));
            if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                throw new InactiveException();
            } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
                throw new OverdrawException();
            }
        }

        @Override
        public double getBalance() throws IOException {
            LocalDriver.LocalAccount account = target.path("/" + number).request().accept(MediaType.APPLICATION_JSON).get(LocalDriver.LocalAccount.class);
            return account.getBalance();
        }

        private void setBalance(double newamout) throws InactiveException {
            Form form = new Form();
            form.param("amount", Double.toString(newamout));

            Response response = target.path("/" + number).request().post(Entity.form(form));
            if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                throw new InvalidParameterException();
            } else if (response.getStatus() == Response.Status.GONE.getStatusCode()) {
                throw new InactiveException();
            }
        }
    }
}
