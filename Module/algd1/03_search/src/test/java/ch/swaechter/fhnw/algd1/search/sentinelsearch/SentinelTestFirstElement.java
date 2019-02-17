package ch.swaechter.fhnw.algd1.search.sentinelsearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class SentinelTestFirstElement extends SentinelTest {

	@Override
	@Test
	public void testNumberAtStart() {
		int[] numbers = { 5, 2, 44, 534, 22 };
		assertExistenceAndArrayEquality(numbers, 5, 0);
	}

	private void assertExistenceAndArrayEquality(int[] numbers, int value, int index) {
		int[] copy = Arrays.copyOf(numbers, numbers.length);
		assertEquals(index, SentinelSearch.firstIndex(numbers, value));
		assertTrue("Array elements have changed",Arrays.equals(numbers, copy));
	}

	@Override
	@Test
	public void testNumberInBetween() {
		int[] numbers = { 5, 2, 44, 534, 22 };
		assertExistenceAndArrayEquality(numbers, 44,2);
	}

	@Override
	@Test
	public void testNumberAtEnd() {
		int[] numbers = { 5, 2, 44, 534, 22 };
		assertExistenceAndArrayEquality(numbers, 22, 4);
	}

	@Override
	@Test
	public void testNonExistingNumber() {
		int[] numbers = { 5, 2, 44, 534, 22 };
		int[] copy = Arrays.copyOf(numbers, numbers.length);
		assertEquals(-1, SentinelSearch.firstIndex(numbers, 30));
		assertTrue("Array elements have changed", Arrays.equals(numbers, copy));
	}

	@Test
	public void findFirstElementTest() {
		int[] numbers = { 5, 2, 44, 534, 22, 44 };
		int[] copy = Arrays.copyOf(numbers, numbers.length);
		assertEquals(2, SentinelSearch.firstIndex(numbers, 44));
		assertTrue("Array elements have changed", Arrays.equals(numbers, copy));
	}
	
	@Test
	public void findFirstElementTest2() {
		int[] numbers = { 5, 2, 44, 534, 22, 44, 45, 65 };
		int[] copy = Arrays.copyOf(numbers, numbers.length);
		assertEquals(2, SentinelSearch.firstIndex(numbers, 44));
		assertTrue("Array elements have changed", Arrays.equals(numbers, copy));
	}
	
}
