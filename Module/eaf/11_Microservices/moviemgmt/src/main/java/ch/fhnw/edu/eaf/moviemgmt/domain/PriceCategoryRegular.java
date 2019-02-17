package ch.fhnw.edu.eaf.moviemgmt.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("RegularPriceCategory")
public class PriceCategoryRegular extends PriceCategory {

	@Override
	public double getCharge(int daysRented) {
		double result = 2;
		if (daysRented > 2)
			result += (daysRented - 2) * 1.5;
		return result;
	}

	@Override
	public String toString() {
		return "Regular";
	}

	@Override
	public String getName() {
		return toString();
	}
}
