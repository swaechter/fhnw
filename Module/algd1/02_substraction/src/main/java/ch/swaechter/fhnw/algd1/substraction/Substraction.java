package ch.swaechter.fhnw.algd1.substraction;

public class Substraction {

    public static void main(String[] args) {
        substractFalse();
        substractTrue();
    }

    public static void substractFalse() {
        int n = 80;
        double x = 1, s = Math.pow(2, n + 1);
        while (n >= 0) {
            s = s - x;
            x = x * 2;
            n--;
        }
        System.out.println("s: " + s + " " + (s == 1.0) + ", " + (s <= 2.0));
    }

    public static void substractTrue() {
        int n = 80;
        double x = Math.pow(2, n), s = Math.pow(2, n + 1);
        while (n >= 0) {
            s = s - x;
            x = x / 2;
            n--;
        }
        System.out.println("s: " + s + " " + (s == 1.0) + ", " + (s <= 2.0));
    }
}
