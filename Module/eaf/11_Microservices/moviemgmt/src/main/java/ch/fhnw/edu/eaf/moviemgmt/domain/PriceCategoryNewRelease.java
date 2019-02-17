package ch.fhnw.edu.eaf.moviemgmt.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("NewReleasePriceCategory")
public class PriceCategoryNewRelease extends PriceCategory {

	@Override
	public double getCharge(int daysRented) {
		return daysRented * 3;
	}

	@Override
	public int getFrequentRenterPoints(int daysRented) {
		// add bonus for two day new release rental
		if (daysRented > 1) {
			return 2;
		}
		else {
			return 1;
		}
	}

	@Override
	public String toString() {
		return "New Release";
	}

	@Override
	public String getName() {
		return toString();
	}
}
