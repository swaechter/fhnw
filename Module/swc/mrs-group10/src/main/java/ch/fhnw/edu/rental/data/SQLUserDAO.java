/**
 *
 */
package ch.fhnw.edu.rental.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import ch.fhnw.edu.rental.model.IUser;
import ch.fhnw.edu.rental.model.User;

/**
 * @author christoph.denzler
 * 
 */
public class SQLUserDAO implements UserDAO {

    /** SQL statement to delete user. */
    private static final String DELETE_SQL = "DELETE FROM clients WHERE id = ?";
    /** SQL statement to create user. */
    private static final String INSERT_SQL = "INSERT INTO clients (id, firstname, name) VALUES (?, ?, ?)";
    /** SQL statement to update user. */
    private static final String UPDATE_SQL = "UPDATE clients SET firstname = ?, name = ? WHERE id = ?";
    /** SQL statement to get user by id. */
    private static final String GET_BY_ID_SQL = "SELECT id, firstname, name FROM clients WHERE id = ?";
    /** SQL statement to get user by name. */
    private static final String GET_BY_NAME_SQL = "SELECT id, firstname, name FROM clients WHERE name = ?";
    /** SQL statement to get all users. */
    private static final String GET_ALL_SQL = "SELECT * FROM clients";

    /** java.sql.Connection to use for db access. */
    private Connection connection;

    /**
     * Create a new DAO which uses the given connection.
     * 
     * @param c connection.
     */
    public SQLUserDAO(Connection c) {
        connection = c;
    }

    @Override
    public void delete(IUser user) {
        try {
            PreparedStatement ps = connection.prepareStatement(DELETE_SQL);
            ps.setInt(1, user.getId());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read single User object.
     * 
     * @param r cursor into result set.
     * @return newly read User object
     * @throws SQLException whenever there is a problem
     */
    private User readUser(ResultSet r) throws SQLException {
        String firstname = r.getString("FirstName");
        String lastname = r.getString("Name");
        int i = r.getInt("Id");
        User u = new User(lastname, firstname);
        u.setId(i);

        return u;
    }

    @Override
    public List<IUser> getAll() {
        try {
            List<IUser> result = new LinkedList<IUser>();
            PreparedStatement ps = connection.prepareStatement(GET_ALL_SQL);
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                result.add(readUser(r));
            }
            r.close();
            ps.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IUser getById(int id) {
        try {
            User result = null;
            PreparedStatement ps = connection.prepareStatement(GET_BY_ID_SQL);
            ps.setInt(1, id);
            ResultSet r = ps.executeQuery();
            if (r.next()) {
                result = readUser(r);
            }
            r.close();
            ps.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<IUser> getByName(String name) {
        try {
            List<IUser> result = new LinkedList<IUser>();
            PreparedStatement ps = connection.prepareStatement(GET_BY_NAME_SQL);
            ps.setString(1, name);
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                result.add(readUser(r));
            }
            r.close();
            ps.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveOrUpdate(IUser user) {
        try {
            PreparedStatement ps = connection.prepareStatement(GET_BY_ID_SQL);
            ps.setInt(1, user.getId());
            ResultSet r = ps.executeQuery();
            if (r.next()) { // there exists already a user with this id => UPDATE
                PreparedStatement update = connection.prepareStatement(UPDATE_SQL);
                update.setString(1, user.getFirstName());
                update.setString(2, user.getName());
                update.setInt(3, user.getId());
                update.execute();
                connection.commit();
                update.close();
            } else { // new user => INSERT
                PreparedStatement insert = connection.prepareStatement(INSERT_SQL);
                insert.setInt(1, user.getId());
                insert.setString(2, user.getFirstName());
                insert.setString(3, user.getName());
                insert.execute();
                connection.commit();
                insert.close();
            }
            r.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
