package ch.fhnw.vesys.serversocket;

import ch.fhnw.vesys.shared.api.BankDriver;
import ch.fhnw.vesys.shared.local.LocalDriver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SocketServer {

    private SocketServer(int port) throws IOException {
        ServerSocket serversocket = new ServerSocket(port);
        BankDriver bankdriver = new LocalDriver();
        ExecutorService service = Executors.newCachedThreadPool();
        while (true) {
            Socket socket = serversocket.accept();
            service.execute(new RequestHandler(bankdriver, socket));
        }
    }

    public static void main(String[] args) throws IOException {
        new SocketServer(1234);
    }
}
