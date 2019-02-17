package ch.fhnw.cuie.module01.tablestyling;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Dieter Holz
 */
public class PersonPM {
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName  = new SimpleStringProperty();
    private final StringProperty city      = new SimpleStringProperty();

    public PersonPM(String firstName, String lastName, String city){
        setFirstName(firstName);
        setLastName(lastName);
        setCity(city);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }
}
