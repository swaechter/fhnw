package ch.fhnw.edu.rental.model;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents the client of a movie store.
 * 
 */
public class User implements IUser {

    /**
     * Flag indicating whether object has been initialized.
     */
    private boolean initialized = false;

    /**
     * An identification number unique to each user.
     */
    private int id;

    /**
     * The user's family name.
     */
    private String name;

    /**
     * The user's first name.
     */
    private String firstName;

    /**
     * A list of rentals of the user.
     */
    private List<Rental> rentals = new LinkedList<Rental>();

    /**
     * Create a new user with the given name information.
     * 
     * @param aName the user's family name.
     * @param aFirstName the user's first name.
     * @throws NullPointerException The name must neither be <code>null</code>.
     * @throws MovieRentalException If the name is empty ("") or longer than 40 characters.
     */
    public User(String aName, String aFirstName) {

        if (aName != null) {
            if ((aName.length() == 0) || (aName.length() > 40)) {
                throw new MovieRentalException("invalid name value");
            }
        } else {
            throw new NullPointerException("invalid name value");
        }

        checkIfFirstNameValid(aFirstName);

        this.name = aName;
        this.firstName = aFirstName;
    }

    /**
     * Checks if first name is valid.
     * 
     * @param aFirstName the first name of the user.
     */
    private void checkIfFirstNameValid(String aFirstName) {
        if (aFirstName != null) {
            if ((aFirstName.length() == 0) || (aFirstName.length() > 40)) {
                throw new MovieRentalException("invalid firstName value");
            }
        } else {
            throw new NullPointerException("invalid firstName value");
        }

    }

    /**
     * @return The user's unique identification number.
     */
    public int getId() {
        return id;
    }

    /**
     * @param anID set the user's unique identification number.
     */
    public void setId(int anID) {
        if (initialized) {
            throw new MovieRentalException("illegal change of user's id");
        } else {
            initialized = true;
            this.id = anID;
        }
    }

    /**
     * @return get a list of the user's rentals.
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * @param someRentals set the user's rentals.
     */
    public void setRentals(List<Rental> someRentals) {
        this.rentals = someRentals;
    }

    /** {@inheritDoc} */
    public String getName() {
        return name;
    }

    /**
     * @param aName set the user's family name.
     * @throws NullPointerException The name must neither be <code>null</code>.
     * @throws MovieRentalException If the name is emtpy ("") or longer than 40 characters.
     */
    public void setName(String aName) {
        if (aName == null) {
            throw new NullPointerException("invalid name value");
        } else if (aName.length() == 0 || aName.length() > 40) {
            throw new MovieRentalException("invalid name value");
        }
        this.name = aName;
    }

    /**
     * @return get the user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param aFirstName set the user's family name.
     * @throws NullPointerException The first name must not be <code>null</code>.
     * @throws MovieRentalException If the name is emtpy ("") or longer than 40 characters.
     */
    public void setFirstName(String aFirstName) {
        if (aFirstName == null) {
            throw new NullPointerException("invalid firstName value");
        } else if (aFirstName.length() == 0 || aFirstName.length() > 40) {
            throw new MovieRentalException("invalid firstName value");
        }
        this.firstName = aFirstName;
    }

    /**
     * @return user's birth date.
     */
    public Calendar getBirthdate() {
      // Todo: change this for Modulpraktikum Unit Testing
      return null;
    }

    /**
     * Calculate the total charge the user has to pay for all his/her rentals.
     * 
     * @return the total charge.
     */
    public double getCharge() {
        double result = 0.0d;
        for (Rental rental : rentals) {
            result += rental.getMovie().getPriceCategory().getCharge(rental.getRentalDays());
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.fhnw.edu.rental.model.IUser#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        boolean result = this == o;
        if (!result) {
            if (o instanceof User) {
                User other = (User) o;
                result = initialized ? id == other.id : initialized == other.initialized;
                result &= name.equals(other.name);
                result &= firstName.equals(other.firstName);
            }
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.fhnw.edu.rental.model.IUser#hashCode()
     */
    @Override
    public int hashCode() {
        int result = (initialized) ? id : 0;
        result = 19 * result + name.hashCode();
        result = 19 * result + firstName.hashCode();
        return result;
    }

    /**
     * check if user has rentals.
     * 
     * @return true if found
     */
    public boolean hasRentals() {
        return !this.rentals.isEmpty();
    }

    /**
     * add a new rental to the user.
     * 
     * @param rental the rental
     * @return number of rentals of the user
     */
    public int addRental(Rental rental) {

        this.rentals.add(rental);

        return this.rentals.size();

    }
}
