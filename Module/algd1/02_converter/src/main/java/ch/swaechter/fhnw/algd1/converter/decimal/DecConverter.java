package ch.swaechter.fhnw.algd1.converter.decimal;

/**
 * @author Wolfgang Weck
 */
public class DecConverter {
    public static int fromString(String text) {
        return Integer.parseInt(text);
    }

    public static String toString(int x) {
        return Integer.toString(x);
    }
}
