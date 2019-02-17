package ch.fhnw.edu.rental.data;

import java.util.List;

import ch.fhnw.edu.rental.model.Movie;

/**
 * @author wolfgang.schwaiger
 * 
 */
public interface MovieDAO {
    /**
     * @param id none.
     * @return none.
     */
    Movie getById(int id);

    /**
     * @return none.
     */
    List<Movie> getAll();

    /**
     * @param title none.
     * @return none.
     */
    List<Movie> getByTitle(String title);

    /**
     * @param movie none.
     */
    void saveOrUpdate(Movie movie);

    /**
     * @param movie none.
     */
    void delete(Movie movie);
}
