package ch.swaechter.javafx.addressviewer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DummyApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primarystage) throws Exception {
        Button button = new Button();
        button.setText("Dummy App");
        button.setOnAction(event -> {
            System.out.println("Hello form the Dummy App");
        });

        StackPane root = new StackPane();
        root.getChildren().add(button);
        primarystage.setScene(new Scene(root, 300, 250));
        primarystage.setTitle("Dummy App");
        primarystage.show();
    }
}
