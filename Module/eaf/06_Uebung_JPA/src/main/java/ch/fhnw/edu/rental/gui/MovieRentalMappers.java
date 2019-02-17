package ch.fhnw.edu.rental.gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class MovieRentalMappers {
	
	private BusinessLogic services;

	public MovieRentalMappers(BusinessLogic services) {
		this.services = services;
	}
	
	public Object[][] getUserListAsObject() {
		final List<Object[]> users = new LinkedList<Object[]>();
		services.visitUsers(
			new BusinessLogic.UserVisitor() {
				@Override
				public void visit(Long id, String lastName, String firstName){
					users.add(new Object[]{id, lastName, firstName});
				}
			}
		);
		return users.toArray(new Object[][]{});
	}

	public Object[][] getMovieListAsObject(final boolean rented) {
		final List<Object[]> movies = new LinkedList<Object[]>();
		services.visitMovies(
			new BusinessLogic.MovieVisitor() {
				@Override
				public void visit(Long id, String title, LocalDate releaseDate, boolean isRented, String priceCategory){
					if(rented == isRented)
						movies.add(new Object[]{id, title, releaseDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), isRented, priceCategory});
				}
			}
		);
		return movies.toArray(new Object[][]{});
	}

	public Object[][] getMovieListAsObject() {
		final List<Object[]> movies = new LinkedList<Object[]>();
		services.visitMovies(
			new BusinessLogic.MovieVisitor() {
				@Override
				public void visit(Long id, String title, LocalDate releaseDate, boolean isRented, String priceCategory){
					movies.add(new Object[]{id, title, releaseDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), isRented, priceCategory});
				}
			}
		);
		return movies.toArray(new Object[][]{});
	}

	public Object[][] getRentalListAsObject() {
		final List<Object[]> rentals = new LinkedList<Object[]>();
		services.visitRentals(
			new BusinessLogic.RentalVisitor() {
				@Override
				public void visit(Long id, int rentalDays, LocalDate rentalDate,
						String lastName, String firstName, String movieTitle,
						int remainingDays, double rentalFee) {
					rentals.add(new Object[]{
						id, rentalDays, rentalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
						lastName, firstName, movieTitle, remainingDays, rentalFee
					});
					
				}
			}
		);
		return rentals.toArray(new Object[][]{});
	}
	
	public Object[][] getRentalListAsObjectForUser(Long userId){
		final List<Object[]> rentals = new LinkedList<Object[]>();
		services.visitRentalsOfUser(userId, 
			new BusinessLogic.RentalVisitor() {
				@Override
				public void visit(Long id, int rentalDays, LocalDate rentalDate,
						String lastName, String firstName, String movieTitle,
						int remainingDays, double rentalFee) {
					rentals.add(new Object[]{
						id, rentalDays, rentalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
						movieTitle, remainingDays, rentalFee
					});
					
				}
			}
		);
		return rentals.toArray(new Object[][]{});
	}

}
