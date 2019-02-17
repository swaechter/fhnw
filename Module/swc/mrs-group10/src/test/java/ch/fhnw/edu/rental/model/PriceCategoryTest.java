package ch.fhnw.edu.rental.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.fhnw.edu.rental.model.PriceCategory;

public class PriceCategoryTest {

    private PriceCategory pc;

    private class TestCategory extends PriceCategory {

        @Override
        public double getCharge(int daysRented) {
            return 0;
        }

    }

    @Before
    public void setUp() throws Exception {
        pc = new TestCategory();
    }

    @Test
    public void testGetFrequentRenterPoints() {
        assertEquals(0, pc.getFrequentRenterPoints(-6));
        assertEquals(0, pc.getFrequentRenterPoints(0));
        assertEquals(1, pc.getFrequentRenterPoints(1));
        assertEquals(1, pc.getFrequentRenterPoints(2));
        assertEquals(1, pc.getFrequentRenterPoints(4000));
    }

}
