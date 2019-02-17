package ch.fhnw.edu.rental.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ch.fhnw.edu.rental.data.DataSet;
import ch.fhnw.edu.rental.gui.MovieRentalView;
import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.RegularPriceCategory;
import ch.fhnw.edu.rental.model.User;

public class DataSetTest {

    private DataSet ds;

    @Before
    public void setUp() throws Exception {

        ds = new DataSet(MovieRentalView.getDateFormatter());
    }

    @After
    public void tearDown() throws Exception {
        ds = null;
        System.gc();
    }

    /**
     * A lame test, nevertheless it helps to boost test coverage :-).
     * 
     * @throws Exception
     */
    @Test
    public void testHashCode() throws Exception {
        DataSet ds2 = new DataSet(MovieRentalView.getDateFormatter());
        assertEquals(ds.hashCode(), ds2.hashCode());
        List<Movie> ml = ds2.getMovieList();
        ml.clear();
        ml.add(new Movie("Bla", RegularPriceCategory.getInstance()));
        assertTrue(ds.hashCode() != ds2.hashCode());
    }

    @Test
    public void testDataSet() throws Exception {
        assertNotNull(ds);
    }

    @Test
    public void testSetterGetterUserList() {
        assertNotNull(ds.getUserList());
    }

    @Test
    public void testGetUserListAsObject() {
        Object[][] arr = ds.getUserListAsObject();
        assertNotNull(arr);
        assertEquals(arr.length, ds.getUserList().size());
        assertEquals(3, arr[0].length);
    }

    @Test
    public void testGetUserByName() {
        User userNotFound = (User) ds.getUserByName("doesNotExist");
        assertNull(userNotFound);

        User user = (User) ds.getUserByName("Meier");
        assertNotNull(user);

        assertTrue(user.hasRentals());
    }

    @Test
    public void testRentalsOfUser() {
        User user = (User) ds.getUserById(1);
        assertNotNull(user);

        assertTrue(user.hasRentals());
    }

    @Test
    public void testSetterGetterMovieList() {
        assertNotNull(ds.getMovieList());
    }

    @Test
    public void testGetMovieListAsObjectBooleanBoolean() {
        Object[][] arr = ds.getMovieListAsObject(false, false);
        assertNotNull(arr);
        arr = ds.getMovieListAsObject(false, true);
        assertNotNull(arr);
        arr = ds.getMovieListAsObject(true, false);
        assertNotNull(arr);
        arr = ds.getMovieListAsObject(true, true);
        assertNotNull(arr);
    }

    @Test
    public void testGetMovieById() {
        Movie m = ds.getMovieById(1);
        assertNotNull(m);
        assertEquals(1, m.getId());

        m = ds.getMovieById(-1000);
        assertNull(m);
    }

    @Test
    public void testUpdateMovie() {
        Movie m1 = ds.getMovieById(1);
        m1.setRented(!m1.isRented());
        ds.updateMovie(m1);

        Movie m2 = ds.getMovieById(1);
        assertEquals(m1, m2);
    }

    @Test
    public void testGetMovieListAsObject() {
        Object[][] arr = ds.getMovieListAsObject();
        assertNotNull(arr);
    }

    @Test
    public void testSetterGetterRentalList() {
        assertNotNull(ds.getRentalList());
    }

    @Test
    public void testGetRentalListAsObject() {
        Object[][] arr = ds.getRentalListAsObject();
        assertNotNull(arr);
        assertEquals(arr.length, ds.getRentalList().size());
        assertEquals(8, arr[0].length);
    }

    @Test
    @Ignore
    public void testEqualsObject() throws Exception {
    }

}
