package ch.fhnw.cuie.module01.cssplaygrounds.gradients;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * @author Dieter Holz
 */
public class GradientPane extends HBox {
	private Region regionA;
	private Region regionB;

	public GradientPane() {
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
		regionA.getStyleClass().addAll("playground", "linearGradientPlayground");

		regionB = new Region();
		regionB.getStyleClass().addAll("playground", "radialGradientPlayground");
	}

	private void layoutParts() {
		setHgrow(regionA, Priority.ALWAYS);
		setHgrow(regionB, Priority.ALWAYS);
		getChildren().addAll(regionA, regionB);
	}
}
