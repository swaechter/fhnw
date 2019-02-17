package ch.swaechter.fhnw.algd1.binary;

public class Binary {

    public static void main(String[] args) {
        System.out.println("Value -128 as binary: " + toString(-128));
        System.out.println("Value -3 as binary: " + toString(-3));
        System.out.println("Value 3 as binary: " + toString(3));
        System.out.println("Value 127 as binary: " + toString(127));

        System.out.println("Value -128 as decimal: " + fromString("10000000"));
        System.out.println("Value -3 as decimal: " + fromString("11111101"));
        System.out.println("Value 3 as decimal: " + fromString("00000011"));
        System.out.println("Value 127 as decimal: " + fromString("01111111"));
    }

    public static String toString(int value) {
        if (value < 0) {
            value += 256;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            builder.insert(0, (char) ('0' + (value % 2)));
            value /= 2;
        }
        return builder.toString();
    }

    public static int fromString(String value) {
        int builder = 0;
        for (int i = 0; i < value.length(); i++) {
            builder = 2 * builder;
            if (value.charAt(i) == '1') {
                builder++;
            }
        }
        return builder > 127 ? builder - 256 : builder;
    }
}
