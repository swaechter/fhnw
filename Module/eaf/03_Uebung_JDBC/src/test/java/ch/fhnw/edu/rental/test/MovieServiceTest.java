package ch.fhnw.edu.rental.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.model.PriceCategoryNewRelease;
import ch.fhnw.edu.rental.model.PriceCategoryRegular;
import ch.fhnw.edu.rental.services.MovieService;

@RunWith(SpringRunner.class)
@SpringBootTest(properties={"gui=false"})
@Transactional
public class MovieServiceTest {
	
	@Autowired
	private MovieService movieService;
	
	private int totalNumberOfMovies;
	
	@Before
	public void setUp() {
		totalNumberOfMovies = movieService.getAllMovies().size();
	}
	
	@Test
	public void testChangePriceCategory() {
		String title = "Marie Curie";
		Movie movie = movieService.getMovieById(1L);
		assertEquals(movie.getTitle(), title);

		PriceCategory priceCategory = movie.getPriceCategory();
		assertTrue(priceCategory instanceof PriceCategoryRegular);

		List<PriceCategory> categories = movieService.getAllPriceCategories();
		assertEquals(categories.size(), 3);

		PriceCategory category = categories.get(2);
		assertTrue(category instanceof PriceCategoryNewRelease);

		movie.setPriceCategory(category);
		movie = movieService.saveMovie(movie);

		Movie updatedMovie = movieService.getMovieById(1L);
		assertEquals(updatedMovie.getTitle(), title);

		PriceCategory updatePriceCategory = movie.getPriceCategory();
		assertTrue(updatePriceCategory instanceof PriceCategoryNewRelease);
	}

	@Test
	public void testCreateMovie() {
		List<PriceCategory> categories = movieService.getAllPriceCategories();
		assertEquals(3, categories.size());

		List<Movie> movies = movieService.getAllMovies();

		PriceCategory category = categories.get(2);
		assertTrue(category instanceof PriceCategoryNewRelease);

		Movie movie = new Movie("testMovie", LocalDate.now(), category);
		movie = movieService.saveMovie(movie);

		movies = movieService.getAllMovies();
		assertEquals(totalNumberOfMovies + 1, movies.size());
	}

	@Test
	public void testCreateMovie2() {
		List<PriceCategory> categories = movieService.getAllPriceCategories();
		assertEquals(3, categories.size());

		PriceCategory category = categories.get(1);
		Movie movie = new Movie("testMovie", LocalDate.now(), category);

		assertTrue(movie.getId() == null);
		movie = movieService.saveMovie(movie);
		assertTrue(movie.getId() != null);

		Movie movie2 = movieService.getMovieById(movie.getId());
		assertTrue(movie.getReleaseDate().equals(movie2.getReleaseDate()));
		assertTrue(movie.equals(movie2));
	}


	@Test
	public void testDeleteMovie() {
		List<Movie> movies = movieService.getAllMovies();
		
		
		Movie movie = null;
		String title = "Die göttliche Ordnung";
		for (Movie m : movies) {
			if (m.getTitle().equals(title)) {
				movie = m;
			}
		}
		assertTrue("Movie " + title + "not found", movie != null);

		movieService.deleteMovie(movie);

		movies = movieService.getAllMovies();
		assertEquals(totalNumberOfMovies-1, movies.size());
	}
	
	@Test(expected=RuntimeException.class)
	public void testDeleteRentedMovie() {
		Movie movie = movieService.getMovieById(1L);
		assertTrue(movie.isRented());
		movieService.deleteMovie(movie);
	}

	@Test(expected = RuntimeException.class)
	public void testDeleteMovieUsedByRental() {
		List<Movie> movies = movieService.getAllMovies();

		Movie movie = movies.get(0);
		assertEquals("Marie Curie", movie.getTitle());
		movieService.deleteMovie(movie);
	}
	
	@Test
	public void testGetByTitle() {
		List<Movie> movies = movieService.getAllMovies();
		Movie m = movies.get(0);
		
		movies = movieService.getMoviesByTitle(m.getTitle());
		assertTrue("result must contain movie m", movies.size() > 0);
		assertTrue("result must contain movie m", movies.contains(m));
	}

	@Test
	public void testDeleteAndInsertMovie() {
		List<Movie> movies = movieService.getAllMovies();

		Movie movie = movies.get(4);

		movieService.deleteMovie(movie);

		movies = movieService.getAllMovies();
		assertEquals(totalNumberOfMovies-1, movies.size());
		
		movie = movieService.saveMovie(movie);

		movies = movieService.getAllMovies();
		assertEquals(totalNumberOfMovies, movies.size());
	}

//	@Test
//	public void changeMovie() {
//		List<Movie> movies = movieService.getAllMovies();
//		assertEquals(5, movies.size());
//		Movie m1 = movies.get(1);
//		Movie m2 = new Movie("####", m1.getReleaseDate(), m1.getPriceCategory());
//		m2.setId(m1.getId());
//		m2.setRented(m1.isRented());
//		movieService.saveOrUpdateMovie(m2);
//		
//		assertEquals(m1.getTitle(), m2.getTitle());
//	}

}
