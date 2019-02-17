package ch.fhnw.cuie.module01.cssplaygrounds.bordersandbackgrounds;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * @author Dieter Holz
 */
public class BordersAndBackgroundPane extends HBox {
	private Region regionA;
	private Region regionB;
	private Region regionC;

	public BordersAndBackgroundPane() {
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
		regionA = new Region();
		regionA.getStyleClass().addAll("playground", "backgroundPlayground");

		regionB = new Region();
		regionB.getStyleClass().addAll("playground", "borderPlayground");

		regionC = new Region();
		regionC.getStyleClass().addAll("playground", "borderAndBackgroundPlayground");
	}

	private void layoutParts() {
		setHgrow(regionA, Priority.ALWAYS);
		setHgrow(regionB, Priority.ALWAYS);
		getChildren().addAll(regionA, regionB, regionC);
	}
}
