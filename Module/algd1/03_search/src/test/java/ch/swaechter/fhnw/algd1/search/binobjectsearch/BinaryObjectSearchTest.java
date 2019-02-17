package ch.swaechter.fhnw.algd1.search.binobjectsearch;

import static org.junit.Assert.*;

import org.junit.Test;

public class BinaryObjectSearchTest {
	@Test
	public void testExistingIntegerAtStart() {
		Integer[] elements = new Integer[] { 1, 3, 4, 5, 9 };
		int index = BinaryObjectSearch.binSearch(elements, 1);
		assertEquals(0, index);
	}

	@Test
	public void testExistingIntegerInBetween1() {
		Integer[] elements = new Integer[] { 1, 3, 4, 5, 9 };
		int index = BinaryObjectSearch.binSearch(elements, 3);
		assertEquals(1, index);
	}

	@Test
	public void testExistingIntegerInBetween2() {
		Integer[] elements = new Integer[] { 1, 3, 4, 5, 9 };
		int index = BinaryObjectSearch.binSearch(elements, 4);
		assertEquals(2, index);
	}

	@Test
	public void testExistingIntegerInBetween3() {
		Integer[] elements = new Integer[] { 1, 3, 4, 5, 9 };
		int index = BinaryObjectSearch.binSearch(elements, 5);
		assertEquals(3, index);
	}

	@Test
	public void testExistingIntegerAtEnd() {
		Integer[] elements = new Integer[] { 1, 3, 4, 5, 9 };
		int index = BinaryObjectSearch.binSearch(elements, 9);
		assertEquals(4, index);
	}

	@Test
	public void testNonExistingInteger() {
		Integer[] elements = new Integer[] { 1, 3, 4, 5, 9 };
		int index = BinaryObjectSearch.binSearch(elements, 7);
		assertEquals(-1, index);
	}

	@Test
	public void testTooSmallInteger() {
		Integer[] elements = new Integer[] { 1, 3, 4, 5, 9 };
		int index = BinaryObjectSearch.binSearch(elements, 0);
		assertEquals(-1, index);
	}

	@Test
	public void testTooLargeInteger() {
		Integer[] elements = new Integer[] { 1, 3, 4, 5, 9 };
		int index = BinaryObjectSearch.binSearch(elements, 11);
		assertEquals(-1, index);
	}

	@Test
	public void testEqualInteger() {
		Integer[] elements = new Integer[] { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 };
		int index = BinaryObjectSearch.binSearch(elements, 6);
		assertEquals(-1, index);
	}

	@Test
	public void testExistingStringFirstHalf() {
		String[] elements = new String[] { "A", "C", "F", "G", "K", "O", "T", "Z" };
		int index = BinaryObjectSearch.binSearch(elements, "C");
		assertEquals(1, index);
	}

	@Test
	public void testExistingStringSecondtHalf() {
		String[] elements = new String[] { "A", "C", "F", "G", "K", "O", "T", "Z" };
		int index = BinaryObjectSearch.binSearch(elements, "O");
		assertEquals(5, index);
	}
}
