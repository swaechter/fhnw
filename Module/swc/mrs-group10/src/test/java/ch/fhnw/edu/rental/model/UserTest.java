/**
 * 
 */
package ch.fhnw.edu.rental.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author christoph.denzler
 * 
 */
public class UserTest {
    /** String constant. */
    private static final String NAME = "name";
    /** String constant. */
    private static final String FIRSTNAME = "first name";
    /** String constant. */
    private static final String EMPTYSTRING = "";

    /**
     * Test method for
     * {@link ch.fhnw.edu.rental.model.User#User(java.lang.String, java.lang.String)} .
     */
    @Test
    public void testUser() {
        User u = new User(NAME, FIRSTNAME);
        assertNotNull("u should not be null", u);

        // check if name and firstname were stored correctly
        String n = u.getName();
        String f = u.getFirstName();
        assertEquals(NAME, n);
        assertEquals(FIRSTNAME, f);

        // check if there exists a rental list
        List<Rental> rentals = u.getRentals();
        assertNotNull("rentals list should be empty, not null", rentals);
        assertEquals(0, rentals.size());
    }

    /**
     * Test if correct exeptions are thrown in constructor.
     */
    @Test
    public void testUserExceptions() {
        IUser u = null;
        try {
            u = new User(null, FIRSTNAME);
            fail();
        } catch (NullPointerException e) {
            assertEquals("invalid name value", e.getMessage());
        }
        try {
            u = new User(NAME, null);
            fail();
        } catch (NullPointerException e) {
            assertEquals("invalid firstName value", e.getMessage());
        }
        try {
            u = new User(EMPTYSTRING, FIRSTNAME);
            fail();
        } catch (MovieRentalException e) {
            assertEquals("invalid name value", e.getMessage());
        }
        try {
            u = new User(NAME, EMPTYSTRING);
            fail();
        } catch (MovieRentalException e) {
            assertEquals("invalid firstName value", e.getMessage());
        }
        assertNull("u must not be assigned", u);
    }

    /**
     * Test method for {@link ch.fhnw.edu.rental.model.User#getId()}. Test method for
     * {@link ch.fhnw.edu.rental.model.User#setId()}.
     */
    @Test(expected = MovieRentalException.class)
    public void testSetterGetterId() {
        User u = new User(NAME, FIRSTNAME);
        u.setId(42);
        assertEquals(42, u.getId());
        u.setId(0);
    }

    /**
     * Test method for {@link ch.fhnw.edu.rental.model.User#getRentals()} . Test method for
     * {@link ch.fhnw.edu.rental.model.User#setRentals()}.
     */
    @Test
    public void testSetterGetterRentals() {
        List<Rental> list = new LinkedList<Rental>();
        User u = new User(NAME, FIRSTNAME);
        u.setRentals(list);
        assertEquals(list, u.getRentals());
        u.setRentals(null);
        assertNull("rental list must be null", u.getRentals());
    }

    /**
     * Test method for {@link ch.fhnw.edu.rental.model.User#getName()}. Test method for
     * {@link ch.fhnw.edu.rental.model.User#setName()}.
     */
    @Test
    public void testSetterGetterName() {
        User u = new User(NAME, FIRSTNAME);
        try {
            u.setName(null);
            fail("Missing NullPointerException");
        } catch (NullPointerException npe) {
            assertEquals("invalid name value", npe.getMessage());
        }
        try {
            u.setName("");
            fail("Missing MovieRentalException");
        } catch (MovieRentalException npe) {
            assertEquals("invalid name value", npe.getMessage());
        }
        try {
            u.setName("This is a very long name which is over fourty characters long and thus should result in an exception");
            fail("Missing MovieRentalException");
        } catch (MovieRentalException npe) {
            assertEquals("invalid name value", npe.getMessage());
        }
        u.setName("Bla");
        assertEquals("Bla", u.getName());
    }

    /**
     * Test method for {@link ch.fhnw.edu.rental.model.User#setName(java.lang.String)}. Test method
     * for {@link ch.fhnw.edu.rental.model.User#getFirstName()}.
     */
    @Test
    public void testSetterGetterFirstName() {
        User u = new User(NAME, FIRSTNAME);
        try {
            u.setFirstName(null);
            fail("Missing NullPointerException");
        } catch (NullPointerException npe) {
            assertEquals("invalid firstName value", npe.getMessage());
        }
        try {
            u.setFirstName("");
            fail("Missing MovieRentalException");
        } catch (MovieRentalException npe) {
            assertEquals("invalid firstName value", npe.getMessage());
        }
        try {
            u.setFirstName("This is a very long name which is over fourty characters long and thus should result in an exception");
            fail("Missing MovieRentalException");
        } catch (MovieRentalException npe) {
            assertEquals("invalid firstName value", npe.getMessage());
        }
        u.setFirstName("Bla");
        assertEquals("Bla", u.getFirstName());
    }

    /**
     * Test method for {@link ch.fhnw.edu.rental.model.User#getCharge()}.
     */
    @Test
    public void testGetCharge() {
    	double delta=1e-6;
        User u = new User(NAME, FIRSTNAME);
        // a newly created user has no rentals and no charge.
        double charge = u.getCharge();
        assertEquals(0.0d, charge, delta);

        PriceCategory regular = RegularPriceCategory.getInstance();

        // first check regular movie
        Movie mov = new Movie("A", regular);
        Rental r = new Rental(u, mov, 1);
        charge = r.getRentalFee();
        assertEquals(charge, u.getCharge(), delta);

        // now add another two regular movies
        mov = new Movie("B", regular);
        r = new Rental(u, mov, 1);
        charge += r.getRentalFee();
        mov = new Movie("C", regular);
        r = new Rental(u, mov, 1);
        charge += r.getRentalFee();
        assertEquals(charge, u.getCharge(), delta);
    }

    /**
     * Test method for {@link ch.fhnw.edu.rental.model.User#equals(java.lang.Object)}.
     * 
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @Test
    @Ignore
    public void testEquals() throws SecurityException, NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
    }

    @Test
    public void testHashCode() throws SecurityException, NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        // dummy user objects
        IUser x = new User(NAME, FIRSTNAME);
        IUser y = new User(NAME, FIRSTNAME);

        assertEquals(x.hashCode(), y.hashCode());
        x.setId(42);
        assertTrue(x.hashCode() != y.hashCode());
        y.setId(42);
        assertEquals(x.hashCode(), y.hashCode());
    }

}
