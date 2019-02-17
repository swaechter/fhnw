package ch.fhnw.edu.rental.persistence;

import java.util.List;

import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;

public interface RentalRepository extends Repository<Rental, Long> {
	List<Rental> findByUser(User user);
}
