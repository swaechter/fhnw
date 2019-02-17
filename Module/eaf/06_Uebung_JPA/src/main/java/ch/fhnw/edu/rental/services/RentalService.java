package ch.fhnw.edu.rental.services;

import java.util.List;

import ch.fhnw.edu.rental.model.Rental;

public interface RentalService {
	public Rental getRentalById(Long id);

	public List<Rental> getAllRentals();
	
	public void deleteRental(Rental rental);
}
