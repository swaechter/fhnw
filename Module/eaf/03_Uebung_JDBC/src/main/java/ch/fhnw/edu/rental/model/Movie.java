package ch.fhnw.edu.rental.model;

import java.time.LocalDate;
import java.util.Objects;

public class Movie {
    private Long id;

    private final String title;
    private final LocalDate releaseDate;
    private boolean rented;
    private PriceCategory priceCategory;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Movie movie = (Movie) object;
        return id != null && Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
