package ch.fhnw.vesys.serverrest;

import ch.fhnw.vesys.shared.api.BankDriver;
import ch.fhnw.vesys.shared.local.LocalDriver;
import org.glassfish.jersey.server.ResourceConfig;

class RestConfig extends ResourceConfig {

    RestConfig() {
        BankDriver bankdriver = new LocalDriver();
        register(new RestController(bankdriver));
    }
}
