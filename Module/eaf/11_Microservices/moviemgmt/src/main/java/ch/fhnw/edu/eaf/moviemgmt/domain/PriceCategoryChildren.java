package ch.fhnw.edu.eaf.moviemgmt.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("ChildrenPriceCategory")
public class PriceCategoryChildren extends PriceCategory {

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

	@Override
	public String getName() {
		return toString();
	}
}
