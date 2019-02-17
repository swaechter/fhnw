package ch.swaechter.fhnw.algd1.search.sentinelsearch;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class SentinelTest {
	@Test
	public void testNumberAtStart() {
		int[] numbers = { 5, 2, 44, 534, 22 };
		assertExistenceAndArrayEquality(numbers, 5);
	}

	private void assertExistenceAndArrayEquality(int[] numbers, int value) {
		int[] copy = Arrays.copyOf(numbers, numbers.length);
		assertTrue(value + " not found", SentinelSearch.exists(numbers, value));
		assertTrue("Array elements have changed", Arrays.equals(numbers, copy));
	}

	@Test
	public void testNumberInBetween() {
		int[] numbers = { 5, 2, 44, 534, 22 };
		assertExistenceAndArrayEquality(numbers, 44);
	}

	@Test
	public void testNumberAtEnd() {
		int[] numbers = { 5, 2, 44, 534, 22 };
		assertExistenceAndArrayEquality(numbers, 22);
	}

	@Test
	public void testNumberBeforeEnd() {
		int[] numbers = { 5, 2, 44, 534, 22 };
		assertExistenceAndArrayEquality(numbers, 534);
	}

	@Test
	public void testNonExistingNumber() {
		int[] numbers = { 5, 2, 44, 534, 22 };
		int[] copy = Arrays.copyOf(numbers, numbers.length);
		assertFalse("30 is not an element of data", SentinelSearch.exists(numbers, 30));
		assertTrue("Array elements have changed", Arrays.equals(numbers, copy));
	}
}
