package ch.fhnw.edu.rental.services;

import java.util.List;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;

public interface UserService {
	public User getUserById(Long id);
	
	public User save(User user);
	
	public void deleteUser(User user);
	
	public List<User> getAllUsers();
	
	public List<User> getUsersByName(String name);
	
	public Rental rentMovie(User user, Movie movie, int days);
	
	public void returnMovie(User user, Movie movie);
}
