package ch.fhnw.edu.rental.model;

import java.sql.Date;
import java.util.Calendar;

/**
 * @author wolfgang.schwaiger
 * 
 */
public class Rental {
    /** Maximum amount of movies a user can rent. */
    private static final int MAX_MOVIES = 3;

    /** Flag indicating whether object has been initialized. */
    private boolean initialized = false;

    /** Unique identifier for a rental object. */
    private int id;

    /** The movie that was rented. */
    private Movie movie;

    /** The user who is renting. */
    private IUser user;

    /** The date when the move was rented. */
    private Date rentalDate;

    /** The number of days the movie is rented. */
    private int rentalDays;

    /**
     * Constructs a real rental of a movie to a user at a given date for a certain number of days.
     * 
     * @param aUser User who is renting aMovie.
     * @param aMovie Movie that is rented.
     * @param nofRentalDays number of intended days of rental.
     * @throws IllegalStateException if the movie is already rented.
     * @throws NullPointerException if not all input parameters where set.
     * @throws IllegalArgumentException if the user is too young to see the movie
     */
    public Rental(IUser aUser, Movie aMovie, int nofRentalDays) {
        this(aUser, aMovie, nofRentalDays, new Date(Calendar.getInstance().getTimeInMillis()));
        if (aMovie.isRented()) {
            throw new IllegalStateException("movie is already rented!");
        }
        aMovie.setRented(true);
    }

    /**
     * Constructs a rental of a movie to a user at a given date for a certain number of days.
     * 
     * @param aUser User who is renting aMovie.
     * @param aMovie Movie that is rented.
     * @param nofRentalDays number of intended days of rental.
     * @param rentaldate the date the movie was rented.
     * @throws IllegalStateException if the movie is already rented.
     * @throws NullPointerException if not all input parameters where set.
     * @throws IllegalArgumentException if the user is too young to see the movie
     */
    private Rental(IUser aUser, Movie aMovie, int nofRentalDays, Date rentaldate) {
        if (!(isValidUser(aUser) && isValidMovie(aMovie) && isValidRentalDays(nofRentalDays))) {
            throw new NullPointerException("not all input parameters are set!");
        }

        if (aUser.getRentals().size() > MAX_MOVIES) {
            throw new MovieRentalException("Max. " + MAX_MOVIES + " Filme ausleihbar");
        }

        this.user = aUser;
        aUser.getRentals().add(this);
        this.movie = aMovie;
        this.rentalDays = nofRentalDays;
        this.rentalDate = rentaldate;
    }

    /**
     * This factory method creates Rentals from database.
     *
     * @param aUser User who is renting aMovie.
     * @param aMovie Movie that is rented.
     * @param nofRentalDays number of intended days of rental.
     * @param rentalDate the date the movie was rented.
     * @return new rental object
     */
    public static Rental createRentalFromDb(IUser aUser, Movie aMovie, int nofRentalDays, Date rentalDate) {
        return new Rental(aUser, aMovie, nofRentalDays, rentalDate);
    }

    /**
     * Calculate the number of remaining days in this rental. A call to this method will also reset
     * the stored value of nofRentalDays.
     * 
     * @param date calculate the remaining rental days starting from this date.
     * @return the number of remaining rental days. If this value is negative, then it indicates the
     *         number of days this movie is due.
     */
    public int calcRemainingDaysOfRental(Date date) {
        if (date == null) {
            throw new NullPointerException("given date is not set!");
        }
        // calculate difference between rental date and current
        // date excluding the captured rental days
        Long diff = ((rentalDate.getTime() - date.getTime()) / 86400000) + rentalDays;
        // set rental new calculated rental days
        setRentalDays(Math.abs(diff.intValue()));

        return diff.intValue();
    }

    /**
     * @return The rental fee to pay for this rental.
     */
    public double getRentalFee() {
        return movie.getPriceCategory().getCharge(rentalDays);
    }

    /**
     * @return the unique rental identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * @return the rented movie.
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * @return the user who is renting.
     */
    public IUser getUser() {
        return user;
    }

    /**
     * @return the rental date.
     */
    public Date getRentalDate() {
        return rentalDate;
    }

    /**
     * @return the expected number of rental days.
     */
    public int getRentalDays() {
        return rentalDays;
    }

    /**
     * The unique identifier can only be set once.
     * 
     * @param anId a unique identifier for rentals.
     * @throws MovieRentalException if the id has already been set.
     */
    public void setId(int anId) {
        if (initialized) {
            throw new MovieRentalException("illegal change of rental's id");
        } else {
            this.id = anId;
            initialized = true;
        }
    }

    /**
     * @param aMovie the movie that is rented.
     * @throws NullPointerException if aMovie is <code>null</code>.
     */
    protected void setMovie(Movie aMovie) {
        if (isValidMovie(aMovie)) {
            this.movie = aMovie;
        } else {
            throw new NullPointerException("no movie");
        }
    }

    /**
     * @param anUser the user that is renting a movie.
     * @throws NullPointerException if anUser is <code>null</code>.
     */
    protected void setUser(User anUser) {
        if (isValidUser(anUser)) {
            this.user = anUser;
        } else {
            throw new NullPointerException("no user");
        }
    }

    /**
     * @param aRentalDate the date of the rental.
     */
    protected void setRentalDate(Date aRentalDate) {
        this.rentalDate = aRentalDate;
    }

    /**
     * @param nofRentalDays the number of days the rental is expected to last.
     */
    public void setRentalDays(int nofRentalDays) {
        this.rentalDays = nofRentalDays;
    }

    @Override
    public boolean equals(Object o) {
        boolean result = this == o;
        if (!result) {
            if (o instanceof Rental) {
                Rental other = (Rental) o;
                result = initialized ? id == other.id : initialized == other.initialized;
                result &= this.movie.equals(other.movie);
                result &= this.user.equals(other.user);
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        int result = initialized ? id : 0;
        result = result * 19 + movie.hashCode();
        result = result * 19 + user.hashCode();
        return result;
    }

    /**
     * @param m test if movie is valid for this rental.
     * @return validity of movie.
     */
    private static boolean isValidMovie(Movie m) {
        return m != null;
    }

    /**
     * @param u test if user is valid for this rental.
     * @return validity of user.
     */
    private static boolean isValidUser(IUser u) {
        return u != null;
    }

    /**
     * @param days test if rental days are in valid range.
     * @return validity of rental days.
     */
    private static boolean isValidRentalDays(int days) {
        return days > 0;
    }
}
