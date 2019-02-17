package ch.fhnw.edu.rental.data;

import java.util.List;

import ch.fhnw.edu.rental.model.IUser;

/**
 * @author wolfgang.schwaiger
 * 
 */
public interface UserDAO {
    /**
     * @param id none.
     * @return none.
     */
    IUser getById(int id);

    /**
     * @return none.
     */
    List<IUser> getAll();

    /**
     * @param user none.
     */
    void saveOrUpdate(IUser user);

    /**
     * @param user none.
     */
    void delete(IUser user);

    /**
     * @param name none.
     * @return none.
     */
    List<IUser> getByName(String name);
}
