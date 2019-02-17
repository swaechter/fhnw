package ch.fhnw.algd1.maxsubsequence;

public class MaxSubProblem {

    public int maxSub1(int[] data) {
        int max = 0;
        for (int i = 0; i < data.length; i++) {
            int income = 0;
            for (int j = i; j < data.length; j++) {
                income += data[j];
                if (income > max) {
                    max = income;
                }
            }
        }
        return max;
    }

    public int maxSub2(int[] data) {
        // Make all values absolute
        int tmpvalue = 0;
        int[] tmpdata = new int[data.length + 1];
        for (int i = 0; i < data.length; i++) {
            tmpvalue += data[i];
            tmpdata[i + 1] = tmpvalue;
        }
        tmpdata[0] = 0;

        // Search for the maximum
        int max = 0;
        for (int i = 0; i < tmpdata.length; i++) {
            for (int j = i + 1; j < tmpdata.length; j++) {
                int diff = tmpdata[j] - tmpdata[i];
                if (diff > max) {
                    max = diff;
                }
            }
        }
        return max;
    }

    public int maxSub(int[] data) {
        int maxend = data[0];
        int maxfar = data[0];
        for (int i = 1; i < data.length; i++) {
            maxend = Math.max(data[i], maxend + data[i]);
            maxfar = Math.max(maxfar, maxend);
        }
        return maxfar > 0 ? maxfar : 0;
    }
}
