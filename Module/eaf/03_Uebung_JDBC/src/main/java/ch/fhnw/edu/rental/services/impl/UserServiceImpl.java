package ch.fhnw.edu.rental.services.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.persistence.MovieRepository;
import ch.fhnw.edu.rental.persistence.RentalRepository;
import ch.fhnw.edu.rental.persistence.UserRepository;
import ch.fhnw.edu.rental.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RentalRepository rentalRepo;
	
	@Autowired
	private MovieRepository movieRepo;

	@Override
	public User getUserById(Long id) {
		return userRepo.findById(id).orElse(null);
	}
	
	@Override
	public List<User> getAllUsers() {
		List<User> users = userRepo.findAll();
		log.debug("getAllUsers() done");
		return users;
	}

	@Override
	public User save(User user) {
		user = userRepo.save(user);
		log.debug("saved or updated user[" + user.getId() + "]");
		return user;
	}

	@Override
	public void deleteUser(User user) {
		if (user == null) {
			throw new RuntimeException("'user' parameter is not set!");
		}

		userRepo.delete(user);	// if (user.getRentals().size()>0) associated rentals 
								// have to be deleted by userDao.delete(user) as well
		if (log.isDebugEnabled()) {
			log.debug("user[" + user.getId() + "] deleted");
		}
	}

	@Override
	public List<User> getUsersByName(String name) {
		List<User> users = userRepo.findByLastName(name);
		return users;
	}

	@Override
	public Rental rentMovie(User user, Movie movie, int days) {
		if (user == null) 
			throw new IllegalArgumentException("parameter 'user' is null!");
		if (movie == null) 
			throw new IllegalArgumentException("parameter 'movie' is null!");
		if (days < 1)
			throw new IllegalArgumentException("parameter 'days' must be > 0");

		Rental rental = new Rental(user, movie, days);
		rental = rentalRepo.save(rental);

		// the constructor of rental changed the movie to rented, this change must
		// be persisted.
		rental.setMovie(movieRepo.save(movie));

		return rental;
	}
	
	@Override
	public void returnMovie(User user, Movie movie) {
		if (user == null) 
			throw new IllegalArgumentException("parameter 'user' is null!");
		if (movie == null) 
			throw new IllegalArgumentException("parameter 'movie' is null!");

		Rental rentalToRemove = null;
		for (Rental rental : user.getRentals()) {
			if (rental.getMovie().equals(movie)) {
				rentalToRemove = rental;
				break;
			}
		}
		
		user.getRentals().remove(rentalToRemove);
		rentalToRemove.getMovie().setRented(false);
		rentalRepo.delete(rentalToRemove);
	}
}
