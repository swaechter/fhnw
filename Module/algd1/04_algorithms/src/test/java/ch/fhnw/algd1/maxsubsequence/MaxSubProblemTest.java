package ch.fhnw.algd1.maxsubsequence;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaxSubProblemTest {
    private MaxSubProblem ms;

    @Before
    public void init() {
        ms = new MaxSubProblem();
    }

    @Test
    public void test1() {
        int[] numbers = new int[]{1, 3, -5, 3, 3, 2, -9, -2};
        assertEquals(8, ms.maxSub(numbers));
    }

    @Test
    public void test2() {
        int[] numbers = new int[]{31, -41, 59, 26, -53, 58, 97, -93, -23};
        assertEquals(187, ms.maxSub(numbers));
    }

    @Test
    public void testGlobalMaxBeforeMin() {
        int[] numbers = new int[]{31, -41, 259, 26, -453, 58, 97, -93, -23};
        assertEquals(285, ms.maxSub(numbers));
    }

    @Test
    public void testMaxSeqStartingAt0() {
        int[] numbers = new int[]{41, -31, 59, -97, -53, -58, 26};
        assertEquals(69, ms.maxSub(numbers));
    }

    @Test
    public void testMaxSeqEndingAtEnd() {
        int[] numbers = new int[]{31, -41, 59, 26, -53, 58, 97};
        assertEquals(187, ms.maxSub(numbers));
    }

    @Test
    public void testOneStepMax() {
        int[] numbers = new int[]{2, -10, 8, -10, 2};
        assertEquals(8, ms.maxSub(numbers));
    }

    @Test
    public void testOneStepMaxAt0() {
        int[] numbers = new int[]{41, -31, -59, -26, -13};
        assertEquals(41, ms.maxSub(numbers));
    }

    @Test
    public void testOneStepMaxAtEnd() {
        int[] numbers = new int[]{-31, -59, -26, -13, 47};
        assertEquals(47, ms.maxSub(numbers));
    }

    @Test
    public void testShortArray() {
        int[] numbers = new int[]{12};
        assertEquals(12, ms.maxSub(numbers));
    }

    @Test
    public void testShortNegativeArray() {
        int[] numbers = new int[]{-12};
        assertEquals(0, ms.maxSub(numbers));
    }

    @Test
    public void testAllNegative() {
        int[] numbers = new int[]{-31, -59, -26, -13, -47};
        assertEquals(0, ms.maxSub(numbers));
    }
}
