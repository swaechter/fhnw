/**
 * 
 */
package ch.fhnw.edu.rental.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.fhnw.edu.rental.model.ChildrenPriceCategory;

/**
 * @author Christoph
 * 
 */
public class ChildrenPriceCategoryTest {

    private ChildrenPriceCategory cpc;

    /**
     * set up a new object to test.
     */
    @Before
    public void setUp() {
        cpc = ChildrenPriceCategory.getInstance();
    }

    /**
     * Test method for {@link ch.fhnw.edu.rental.model.ChildrenPriceCategory#getCharge(int)}.
     */
    @Test
    public void testGetCharge() {
        assertEquals(0.0D, cpc.getCharge(-5), 1.0e-10);
        assertEquals(0.0, cpc.getCharge(0), 1.0e-10);
        assertEquals(1.5D, cpc.getCharge(1), 1.0e-10);
        assertEquals(1.5D, cpc.getCharge(2), 1.0e-10);
        assertEquals(1.5D, cpc.getCharge(3), 1.0e-10);
        assertEquals(3.0D, cpc.getCharge(4), 1.0e-10);
        assertEquals(151.5D, cpc.getCharge(103), 1.0e-10);

    }

    /**
     * Test method for {@link ch.fhnw.edu.rental.model.ChildrenPriceCategory#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals("Children", cpc.toString());
    }

}
