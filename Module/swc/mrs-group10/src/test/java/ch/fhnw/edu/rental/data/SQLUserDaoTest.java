package ch.fhnw.edu.rental.data;

import static org.dbunit.Assertion.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.dbunit.DatabaseUnitException;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.InputSource;

import ch.fhnw.edu.rental.model.IUser;
import ch.fhnw.edu.rental.model.User;

public class SQLUserDaoTest {

    private IDatabaseTester tester;
    private Connection connection;

    /** SQL command to create table CLIENTS. */
    private static final String CREATE_CLIENTS = "drop table if exists clients; create table CLIENTS ("
            + "  Id INTEGER IDENTITY, " + "  Name VARCHAR(255), FirstName VARCHAR(255)" + ")";

    private static final String COUNT_SQL = "SELECT COUNT(*) FROM clients";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // load driver
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (Exception e) {
            System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
            return;
        }

        // get connection to db
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:mrs", "sa", "");

        // create tables in db
        try {
            Statement st = null;
            st = conn.createStatement(); // statements
            int i = st.executeUpdate(CREATE_CLIENTS); // run the query
            if (i == -1) {
                System.out.println("db error : " + CREATE_CLIENTS);
            }
            st.close();
        } catch (SQLException ex2) {
            ex2.printStackTrace();
        } finally {
            conn.close();
        }

    }

    @Before
    public void setUp() throws Exception {
        tester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:mrs", "sa", "");
        connection = tester.getConnection().getConnection();
        InputStream stream = this.getClass().getResourceAsStream("UserDaoTestData.xml");

        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new InputSource(stream));
        tester.setDataSet(dataSet);
        tester.onSetup();
    }

    @After
    public void tearDown() throws Exception {
        tester.onTearDown();
    }

    @Test(expected = RuntimeException.class)
    public void testDelete() throws Exception {
        // count no. of rows before deletion
        Statement s = connection.createStatement();
        ResultSet r = s.executeQuery(COUNT_SQL);
        r.next();
        int rows = r.getInt(1);
        assertEquals(3, rows);

        // Delete non-existing record
        UserDAO dao = new SQLUserDAO(connection);
        User user = new User("Denzler", "Christoph");
        user.setId(42);
        dao.delete(user);

        // Fetch database data after deletion
        IDataSet databaseDataSet = tester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("CLIENTS");

        InputStream stream = this.getClass().getResourceAsStream("UserDaoTestResult.xml");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(stream);
        ITable expectedTable = expectedDataSet.getTable("CLIENTS");

        assertEquals(expectedTable, actualTable);

        // now provoke an SQLException
        connection.close();
        dao.delete(user);
    }

    @Test(expected = RuntimeException.class)
    public void testGetAll() throws DatabaseUnitException, SQLException, Exception {
        UserDAO dao = new SQLUserDAO(connection);
        List<IUser> userlist = dao.getAll();
        ITable actualTable = (ITable) userlist;

        InputStream stream = this.getClass().getResourceAsStream("UserDaoTestData.xml");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(stream);
        ITable expectedTable = expectedDataSet.getTable("CLIENTS");

        assertEquals(expectedTable, actualTable);

        stream = this.getClass().getResourceAsStream("UserDaoSingleRowTest.xml");
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(stream);
        DatabaseOperation.CLEAN_INSERT.execute(tester.getConnection(), dataSet);

        dao = new SQLUserDAO(tester.getConnection().getConnection());
        userlist = dao.getAll();
        assertEquals(1, userlist.size());
        assertEquals("Bond", userlist.get(0).getName());

        stream = this.getClass().getResourceAsStream("UserDaoEmpty.xml");
        dataSet = new XmlDataSet(stream);
        DatabaseOperation.CLEAN_INSERT.execute(tester.getConnection(), dataSet);

        Connection conn = tester.getConnection().getConnection();
        dao = new SQLUserDAO(conn);
        userlist = dao.getAll();
        assertNotNull(userlist);
        assertEquals(0, userlist.size());

        // provoke SQLException
        conn.close();
        dao.getAll();
    }

    @Test(expected = RuntimeException.class)
    public void testGetById() throws SQLException {
        UserDAO dao = new SQLUserDAO(connection);
        IUser user = dao.getById(42);
        assertEquals("Micky", user.getFirstName());
        assertEquals("Mouse", user.getName());
        assertEquals(42, user.getId());

        // provoke SQLException
        connection.close();
        dao.getById(42);
    }

    @Test(expected = RuntimeException.class)
    public void testGetByName() throws SQLException {
        UserDAO dao = new SQLUserDAO(connection);
        List<IUser> userlist = dao.getByName("Duck");
        assertEquals(2, userlist.size());

        // provoke SQLException
        connection.close();
        dao.getByName("Duck");
    }

    @Test(expected = RuntimeException.class)
    public void testSaveOrUpdate() throws SQLException {
        // count no. of rows before operation
        Statement s = connection.createStatement();
        ResultSet r = s.executeQuery(COUNT_SQL);
        r.next();
        int rows0 = r.getInt(1);

        UserDAO dao = new SQLUserDAO(connection);
        IUser daisy = dao.getById(13);
        daisy.setFirstName("Daisy");
        dao.saveOrUpdate(daisy);
        IUser actual = dao.getById(13);
        assertEquals(daisy.getFirstName(), actual.getFirstName());

        r = s.executeQuery(COUNT_SQL);
        r.next();
        int rows1 = r.getInt(1);
        assertEquals(rows0, rows1);

        User goofy = new User("Goofy", "Goofy");
        goofy.setId(8);
        dao.saveOrUpdate(goofy);
        actual = dao.getById(8);
        assertEquals(goofy.getFirstName(), actual.getFirstName());

        r = s.executeQuery(COUNT_SQL);
        r.next();
        int rows2 = r.getInt(1);
        assertEquals(rows1 + 1, rows2);

        // provoke SQLException
        connection.close();
        dao.saveOrUpdate(goofy);
    }

}
