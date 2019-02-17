package ch.fhnw.vesys.clientsocket;

import ch.fhnw.vesys.shared.core.Sender;
import ch.fhnw.vesys.shared.core.SerializationDriver;
import ch.fhnw.vesys.shared.core.Task;

import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketDriver extends SerializationDriver {

    private static final SocketSender sender = new SocketSender();

    public SocketDriver() {
        super(sender);
    }

    @Override
    public void connect(String[] args) {
        SocketSender.hostname = args[0];
        SocketSender.port = Integer.parseInt(args[1]);
        System.out.println("Connected to socket driver.");
    }

    @Override
    public void disconnect() {
        System.out.println("Disconnected from socket driver");
    }

    private static class SocketSender implements Sender {

        static String hostname;

        static int port;

        @Override
        public Task sendTask(Task task) {
            try {
                Socket socket = new Socket(hostname, port);
                ObjectOutput outputstream = new ObjectOutputStream(socket.getOutputStream());
                outputstream.writeObject(task);
                ObjectInputStream inputstream = new ObjectInputStream(socket.getInputStream());
                task = (Task) inputstream.readObject();
                inputstream.close();
                outputstream.close();
                socket.close();
                return task;
            } catch (Exception exception) {
                return new Task.InvalidTask();
            }
        }
    }
}
