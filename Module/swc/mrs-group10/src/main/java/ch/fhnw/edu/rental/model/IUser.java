package ch.fhnw.edu.rental.model;

import java.util.Calendar;
import java.util.List;

/**
 * Represents a user object.
 * 
 */
public interface IUser {

    /**
     * @param anID set the user's unique identification number.
     */
    void setId(int anID);

    /**
     * @param aName set the user's family name.
     * @throws NullPointerException The name must neither be <code>null</code>.
     * @throws MovieRentalException If the name is emtpy ("") or longer than 40 characters.
     */
    void setName(String aName);

    /**
     * @return get the user's first name.
     */

    String getFirstName();

    /**
     * @return get the user's family name.
     */
    String getName();

    /**
     * @return The user's unique identification number.
     */
    int getId();

    /**
     * Special equals method to check user equality.
     * 
     * @param obj user object
     * @return true if equal
     */
    boolean equals(Object obj);

    /**
     * Generate special hash code for user.
     * 
     * @return generated hash code
     */
    int hashCode();

    /**
     * Get all rentals of user.
     * 
     * @return list of rentals
     */
    List<Rental> getRentals();

    /**
     * Get user's birthdate.
     * 
     * @return user's birthdate
     */
    Calendar getBirthdate();

    /**
     * Set rentals of user.
     * 
     * @param rentalsList rental list
     */
    void setRentals(List<Rental> rentalsList);

    /**
     * Set first name of user.
     * 
     * @param firstName user's first name
     */
    void setFirstName(String firstName);

}
