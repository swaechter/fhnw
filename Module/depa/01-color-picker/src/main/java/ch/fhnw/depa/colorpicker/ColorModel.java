package ch.fhnw.depa.colorpicker;

import java.util.Observable;

public class ColorModel extends Observable {

    private int red;

    private int green;

    private int blue;

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        setColor(red, getGreen(), getBlue());
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        setColor(getRed(), green, getBlue());
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        setColor(getRed(), getGreen(), blue);
    }

    public void setColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        setChanged();
        notifyObservers();
    }

    // See https://graphicdesign.stackexchange.com/questions/75417/how-to-make-a-given-color-a-bit-darker-or-lighter
    public void darkenColor(double ratio) {
        int newred = (int) (red * ratio);
        int newgreen = (int) (green * ratio);
        int newblue = (int) (blue * ratio);
        setColor(newred, newgreen, newblue);
    }

    // See https://graphicdesign.stackexchange.com/questions/75417/how-to-make-a-given-color-a-bit-darker-or-lighter
    public void brightenColor(double ratio) {
        int newred = red + (int) Math.ceil((255 - red) * ratio);
        int newgreen = green + (int) Math.ceil((255 - green) * ratio);
        int newblue = blue + (int) Math.ceil((255 - blue) * ratio);
        setColor(newred, newgreen, newblue);
    }
}
