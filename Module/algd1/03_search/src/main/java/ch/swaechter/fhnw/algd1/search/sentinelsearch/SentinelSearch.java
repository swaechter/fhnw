package ch.swaechter.fhnw.algd1.search.sentinelsearch;

public class SentinelSearch {
    public static boolean exists(int[] data, int value) {
        if (data.length == 0) {
            return false;
        }

        int tempval = data[data.length - 1];
        if(tempval == value) {
            return true;
        }

        data[data.length - 1] = value;

        int i = 0;
        while (data[i] != value) {
            i++;
        }

        data[data.length - 1] = tempval;

        return i != data.length - 1;
    }

    public static int firstIndex(int[] data, int value) {
        if (data.length == 0) {
            return -1;
        }

        int tempval = data[data.length - 1];
        data[data.length - 1] = value;

        int i = 0;
        while (data[i] != value) {
            i++;
        }

        data[data.length - 1] = tempval;

        return (data[i] == value) ? i : -1;
    }
}
