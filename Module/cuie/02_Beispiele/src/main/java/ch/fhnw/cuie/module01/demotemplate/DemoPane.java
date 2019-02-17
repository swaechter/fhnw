package ch.fhnw.cuie.module01.demotemplate;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * @author Dieter Holz
 */
public class DemoPane extends StackPane {
    private Button button;

    public DemoPane() {
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
        button = new Button("Hello World");
    }

    private void layoutParts() {
        getChildren().add(button);
    }
}
