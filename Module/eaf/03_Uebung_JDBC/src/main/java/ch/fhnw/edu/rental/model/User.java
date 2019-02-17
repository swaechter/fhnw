package ch.fhnw.edu.rental.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private Long id;

    private String lastName;
    private String firstName;
    private String email;
    private List<Rental> rentals;

    // Default constructor so we can cache the object without having to load field references
    public User() {
    }

    public User(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.rentals = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public int getCharge() {
        int result = 0;
        for (Rental rental : rentals) {
            result += rental.getMovie().getPriceCategory().getCharge(rental.getRentalDays());
        }
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
