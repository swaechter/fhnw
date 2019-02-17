package ch.swaechter.fhnw.algd1.search.binsearchfirst;

public class BinSearchFirstElement {

    public static int binSearch(int[] data, int value) {
        int l = -1, h = data.length;
        while (l + 1 != h) {
            int m = l + (h - l) / 2;
            if (data[m] < value) {
                l = m;
            } else {
                h = m;
            }
        }
        return h;
    }
}
