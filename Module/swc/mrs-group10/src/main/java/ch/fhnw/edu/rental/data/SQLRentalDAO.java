package ch.fhnw.edu.rental.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import ch.fhnw.edu.rental.model.IUser;
import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.Rental;

/**
 * @author Christoph
 * 
 */
public class SQLRentalDAO implements RentalDAO {

    /** SQL statement to delete movie. */
    private static final String DELETE_SQL = "DELETE FROM rentals WHERE id = ?";
    /** SQL statement to create movie. */
    private static final String INSERT_SQL = "INSERT INTO rentals (id, movieid, clientid, rentaldate, rentaldays)"
            + "  VALUES (?, ?, ?, ?, ?)";
    /** SQL statement to update movie. */
    private static final String UPDATE_SQL = "UPDATE movies "
            + "SET movieid = ?, clientid = ?, rentaldate = ?, rentaldays = ? " + "WHERE id = ?";
    /** select clause of queries. */
    private static final String SELECT_CLAUSE = "SELECT id, movieid, clientid, rentaldate, rentaldays "
            + "  FROM rentals ";
    /** SQL statement to get movie by id. */
    private static final String GET_BY_ID_SQL = SELECT_CLAUSE + " WHERE id = ?";

    /** SQL statement to get all movies. */
    private static final String GET_ALL_SQL = SELECT_CLAUSE;

    /** java.sql.Connection to use for db access. */
    private Connection connection;

    /**
     * Create a new DAO which uses the given connection.
     * 
     * @param c connection.
     */
    public SQLRentalDAO(Connection c) {
        connection = c;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ch.fhnw.edu.rental.rentalmgmt.dao.RentalDAO#delete(ch.fhnw.edu.rental.rentalmgmt.model.Rental
     * )
     */
    @Override
    public void delete(Rental rental) {
        try {
            PreparedStatement ps = connection.prepareStatement(DELETE_SQL);
            ps.setInt(1, rental.getId());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read single Rental object.
     * 
     * @param r cursor into result set
     * @return Rental object
     * @throws SQLException whenever there is a problem.
     */
    private Rental readRental(ResultSet r) throws SQLException {
        int id = r.getInt("id");
        int mid = r.getInt("movieid");
        int cid = r.getInt("clientid");
        Date rentaldate = r.getDate("rentaldate");
        int rentaldays = r.getInt("rentaldays");

        SQLMovieDAO mDao = new SQLMovieDAO(connection);
        Movie m = mDao.getById(mid);

        SQLUserDAO uDao = new SQLUserDAO(connection);
        IUser u = uDao.getById(cid);

        Rental rental = Rental.createRentalFromDb(u, m, rentaldays, rentaldate);
        rental.setId(id);
        return rental;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.fhnw.edu.rental.rentalmgmt.dao.RentalDAO#getAll()
     */
    @Override
    public List<Rental> getAll() {
        try {
            List<Rental> result = new LinkedList<Rental>();
            PreparedStatement ps = connection.prepareStatement(GET_ALL_SQL);
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                Rental rtl = readRental(r);
                result.add(rtl);
            }
            r.close();
            ps.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.fhnw.edu.rental.rentalmgmt.dao.RentalDAO#getById(java.lang.Long)
     */
    @Override
    public Rental getById(Long id) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ch.fhnw.edu.rental.rentalmgmt.dao.RentalDAO#getRentalsByUser(ch.fhnw.edu.rental.usermgmt.
     * model.User)
     */
    @Override
    public List<Rental> getRentalsByUser(IUser user) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ch.fhnw.edu.rental.rentalmgmt.dao.RentalDAO#saveOrUpdate(ch.fhnw.edu.rental.rentalmgmt.model
     * .Rental)
     */
    @Override
    public void saveOrUpdate(Rental rental) {
        try {
            PreparedStatement ps = connection.prepareStatement(GET_BY_ID_SQL);
            ps.setInt(1, rental.getId());
            ResultSet r = ps.executeQuery();
            PreparedStatement writeStmt;
            int paramcount = 1;
            if (r.next()) { // there exists already a user with this id => UPDATE
                writeStmt = connection.prepareStatement(UPDATE_SQL);
            } else {
                writeStmt = connection.prepareStatement(INSERT_SQL);
                writeStmt.setInt(paramcount++, rental.getId());
            }
            writeStmt.setInt(paramcount++, rental.getMovie().getId());
            writeStmt.setInt(paramcount++, rental.getUser().getId());
            writeStmt.setDate(paramcount++, rental.getRentalDate());
            writeStmt.setInt(paramcount, rental.getRentalDays());
            writeStmt.execute();
            connection.commit();
            writeStmt.close();
            r.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
