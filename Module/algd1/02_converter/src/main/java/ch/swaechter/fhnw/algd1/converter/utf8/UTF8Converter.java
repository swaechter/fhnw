package ch.swaechter.fhnw.algd1.converter.utf8;

/**
 * @author Wolfgang Weck
 */
public class UTF8Converter {
    public static byte[] codePointToUTF(int x) {
        byte[] b = null;
        if (x < 1 << 7) { // up to 7 Bit, 1 Byte
            b = new byte[1];
            b[0] = (byte) x;
        } else if (x < 1 << 11) { // up to 11 Bit, 2 Byte
            b = new byte[2];
            b[1] = (byte) ((x & 0b0011_1111) | 0b1000_0000);
            b[0] = (byte) ((x >>> 6) | 0b1100_0000);
        } else if (x < 1 << 16) { // up to 16 Bit, 3 Byte
            b = new byte[3];
            b[2] = (byte) ((x & 0b0011_1111) | 0b1000_0000);
            x = x >>> 6;
            b[1] = (byte) ((x & 0b0011_1111) | 0b1000_0000);
            b[0] = (byte) ((x >>> 6) | 0b1110_0000);
        } else { // up to 21 Bit, 4 Byte
            b = new byte[4];
            b[3] = (byte) ((x & 0b0011_1111) | 0b1000_0000);
            x = x >>> 6;
            b[2] = (byte) ((x & 0b0011_1111) | 0b1000_0000);
            x = x >>> 6;
            b[1] = (byte) ((x & 0b0011_1111) | 0b1000_0000);
            b[0] = (byte) ((x >>> 6) | 0b1111_0000);
        }
        return b;
    }

    public static int UTFtoCodePoint(byte[] bytes) {
        if (isValidUTF8(bytes)) {
            int x;
            if (bytes.length == 1) x = bytes[0];
            else if (bytes.length == 2) x = bytes[0] & 0b0001_1111;
            else if (bytes.length == 3) x = bytes[0] & 0b0000_1111;
            else x = bytes[0] & 0b0000_0111;
            for (int i = 1; i < bytes.length; i++) {
                x = (x << 6) | (bytes[i] & 0b0011_1111);
            }
            return x;
        } else return 0;
    }

    private static boolean isValidUTF8(byte[] bytes) {
        if (bytes.length == 1) return (bytes[0] & 0b1000_0000) == 0;
        else if (bytes.length == 2) return ((bytes[0] & 0b1110_0000) == 0b1100_0000) && isFollowup(bytes[1]);
        else if (bytes.length == 3)
            return ((bytes[0] & 0b1111_0000) == 0b1110_0000) && isFollowup(bytes[1]) && isFollowup(bytes[2]);
        else if (bytes.length == 4)
            return ((bytes[0] & 0b1111_1000) == 0b1111_0000) && isFollowup(bytes[1]) && isFollowup(bytes[2]) && isFollowup(bytes[3]);
        else return false;
    }

    private static boolean isFollowup(byte b) {
        return (b & 0b1100_0000) == 0b1000_0000;
    }
}
