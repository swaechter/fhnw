package com.gluonapplication.countrycantonmap.view;

import com.gluonapplication.countrycantonmap.model.Canton;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class CountryCantonMap extends AbstractView {

    public interface CantonClickCallback {

        void onCantonClick(Canton canton);
    }

    private ObservableList<Canton> cantonList;

    private CantonClickCallback cantonClickCallback;

    private Color activeCantonColor;

    private Color inactiveCantonColor;

    private Color borderColor;

    private Color backgroundColor;

    public CountryCantonMap(ObservableList<Canton> cantonList) {
        initializeData(cantonList);
        initializeSelf();
        layoutParts();
        setupValueChangeListeners();
        drawBackground();
        drawCantonsAndBorders();
    }

    private void initializeData(ObservableList<Canton> cantonList) {
        this.cantonList = cantonList;
    }

    private void initializeSelf() {
        activeCantonColor = Color.valueOf("#990000");
        inactiveCantonColor = Color.valueOf("#006666");
        borderColor = Color.valueOf("#339999");
        backgroundColor = Color.valueOf("#CCE6FF");
    }

    private void layoutParts() {
        for (Canton canton : cantonList) {
            getPane().getChildren().add(canton.getSvgPath());
        }
        getChildren().add(getPane());
    }

    private void setupValueChangeListeners() {
        for (Canton canton : cantonList) {
            canton.getSvgPath().setOnMouseClicked(event -> {
                if (cantonClickCallback != null) {
                    cantonClickCallback.onCantonClick(canton);
                    drawCantonsAndBorders();
                }
            });

            canton.isActiveProperty().addListener((observable, oldValue, newValue) -> {
                drawCantonsAndBorders();
            });
        }
    }

    private void drawCantonsAndBorders() {
        for (Canton canton : cantonList) {
            SVGPath svgPath = canton.getSvgPath();
            svgPath.setFill(canton.isIsActive() ? activeCantonColor : inactiveCantonColor);
            svgPath.setStroke(borderColor);
        }
    }

    private void drawBackground() {
        setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public ObservableList<Canton> getCantonList() {
        return cantonList;
    }

    public CantonClickCallback getCantonClickCallback() {
        return cantonClickCallback;
    }

    public void setCantonClickCallback(CantonClickCallback cantonClickCallback) {
        this.cantonClickCallback = cantonClickCallback;
    }

    public Color getActiveCantonColor() {
        return activeCantonColor;
    }

    public void setActiveCantonColor(Color activeCantonColor) {
        this.activeCantonColor = new Color(activeCantonColor.getRed(), activeCantonColor.getGreen(), activeCantonColor.getBlue(), 1);
        drawCantonsAndBorders();
    }

    public Color getInactiveCantonColor() {
        return inactiveCantonColor;
    }

    public void setInactiveCantonColor(Color inactiveCantonColor) {
        this.inactiveCantonColor = new Color(inactiveCantonColor.getRed(), inactiveCantonColor.getGreen(), inactiveCantonColor.getBlue(), 1);
        drawCantonsAndBorders();
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = new Color(borderColor.getRed(), borderColor.getGreen(), borderColor.getBlue(), 1);
        drawCantonsAndBorders();
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 1);
        drawBackground();
    }
}
