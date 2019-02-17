package ch.fhnw.edu.eaf.moviemgmt.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.fhnw.edu.eaf.moviemgmt.domain.Movie;
import ch.fhnw.edu.eaf.moviemgmt.domain.PriceCategory;
import ch.fhnw.edu.eaf.moviemgmt.persistence.MovieRepository;
import ch.fhnw.edu.eaf.moviemgmt.persistence.PriceCategoryRepository;

@RestController
@RequestMapping("/movies")
public class MovieController {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private PriceCategoryRepository priceCategoryRepository;

	@GetMapping
	public ResponseEntity<List<Movie>> findAll() {
		Sort sort = new Sort(Direction.ASC, "id");
		List<Movie> movies = movieRepository.findAll(sort);
		log.debug("Found " + movies.size() + " movies");
		return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
	}

	@GetMapping(value = "/categories")
	public ResponseEntity<List<PriceCategory>> findAllPriceCategories() {
		Sort sort = new Sort(Direction.ASC, "id");
		List<PriceCategory> categories = priceCategoryRepository.findAll(sort);
		log.debug("Found " + categories.size() + " price categories");
		return new ResponseEntity<List<PriceCategory>>(categories, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Movie> findById(@PathVariable Long id) {
		Optional<Movie> movieOptional = movieRepository.findById(id);
		if (movieOptional.isPresent()) {
			Movie movie = movieOptional.get();
			log.debug("Found movie with id=" + movie.getId());
			return new ResponseEntity<Movie>(movie, HttpStatus.OK);
		}
		return new ResponseEntity<Movie>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<Movie> create(@Valid @RequestBody Movie movie) {
		String pcName = movie.getPriceCategory().getName();
		List<PriceCategory> categories = priceCategoryRepository.findAll();
		PriceCategory pc = null;
		for(PriceCategory tpc : categories) {
			if (tpc.getName().equals(pcName)) {
				pc = tpc;
			}
		}
 		movie.setPriceCategory(pc);
		movie = movieRepository.save(movie);
		log.debug("Created movie with id=" + movie.getId());
		return new ResponseEntity<Movie>(movie, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Movie> update(@Valid @RequestBody Movie newMovie, @PathVariable Long id) {
		Optional<Movie> movieOptional = movieRepository.findById(id);
		if (movieOptional.isPresent()) {
			Movie movie = movieOptional.get();
			movie.setRented(newMovie.isRented());
			movie.setReleaseDate(newMovie.getReleaseDate());
			movie.setTitle(newMovie.getTitle());
			movieRepository.save(movie);
			log.debug("Updated movie with id=" + movie.getId());
			return new ResponseEntity<Movie>(movie, HttpStatus.OK);
		}
		return new ResponseEntity<Movie>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		Optional<Movie> movieOptional = movieRepository.findById(id);
		if (movieOptional.isPresent()) {
			Movie movie = movieOptional.get();
			movieRepository.delete(movie);
			log.debug("Deleted movie with id=" + id);
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}

}
