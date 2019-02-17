package ch.fhnw.algd1.maxsubsequence.measuring;

public class MaxSequence {

    public static void main(String[] args) {
        try {
            System.out.println("Wenn Ihr Programm korrekt funktioniert:\nSenden Sie das Programm zusammen mit der folgenden Ausgabe an\nIhren Dozenten und an wolfgang.weck@fhnw.ch\n");
            int n = 10; // start length
            double t0, t1;
            t0 = MaxSequenceTest.test(n);
            n *= 2;
            while (t0 < 10 && n < 10_000_000) {
                t1 = MaxSequenceTest.test(n);
                n *= 2;
                System.out.println("      >>> ratio:  " + t1 / t0);
                t0 = t1;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
