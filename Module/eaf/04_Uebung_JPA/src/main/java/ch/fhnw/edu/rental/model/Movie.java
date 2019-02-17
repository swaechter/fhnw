package ch.fhnw.edu.rental.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "MOVIES")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MOVIE_ID")
    private Long id;

    @Column(name = "MOVIE_TITLE")
    private String title;

    @Column(name = "MOVIE_RELEASEDATE")
    private LocalDate releaseDate;

    @Column(name = "MOVIE_RENTED")
    private boolean rented;

    @OneToOne
    @JoinColumn(name = "PRICECATEGORY_FK")
    private PriceCategory priceCategory;

    // Mandatory JPA default constructor
    protected Movie() {
    }

    public Movie(String title, LocalDate releaseDate, PriceCategory priceCategory) throws NullPointerException {
        if ((title == null) || (releaseDate == null) || (priceCategory == null)) {
            throw new NullPointerException("not all input parameters are set!");
        }
        this.title = title;
        this.releaseDate = releaseDate;
        this.priceCategory = priceCategory;
        this.rented = false;
    }

    public PriceCategory getPriceCategory() {
        return priceCategory;
    }

    public void setPriceCategory(PriceCategory priceCategory) {
        this.priceCategory = priceCategory;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
