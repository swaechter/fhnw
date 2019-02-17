package ch.fhnw.cuie.module01.tablestyling;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;


public class TableStylingPane extends StackPane {
    private TableView<PersonPM> table;

    public TableStylingPane() {
        initializeSelf();
        initializeParts();
        layoutParts();
    }

    private void initializeSelf() {
        String fonts = getClass().getResource("/fonts/fonts.css").toExternalForm();
        getStylesheets().add(fonts);

        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);
    }

    private void initializeParts() {
        ObservableList<PersonPM> data = FXCollections.observableArrayList();
        data.addAll(new PersonPM("Hugo"     , "Maier"              , "Basel"),
                    new PersonPM("Bernd"    , "Mayer"              , "Brugg"),
                    new PersonPM("Karl"     , "Müller-Lüdenscheidt", "Aarau"),
                    new PersonPM("Urs"      , "Unteregger"         , "Olten"),
                    new PersonPM("Beat"     , "Schmidt"            , "Genf"),
                    new PersonPM("Birgit"   , "Lamard"             , "St. Gallen"),
                    new PersonPM("Heidi"    , "Heilmann"           , "Winterthur"),
                    new PersonPM("Claudia"  , "Basler"             , "Lugano"),
                    new PersonPM("Petra"    , "Peters"             , "Brig"),
                    new PersonPM("Ursula"   , "Unteregger"         , "Baden"),
                    new PersonPM("Barbara"  , "Bauer"              , "Bern"),
                    new PersonPM("Mechthild", "Gross"              , "Spiez"),
                    new PersonPM("Andrea"   , "Unruh"              , "Liestal"),
                    new PersonPM("Gunter"   , "Burger"             , "Frauenfeld"),
                    new PersonPM("Hagen"    , "Wagner"             , "Schaffhausen"),
                    new PersonPM("Siegfried", "Lindner"            , "Chur")
                   );
        table = new TableView<>(data);

        TableColumn<PersonPM, String> firstNameCol = new TableColumn<>("Vorname");
        firstNameCol.setCellValueFactory(cell -> cell.getValue().firstNameProperty());

        TableColumn<PersonPM, String> lastNameCol = new TableColumn<>("Nachname");
        lastNameCol.setCellValueFactory(cell -> cell.getValue().lastNameProperty());

        TableColumn<PersonPM, String> cityCol = new TableColumn<>("Stadt");
        cityCol.setCellValueFactory(cell -> cell.getValue().cityProperty());

        table.getColumns().addAll(firstNameCol, lastNameCol, cityCol);
    }

    private void layoutParts() {
        getChildren().add(table);
    }
}
