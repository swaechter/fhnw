package ch.fhnw.edu.rental.data;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ch.fhnw.edu.rental.model.IUser;
import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;

/**
 * @author wolfgang.schwaiger none
 */
public class DataSet {

    /**
     * none.
     */
    private List<IUser> userList;
    /**
     * none.
     */
    private List<Movie> movieList;
    /**
     * none.
     */
    private List<Rental> rentalList;

    /**
     * the currently used date format.
     */
    private DateFormat dateFormat;

    /**
     * used database.
     */
    private Database db;

    /**
     * @param dateFormat the used date format.
     * @throws Exception none.
     */
    public DataSet(DateFormat dateFormat) throws Exception {

        this.dateFormat = dateFormat;
        db = new Database();

        db.initDatabase();
        initLists();
    }

    /**
     * @throws Exception none.
     */
    private void initLists() throws Exception {

        userList = db.initUserList();
        movieList = db.initMovieList();
        rentalList = db.initRentalList();

        assignRentalsToUsers();
    }

    /**
     * assign rentals to the users.
     */
    private void assignRentalsToUsers() {

        for (Rental r : rentalList) {
            User u = (User) r.getUser();
            User userFromList = (User) this.getUserById(u.getId());
            userFromList.addRental(r);
        }

    }

    // /**
    // * @throws Exception
    // * none.
    // */
    // public void releaseAllResources() throws Exception {
    // connection.close();
    // }
    //
    /**
     * @return a list of users.
     */
    public List<IUser> getUserList() {
        return this.userList;
    }

    /**
     * Get user object by its id.
     * 
     * @param id identification of user
     * @return user object
     */
    public IUser getUserById(int id) {
        for (IUser c : userList) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    /**
     * Get user object by its surname.
     * 
     * @param name identification of user
     * @return user object
     */
    public IUser getUserByName(String name) {
        for (IUser c : userList) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Updates the rental list with changed user data.
     * 
     * @param user new user data
     */
    public void updateRentalsWithUser(User user) {
        for (int i = 0; i < rentalList.size(); i++) {
            if (rentalList.get(i).getUser().getId() == user.getId()) {
                rentalList.get(i).getUser().setName(user.getName());
                rentalList.get(i).getUser().setFirstName(user.getFirstName());
            }
        }

    }

    /**
     * @return user array list as an object.
     */
    public Object[][] getUserListAsObject() {
        int listSize = userList != null ? userList.size() : 0;
        Object[][] userArray = new Object[listSize][3];

        if (userList != null) {
            int i = 0;
            for (IUser u : userList) {
                userArray[i][0] = u.getId();
                userArray[i][1] = u.getName();
                userArray[i][2] = u.getFirstName();
                i++;
            }
        }
        return userArray;
    }

    /**
     * @return none.
     */
    public List<Movie> getMovieList() {
        return this.movieList;
    }

    /**
     * @param rented true return rented, false do not return rented
     * @param available true return not rented, false do not available
     * @return none
     */
    public Object[][] getMovieListAsObject(boolean rented, boolean available) {
        ArrayList<Movie> movieArray = null;

        if (movieList != null) {
            movieArray = prepareMovieArray(rented, available);
        }

        Object[][] movieObjArray = new Object[movieArray.size()][5];

        int i = 0;
        for (Movie m : movieArray) {
            movieObjArray[i++] = fillInMovieArrayElement(m);
        } // end of for

        return movieObjArray;

    }

    /**
     * Iterates over the internal movie list and fills them into an array.
     * @param rented choose rented movies
     * @param available choose available movies
     * @return new array
     */
    private ArrayList<Movie> prepareMovieArray(boolean rented, boolean available) {
        ArrayList<Movie> movieArray = new ArrayList<Movie>();
        
        for (Movie m : movieList) {
            if (rented && m.isRented()) {
                movieArray.add(m);
            }
            if (available && !m.isRented()) {
                movieArray.add(m);
            }
        } // end of for
        
        return movieArray;
    }

    /**
     * @param id none.
     * @return none.
     */
    public Movie getMovieById(long id) {

        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getId() == id) {
                return movieList.get(i);
            }
        } // end of for

        return null;

    }

