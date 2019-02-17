/*
 * Created on Mar 5, 2016
 */
package ch.fhnw.swa.restaurant;

/**
 * @author Wolfgang Weck
 */
public final class Log {
    private static long startTime;
    private static int sec;

    public static void init(int sec) {
        Log.sec = sec;
        startTime = System.currentTimeMillis();
    }

    public static String formatedTime(int duration) {
        int d = (duration + sec / 2) / sec;
        if (d < 3600) return String.format("%d:%02d", d / 60, d % 60);
        else
            return String.format("%02d:%02d:%02d", d / (3600), (d / 60) % 60, d % 60);
    }

    public static void event(String msg) {
        long t = (System.currentTimeMillis() - startTime + sec / 2) / sec;
        System.out.printf("%02d:%02d:%02d - %s%n", t / (3600), (t / 60) % 60,
                t % 60, msg);
    }
}
