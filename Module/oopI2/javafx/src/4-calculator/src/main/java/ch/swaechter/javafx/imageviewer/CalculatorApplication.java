package ch.swaechter.javafx.imageviewer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CalculatorApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primarystage) throws IOException {
        Scene scene = new Scene(new CalculatorWindow());
        scene.getStylesheets().add(this.getClass().getResource("/Style.css").toExternalForm());

        primarystage.setScene(scene);
        primarystage.setTitle("Image Viewer");
        primarystage.setMinWidth(400);
        primarystage.setMinHeight(300);
        primarystage.show();
    }
}
