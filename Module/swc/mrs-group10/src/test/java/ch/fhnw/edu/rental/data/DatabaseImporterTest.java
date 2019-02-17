package ch.fhnw.edu.rental.data;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseImporterTest {

    TestDatabase database;
    class TestDatabase extends Database  {
        
        /**
         * Import data into database.
         * 
         * @throws Exception throws various exceptions
         */
        @Override
        protected void importData() throws Exception {
            DatabaseImporter dbImport = new DatabaseImporter();
            dbImport.setDataFile("invalidFile");
            dbImport.importData(getConnection());
        }
    }
    
    @Before
    public void setUp() throws Exception {
        database = new TestDatabase();
        
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected=FileNotFoundException.class)
    public final void testInvalidDataFile() throws Exception {
        database.initDatabase();
    }
}
