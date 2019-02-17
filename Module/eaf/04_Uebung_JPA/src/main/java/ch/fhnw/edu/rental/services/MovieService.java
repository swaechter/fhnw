package ch.fhnw.edu.rental.services;

import java.util.List;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.PriceCategory;

public interface MovieService {
	public Movie getMovieById(Long id);
	
	public List<Movie> getAllMovies();
	
	public List<Movie> getMoviesByTitle(String title);
	
	public Movie saveMovie(Movie movie);
	
	public void deleteMovie(Movie movie);
	
	public List<PriceCategory> getAllPriceCategories();
}
