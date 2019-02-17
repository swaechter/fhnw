/**
 * 
 */
package ch.fhnw.edu.rental.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author christoph.denzler
 * 
 */
public class RentalTest {
    /** Exception message. */
    private static final String NULLMESSAGE = "not all input parameters are set!";
    /** Exception message. */
    private static final String RENTEDMESSAGE = "movie is already rented!";

    /** users to use in tests. */
    private User u1, u2;

    /** movies to use in tests. */
    private Movie m1, m2;

    /** the release date used for the movies m1 and m2. */
    private Date d = new Date(Calendar.getInstance().getTimeInMillis());

    /** the price category used for the movies m1 and m2. */
    private PriceCategory pc = RegularPriceCategory.getInstance();

    /**
     * @throws java.lang.Exception should not be thrown
     */
    @Before
    public void setUp() throws Exception {
        u1 = new User("Mouse", "Mickey");
        u2 = new User("Duck", "Donald");
        m1 = new Movie("The Kid", d, pc);
        m2 = new Movie("Goldrush", d, pc);
    }

    /**
     * @throws java.lang.Exception should not be thrown
     */
    @After
    public void tearDown() throws Exception {
        u1 = null;
        u2 = null;
        m1 = null;
        m2 = null;
    }

    /**
     * Test method for
     * {@link ch.fhnw.edu.rental.model.Rental#Rental(ch.fhnw.edu.rental.model.User, ch.fhnw.edu.rental.model.Movie, int)}
     * .
     * 
     * @throws InterruptedException should not be thrown.
     */
    @Test
    public void testRental() throws InterruptedException {
        Date before = new Date(Calendar.getInstance().getTimeInMillis());
        Thread.sleep(10); // spend some time in order to get a different timestamp in the following
                          // contstructor.
        Rental r = new Rental(u1, m1, 42);
        assertNotNull(r);
        // is the rental registered with the user?
        assertTrue(u1.getRentals().contains(r));
        // has the movie's rented state been set to rented?
        assertTrue(m1.isRented());
        // is the number of rental days set correctly?
        assertEquals(42, r.getRentalDays());
        // spend some more time to get yet another timestamp
        Thread.sleep(10);
        Date after = new Date(Calendar.getInstance().getTimeInMillis());
        // has the rental date been set in between?
        assertTrue(before.before(r.getRentalDate()));
        assertTrue(after.after(r.getRentalDate()));
        // do we get what we set?
        assertEquals(u1, r.getUser());
        assertEquals(m1, r.getMovie());
    }

    /**
     * Test method for
     * {@link ch.fhnw.edu.rental.model.Rental#Rental(ch.fhnw.edu.rental.model.User, ch.fhnw.edu.rental.model.Movie, int)}
     * .
     * 
     * @throws InterruptedException should not be thrown.
     */
    @Test
    public void testExceptionRental() throws InterruptedException {
        Rental r = null;
        try {
            r = new Rental(null, m1, 1);
        } catch (NullPointerException e) {
            assertEquals(NULLMESSAGE, e.getMessage());
        }
        try {
            r = new Rental(u1, null, 1);
        } catch (NullPointerException e) {
            assertEquals(NULLMESSAGE, e.getMessage());
        }
        try {
            r = new Rental(u1, m1, 0);
        } catch (NullPointerException e) {
            assertEquals(NULLMESSAGE, e.getMessage());
        }
        try {
            r = new Rental(null, m1, -11);
        } catch (NullPointerException e) {
            assertEquals(NULLMESSAGE, e.getMessage());
        }
        try {
            m2.setRented(true);
            r = new Rental(u2, m2, 1);
        } catch (IllegalStateException e) {
            assertEquals(RENTEDMESSAGE, e.getMessage());
        }
        assertNull(r);
    }

    /**
     * Test method for
     * {@link ch.fhnw.edu.rental.model.Rental#calcRemainingDaysOfRental(java.util.Date)}.
     */
    @Test
    public void testCalcRemainingDaysOfRental() {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2008, 10, 05);
        Date today = new Date(cal.getTimeInMillis());
        cal.clear();
        cal.set(2008, 10, 06);
        Date tomorrow = new Date(cal.getTimeInMillis());

        Rental r = new Rental(u1, m1, 1);
        r.setRentalDate(today);
        int remaining = r.calcRemainingDaysOfRental(tomorrow);
        assertEquals(0, remaining);

        try {
            r.calcRemainingDaysOfRental(null);
            fail();
        } catch (NullPointerException npe) {
            assertEquals("given date is not set!", npe.getMessage());
        }
    }

    @Test
    public void testSetterGetterId() {
        Rental r = new Rental(u1, m1, 42);
        r.setId(11);
        assertEquals(11, r.getId());

        try { // setting id a 2nd time
            r.setId(47);
            fail();
        } catch (MovieRentalException mre) {
            assertEquals("illegal change of rental's id", mre.getMessage());
        }
        assertEquals(11, r.getId());
    }

    @Test
    public void testSetterGetterMovie() {
        Rental r = new Rental(u1, m1, 5);
        r.setMovie(m2);
        assertEquals(m2, r.getMovie());

        try {
            r.setMovie(null);
            fail();
        } catch (NullPointerException npe) {
            assertEquals("no movie", npe.getMessage());
        }
        assertEquals(m2, r.getMovie());
    }

    @Test
    public void testSetterGetterUser() {
        Rental r = new Rental(u1, m1, 5);
        r.setUser(u2);
        assertEquals(u2, r.getUser());

        try {
            r.setUser(null);
            fail();
        } catch (NullPointerException npe) {
            assertEquals("no user", npe.getMessage());
        }
        assertEquals(u2, r.getUser());
    }

    @Test
    @Ignore
    public void testEquals() {
    }

    @Test
    public void testHashCode() {
        Rental x = new Rental(u1, m1, 5);
        m1.setRented(false);
        Rental y = new Rental(u1, m1, 5);

        assertEquals(x.hashCode(), y.hashCode());

        x.setId(42);
        assertTrue(x.hashCode() != y.hashCode());
        y.setId(42);
        assertEquals(x.hashCode(), y.hashCode());

        x.setMovie(m2);
        assertTrue(x.hashCode() != y.hashCode());
        y.setMovie(m2);
        assertEquals(x.hashCode(), y.hashCode());

        x.setUser(u2);
        assertTrue(x.hashCode() != y.hashCode());
        y.setUser(u2);
        assertEquals(x.hashCode(), y.hashCode());
    }

}
