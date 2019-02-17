package ch.fhnw.cuie.module01.cssplaygrounds.bordersandbackgrounds;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Dieter Holz
 */
public class BordersAndBackgroundPlayground extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent rootPanel = new BordersAndBackgroundPane();

		Scene scene = new Scene(rootPanel);

		primaryStage.setTitle("Borders-Background-Playground");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
