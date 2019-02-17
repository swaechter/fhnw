package ch.fhnw.edu.rental.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.services.MovieService;
import ch.fhnw.edu.rental.services.RentalService;
import ch.fhnw.edu.rental.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(properties={"gui=false"})
@Transactional
public class RentalServiceTest {
	
	@Autowired
	private UserService userService;

	@Autowired
	private MovieService movieService;

	@Autowired
	private RentalService rentalService;

	@Test
	public void testRentMovie() {
		User u  = userService.getAllUsers().get(0);
		int size = u.getRentals().size();
		
		// search a movie which is not yet rented
		Movie m = null;
		List<Movie> movies = movieService.getAllMovies();
		for(Movie mm : movies) 
			if(!mm.isRented()) m = mm;
			
		userService.rentMovie(u, m, 10);
		Assert.assertEquals(size+1, u.getRentals().size());
		
		// check whether this movie is also assigned to u in the DB
		for(User uu : userService.getAllUsers())
			if(u.getId().equals(uu.getId())) u = uu;
		Assert.assertEquals(size+1, u.getRentals().size());
	}

	@Test
	public void testGetAllRentals() {
		List<Rental> rentals = rentalService.getAllRentals();
		Assert.assertEquals(3, rentals.size());
	}
	
	@Test
	public void testGetAllRentalInfos() {
		List<Rental> rentals = rentalService.getAllRentals();
		Assert.assertEquals(3, rentals.size());
		Rental rental = rentals.get(0);
		Assert.assertNotNull(rental.getUser());
		Assert.assertNotNull(rental.getMovie());
		Assert.assertNotNull(rental.getUser().getEmail());
		Assert.assertNotNull(rental.getMovie().getTitle());
	}	

	@Test
	public void testGetAllRentalsByUser() {
		List<User> users = userService.getUsersByName("Keller");
		Assert.assertEquals(1, users.size());
		User user = users.get(0);
		List<Rental> rentals = user.getRentals();
		Assert.assertEquals(2, rentals.size());
	}

	@Test
	public void testReadRental() {
		Rental rental = rentalService.getRentalById(1L);
		User user = rental.getUser();
		Assert.assertEquals("Keller", user.getLastName());
		Assert.assertEquals(2, user.getRentals().size());
	}
	
	@Test
	public void testDeleteRental() {
		Rental rental = rentalService.getRentalById(1L);
		User user = rental.getUser();
		Assert.assertEquals("Keller", user.getLastName());
		Assert.assertEquals(2, user.getRentals().size());
		rentalService.deleteRental(rental);
		List<Rental> rentals = rentalService.getAllRentals();
		Assert.assertEquals(2, rentals.size());
		user = userService.save(user);
		user = userService.getUserById(user.getId());
		Assert.assertEquals(1, user.getRentals().size());
	}
}
