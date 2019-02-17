package ch.fhnw.edu.rental.gui;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.services.MovieService;
import ch.fhnw.edu.rental.services.RentalService;
import ch.fhnw.edu.rental.services.UserService;

@Component
public class BusinessLogicLocal implements BusinessLogic {

    @Autowired
	private MovieService movieService;
	
    @Autowired
	private UserService userService;
	
    @Autowired
	private RentalService rentalService;

	public BusinessLogicLocal() {
	}

	public String getUserLastName(Long id){
		return getUser(id).getLastName();
	}
	public String getUserFirstName(Long id){
		return getUser(id).getFirstName();
	}
	public int getUserRentalsSize(Long id){
		return getUser(id).getRentals().size();
	}
	
	public String getMovieTitle(Long id){
		return getMovie(id).getTitle();
	}
	public String getMoviePriceCategory(Long id){
		return getMovie(id).getPriceCategory().toString();
	}
	public LocalDate getMovieReleaseDate(Long id){
		return getMovie(id).getReleaseDate();
	}
	public boolean getMovieIsRented(Long id){
		return getMovie(id).isRented();
	}
	
	
	private User getUser(Long id){
		return userService.getUserById(id);
	}
	
	private Movie getMovie(Long id){
		return movieService.getMovieById(id);
	}
	
	private Rental getRental(Long id){
		return rentalService.getRentalById(id);
	}
	
	
	public void removeRental(Long rentalId){
		rentalService.deleteRental(getRental(rentalId));
	}
	
	public void deleteUser(Long userId) {
		userService.deleteUser(getUser(userId));
	}

	public void updateUser(Long userId, String lastName, String firstName) {
		// this method only changes the name of the user, but the referenced rentals are not changed.
		User user = new User(lastName, firstName);
		user.setId(userId);
		user = userService.save(user); // save method has to ignore the associated rentals
	}

	public Long createUser(String lastName, String firstName) {
		User user = new User(lastName, firstName);
		user = userService.save(user);
		return user.getId();
	}
	
	public void deleteMovie(Long movieId) {
		movieService.deleteMovie(getMovie(movieId));
	}

	public Long createMovie(String movieTitle, LocalDate date, String category) {
		Movie movie = null;
		for(PriceCategory pc : movieService.getAllPriceCategories()){
			if(pc.toString().equals(category)){
				movie = new Movie(movieTitle, date, pc);
			}
		}
		movie = movieService.saveMovie(movie);
		return movie.getId();
	}

	public void updateMovie(Long movieId, String movieTitle, LocalDate date,	String category) {
		// only called when movie is updated
		Movie movie = null;
		Movie orig = movieService.getMovieById(movieId);
		for(PriceCategory pc : movieService.getAllPriceCategories()){
			if(pc.toString().equals(category)){
				movie = new Movie(movieTitle, date, pc);
			}
		}
		movie.setId(movieId);
		movie.setRented(orig.isRented());
		movie = movieService.saveMovie(movie);
	}

	public void createRental(Long movieId, Long userId, Integer rentalDays) {
		Movie movie = getMovie(movieId);
		User user = getUser(userId);
		userService.rentMovie(user, movie, rentalDays);
	}
	
	public void visitUsers(UserVisitor visitor) {
		for(User u : userService.getAllUsers()){
			visitor.visit(u.getId(), u.getLastName(), u.getFirstName());
		}
	}

	public void visitMovies(MovieVisitor visitor) {
		for (Movie m : movieService.getAllMovies()) {
			visitor.visit(m.getId(), m.getTitle(), m.getReleaseDate(), m.isRented(), m.getPriceCategory().toString());
		}
	}

	public void visitRentals(RentalVisitor visitor) {
		for(Rental r : rentalService.getAllRentals()){
			User user = r.getUser();
			Movie movie = r.getMovie();
			int remainingDays = r.getRentalDays() - (int)ChronoUnit.DAYS.between(r.getRentalDate(), LocalDate.now());
			visitor.visit(r.getId(), r.getRentalDays(), r.getRentalDate(), user.getLastName(), user.getFirstName(), movie.getTitle(), remainingDays, r.getRentalFee());
		}
	}

	public void visitRentalsOfUser(Long userId, RentalVisitor visitor) {
		User user = getUser(userId);
		for(Rental r : user.getRentals()) {
			int remainingDays = r.getRentalDays() - (int)ChronoUnit.DAYS.between(r.getRentalDate(), LocalDate.now());
			visitor.visit(r.getId(), r.getRentalDays(), r.getRentalDate(), user.getLastName(), user.getFirstName(), r.getMovie().getTitle(), remainingDays, r.getRentalFee());
		}
	}

}
