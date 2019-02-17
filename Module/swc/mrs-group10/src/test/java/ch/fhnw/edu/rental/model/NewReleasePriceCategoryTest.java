package ch.fhnw.edu.rental.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.fhnw.edu.rental.model.NewReleasePriceCategory;

public class NewReleasePriceCategoryTest {

    private static final double tolerance = 1.0e-10;

    private NewReleasePriceCategory nrpc;

    @Before
    public void setUp() throws Exception {
        nrpc = NewReleasePriceCategory.getInstance();
    }

    @Test
    public void testGetCharge() {
        assertEquals(0.0d, nrpc.getCharge(-5), tolerance);
        assertEquals(0.0d, nrpc.getCharge(0), tolerance);
        assertEquals(3.0d, nrpc.getCharge(1), tolerance);
        assertEquals(6.0d, nrpc.getCharge(2), tolerance);
        assertEquals(66.0d, nrpc.getCharge(22), tolerance);
    }

    @Test
    public void testGetFrequentRenterPoints() {
        assertEquals(0, nrpc.getFrequentRenterPoints(-3));
        assertEquals(0, nrpc.getFrequentRenterPoints(0));
        assertEquals(1, nrpc.getFrequentRenterPoints(1));
        assertEquals(2, nrpc.getFrequentRenterPoints(2));
        assertEquals(2, nrpc.getFrequentRenterPoints(50));
    }

    @Test
    public void testToString() {
        assertEquals("New Release", nrpc.toString());
    }

}
