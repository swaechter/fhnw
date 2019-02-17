package ch.fhnw.cuie.module01.cssplaygrounds.gradients;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Dieter Holz
 */
public class GradientPlayground extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent rootPanel = new GradientPane();

		Scene scene = new Scene(rootPanel);

		primaryStage.setTitle("Gradient Playground");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
