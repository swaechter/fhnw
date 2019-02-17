package ch.fhnw.edu.rental.data;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Loads Jdbc driver and establishs connnection.
 * 
 */
public final class DatabaseJdbcDriver {

    /**
     * made private.
     */
    private DatabaseJdbcDriver() {
    }

    /**
     * Load the database driver.
     * 
     * @return connection to db
     * @throws Exception load driver exception.
     */
    public static Connection loadDriver() throws Exception {

        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (Exception e) {
            System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
            return null;
        }

        // get connection to db
        return DriverManager.getConnection("jdbc:hsqldb:mem:mrs", "sa", "");
    }

    /**
     * Gets connection to database.
     * 
     * @return db connection
     * @throws Exception sqlException
     */
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection("jdbc:hsqldb:mem:mrs", "sa", "");
    }

}
