package ch.fhnw.vesys.serverrest;

import ch.fhnw.vesys.shared.api.Account;
import ch.fhnw.vesys.shared.api.BankDriver;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Singleton
@Path("/accounts")
@Produces({MediaType.APPLICATION_JSON})
public class RestController {

    private final BankDriver bankdriver;

    RestController(BankDriver bankdriver) {
        this.bankdriver = bankdriver;
    }

    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getAccounts() throws Exception {
        String[] numbers = bankdriver.getBank().getAccountNumbers().toArray(new String[]{});
        return Response.ok(numbers).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public Response createAccount(@Context UriInfo uriinfo, @FormParam("owner") String owner) throws Exception {
        String number = bankdriver.getBank().createAccount(owner);
        UriBuilder builder = uriinfo.getAbsolutePathBuilder();
        builder.path(number);
        return Response.created(builder.build()).entity(number).build();
    }

    @GET
    @Path("{number}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getAccount(@PathParam("number") String number) throws Exception {
        Account account = bankdriver.getBank().getAccount(number);
        if (account == null) {
            throw new NotFoundException("Unable to find the account");
        }
        return Response.ok(account).build();
    }

    @PUT
    @Path("{number}")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public Response updateAccount(@PathParam("number") String number, @FormParam("amount") double amount) throws Exception {
        Account account = bankdriver.getBank().getAccount(number);
        if (account == null) {
            throw new NotFoundException("Unable to find the account");
        }

        if (amount < 0 && account.getBalance() - amount < 0) {
            return Response.serverError().build();
        }

        try {
            if (amount > 0) {
                account.deposit(amount);
            } else if (amount < 0) {
                account.withdraw(Math.abs(amount));
            }
        } catch (Exception exception) {
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("{number}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteAccount(@PathParam("number") String number) throws Exception {
        Account account = bankdriver.getBank().getAccount(number);
        if (account == null) {
            throw new NotFoundException("Unable to find the account");
        }

        if (bankdriver.getBank().closeAccount(number)) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
    }

    @HEAD
    @Path("{number}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response checkAccount(@PathParam("number") String number) throws Exception {
        Account account = bankdriver.getBank().getAccount(number);
        if (account == null) {
            throw new NotFoundException("Unable to find the account");
        }

        if (account.isActive()) {
            return Response.ok().build();
        } else {
            return Response.noContent().build();
        }
    }
}
