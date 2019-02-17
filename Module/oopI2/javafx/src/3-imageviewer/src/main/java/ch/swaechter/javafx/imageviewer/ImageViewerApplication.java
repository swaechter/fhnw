package ch.swaechter.javafx.imageviewer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Locale;

public class ImageViewerApplication extends Application {

    private static final String[] BUTTON_NAMES = {"<<<", "<", ">", ">>>", "Open"};

    private static final String[] SIZE_PREFIXES = {" bytes", "KB", "MB", " GB", " TB"};

    private final ListView<File> filelistview = new ListView<>();

    private final ImageViewerPanel imagepanel = new ImageViewerPanel();

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

        ObservableList<File> data = FXCollections.observableArrayList();
        filelistview.setItems(data);
        filelistview.setCellFactory(e -> new FileListCell());
        SelectionModel<File> selection = filelistview.getSelectionModel();
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

        VBox.setMargin(buttons[4], new Insets(50, 0, 10, 0));

        buttons[0].setOnAction(e -> {
            if (currentfiles != null) {
                currentindex = 0;
                imagepanel.setPicture(currentfiles[currentindex]);
                selection.select(currentindex);
                filelistview.scrollTo(currentindex);
                updateStatus();
            }
        });

        buttons[1].setOnAction(e -> {
            if (currentfiles != null && currentindex > 0) {
                currentindex--;
                imagepanel.setPicture(currentfiles[currentindex]);
                selection.select(currentindex);
                filelistview.scrollTo(currentindex);
                updateStatus();
            }
        });

        buttons[2].setOnAction(e -> {
            if (currentfiles != null && currentindex < currentfiles.length - 1) {
                currentindex++;
                imagepanel.setPicture(currentfiles[currentindex]);
                selection.select(currentindex);
                filelistview.scrollTo(currentindex);
                updateStatus();
            }
        });

        buttons[3].setOnAction(e -> {
            if (currentfiles != null) {
                currentindex = currentfiles.length - 1;
                imagepanel.setPicture(currentfiles[currentindex]);
                selection.select(currentindex);
                filelistview.scrollTo(currentindex);
                updateStatus();
            }
        });

        buttons[4].setOnAction(e -> {
            DirectoryChooser directorychooser = new DirectoryChooser();
            directorychooser.setInitialDirectory(currentpath);
            File newcurrentpath = directorychooser.showDialog(primarystage);
            if (newcurrentpath != null) {
                currentpath = newcurrentpath;
                currentfiles = currentpath.listFiles();
                if (currentfiles.length == 0) {
                    currentfiles = null;
                } else {
                    currentindex = 0;
                    data.clear();
                    data.setAll(currentfiles);
                    imagepanel.setPicture(currentfiles[currentindex]);
                    directorychooser.setInitialDirectory(currentpath);
                }
            }
            updateStatus();
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

    private static class FileListCell extends ListCell<File> {
        @Override
        protected void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) setText(item.getName());
        }
    }
}
