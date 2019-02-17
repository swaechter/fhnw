package ch.fhnw.vesys.serverservlet;

import ch.fhnw.vesys.shared.api.BankDriver;
import ch.fhnw.vesys.shared.local.LocalDriver;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

class ServletServer {

    private ServletServer(int port) throws Exception {
        BankDriver bankdriver = new LocalDriver();

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);

        Context context = tomcat.addContext("", new File(".").getAbsolutePath());

        Tomcat.addServlet(context, "ServletServer", new RequestHandler(bankdriver));
        context.addServletMappingDecoded("/*", "ServletServer");

        tomcat.start();
        tomcat.getServer().await();
    }

    public static void main(String[] args) throws Exception {
        new ServletServer(1234);
    }
}
