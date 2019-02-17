package ch.fhnw.edu.rental.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Children")
public class PriceCategoryChildren extends PriceCategory {

    // Mandatory JPA default constructor
    protected PriceCategoryChildren() {
    }

    @Override
    public double getCharge(int daysRented) {
        double result = 1.5;
        if (daysRented > 3) {
            result += (daysRented - 3) * 1.5;
        }
        return result;
    }

    @Override
    public String toString() {
        return "Children";
    }
}
