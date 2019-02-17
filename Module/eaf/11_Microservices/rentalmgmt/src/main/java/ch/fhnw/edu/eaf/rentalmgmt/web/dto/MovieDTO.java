package ch.fhnw.edu.eaf.rentalmgmt.web.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class MovieDTO {
	public Long id;
	public String title;
	public boolean rented;
	public Date releaseDate;
	public PriceCategoryDTO priceCategory;

	// Just needed for junit testing
	public void setId(long id) {
		this.id = id;
	}
}

@JsonAutoDetect
class PriceCategoryDTO {
	public Long id;
	public String name;
}
