package ch.fhnw.cuie.project.countrycantonmap;

import ch.fhnw.cuie.project.countrycantonmap.model.Canton;
import ch.fhnw.cuie.project.countrycantonmap.model.CantonUtils;
import ch.fhnw.cuie.project.countrycantonmap.view.CountryCantonMap;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CountryCantonMapApplication extends Application {

    private CountryCantonMap countryCantonMap;

    private Label activeCantonColorLabel;

    private ColorPicker activeCantonColorPicker;

    private Label inactiveCantonColorLabel;

    private ColorPicker inactiveCantonColorPicker;

    private Label borderColorLabel;

    private ColorPicker borderColorPicker;

    private Label backgroundColorLabel;

    private ColorPicker backgroundColorPicker;

    private Label cantonListViewLabel;

    private ListView<Canton> cantonListView;

    private VBox vBox;

    private BorderPane borderPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initializeParts();
        layoutParts(primaryStage);
        setupValueChangeListeners();
        setupColors();
    }

    private void initializeParts() {
        ObservableList<Canton> observableCantonList = CantonUtils.getAllCantons();
        countryCantonMap = new CountryCantonMap(observableCantonList);

        activeCantonColorLabel = new Label("Canton Active");
        activeCantonColorPicker = new ColorPicker();
        activeCantonColorPicker.setValue(countryCantonMap.getActiveCantonColor());

        inactiveCantonColorLabel = new Label("Canton Inactive");
        inactiveCantonColorPicker = new ColorPicker();
        inactiveCantonColorPicker.setValue(countryCantonMap.getInactiveCantonColor());

        borderColorLabel = new Label("Border");
        borderColorPicker = new ColorPicker();
        borderColorPicker.setValue(countryCantonMap.getBorderColor());

        backgroundColorLabel = new Label("Background");
        backgroundColorPicker = new ColorPicker();
        backgroundColorPicker.setValue(countryCantonMap.getBackgroundColor());

        cantonListViewLabel = new Label("Current Canton");
        cantonListView = new ListView<>(observableCantonList);
        cantonListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void layoutParts(Stage stage) {
        vBox = new VBox();
        vBox.getChildren().addAll(activeCantonColorLabel, activeCantonColorPicker, inactiveCantonColorLabel, inactiveCantonColorPicker, borderColorLabel, borderColorPicker, backgroundColorLabel, backgroundColorPicker, cantonListViewLabel, cantonListView);

        borderPane = new BorderPane();
        borderPane.setCenter(countryCantonMap);
        borderPane.setRight(vBox);

        Scene scene = new Scene(borderPane, 1000, 600);
        stage.setTitle("Switzerland <3");
        stage.setScene(scene);
        stage.show();
    }

    private void setupValueChangeListeners() {
        cantonListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Canton> selectedCantons = cantonListView.getSelectionModel().getSelectedItems();
            for (Canton canton : countryCantonMap.getCantonList()) {
                canton.setIsActive(selectedCantons.contains(canton));
            }
        });

        countryCantonMap.setCantonClickCallback(canton -> {
            System.out.println("Canton clicked: " + canton);
            //canton.setIsActive(!canton.isIsActive());
        });

        activeCantonColorPicker.setOnAction(actionEvent -> countryCantonMap.setActiveCantonColor(activeCantonColorPicker.getValue()));
        inactiveCantonColorPicker.setOnAction(actionEvent -> countryCantonMap.setInactiveCantonColor(inactiveCantonColorPicker.getValue()));
        borderColorPicker.setOnAction(actionEvent -> countryCantonMap.setBorderColor(borderColorPicker.getValue()));
        backgroundColorPicker.setOnAction(actionEvent -> updateBackground());
    }

    private void setupColors() {
        updateBackground();
    }

    private void updateBackground() {
        Color color = backgroundColorPicker.getValue();
        color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 1);

        countryCantonMap.setBackgroundColor(color);
        borderPane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.setBackground(Background.EMPTY);
    }
}
