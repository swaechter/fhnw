package ch.fhnw.edu.rental.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.fhnw.edu.rental.model.RegularPriceCategory;

public class RegularPriceCategoryTest {
    private static final double tolerance = 1.0e-10;

    private RegularPriceCategory rpc;

    @Before
    public void setUp() throws Exception {
        rpc = RegularPriceCategory.getInstance();
    }

    @Test
    public void testGetCharge() {
        assertEquals(0.0d, rpc.getCharge(-3), tolerance);
        assertEquals(0.0d, rpc.getCharge(0), tolerance);
        assertEquals(2.0d, rpc.getCharge(1), tolerance);
        assertEquals(2.0d, rpc.getCharge(2), tolerance);
        assertEquals(3.5d, rpc.getCharge(3), tolerance);
        assertEquals(5.0d, rpc.getCharge(4), tolerance);
        assertEquals(32.0d, rpc.getCharge(22), tolerance);
    }

    @Test
    public void testToString() {
        assertEquals("Regular", rpc.toString());
    }

}
