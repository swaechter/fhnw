package ch.fhnw.edu.jpa.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String lastName;

    private String firstName;

    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private List<Rental> rentals = new ArrayList<>();

    protected User() {
        // JPA requires that a default constructor is available
    }

    public User(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }


    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

}
