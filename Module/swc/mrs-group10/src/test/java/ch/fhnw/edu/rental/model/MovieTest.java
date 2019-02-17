/**
 * 
 */
package ch.fhnw.edu.rental.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Test;

/**
 * @author christoph.denzler
 * 
 */
public class MovieTest {

    /**
     * Expected exception message.
     */
    private static final String MESSAGE = "not all input parameters are set!";

    /**
     * Hashcode and equals are always overridden and tested together. Test method for
     * {@link ch.fhnw.edu.rental.model.Movie#hashCode()}.
     * 
     * @throws InterruptedException should not be thrown
     */
    @Test
    public void testHashCode() throws InterruptedException {
        Date d = new Date(Calendar.getInstance().getTimeInMillis());
        PriceCategory pc = RegularPriceCategory.getInstance();
        Movie x = new Movie();
        Movie y = new Movie("A", d, pc);
        Movie z = new Movie("A", d, pc);

        // do we get consistently the same result?
        int h = x.hashCode();
        assertEquals(h, x.hashCode());
        h = y.hashCode();
        assertEquals(h, y.hashCode());

        // do we get the same result from two equal objects?
        h = y.hashCode();
        assertEquals(h, z.hashCode());

        // still the same hashcode after changing rented state?
        z.setRented(true);
        assertEquals(h, z.hashCode());

        z = new Movie("A", d, pc); // get a new Movie
        z.setPriceCategory(ChildrenPriceCategory.getInstance());
        assertEquals(h, z.hashCode());

        // now, let's see if we can get different hashcodes as well
        z = new Movie("B", d, pc);
        assertFalse(h == z.hashCode());
        Thread.sleep(10); // sleep some time to get a differen timestamp
        z = new Movie("A", new Date(Calendar.getInstance().getTimeInMillis()), pc);
        assertFalse(h == z.hashCode());
        z = new Movie("A", d, pc);
        z.setId(42);
        assertFalse(h == z.hashCode());
    }

    /**
     * Test method for {@link ch.fhnw.edu.rental.model.Movie#Movie()}.
     */
    @Test
    public void testMovie() {
        Movie m = new Movie();
        assertNull(m.getPriceCategory());
        assertNull(m.getReleaseDate());
        assertNull(m.getTitle());
        assertFalse(m.isRented());
    }

    /**
     * Test method for
     * {@link ch.fhnw.edu.rental.model.Movie#Movie(java.lang.String, ch.fhnw.edu.rental.model.PriceCategory)}
     * .
     * 
     * @throws InterruptedException must not be thrown
     */
    @Test
    public void testMovieStringPriceCategory() throws InterruptedException {
        // get time before object creation
        Date before = new Date(Calendar.getInstance().getTimeInMillis());
        // spend some time to be able to detect differences in timestamps
        Thread.sleep(10);

        // now allocate new instance
        Movie m = new Movie("A", RegularPriceCategory.getInstance());
        // get time after object creation
        Thread.sleep(10);
        Date after = new Date(Calendar.getInstance().getTimeInMillis());

        assertEquals("A", m.getTitle());
        assertEquals(RegularPriceCategory.class, m.getPriceCategory().getClass());
        Date releaseDate = m.getReleaseDate();
        assertNotNull(releaseDate);
        assertTrue(before.before(releaseDate));
        assertTrue("Expected release date to be earlier.", after.after(releaseDate));
        assertFalse(m.isRented());
    }

    /**
     * Test method for
     * {@link ch.fhnw.edu.rental.model.Movie#Movie(java.lang.String, ch.fhnw.edu.rental.model.PriceCategory)}
     * .
     */
    @Test
    public void testExceptionMovieStringPriceCategory() {
        Movie m = null;
        try {
            m = new Movie(null, RegularPriceCategory.getInstance());
        } catch (NullPointerException e) {
            assertEquals(MESSAGE, e.getMessage());
        }
        try {
            m = new Movie("A", null);
        } catch (NullPointerException e) {
            assertEquals(MESSAGE, e.getMessage());
        }
        assertNull(m);
    }

