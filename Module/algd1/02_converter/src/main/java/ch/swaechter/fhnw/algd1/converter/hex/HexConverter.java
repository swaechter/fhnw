package ch.swaechter.fhnw.algd1.converter.hex;

/**
 * @author Wolfgang Weck
 */
public class HexConverter {
    public static String toString(int x) {
        StringBuilder s = new StringBuilder();
        if (x == 0) return "0";
        boolean neg = x < 0;
        if (neg) x = -x;
        while (x > 0) {
            int d = x % 16;
            if (d < 10) s.insert(0, (char) ('0' + d));
            else s.insert(0, (char) ('A' - 10 + d));
            x = x / 16;
        }
        if (neg) s.insert(0, '-');
        return s.toString();
    }

    public static int fromString(String text) {
        int x = 0, i = 0;
        boolean neg = text.charAt(0) == '-';
        if (neg) i++;
        while (i < text.length()) {
            x = 16 * x;
            char c = text.charAt(i);
            if (c <= '9') x = x + c - '0';
            else x = x + c - 'A' + 10;
            i++;
        }
        return x;
    }
}
