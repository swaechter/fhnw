package ch.swaechter.fhnw.algd1.algorithms;

public class Algorithms {

    public static void main(String[] args) {
        System.out.println("Is 1 in {1,2,3}: " + hasElement(new int[]{1, 2, 3}, 1));
        System.out.println("Is 1 in {2,1,3}: " + hasElement(new int[]{2, 1, 3}, 1));
        System.out.println("Is 1 in {2,3,1}: " + hasElement(new int[]{2, 3, 1}, 1));
        System.out.println("Is 1 in {2,3,2}: " + hasElement(new int[]{2, 3, 2}, 1));

        System.out.println("All greater than 0 in {1,2,3}: " + areAllElementsGreater(new int[]{1, 2, 3}, 0));
        System.out.println("All greater than 0 in {1,0,3}: " + areAllElementsGreater(new int[]{1, 0, 3}, 0));
        System.out.println("All greater than 0 in {1,2,0}: " + areAllElementsGreater(new int[]{1, 2, 0}, 0));

        System.out.println("Power of 2 that is larger than 123456: " + getPowerOfTwo(123456));
    }

    // Suche den ersten Index i bei dem gilt array[i] == element. Ist i kleiner der Länge und ist array[i] == element, so existiert das Element im Array.
    public static boolean hasElement(int[] array, int element) {
        int i = 0;
        while (i < array.length && array[i] != element) {
            i++;
        }
        return i < array.length && array[i] == element;
    }

    // Suche nach Elementen array[i] > element. Ist i gleich der Länge des Arrays, so sind alle Werte grösser als das Element
    public static boolean areAllElementsGreater(int[] array, int element) {
        int i = 0;
        while (i < array.length && array[i] > element) {
            i++;
        }
        return i == array.length;
    }

    // Suche nach einem Wert grösser als Limit. Ist dieser grösser, ist der Exponent der gesuchte Wert
    public static int getPowerOfTwo(int limit) {
        int x = 1;
        while(x <= limit) {
            x *= 2;
        }
        return x;
    }
}
