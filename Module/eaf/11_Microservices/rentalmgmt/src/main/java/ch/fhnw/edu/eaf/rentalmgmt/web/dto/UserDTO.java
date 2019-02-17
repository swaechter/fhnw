package ch.fhnw.edu.eaf.rentalmgmt.web.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class UserDTO {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("email")
	private String email;
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

}
