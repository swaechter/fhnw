package ch.fhnw.edu.jpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
public class Rental {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Movie movie;

    private LocalDate rentalDate;

    private int rentalDays;

    protected Rental() {
        // JPA requires that a default constructor is available
    }

    public Rental(Movie movie, int rentalDays) {
        this(movie, rentalDays, LocalDate.now());
    }

    public Rental(Movie movie, int rentalDays, LocalDate rentalDate) {
        if (movie == null || rentalDays <= 0) {
            throw new IllegalArgumentException("not all input parameters are set!");
        }
        this.movie = movie;
        this.rentalDays = rentalDays;
        this.rentalDate = rentalDate;
    }

    public Long getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public int getRentalDays() {
        return rentalDays;
    }

}