    /**
     * Test method for
     * {@link ch.fhnw.edu.rental.model.Movie#Movie(java.lang.String, java.util.Date, ch.fhnw.edu.rental.model.PriceCategory)}
     * .
     */
    @Test
    public void testMovieStringDatePriceCategory() {
        Date d = new Date(Calendar.getInstance().getTimeInMillis());
        Movie m = new Movie("B", d, RegularPriceCategory.getInstance());

        assertNotNull(m);
        assertEquals("B", m.getTitle());
        assertEquals(RegularPriceCategory.class, m.getPriceCategory().getClass());
        Date releaseDate = m.getReleaseDate();
        assertNotNull(releaseDate);
        assertEquals(d, releaseDate);
        assertFalse(m.isRented());
    }

    /**
     * Test method for
     * {@link ch.fhnw.edu.rental.model.Movie#Movie(java.lang.String, java.util.Date, ch.fhnw.edu.rental.model.PriceCategory)}
     * .
     */
    @Test
    public void testExceptionMovieStringDatePriceCategory() {
        Movie m = null;
        try {
            m = new Movie(null, new Date(Calendar.getInstance().getTimeInMillis()), RegularPriceCategory.getInstance());
        } catch (NullPointerException e) {
            assertEquals(MESSAGE, e.getMessage());
        }
        try {
            m = new Movie("A", null, RegularPriceCategory.getInstance());
        } catch (NullPointerException e) {
            assertEquals(MESSAGE, e.getMessage());
        }
        try {
            m = new Movie("A", new Date(Calendar.getInstance().getTimeInMillis()), null);
        } catch (NullPointerException e) {
            assertEquals(MESSAGE, e.getMessage());
        }
        assertNull(m);
    }

    /**
     * Test method for {@link ch.fhnw.edu.rental.model.Movie#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObject() {
        // Test object with itself
        Movie movie1 = new Movie();
        movie1.setReleaseDate(new Date(Calendar.getInstance().getTimeInMillis()));
        movie1.setTitle("Star Wars 1");
        movie1.setId(100);
        assertTrue(movie1.equals(movie1));

        // Test release date
        Movie movie2a = new Movie();
        movie2a.setReleaseDate(new Date(Calendar.getInstance().getTimeInMillis() - 2000));
        movie2a.setTitle("Star Wars 1");
        movie2a.setId(100);
        assertFalse(movie1.equals(movie2a));

        Movie moview2b = new Movie();
        moview2b.setReleaseDate(null);
        moview2b.setTitle("Star Wars 1");
        moview2b.setId(100);
        assertFalse(movie2a.equals(moview2b));

        // Test title
        Movie movie3a = new Movie();
        movie3a.setReleaseDate(new Date(Calendar.getInstance().getTimeInMillis()));
        movie3a.setTitle("Star Wars 2");
        movie3a.setId(100);
        assertFalse(movie1.equals(movie3a));

        Movie movie3b = new Movie();
        movie3b.setReleaseDate(movie3a.getReleaseDate());
        movie3b.setTitle(null);
        movie3b.setId(100);
        assertFalse(movie3a.equals(movie3b));

        // Test ID
        Movie movie4 = new Movie();
        movie4.setReleaseDate(new Date(Calendar.getInstance().getTimeInMillis()));
        movie4.setTitle("Star Wars 1");
        movie4.setId(1);
        assertFalse(movie1.equals(movie4));

        // Test non existing object
        assertFalse(movie1.equals(null));

        // Test wrong object class
        assertFalse(movie1.equals(new Date(0)));
    }

    @Test(expected = IllegalStateException.class)
    public void testSetTitle() {
        Movie m = new Movie();
        m.setTitle("Hallo");
        assertEquals("Hallo", m.getTitle());
        m.setTitle(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testSetReleaseDate() {
        Movie m = new Movie();
        Date d = new Date(Calendar.getInstance().getTimeInMillis());
        m.setReleaseDate(d);
        assertEquals(d, m.getReleaseDate());
        m.setReleaseDate(null);
    }
}
