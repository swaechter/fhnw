package ch.fhnw.edu.rental.model;

/**
 * @author wolfgang.schwaiger
 * 
 */
public final class RegularPriceCategory extends PriceCategory {

    /** Regular price category id. */
    private static final int ID = 1;

    /**
     * @return the unique identifier of this price category.
     */
    public int getId() {
        return RegularPriceCategory.ID;
    }

    /**
     * A regular movie costs 2 in the first two days and then another 1.5 for each additional day.
     * 
     * @see ch.fhnw.edu.rental.model.PriceCategory#getCharge(int)
     * @param daysRented no of days that a movie is rented.
     * @return rental price for movie.
     */
    @Override
    public double getCharge(int daysRented) {
        double result = 0;
        if (daysRented > 0) {
            result = 2;
        }
        if (daysRented > 2) {
            result = 2 + (daysRented - 2) * 1.5;
        }
        return result;
    }

    @Override
    public String toString() {
        return "Regular";
    }

    /** singleton instance. */
    private static RegularPriceCategory singleton = new RegularPriceCategory();

    /**
     * prevent instantiation from outside.
     */
    private RegularPriceCategory() {
    };

    /**
     * Access singleton instance.
     * 
     * @return singleton
     */
    public static RegularPriceCategory getInstance() {
        return singleton;
    }
}
