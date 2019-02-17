package ch.fhnw.cuie.project.countrycantonmap.view;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class AbstractView extends Region {

    private static final double ARTBOARD_WIDTH = 1000;

    private static final double ARTBOARD_HEIGHT = 600;

    private static final double ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;

    private static final double MINIMUM_WIDTH = 100;

    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

    private static final double MAXIMUM_WIDTH = 800;

    private final Pane pane;

    protected AbstractView() {
        pane = new Pane();
        pane.setMaxSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        pane.setMinSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        pane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
    }

    protected Pane getPane() {
        return pane;
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        Insets padding = getPadding();
        double availableWidth = getWidth() - padding.getLeft() - padding.getRight();
        double availableHeight = getHeight() - padding.getTop() - padding.getBottom();
        double width = Math.max(Math.min(Math.min(availableWidth, availableHeight * ASPECT_RATIO), MAXIMUM_WIDTH), MINIMUM_WIDTH);
        double scalingFactor = width / ARTBOARD_WIDTH;
        if (availableWidth > 0 && availableHeight > 0) {
            pane.relocate((getWidth() - ARTBOARD_WIDTH) * 0.5, (getHeight() - ARTBOARD_HEIGHT) * 0.5);
            pane.setScaleX(scalingFactor);
            pane.setScaleY(scalingFactor);
        }
    }

    @Override
    protected double computeMinWidth(double height) {
        Insets padding = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();
        return MINIMUM_WIDTH + horizontalPadding;
    }

    @Override
    protected double computeMinHeight(double width) {
        Insets padding = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();
        return MINIMUM_HEIGHT + verticalPadding;
    }

    @Override
    protected double computePrefWidth(double height) {
        Insets padding = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();
        return ARTBOARD_WIDTH + horizontalPadding;
    }

    @Override
    protected double computePrefHeight(double width) {
        Insets padding = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();
        return ARTBOARD_HEIGHT + verticalPadding;
    }
}
