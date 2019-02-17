package ch.fhnw.cuie.project.toolbar;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

import java.io.InputStream;

public class HydroToolBar extends ToolBar {

    private static final String FONT_PATH = "/fonts/fontawesome-webfont.ttf";

    private static final String STYLE_PATH = "toolbar-control.css";

    private static final int FONT_SIZE = 18;

    private Font customFont;

    private Button saveButton;

    private Button createButton;

    private Button deleteButton;

    private Button undoButton;

    private Button redoButton;

    private Region horizontalRegionSpacer;

    private Button germanLanguageButton;

    private Button englishLanguageButton;

    private TextField searchTextField;

    public HydroToolBar() {
        initializeFont();
        initializeStyle();
        initializeParts();
        layoutParts();
    }

    private void initializeFont() {
        try {
            InputStream fontInputStream = HydroToolBar.class.getResourceAsStream(FONT_PATH);
            customFont = Font.loadFont(fontInputStream, FONT_SIZE);
        } catch (Exception exception) {
            throw new RuntimeException("Achtung: Die Datei " + FONT_PATH + " konnte nicht geladen/verarbeitet werden. Bitte füge Sie deinem Projekt hinzu!", exception);
        }
    }

    private void initializeStyle() {
        try {
            String stylesheet = getClass().getResource(STYLE_PATH).toExternalForm();
            getStylesheets().add(stylesheet);
            getStyleClass().add("toolbar-control");
        } catch (Exception exception) {
            throw new RuntimeException("Achtung: Die Datei " + STYLE_PATH + " konnte nicht geladen/verarbeitet werden. Bitte füge Sie deinem Projekt hinzu!", exception);
        }
    }

    private void initializeParts() {
        horizontalRegionSpacer = new Region();
        saveButton = createIconButton('\uf0c7');
        createButton = createIconButton('\uf067');
        deleteButton = createIconButton('\uf00d');
        undoButton = createIconButton('\uf0e2');
        redoButton = createIconButton('\uf01e');
        germanLanguageButton = createTextButton("DE");
        englishLanguageButton = createTextButton("EN");
        searchTextField = createTextField();
    }

    private void layoutParts() {
        HBox.setHgrow(horizontalRegionSpacer, Priority.ALWAYS);
        getItems().addAll(saveButton, createButton, deleteButton, undoButton, redoButton, horizontalRegionSpacer, germanLanguageButton, englishLanguageButton, searchTextField);
    }

    private Button createIconButton(char iconName) {
        Button button = new Button();
        button.setText(String.valueOf(iconName));
        button.setFont(customFont);
        button.getStyleClass().add("button-icon");
        return button;
    }

    private Button createTextButton(String languageName) {
        Button button = new Button();
        button.setText(languageName);
        button.setFont(customFont);
        button.getStyleClass().add("button-text");
        return button;
    }

    private TextField createTextField() {
        TextField textField = new TextField();
        textField.getStyleClass().add("textfield");
        return textField;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getCreateButton() {
        return createButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getUndoButton() {
        return undoButton;
    }

    public Button getRedoButton() {
        return redoButton;
    }

    public Button getGermanLanguageButton() {
        return germanLanguageButton;
    }

    public Button getEnglishLanguageButton() {
        return englishLanguageButton;
    }

    public TextField getSearchTextField() {
        return searchTextField;
    }
}
