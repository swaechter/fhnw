package ch.fhnw.edu.rental.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import ch.fhnw.edu.rental.model.IUser;
import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.Rental;

/**
 * Responsible to initialize database.
 * 
 */
public class Database {
    /** SQL command to create table CLIENTS. */
    private static final String CREATE_CLIENTS = "drop table if exists CLIENTS; create table CLIENTS ("
            + "  Id INTEGER IDENTITY, " + "  Name VARCHAR(255), FirstName VARCHAR(255)" + ")";

    /** SQL command to create table SWC_RENTALS. */
    private static final String CREATE_RENTALS = "drop table if exists RENTALS; create table RENTALS ("
            + "  Id INTEGER IDENTITY," + "  MovieId INTEGER," + "  ClientId INTEGER," + "  RentalDate DATE,"
            + "  RentalDays INTEGER" + ")";

    /** SQL command to create table SWC_MOVIES. */
    private static final String CREATE_MOVIES = "drop table if exists MOVIES; create table MOVIES ("
            + "  Id INTEGER IDENTITY," + "  Title VARCHAR(255)," + "  IsRented BOOLEAN," + "  ReleaseDate DATE,"
            + "  PriceCategory INTEGER" + ")";

    /**
     * Connection to database.
     */
    private Connection connection;

    /**
     * Initializes the database.
     * 
     * @throws Exception none.
     * 
     */
    public void initDatabase() throws Exception {

        connection = DatabaseJdbcDriver.loadDriver();

        createDatabaseModel();

        importData();

    }
    
    /**
     * 
     * @return the database connection
     */
    protected Connection getConnection() {
        return this.connection;
    }

    /**
     * Create the database tables.
     */
    private void createDatabaseModel() {
        // create tables in db
        try {
            command(CREATE_CLIENTS);
            command(CREATE_MOVIES);
            command(CREATE_RENTALS);
        } catch (SQLException ex2) {
            ex2.printStackTrace();
        }
    }

    /**
     * Import data into database.
     * 
     * @throws Exception throws various exceptions
     */
    protected void importData() throws Exception {

        DatabaseImporter dbImport = new DatabaseImporter();

        dbImport.importData(connection);
    }

    /**
     * use for SQL commands CREATE, DROP, INSERT and UPDATE.
     * 
     * @param expression SQL command
     * @throws SQLException when something went wrong
     */
    protected synchronized void command(String expression) throws SQLException {
        Statement st = null;
        st = connection.createStatement(); // statements
        int i = st.executeUpdate(expression); // run the query
        if (i == -1) {
            System.out.println("db error : " + expression);
        }
        st.close();
    }

    /**
     * Get list of users.
     * 
     * @return List of users
     * @throws Exception sql exception
     */
    public List<IUser> initUserList() throws Exception {

        connection = DatabaseJdbcDriver.getConnection();

        SQLUserDAO udao = new SQLUserDAO(connection);

        List<IUser> uList = udao.getAll();

        connection.close();

        return uList;
    }

    /**
     * Get list of movies.
     * 
     * @return List of movies
     * @throws Exception sql exception
     */
    public List<Movie> initMovieList() throws Exception {

        connection = DatabaseJdbcDriver.getConnection();

        SQLMovieDAO mdao = new SQLMovieDAO(connection);

        List<Movie> mList = mdao.getAll();
        connection.close();

        return mList;
    }

    /**
     * Get list of rentals.
     * 
     * @return List of rentals
     * @throws Exception sql exception
     */
    public List<Rental> initRentalList() throws Exception {

        connection = DatabaseJdbcDriver.getConnection();

        SQLRentalDAO rdao = new SQLRentalDAO(connection);

        List<Rental> rList = rdao.getAll();

        connection.close();

        return rList;
    }
}