    /**
     * updates the given movie with all values, identified by Movie.getId().
     * 
     * @param movie none.
     */
    public void updateMovie(Movie movie) {

        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getId() == movie.getId()) {
                movieList.get(i).setRented(movie.isRented());
                movieList.get(i).setPriceCategory(movie.getPriceCategory());
            }
        } // end of for

    }

    /**
     * @return none.
     */
    public Object[][] getMovieListAsObject() {
        int listSize = movieList != null ? movieList.size() : 0;
        Object[][] movieArray = new Object[listSize][5];

        if (movieList != null) {
            int i = 0;
            for (Movie m : movieList) {
                movieArray[i++] = fillInMovieArrayElement(m);
            } // end of for
        }

        return movieArray;
    }

    /**
     * Fill in a movie's data into an object array.
     * 
     * @param movie copy movie's data
     * @return array of Object with data in its elements.
     */
    private Object[] fillInMovieArrayElement(Movie movie) {
        Object[] result = new Object[5];
        result[0] = movie.getId();
        result[1] = movie.getTitle();
        result[2] = dateFormat.format(movie.getReleaseDate());
        result[3] = movie.isRented();
        result[4] = movie.getPriceCategory();
        return result;
    }

    /**
     * @return none.
     */
    public List<Rental> getRentalList() {
        return this.rentalList;
    }

    /**
     * @return none.
     */
    public Object[][] getRentalListAsObject() {
        int listSize = rentalList != null ? rentalList.size() : 0;
        Object[][] rentalArray = new Object[listSize][8];

        if (rentalList != null) {
            int i = 0;
            for (Rental r : rentalList) {
                rentalArray[i][0] = r.getId();
                rentalArray[i][1] = r.getRentalDays();
                rentalArray[i][2] = dateFormat.format(r.getRentalDate());
                rentalArray[i][3] = r.getUser().getName();
                rentalArray[i][4] = r.getUser().getFirstName();
                rentalArray[i][5] = r.getMovie().getTitle();
                rentalArray[i][6] = r.calcRemainingDaysOfRental(new Date(Calendar.getInstance().getTimeInMillis()));
                rentalArray[i][7] = r.getRentalFee();
                // reset rental days
                r.setRentalDays((Integer) rentalArray[i][1]);
                i++;
            } // end of for
        }

        return rentalArray;
    }

    /**
     * (non-Javadoc).
     * 
     * @see java.lang.Object#hashCode()
     * @return none.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((movieList == null) ? 0 : movieList.hashCode());
        result = prime * result + ((rentalList == null) ? 0 : rentalList.hashCode());
        result = prime * result + ((userList == null) ? 0 : userList.hashCode());
        return result;
    }

    /**
     * (non-Javadoc).
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     * @param obj none.
     * @return none.
     */
    @Override
    public boolean equals(Object obj) {

        if ((obj == null) || !(obj instanceof DataSet)) {
            return false;
        }
        
        return equals((DataSet) obj);
    }

    /**
     * Compares DataSet instance for equality.
     * @param dataset the object to compare
     * @return true if equal
     */
    private boolean equals(final DataSet dataset) {
        
        if (this == dataset) {
            return true;
        }
        
        if (!isMovieListEqual(dataset)) {
            return false;
        }

        if (!isRentalListEqual(dataset)) {
            return false;
        }

        if (!isUserListEqual(dataset)) {
            return false;
        }

        return true;
    }

    /**
     * Checks for user list equality.
     * 
     * @param other the object to compare with
     * @return true, if equal
     */
    private boolean isUserListEqual(final DataSet other) {
        if (userList == null) {
            if (other.userList != null) {
                return false;
            }
        } else if (!userList.equals(other.userList)) {
            return false;
        }
        return true;
    }

    /**
     * Checks for rental list equality.
     * 
     * @param other the object to compare with
     * @return true, if equal
     */
    private boolean isRentalListEqual(final DataSet other) {
        if (rentalList == null) {
            if (other.rentalList != null) {
                return false;
            }
        } else if (!rentalList.equals(other.rentalList)) {
            return false;
        }

        return true;
    }

    /**
     * Checks for movie list equality.
     * 
     * @param other the object to compare with
     * @return true, if equal
     */
    private boolean isMovieListEqual(final DataSet other) {
        if (movieList == null) {
            if (other.movieList != null) {
                return false;
            }
        } else if (!movieList.equals(other.movieList)) {
            return false;
        }

        return true;
    }

}
