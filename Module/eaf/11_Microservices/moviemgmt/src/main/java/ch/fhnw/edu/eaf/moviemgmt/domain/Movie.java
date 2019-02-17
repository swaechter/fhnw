package ch.fhnw.edu.eaf.moviemgmt.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "movie_id")
	private Long id;

	@Column(name = "movie_title")
	private String title;

	@Column(name = "movie_rented")
	private boolean rented;

	@Column(name = "movie_releasedate")
	private Date releaseDate;

	@ManyToOne
	@JoinColumn(name = "pricecategory_fk")
	private PriceCategory priceCategory;

	public Movie(String title, Date releaseDate, PriceCategory priceCategory)
			throws NullPointerException {
		if ((title == null) || (releaseDate == null)
				|| (priceCategory == null)) {
			throw new NullPointerException("not all input parameters are set!");
		}
		this.title = title;
		this.releaseDate = releaseDate;
		this.priceCategory = priceCategory;
		this.rented = false;
	}

}
