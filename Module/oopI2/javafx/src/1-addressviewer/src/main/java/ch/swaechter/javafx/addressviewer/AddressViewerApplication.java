package ch.swaechter.javafx.addressviewer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class AddressViewerApplication extends Application {

    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
        saveWindowDimensions();
    }

    @Override
    public void start(Stage primarystage) throws Exception {
        Person persona = new Person();
        persona.setFirstName("Hans");
        persona.setName("Müller");

        Person personb = new Person();
        personb.setFirstName("Fritz");
        personb.setName("Keller");

        Person personc = new Person();
        personc.setFirstName("Ueli");
        personc.setName("Maurer");

        File storagefile = new File("test.raf");
        storagefile.delete();

        RA_Storage<Person> storage = new RA_Storage<>("test.raf", Person.class);
        storage.appendItem(persona);
        storage.appendItem(personb);
        storage.appendItem(personc);

        stage = primarystage;

        AddressViewerWindow addressviewerwindow = new AddressViewerWindow(storage);
        primarystage.setMinWidth(400);
        primarystage.setMinHeight(300);
        primarystage.setWidth(400);
        primarystage.setHeight(300);
        primarystage.setScene(new Scene(addressviewerwindow, 300, 250));
        primarystage.setTitle("Address Viewer");
        restoreWindowDimensions();
        primarystage.show();
    }

    private static void saveWindowDimensions() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("settings.ini")));
            writer.write("posx = " + stage.getX());
            writer.newLine();
            writer.write("posy = " + stage.getY());
            writer.newLine();
            writer.write("width = " + stage.getWidth());
            writer.newLine();
            writer.write("height = " + stage.getHeight());
            writer.close();
        } catch (IOException exception) {
            System.out.println("severe I/O error while writing ini-file");
        }
    }

    private static void restoreWindowDimensions() {
        double x = 100, y = 100, w = 300, h = 300;
        File control = new File("settings.ini");
        if (control.exists()) {
            String line = " ", name, value;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(control));
                int separator;
                while (line != null) {
                    line = reader.readLine();
                    if (line != null) {
                        separator = line.indexOf('=');
                        if (separator < 0) {
                            reader.close();
                            throw new RuntimeException("ini-file contains a line of invalid format");
                        }
                        name = (line.substring(0, separator)).trim();
                        value = (line.substring(separator + 1, line.length())).trim();
                        switch (name) {
                            case "posx":
                                x = Double.parseDouble(value);
                                break;
                            case "posy":
                                y = Double.parseDouble(value);
                                break;
                            case "width":
                                w = Double.parseDouble(value);
                                break;
                            case "height":
                                h = Double.parseDouble(value);
                                break;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("severe I/O error while reading ini-file");
            }
        }
        stage.setWidth(w);
        stage.setHeight(h);
        stage.setX(x);
        stage.setY(y);
    }
}
