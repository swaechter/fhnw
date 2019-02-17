package ch.fhnw.cuie.project.countrycantonmap.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.shape.SVGPath;

import java.util.Objects;

public class Canton {

    private final SVGPath svgPath;

    private final String displayName;

    private final BooleanProperty isActive;

    public Canton(String displayName, String pathValue) {
        this.svgPath = new SVGPath();
        this.svgPath.setContent(pathValue);
        this.displayName = displayName;
        this.isActive = new SimpleBooleanProperty(false);
    }

    public SVGPath getSvgPath() {
        return svgPath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isIsActive() {
        return isActive.get();
    }

    public BooleanProperty isActiveProperty() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive.set(isActive);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Canton canton = (Canton) object;
        return Objects.equals(displayName, canton.displayName);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
