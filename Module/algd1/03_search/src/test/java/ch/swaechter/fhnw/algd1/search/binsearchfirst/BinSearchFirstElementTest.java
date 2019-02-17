package ch.swaechter.fhnw.algd1.search.binsearchfirst;

import static org.junit.Assert.*;

import org.junit.Test;

public class BinSearchFirstElementTest {
	@Test
	public void findFirstElement() {
		int[] numbers = new int[] { 1, 2, 3, 3, 3, 3, 3, 4, 5 };
		int index = BinSearchFirstElement.binSearch(numbers, 3);
		assertEquals(2, index);
	}

	@Test
	public void findFirstElementFirstHalf() {
		int[] numbers = new int[] { 1, 2, 2, 3, 3, 3, 3, 3, 4, 5 };
		int index = BinSearchFirstElement.binSearch(numbers, 2);
		assertEquals(1, index);
	}

	@Test
	public void findFirstElementSecondHalf() {
		int[] numbers = new int[] { 1, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 5 };
		int index = BinSearchFirstElement.binSearch(numbers, 4);
		assertEquals(8, index);
	}

	@Test
	public void testAllElementsEqual() {
		int[] numbers = new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 };
		int index = BinSearchFirstElement.binSearch(numbers, 3);
		assertEquals(0, index);
	}

	@Test
	public void testNoFirstElement() {
		int[] numbers = new int[] { 1, 2, 3, 3, 3, 3, 3, 8, 9 };
		int index = BinSearchFirstElement.binSearch(numbers, 5);
		assertEquals(7, index);
	}

	@Test
	public void testTooSmallElement() {
		int[] numbers = new int[] { 1, 2, 3, 3, 3, 3, 3, 8, 9 };
		int index = BinSearchFirstElement.binSearch(numbers, 0);
		assertEquals(0, index);
	}

	@Test
	public void testTooLargeElement() {
		int[] numbers = new int[] { 1, 2, 3, 3, 3, 3, 3, 8, 9 };
		int index = BinSearchFirstElement.binSearch(numbers, 11);
		assertEquals(9, index);
	}
}
