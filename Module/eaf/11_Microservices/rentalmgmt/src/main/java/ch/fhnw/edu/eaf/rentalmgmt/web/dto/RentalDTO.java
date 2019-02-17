package ch.fhnw.edu.eaf.rentalmgmt.web.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class RentalDTO {
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("user")
	private UserDTO user;
	
	@JsonProperty("movie")
	private MovieDTO movie;
	
	@JsonProperty("rentalDate")
	private Date rentalDate;

	@JsonProperty("rentalDays")
	private int rentalDays;

	public void setId(Long id) {
		this.id = id;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public void setMovie(MovieDTO movie) {
		this.movie = movie;
	}

	public void setRentalDate(Date rentalDate) {
		this.rentalDate = rentalDate;
	}

	public void setRentalDays(int rentalDays) {
		this.rentalDays = rentalDays;
	}
	
	
}
