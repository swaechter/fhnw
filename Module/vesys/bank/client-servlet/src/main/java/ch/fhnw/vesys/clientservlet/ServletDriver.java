package ch.fhnw.vesys.clientservlet;

import ch.fhnw.vesys.shared.core.Sender;
import ch.fhnw.vesys.shared.core.SerializationDriver;
import ch.fhnw.vesys.shared.core.Task;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServletDriver extends SerializationDriver {

    private static final ServletSender sender = new ServletSender();

    public ServletDriver() {
        super(sender);
    }

    @Override
    public void connect(String[] args) {
        ServletSender.hostname = args[0];
        ServletSender.port = Integer.parseInt(args[1]);
        System.out.println("Connected to servlet driver.");

    }

    @Override
    public void disconnect() {
        System.out.println("Disconnected from servlet driver");
    }

    private static class ServletSender implements Sender {

        static String hostname;

        static int port;

        @Override
        public Task sendTask(Task task) {
            try {
                URL url = new URL("http://" + hostname + ":" + port);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                ObjectOutputStream outputstream = new ObjectOutputStream(connection.getOutputStream());
                outputstream.writeObject(task);
                ObjectInputStream inputstream = new ObjectInputStream(connection.getInputStream());
                task = (Task) inputstream.readObject();
                return task;
            } catch (Exception exception) {
                return new Task.InvalidTask();
            }
        }
    }
}
