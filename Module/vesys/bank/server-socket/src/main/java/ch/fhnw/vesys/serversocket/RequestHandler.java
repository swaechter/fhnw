package ch.fhnw.vesys.serversocket;

import ch.fhnw.vesys.shared.api.BankDriver;
import ch.fhnw.vesys.shared.core.Task;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class RequestHandler implements Runnable {

    private final BankDriver bankdriver;

    private final Socket socket;

    RequestHandler(BankDriver bankdriver, Socket socket) {
        this.bankdriver = bankdriver;
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream inputstream = new ObjectInputStream(socket.getInputStream()); ObjectOutputStream outputstream = new ObjectOutputStream(socket.getOutputStream())) {
            Task task = (Task) inputstream.readObject();
            task.executeHandledTask(bankdriver);
            outputstream.writeObject(task);
            socket.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
