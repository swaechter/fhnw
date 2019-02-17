package ch.fhnw.cuie.module01.cssplaygrounds.shapes;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Dieter Holz
 */
public class ShapePlayground extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent rootPanel = new ShapePane();

		Scene scene = new Scene(rootPanel);

		primaryStage.setTitle("Shape Playground");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
