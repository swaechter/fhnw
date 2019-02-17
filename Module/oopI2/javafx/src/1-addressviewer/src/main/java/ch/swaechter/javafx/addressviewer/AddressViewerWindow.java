package ch.swaechter.javafx.addressviewer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.File;

public class AddressViewerWindow extends BorderPane implements EventHandler<ActionEvent> {

    private final String[] LABELS = {"Name", "Adresse", "Geburtsdatum", "Kontostand", "Geschlecht", "Zivilstand", "Brillenträger"};

    private final String FORWARD = ">";

    private final String BACKWARD = "<";

    private final RA_Storage<Person> storage;

    private Person buffer = new Person();

    private final TextField[] inputfields = new TextField[7];

    private final Label position;

    private final Label status;

    private final Button forwardbutton;

    private final Button backwardbutton;

    private final Slider slider;

    private int index;

    public AddressViewerWindow(RA_Storage<Person> storage) {
        VBox labelbox = new VBox();
        VBox inputbox = new VBox();
        HBox controlbox = new HBox();
        VBox buttonbox = new VBox();
        VBox statusbox = new VBox();

        setLeft(labelbox);
        setCenter(inputbox);
        setRight(controlbox);
        setBottom(statusbox);
        setPadding(new Insets(10, 10, 0, 10));

        inputbox.setPadding(new Insets(0, 10, 0, 10));
        inputbox.setSpacing(5);

        buttonbox.setAlignment(Pos.CENTER);
        buttonbox.setPrefHeight(150);

        controlbox.setPadding(new Insets(20, 10, 20, 20));

        statusbox.setPadding(new Insets(5, 0, 5, 0));

        for (int i = 0; i < 7; ++i) {
            Label label = new Label(LABELS[i]);
            label.setMaxHeight(Double.MAX_VALUE);
            VBox.setVgrow(label, Priority.ALWAYS);
            labelbox.getChildren().add(label);
        }

        for (int i = 0; i < 7; ++i) {
            inputfields[i] = new TextField();
            inputfields[i].setMaxHeight(Double.MAX_VALUE);
            inputfields[i].setEditable(false);
            inputfields[i].setFocusTraversable(false);
            inputfields[i].setOnMouseClicked(event -> requestFocus());
            VBox.setVgrow(inputfields[i], Priority.ALWAYS);
            inputbox.getChildren().add(inputfields[i]);
        }

        forwardbutton = new Button(FORWARD);
        forwardbutton.setPrefWidth(50);
        VBox.setMargin(forwardbutton, new Insets(2));
        buttonbox.getChildren().add(forwardbutton);

        backwardbutton = new Button(BACKWARD);
        backwardbutton.setPrefWidth(50);
        VBox.setMargin(backwardbutton, new Insets(2));
        buttonbox.getChildren().add(backwardbutton);

        position = new Label("position");
        position.setPrefWidth(50);
        position.setAlignment(Pos.CENTER);
        VBox.setMargin(position, new Insets(2));
        buttonbox.getChildren().add(position);

        Button exitbutton = new Button("Exit");
        exitbutton.setTooltip(new Tooltip("Close the application"));
        exitbutton.setPrefWidth(50);
        VBox.setMargin(exitbutton, new Insets(2));
        buttonbox.getChildren().add(exitbutton);

        slider = new Slider();
        slider.setOrientation(Orientation.VERTICAL);
        slider.valueProperty().addListener((x, o, n) -> {
            index = (int) (storage.itemCount()) - (int) ((Double) n + 0.5) - 1;
            showRecord(index);
            position.setText("" + (index + 1) + "/" + storage.itemCount());
        });

        controlbox.getChildren().add(new Separator(Orientation.VERTICAL));
        controlbox.getChildren().add(buttonbox);
        controlbox.getChildren().add(slider);

        status = new Label("status");
        VBox.setMargin(status, new Insets(5));

        statusbox.getChildren().add(new Separator());
        statusbox.getChildren().add(status);

        this.storage = storage;
        initData();

        exitbutton.setOnAction(event -> {
            storage.close();
            Platform.exit();
        });

        forwardbutton.setOnAction(this);
        backwardbutton.setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == forwardbutton) {
            if (index < storage.itemCount() - 1) {
                index++;
                showRecord(index);
                position.setText("" + (index + 1) + "/" + storage.itemCount());
            }
        } else if (event.getSource() == backwardbutton) {
            if (index > 0) {
                index--;
                showRecord(index);
                position.setText("" + (index + 1) + "/" + storage.itemCount());

            }
        }
    }

    private void initData() {
        if (!new File("test.raf").exists()) {
            status.setText("File not found!");
            status.setTextFill(Color.RED);
        } else {
            if (storage.isConnected()) {
                status.setText("Connected");
                status.setTextFill(Color.GREEN);
                if (storage.itemCount() > 0) {
                    slider.setMax(storage.itemCount() - 1);
                    slider.setMin(0);
                    slider.setMajorTickUnit(1);
                    slider.setMinorTickCount(0);
                    slider.setBlockIncrement(1);
                    slider.setShowTickLabels(true);
                    slider.setShowTickMarks(true);
                    slider.setValue(slider.getMax());
                    slider.setLabelFormatter(new LabelConverter(storage.itemCount()));
                    slider.setSnapToPixel(true);
                    position.setText("1" + "/" + storage.itemCount());
                    showRecord(0);
                } else {
                    position.setText("0 / 0");
                }
            } else {
                status.setText("Invalid file format!");
            }
        }
    }

    private void showRecord(int record) {
        if (record >= 0 && record <= index && storage.isConnected()) {
            storage.readItem(record, buffer);
            int b = buffer.getBirthdate();
            inputfields[0].setText(buffer.getLastName() + " " + buffer.getFirstName());
            inputfields[1].setText(buffer.getAddress());
            inputfields[2].setText(String.format("%02d", b % 100) + "." + String.format("%02d", b / 100 % 100) + "." + String.format("%04d", b / 10000));
            inputfields[3].setText(String.format("%.2f", buffer.getMoney()));
            inputfields[4].setText(buffer.isFemale() ? "Weiblich" : "Männlich");
            inputfields[5].setText(buffer.isMarried() ? "Verheiratet" : "Unverheiratet");
            inputfields[6].setText(buffer.wearsGlasses() ? "Ja" : "Nein");
            slider.setValue(storage.itemCount() - index - 1);
        }
    }
}
