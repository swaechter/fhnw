package ch.fhnw.cuie.module01.tablestyling;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TableStylingStarter extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent rootPanel = new TableStylingPane();

		Scene scene = new Scene(rootPanel);

		primaryStage.setTitle("Styling von Table und Scrollbar");
		primaryStage.setScene(scene);
		primaryStage.setWidth(300);
		primaryStage.setHeight(300);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
