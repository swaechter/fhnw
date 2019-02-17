package ch.fhnw.edu.eaf.rentalmgmt;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
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
import org.springframework.web.client.RestTemplate;

import ch.fhnw.edu.eaf.rentalmgmt.domain.Rental;
import ch.fhnw.edu.eaf.rentalmgmt.persistence.RentalRepository;
import ch.fhnw.edu.eaf.rentalmgmt.web.RentalController;
import ch.fhnw.edu.eaf.rentalmgmt.web.dto.MovieDTO;
import ch.fhnw.edu.eaf.rentalmgmt.web.dto.UserDTO;

@RunWith(SpringRunner.class)
@WebMvcTest(RentalController.class)
@TestPropertySource(locations = "classpath:test.properties")
public class RentalControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RentalRepository rentalRepositoryMock;

	@MockBean
	private RestTemplate restTemplate;

	@Before
	public void setUp() {
		Mockito.reset(rentalRepositoryMock);
	}

	@Test
	public void findById_RentalFound_ShouldReturnFound() throws Exception {
		Rental rental = new RentalBuilder(1, 1, 10).id(new Long(1)).build();
		Optional<Rental> rOptional = Optional.of(rental);

		UserDTO user = new UserDTO();
		user.setId(1L);
		when(restTemplate.getForObject("http://usermanagement/users/1", UserDTO.class)).thenReturn(user);

		MovieDTO movie = new MovieDTO();
		movie.setId(1L);
		when(restTemplate.getForObject("http://moviemanagement/movies/1", MovieDTO.class)).thenReturn(movie);

		when(rentalRepositoryMock.findById(1L)).thenReturn(rOptional);

		mockMvc.perform(get("/rentals/{id}", 1L).header("Accept", "application/json"))
				// .andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", equalTo(1)))
				.andExpect(jsonPath("$.movie.id", equalTo(1))).andExpect(jsonPath("$.user.id", equalTo(1)));
		Mockito.verify(rentalRepositoryMock, times(1)).findById(1L);
	}

	@Test
	public void findById_RentalNotExisting_ShouldReturnNotFound() throws Exception {
		mockMvc.perform(get("/movies/{id}", 2L).header("Accept", "application/json")).andExpect(status().isNotFound());
		Mockito.verify(rentalRepositoryMock, times(0)).findById(1L);
	}

	@Test
	public void findAll_RentalsFound_ShouldReturnFoundRentals() throws Exception {
		Rental rental1 = new RentalBuilder(1, 1, 10).id(new Long(1)).build();

		Rental rental2 = new RentalBuilder(1, 1, 10).id(new Long(2)).build();

		when(rentalRepositoryMock.findAll(Sort.by("id"))).thenReturn(Arrays.asList(rental1, rental2));

		UserDTO user = new UserDTO();
		user.setId(1L);
		when(restTemplate.getForObject("http://usermanagement/users/1", UserDTO.class)).thenReturn(user);

		MovieDTO movie = new MovieDTO();
		movie.setId(1L);
		when(restTemplate.getForObject("http://moviemanagement/movies/1", MovieDTO.class)).thenReturn(movie);

		mockMvc.perform(get("/rentals").header("Accept", "application/json"))
				// .andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].id", equalTo(1)))
				.andExpect(jsonPath("$[0].user.id", equalTo(1))).andExpect(jsonPath("$[0].movie.id", equalTo(1)))
				.andExpect(jsonPath("$[1].id", equalTo(2))).andExpect(jsonPath("$[1].user.id", equalTo(1)))
				.andExpect(jsonPath("$[1].movie.id", equalTo(1)));
		Mockito.verify(rentalRepositoryMock, times(1)).findAll(Sort.by("id"));
	}
}

class RentalBuilder {
	private Rental rental;

	public RentalBuilder(int userId, int movieId, int rentalDays) {
		rental = new Rental(new Long(userId), new Long(movieId), rentalDays);
	}

	public RentalBuilder id(Long id) {
		rental.setId(id);
		return this;
	}

	public Rental build() {
		return rental;
	}
}
