package ch.fhnw.vesys.serverrest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

class RestServer {

    private RestServer(int port) throws Exception {
        RestConfig config = new RestConfig();
        ServletHolder servletholder = new ServletHolder(new ServletContainer(config));

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(server, "/*");
        context.addServlet(servletholder, "/*");

        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }

    public static void main(String[] args) throws Exception {
        new RestServer(1234);
    }
}
