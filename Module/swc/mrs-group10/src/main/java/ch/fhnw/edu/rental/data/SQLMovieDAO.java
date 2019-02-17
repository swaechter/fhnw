package ch.fhnw.edu.rental.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import ch.fhnw.edu.rental.model.ChildrenPriceCategory;
import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.MovieRentalException;
import ch.fhnw.edu.rental.model.NewReleasePriceCategory;
import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.model.RegularPriceCategory;

/**
 * 
 * @author Christoph Denzler
 * 
 *         SQL access to data.
 */
public class SQLMovieDAO implements MovieDAO {
    /** SQL statement to delete movie. */
    private static final String DELETE_SQL = "DELETE FROM movies WHERE id = ?";
    /** SQL statement to create movie. */
    private static final String INSERT_SQL = "INSERT INTO movies (id, title, isrented, releasedate, pricecategory)"
            + "  VALUES (?, ?, ?, ?, ?)";
    /** SQL statement to update movie. */
    private static final String UPDATE_SQL = "UPDATE movies "
            + "SET title = ?, isrented = ?, releasedate = ?, pricecategory = ? " + "WHERE id = ?";
    /** select clause of queries. */
    private static final String SELECT_CLAUSE = "SELECT id, title, isrented, releasedate, pricecategory "
            + "  FROM movies ";
    /** SQL statement to get movie by id. */
    private static final String GET_BY_ID_SQL = SELECT_CLAUSE + " WHERE id = ?";
    /** SQL statement to get movie by name. */
    private static final String GET_BY_TITLE_SQL = SELECT_CLAUSE + "WHERE title = ?";
    /** SQL statement to get all movies. */
    private static final String GET_ALL_SQL = SELECT_CLAUSE;

    /** Regular price category. */
    private static final int CATEGORY_REGULAR = 1;
    /** Children price category. */
    private static final int CATEGORY_CHILDREN = 2;
    /** New release price category. */
    private static final int CATEGORY_NEW_RELEASE = 3;

    /** java.sql.Connection to use for db access. */
    private Connection connection;

    /**
     * Create a new DAO which uses the given connection.
     * 
     * @param c connection.
     */
    public SQLMovieDAO(Connection c) {
        connection = c;
    }

    @Override
    public void delete(Movie movie) {
        try {
            PreparedStatement ps = connection.prepareStatement(DELETE_SQL);
            ps.setInt(1, movie.getId());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read a single movie object from database.
     * 
     * @param r Cursor into result set.
     * @return a movie object
     * @throws SQLException in case of any problem
     */
    private Movie readMovie(ResultSet r) throws SQLException {
        String title = r.getString("Title");
        boolean isrented = r.getBoolean("IsRented");
        Date date = r.getDate("ReleaseDate");
        int i = r.getInt("Id");
        int pc = r.getInt("PriceCategory");
        PriceCategory cat;
        switch (pc) {
        case CATEGORY_REGULAR:
            cat = RegularPriceCategory.getInstance();
            break;
        case CATEGORY_NEW_RELEASE:
            cat = NewReleasePriceCategory.getInstance();
            break;
        case CATEGORY_CHILDREN:
            cat = ChildrenPriceCategory.getInstance();
            break;
        default:
            throw new MovieRentalException("Unknown Price Category");
        }
        Movie m = new Movie(title, date, cat);
        m.setId(i);
        m.setRented(isrented);
        return m;
    }

    @Override
    public List<Movie> getAll() {
        try {
            List<Movie> result = new LinkedList<Movie>();
            PreparedStatement ps = connection.prepareStatement(GET_ALL_SQL);
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                Movie m = readMovie(r);
                result.add(m);
            }
            r.close();
            ps.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Movie getById(int id) {
        try {
            Movie result = null;
            PreparedStatement ps = connection.prepareStatement(GET_BY_ID_SQL);
            ps.setInt(1, id);
            ResultSet r = ps.executeQuery();
            if (r.next()) {
                result = readMovie(r);
            }
            r.close();
            ps.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Movie> getByTitle(String title) {
        try {
            List<Movie> result = new LinkedList<Movie>();
            PreparedStatement ps = connection.prepareStatement(GET_BY_TITLE_SQL);
            ps.setString(1, title);
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                result.add(readMovie(r));
            }
            r.close();
            ps.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveOrUpdate(Movie movie) {
        try {
            PreparedStatement ps = connection.prepareStatement(GET_BY_ID_SQL);
            ps.setInt(1, movie.getId());
            ResultSet r = ps.executeQuery();
            PreparedStatement writeStmt;
            int paramcount = 1;
            if (r.next()) { // there exists already a user with this id => UPDATE
                writeStmt = connection.prepareStatement(UPDATE_SQL);
            } else {
                writeStmt = connection.prepareStatement(INSERT_SQL);
                writeStmt.setInt(paramcount++, movie.getId());
            }
            writeStmt.setString(paramcount++, movie.getTitle());
            writeStmt.setBoolean(paramcount++, movie.isRented());
            writeStmt.setDate(paramcount++, movie.getReleaseDate());
            writeStmt.setInt(paramcount, movie.getPriceCategory().getId());
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
