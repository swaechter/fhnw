package ch.fhnw.edu.eaf.moviemgmt;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ch.fhnw.edu.eaf.moviemgmt.domain.Movie;
import ch.fhnw.edu.eaf.moviemgmt.domain.PriceCategory;
import ch.fhnw.edu.eaf.moviemgmt.domain.PriceCategoryRegular;
import ch.fhnw.edu.eaf.moviemgmt.persistence.MovieRepository;
import ch.fhnw.edu.eaf.moviemgmt.persistence.PriceCategoryRepository;
import ch.fhnw.edu.eaf.moviemgmt.web.MovieController;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
@TestPropertySource(locations = "classpath:test.properties")
public class MovieControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private Date now;

	@MockBean
	private MovieRepository movieRepositoryMock;

	@MockBean
	private PriceCategoryRepository priceCategoryRepository;

	@Before
	public void setUp() {
		now = new Date();
		Mockito.reset(movieRepositoryMock);
		Mockito.reset(priceCategoryRepository);
	}

	@Test
	public void findById_MovieFound_ShouldReturnFound() throws Exception {
		Movie movie = new MovieBuilder("Movie1", now, new PriceCategoryRegular()).id(new Long(1)).build();

		Optional<Movie> mOptional = Optional.of(movie);
		when(movieRepositoryMock.findById(1L)).thenReturn(mOptional);

		mockMvc.perform(get("/movies/{id}", 1L).header("Accept", "application/json"))
				// .andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", equalTo(1)))
				.andExpect(jsonPath("$.title", equalTo("Movie1")));
	}

	@Test
	public void findById_MovieNotExisting_ShouldReturnNotFound() throws Exception {
		mockMvc.perform(get("/movies/{id}", 2L).header("Accept", "application/json")).andExpect(status().isNotFound());
	}

	@Test
	public void findAll_MoviesFound_ShouldReturnFoundMovies() throws Exception {
		Movie movie1 = new MovieBuilder("Movie1", now, new PriceCategoryRegular()).id(new Long(1)).build();

		Movie movie2 = new MovieBuilder("Movie2", now, new PriceCategoryRegular()).id(new Long(2)).build();

		when(movieRepositoryMock.findAll(Sort.by("id"))).thenReturn(Arrays.asList(movie1, movie2));

		mockMvc.perform(get("/movies").header("Accept", "application/json"))
				// .andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", equalTo(1)))
				.andExpect(jsonPath("$[0].title", equalTo("Movie1")))
				.andExpect(jsonPath("$[1].id", equalTo(2)))
				.andExpect(jsonPath("$[1].title", equalTo("Movie2")));
	}
}

class MovieBuilder {
	private Movie movie;

	public MovieBuilder(String title, Date releaseDate, PriceCategory priceCategory) {
		movie = new Movie(title, releaseDate, priceCategory);
	}

	public MovieBuilder id(Long id) {
		movie.setId(id);
		return this;
	}

	public Movie build() {
		return movie;
	}
}
