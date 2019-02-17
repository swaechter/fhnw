package ch.fhnw.cuie.module01.cssplaygrounds.shadows;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Dieter Holz
 */
public class ShadowPlayground extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent rootPanel = new ShadowPane();

		Scene scene = new Scene(rootPanel);

		String fonts = getClass().getResource("/fonts/fonts.css").toExternalForm();
		scene.getStylesheets().add(fonts);

		String stylesheet = getClass().getResource("style.css").toExternalForm();
		scene.getStylesheets().add(stylesheet);

		primaryStage.setTitle("Shadow Playground");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
