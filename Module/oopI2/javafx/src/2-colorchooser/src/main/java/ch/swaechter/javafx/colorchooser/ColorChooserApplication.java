package ch.swaechter.javafx.colorchooser;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ColorChooserApplication extends Application implements InvalidationListener {

    private StackPane stackpane;

    private Slider rgbsliders[] = new Slider[3];

    private Slider hsbsliders[] = new Slider[3];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primarystage) throws Exception {
        BorderPane borderpane = new BorderPane();
        borderpane.setPadding(new Insets(10, 10, 10, 10));

        stackpane = new StackPane();
        stackpane.setBackground(new Background(new BackgroundFill(new Color(0.5, 0.5, 0.5, 1), new CornerRadii(20), new Insets(10))));

        HBox rgbcontrol = new HBox();
        HBox hsbcontrol = new HBox();

        String[] rgblabels = {"R", "G", "B"};
        for (int i = 0; i < rgbsliders.length; i++) {
            rgbsliders[i] = new Slider();
            rgbsliders[i].setOrientation(Orientation.VERTICAL);
            rgbsliders[i].setPrefWidth(50);
            rgbsliders[i].setMax(1);
            rgbsliders[i].valueProperty().addListener(this);
            rgbsliders[i].setOnMousePressed((e) -> {
                bindRGBtoHSB();
            });

            VBox.setVgrow(rgbsliders[i], Priority.ALWAYS);

            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().add(rgbsliders[i]);
            vbox.getChildren().add(new Label(rgblabels[i]));
            rgbcontrol.getChildren().add(vbox);
        }

        String[] hsblabels = {"H", "S", "B"};
        for (int i = 0; i < hsbsliders.length; i++) {
            hsbsliders[i] = new Slider();
            hsbsliders[i].setOrientation(Orientation.VERTICAL);
            hsbsliders[i].setPrefWidth(50);
            hsbsliders[i].setMax(1);
            hsbsliders[i].valueProperty().addListener(this);
            hsbsliders[i].setOnMousePressed((e) -> {
                bindHSBtoRGB();
            });

            VBox.setVgrow(hsbsliders[i], Priority.ALWAYS);

            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().add(hsbsliders[i]);
            vbox.getChildren().add(new Label(hsblabels[i]));
            hsbcontrol.getChildren().add(vbox);
        }

        borderpane.setCenter(stackpane);
        borderpane.setLeft(rgbcontrol);
        borderpane.setRight(hsbcontrol);

        primarystage.setScene(new Scene(borderpane, 400, 300));
        primarystage.setMinWidth(600);
        primarystage.setMinHeight(300);
        primarystage.setTitle("Color Chooser");
        primarystage.show();

        invalidated(null);
    }

    @Override
    public void invalidated(Observable observable) {
        Color color = new Color(rgbsliders[0].getValue(), rgbsliders[1].getValue(), rgbsliders[2].getValue(), 1);
        BackgroundFill fill = new BackgroundFill(color, new CornerRadii(20), new Insets(10, 10, 10, 10));
        stackpane.setBackground(new Background(fill));
    }

    public void bindRGBtoHSB() {
        for (Slider slider : rgbsliders) {
            slider.valueProperty().unbind();
        }

        DoubleProperty red = rgbsliders[0].valueProperty();
        DoubleProperty green = rgbsliders[1].valueProperty();
        DoubleProperty blue = rgbsliders[2].valueProperty();

        DoubleBinding hsbh = Bindings.createDoubleBinding(() -> {
            Color color = new Color(red.get(), green.get(), blue.get(), 1);
            return color.getHue();
        }, red, green, blue);

        DoubleBinding hsbs = Bindings.createDoubleBinding(() -> {
            Color color = new Color(red.get(), green.get(), blue.get(), 1);
            return color.getSaturation();
        }, red, green, blue);

        DoubleBinding hsbb = Bindings.createDoubleBinding(() -> {
            Color color = new Color(red.get(), green.get(), blue.get(), 1);
            return color.getBrightness();
        }, red, green, blue);

        hsbsliders[0].valueProperty().bind(hsbh);
        hsbsliders[1].valueProperty().bind(hsbs);
        hsbsliders[2].valueProperty().bind(hsbb);
    }

    public void bindHSBtoRGB() {
        for (Slider slider : hsbsliders) {
            slider.valueProperty().unbind();
        }

        DoubleProperty hue = hsbsliders[0].valueProperty();
        DoubleProperty saturation = hsbsliders[1].valueProperty();
        DoubleProperty brightness = hsbsliders[2].valueProperty();

        DoubleBinding rgbred = Bindings.createDoubleBinding(() -> {
            Color color = new Color(hue.get(), saturation.get(), brightness.get(), 1);
            return color.getRed();
        }, hue, saturation, brightness);

        DoubleBinding rgbgreen = Bindings.createDoubleBinding(() -> {
            Color color = new Color(hue.get(), saturation.get(), brightness.get(), 1);
            return color.getBlue();
        }, hue, saturation, brightness);

        DoubleBinding rgbblue = Bindings.createDoubleBinding(() -> {
            Color color = new Color(hue.get(), saturation.get(), brightness.get(), 1);
            return color.getGreen();
        }, hue, saturation, brightness);

        rgbsliders[0].valueProperty().bind(rgbred);
        rgbsliders[1].valueProperty().bind(rgbgreen);
        rgbsliders[2].valueProperty().bind(rgbblue);
    }
}
