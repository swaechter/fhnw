package ch.fhnw.cuie.module01.icondemo;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * @author Dieter Holz
 */
public class IconPane extends StackPane {
	private static final String SAVE   = "\uf0c7";

	private Button button;

	public IconPane() {
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
		button = new Button(SAVE);
		button.getStyleClass().add("icon");
	}

	private void layoutParts() {
		getChildren().add(button);
	}
}
