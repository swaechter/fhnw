package ch.fhnw.edu.rental.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Database.
 * 
 */
public class DatabaseTest {

    private Database database;

    @Before
    public void setup() {

        database = new Database();

    }

    @Test
    public void testDatabaseConstructor() {

        assertNotNull(database);
    }

    @Test
    public void testInitDatabase() {

        try {
            database.initDatabase();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}
