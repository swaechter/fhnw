package ch.fhnw.depa.colorpicker;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class ColorPickerApplication extends Application implements Observer {

    private ColorModel colormodel;

    private ScrollBar redscrollbar;

    private ScrollBar greenscrollbar;

    private ScrollBar bluescrollbar;

    private Spinner<Integer> redrgbspinner;

    private Spinner<Integer> greenrgbspinner;

    private Spinner<Integer> bluergbspinner;

    private Label redhexlabel;

    private Label greenhexlabel;

    private Label bluehexlabel;

    private StackPane stackpane;

    public static void main(String[] args) {
        Application.launch(ColorPickerApplication.class, args);
    }

    @Override
    public void start(Stage primarystage) throws Exception {
        // Create the model
        colormodel = new ColorModel();

        // Create main layout
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 450, 250);
        primarystage.setScene(scene);
        primarystage.setTitle("Color Picker");
        primarystage.setResizable(false);
        primarystage.show();

        // Create the grid layout
        GridPane gridpane = new GridPane();
        root.setCenter(gridpane);

        // Create the button layout
        HBox buttonbox = new HBox();
        GridPane.setConstraints(buttonbox, 3, 1);
        gridpane.add(buttonbox, 3, 3);

        // Create the menu bar
        MenuBar menubar = new MenuBar();
        root.setTop(menubar);

        // Create the main menu
        Menu filemenu = new Menu("File");
        menubar.getMenus().add(filemenu);

        MenuItem exitmenuitem = new MenuItem("Exit");
        filemenu.getItems().add(exitmenuitem);

        // Create the attributes menu
        Menu colorsmenu = new Menu("Colors");
        menubar.getMenus().add(colorsmenu);

        MenuItem color1menuitem = new MenuItem("Burnt Sienna"); // 237 108 97
        MenuItem color2menuitem = new MenuItem("Cornflower Blue"); // 97 149 237
        MenuItem color3menuitem = new MenuItem("Sulu"); // 161 237 97
        colorsmenu.getItems().addAll(color1menuitem, color2menuitem, color3menuitem);

        // Add all listeners
        exitmenuitem.setOnAction((actionevent) -> System.exit(0));
        color1menuitem.setOnAction((actionevent) -> colormodel.setColor(237, 108, 97));
        color2menuitem.setOnAction((actionevent) -> colormodel.setColor(97, 149, 237));
        color3menuitem.setOnAction((actionevent) -> colormodel.setColor(161, 237, 97));

        // Create the scrollbars
        redscrollbar = createScrollBar("red", gridpane, 0, 0, 3, 1);
        greenscrollbar = createScrollBar("green", gridpane, 0, 1, 3, 1);
        bluescrollbar = createScrollBar("blue", gridpane, 0, 2, 3, 1);

        // Create the RGB spinners
        redrgbspinner = createSpinner(gridpane, 3, 0);
        greenrgbspinner = createSpinner(gridpane, 3, 1);
        bluergbspinner = createSpinner(gridpane, 3, 2);

        // Create the hex labels
        redhexlabel = createLabel("0", gridpane, 4, 0);
        greenhexlabel = createLabel("0", gridpane, 4, 1);
        bluehexlabel = createLabel("0", gridpane, 4, 2);

        // Create the buttons
        Button darkerbutton = createButton("Darker", buttonbox);
        Button brighterbutton = createButton("Brighter", buttonbox);

        // Create the stack pane
        stackpane = createStackPane(gridpane, 200, 100, 0, 3, 3, 2);

        // Add all listeners
        redscrollbar.valueProperty().addListener((observable, oldvalue, newvalue) -> {
            if (oldvalue.intValue() != newvalue.intValue()) {
                colormodel.setRed(newvalue.intValue());
            }
        });

        greenscrollbar.valueProperty().addListener((observable, oldvalue, newvalue) -> {
            if (oldvalue.intValue() != newvalue.intValue()) {
                colormodel.setGreen(newvalue.intValue());
            }
        });

        bluescrollbar.valueProperty().addListener((observable, oldvalue, newvalue) -> {
            if (oldvalue.intValue() != newvalue.intValue()) {
                colormodel.setBlue(newvalue.intValue());
            }
        });

        redrgbspinner.valueProperty().addListener((observable, oldvalue, newvalue) -> {
            if (oldvalue.intValue() != newvalue.intValue()) {
                colormodel.setRed(newvalue);
            }
        });

        greenrgbspinner.valueProperty().addListener((observable, oldvalue, newvalue) -> {
            if (oldvalue.intValue() != newvalue.intValue()) {
                colormodel.setGreen(newvalue);
            }
        });

        bluergbspinner.valueProperty().addListener((observable, oldvalue, newvalue) -> {
            if (oldvalue.intValue() != newvalue.intValue()) {
                colormodel.setBlue(newvalue);
            }
        });

        darkerbutton.setOnAction((actionevent) -> colormodel.darkenColor(0.7));
        brighterbutton.setOnAction((actionevent) -> colormodel.brightenColor(0.7));

        // Install the observer and set a color
        colormodel.addObserver(this);
        colormodel.setColor(0, 0, 0);
    }

    @Override
    public void update(Observable observable, Object object) {
        Color color = Color.rgb(colormodel.getRed(), colormodel.getGreen(), colormodel.getBlue(), 1);
        BackgroundFill fill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(fill);
        stackpane.setBackground(background);

        redscrollbar.valueProperty().setValue(colormodel.getRed());
        greenscrollbar.valueProperty().setValue(colormodel.getGreen());
        bluescrollbar.valueProperty().setValue(colormodel.getBlue());

        redrgbspinner.getValueFactory().setValue(colormodel.getRed());
        greenrgbspinner.getValueFactory().setValue(colormodel.getGreen());
        bluergbspinner.getValueFactory().setValue(colormodel.getBlue());

        redhexlabel.setText(Integer.toHexString(colormodel.getRed()));
        greenhexlabel.setText(Integer.toHexString(colormodel.getGreen()));
        bluehexlabel.setText(Integer.toHexString(colormodel.getBlue()));
    }

    private ScrollBar createScrollBar(String colorname, GridPane gridpane, int columnindex, int rowindex, int columnspan, int rowspan) {
        ScrollBar scrollbar = new ScrollBar();
        scrollbar.setMin(0);
        scrollbar.setMax(255);
        scrollbar.setOrientation(Orientation.HORIZONTAL);
        scrollbar.setStyle("-fx-background-color:" + colorname + ";");

        GridPane.setMargin(scrollbar, new Insets(5));
        GridPane.setConstraints(scrollbar, columnspan, rowspan);
        gridpane.add(scrollbar, columnindex, rowindex);

        return scrollbar;
    }

    private Spinner<Integer> createSpinner(GridPane gridpane, int columnindex, int rowindex) {
        Spinner<Integer> spinner = new Spinner<>(0, 255, 0);

        GridPane.setMargin(spinner, new Insets(5));
        gridpane.add(spinner, columnindex, rowindex);

        return spinner;
    }

    private Label createLabel(String text, GridPane gridpane, int columnindex, int rowindex) {
        Label label = new Label(text);

        GridPane.setMargin(label, new Insets(5));
        gridpane.add(label, columnindex, rowindex);

        return label;
    }

    private Button createButton(String text, HBox hbox) {
        Button button = new Button(text);

        HBox.setMargin(button, new Insets(5));
        hbox.getChildren().add(button);

        return button;
    }

    private StackPane createStackPane(GridPane gridpane, int width, int height, int columnindex, int rowindex, int columnspan, int rowspan) {
        StackPane stackpane = new StackPane();
        stackpane.setMinWidth(width);
        stackpane.setMinHeight(height);

        GridPane.setMargin(stackpane, new Insets(5));
        GridPane.setConstraints(stackpane, columnspan, rowspan);
        gridpane.add(stackpane, columnindex, rowindex);

        return stackpane;
    }
}
