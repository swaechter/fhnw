package ch.fhnw.edu.rental.data;

import java.util.List;

import ch.fhnw.edu.rental.model.IUser;
import ch.fhnw.edu.rental.model.Rental;

/**
 * The Rental data object model class.
 * 
 */
public interface RentalDAO {
    /**
     * @param id none.
     * @return none.
     */
    Rental getById(Long id);

    /**
     * @param user none.
     * @return none.
     */
    List<Rental> getRentalsByUser(IUser user);

    /**
     * @return none.
     */
    List<Rental> getAll();

    /**
     * @param rental none.
     */
    void saveOrUpdate(Rental rental);

    /**
     * @param rental none.
     */
    void delete(Rental rental);
}
