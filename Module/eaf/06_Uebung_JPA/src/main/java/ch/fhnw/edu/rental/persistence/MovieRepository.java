package ch.fhnw.edu.rental.persistence;

import java.util.List;

import ch.fhnw.edu.rental.model.Movie;

public interface MovieRepository extends Repository<Movie, Long> {
	List<Movie> findByTitle(String title);
}
