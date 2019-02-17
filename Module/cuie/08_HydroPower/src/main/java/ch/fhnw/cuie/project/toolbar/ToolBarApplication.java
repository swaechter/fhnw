package ch.fhnw.cuie.project.toolbar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ToolBarApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        HydroToolBar toolBar = new HydroToolBar();

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(toolBar);
        borderPane.setCenter(new TextArea()); // Zur Veranschaulichung eine TextArea

        Button saveButton = toolBar.getSaveButton();
        Button createButton = toolBar.getCreateButton();
        Button deleteButton = toolBar.getDeleteButton();
        Button undoButton = toolBar.getUndoButton();
        Button redoButton = toolBar.getRedoButton();
        Button germanLanguageButton = toolBar.getGermanLanguageButton();
        Button englishLanguageButton = toolBar.getEnglishLanguageButton();
        TextField searchTextField = toolBar.getSearchTextField();

        saveButton.setOnAction(event -> System.out.println("Save button clicked!"));
        createButton.setOnAction(event -> System.out.println("Create button clicked!"));
        deleteButton.setOnAction(event -> System.out.println("Delete button clicked!"));
        undoButton.setOnAction(event -> System.out.println("Undo button clicked!"));
        redoButton.setOnAction(event -> System.out.println("Redo button clicked!"));
        germanLanguageButton.setOnAction(event -> System.out.println("German language button clicked!"));
        englishLanguageButton.setOnAction(event -> System.out.println("English language button clicked!"));
        searchTextField.setOnAction(event -> System.out.println("Search text field submitted!"));
        searchTextField.setOnKeyTyped(event -> System.out.println("Search text field typed!"));

        saveButton.setDisable(false); // Aktiv und weiss, default
        saveButton.setDisable(true); // Inaktiv und ausgegraut

        primaryStage.setTitle("Hydro Toolbar");
        primaryStage.setScene(new Scene(borderPane, 800, 600));
        primaryStage.toFront();
        primaryStage.show();
    }
}
