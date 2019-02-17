package ch.swaechter.fhnw.algd1.converter.character;

/**
 * @author Wolfgang Weck
 */
public class CharConverter {
    public static String toString(int x) {
        StringBuilder s = new StringBuilder();
        s.appendCodePoint(x);
        return s.toString();
    }

    public static int fromString(String text) {
        return text.codePointAt(0);
    }
}
