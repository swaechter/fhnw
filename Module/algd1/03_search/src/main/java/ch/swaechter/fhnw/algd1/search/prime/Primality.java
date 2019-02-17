package ch.swaechter.fhnw.algd1.search.prime;

public class Primality {
    public static boolean isPrime(int x) {
        if (x <= 1) {
            return false;
        }

        int i = 2;
        while (i <= x && x % i != 0) {
            i++;
        }
        return i == x;
    }
}
