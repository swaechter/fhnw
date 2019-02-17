package ch.fhnw.edu.rental.model;

import java.io.Serializable;

/**
 * Class MovieRentalException represents a unchecked exception which will be thrown if an error
 * occurs using a method of this package.
 * 
 * @author Juerg Luthiger
 */
public class MovieRentalException extends RuntimeException implements Serializable {
    /**
     * Serial number.
     */
    private static final long serialVersionUID = -2166850969786176646L;

    /**
     * Creates a new MovieMgmtException with given message.
     * 
     * @param s Description of what caused this exception.
     */
    public MovieRentalException(String s) {
        super(s);
    }
}
