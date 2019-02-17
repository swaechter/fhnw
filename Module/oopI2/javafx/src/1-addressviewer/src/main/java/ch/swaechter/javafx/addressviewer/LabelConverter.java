package ch.swaechter.javafx.addressviewer;

import javafx.util.StringConverter;

public class LabelConverter extends StringConverter<Double> {

    private double max;

    public LabelConverter(double max) {
        this.max = max;
    }

    @Override
    public String toString(Double value) {
        return String.format("%.0f", max - value);
    }

    @Override
    public Double fromString(String value) {
        return 0.0;
    }
}
