package ch.swaechter.javafx.imageviewer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Locale;

public class ImageViewerApplication extends Application {

    private static final String[] BUTTON_NAMES = {"<<<", "<", ">", ">>>"};

    private static final String[] SIZE_PREFIXES = {" bytes", "KB", "MB", " GB", " TB"};

    private final ListView<File> filelistview = new ListView<>();

    private ObservableList<File> data = FXCollections.observableArrayList();

    private SelectionModel<File> selection;

    private BooleanProperty hasfolderopen = new SimpleBooleanProperty(false);

    private final ImagePanel imagepanel = new ImagePanel();

    private CheckBox checkbox = new CheckBox();

    private final TextField infotextfield = new TextField();

    private File currentpath;

    private File[] currentfiles;

    private int currentindex;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primarystage) throws IOException {
        BorderPane borderpane = new BorderPane();
        borderpane.setPadding(new Insets(10, 10, 10, 10));

        primarystage.setScene(new Scene(borderpane));
        primarystage.setTitle("Image Viewer");
        primarystage.setMinWidth(700);
        primarystage.setMinHeight(360);

        VBox controlbox = new VBox();
        HBox elementbox = new HBox();

        elementbox.getChildren().addAll(new Separator(Orientation.VERTICAL), controlbox);

        data = FXCollections.observableArrayList();
        filelistview.setItems(data);
        filelistview.setCellFactory(e -> new FileListCell());
        selection = filelistview.getSelectionModel();
        selection.selectedIndexProperty().addListener(e -> {
            currentindex = selection.getSelectedIndex();
            imagepanel.setPicture(currentfiles[currentindex]);
            updateStatus();
        });

        imagepanel.setMinSize(100, 100);

        infotextfield.setEditable(false);

        borderpane.setLeft(filelistview);
        borderpane.setCenter(imagepanel);
        borderpane.setRight(elementbox);
        borderpane.setBottom(infotextfield);
        BorderPane.setMargin(controlbox, new Insets(20, 10, 0, 20));

        Button[] buttons = new Button[BUTTON_NAMES.length];
        for (int i = 0; i < BUTTON_NAMES.length; ++i) {
            buttons[i] = new Button(BUTTON_NAMES[i]);
            buttons[i].setPrefWidth(50);
            VBox.setMargin(buttons[i], new Insets(10, 0, 10, 0));
            controlbox.getChildren().add(buttons[i]);
        }

        MenuBar menubar = new MenuBar();
        Menu filemenu = new Menu("File");
        Menu navimenu = new Menu("Navigate");
        Menu viewmenu = new Menu("View");
        menubar.getMenus().addAll(filemenu, navimenu, viewmenu);

        borderpane.setTop(menubar);
        BorderPane.setMargin(menubar, new Insets(-12, -10, 10, -10));

        MenuItem fileopenitem = new MenuItem("Open ");
        fileopenitem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        fileopenitem.setOnAction(e -> {
            handleOpen(primarystage);
        });

        MenuItem fileexititem = new MenuItem("Exit ");
        fileexititem.setAccelerator(new KeyCodeCombination(KeyCode.ESCAPE));
        fileexititem.setOnAction(e -> {
            Platform.exit();
        });

        filemenu.getItems().addAll(fileopenitem, fileexititem);

        MenuItem navifirstitem = new MenuItem("First Image ");
        navifirstitem.setAccelerator(new KeyCodeCombination(KeyCode.HOME));
        navifirstitem.setOnAction(e -> {
            handleFirst();
        });

        MenuItem navipreviousitem = new MenuItem("Previous Image ");
        navipreviousitem.setAccelerator(new KeyCodeCombination(KeyCode.LEFT));
        navipreviousitem.setOnAction(e -> {
            handlePrevious();
        });

        MenuItem navinextitem = new MenuItem("Next Image ");
        navinextitem.setAccelerator(new KeyCodeCombination(KeyCode.RIGHT));
        navinextitem.setOnAction(e -> {
            handleNext();
        });

        MenuItem navilastitem = new MenuItem("Last Image ");
        navilastitem.setAccelerator(new KeyCodeCombination(KeyCode.END));
        navilastitem.setOnAction(e -> {
            handleLast();
        });

        navimenu.getItems().addAll(navifirstitem, navipreviousitem, navinextitem, navilastitem);
        navimenu.disableProperty().bind(hasfolderopen.not());

        // adding items for "View"
        CheckMenuItem viewlist = new CheckMenuItem("File List  ");
        viewlist.setAccelerator(new KeyCodeCombination(KeyCode.L));
        viewlist.setSelected(true);
        viewlist.selectedProperty().bindBidirectional(checkbox.selectedProperty());

        CheckMenuItem viewgray = new CheckMenuItem("Grayscale  ");
        viewgray.setAccelerator(new KeyCodeCombination(KeyCode.G));
        viewgray.setSelected(false);
        viewgray.setOnAction(e -> {
            imagepanel.setMode(viewgray.isSelected());
        });

        MenuItem viewhistogram = new MenuItem("Histogram ");
        viewhistogram.setAccelerator(new KeyCodeCombination(KeyCode.H));
        viewhistogram.setOnAction(e -> {
            imagepanel.showCurrentHistogram();
        });
        viewmenu.getItems().addAll(viewlist, viewgray, viewhistogram);

        checkbox.setText("List");
        checkbox.setSelected(true);
        VBox.setMargin(checkbox, new Insets(20, 0, 0, 10));
        controlbox.getChildren().add(checkbox);


        buttons[0].setOnAction(e -> {
            handleFirst();
        });

        buttons[1].setOnAction(e -> {
            handlePrevious();
        });

        buttons[2].setOnAction(e -> {
            handleNext();
        });

        buttons[3].setOnAction(e -> {
            handleLast();
        });

        checkbox.selectedProperty().addListener(e -> {
            if (checkbox.isSelected()) {
                borderpane.setLeft(filelistview);
            } else {
                borderpane.getChildren().remove(filelistview);
            }
        });

        updateStatus();

        primarystage.show();
    }

    @Override
    public void init() throws IOException {
        File ini = new File("picview.ini");
        if (!ini.exists()) {
            currentpath = new File("c:/");
        } else {
            BufferedReader textIn = new BufferedReader(new FileReader(ini));
            currentpath = new File(textIn.readLine());
            textIn.close();
        }
    }

    @Override
    public void stop() throws IOException {
        if (currentpath != null) {
            File ini = new File("picview.ini");
            BufferedWriter textOut = new BufferedWriter(new FileWriter(ini));
            textOut.write(currentpath.getAbsolutePath());
            textOut.newLine();
            textOut.close();
        }
    }

    public void updateStatus() {
        if (currentfiles == null || currentfiles.length == 0) {
            infotextfield.setText("No folder open");
        } else {
            int scale = 0;
            double size = currentfiles[currentindex].length();
            while (size >= 1024) {
                size /= 1024;
                ++scale;
            }
            if (scale > 0) {
                infotextfield.setText(" " + (currentindex + 1) + " / " + currentfiles.length + "   (" + String.format(Locale.ENGLISH, "%.1f", size) + SIZE_PREFIXES[scale] + ")");
            } else {
                infotextfield.setText(" " + (currentindex + 1) + " / " + currentfiles.length + "   (" + String.format("%.0f", size) + SIZE_PREFIXES[scale] + ")");
            }
        }
    }

    public void handleOpen(Stage stage) {
        DirectoryChooser directorychooser = new DirectoryChooser();
        directorychooser.setInitialDirectory(currentpath);
        File newcurrentpath = directorychooser.showDialog(stage);
        if (newcurrentpath != null) {
            currentpath = newcurrentpath;
            currentfiles = currentpath.listFiles();
            if (currentfiles.length == 0) {
                currentfiles = null;
                hasfolderopen.setValue(false);
            } else {
                currentindex = 0;
                data.clear();
                data.setAll(currentfiles);
                imagepanel.setPicture(currentfiles[currentindex]);
                directorychooser.setInitialDirectory(currentpath);
                hasfolderopen.setValue(true);
            }
        }
        updateStatus();
    }

    public void handleFirst() {
        if (currentfiles != null) {
            currentindex = 0;
            imagepanel.setPicture(currentfiles[currentindex]);
            selection.select(currentindex);
            filelistview.scrollTo(currentindex);
            updateStatus();
        }
    }

    public void handlePrevious() {
        if (currentfiles != null && currentindex > 0) {
            currentindex--;
            imagepanel.setPicture(currentfiles[currentindex]);
            selection.select(currentindex);
            filelistview.scrollTo(currentindex);
            updateStatus();
        }
    }

    public void handleNext() {
        if (currentfiles != null && currentindex < currentfiles.length - 1) {
            currentindex++;
            imagepanel.setPicture(currentfiles[currentindex]);
            selection.select(currentindex);
            filelistview.scrollTo(currentindex);
            updateStatus();
        }
    }

    public void handleLast() {
        if (currentfiles != null) {
            currentindex = currentfiles.length - 1;
            imagepanel.setPicture(currentfiles[currentindex]);
            selection.select(currentindex);
            filelistview.scrollTo(currentindex);
            updateStatus();
        }
    }

    private static class FileListCell extends ListCell<File> {
        @Override
        protected void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) setText(item.getName());
        }
    }
}
