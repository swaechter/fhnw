package ch.fhnw.edu.rental;

import ch.fhnw.edu.rental.gui.MovieRentalView;

/**
 * The main class of the application. Starts the main gui class.
 */
public final class MovieRentalApplication {

    /**
     * Hide constructor, since pure utility class.
     */
    private MovieRentalApplication() {

    }

    /**
     * @param args program argument list.
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MovieRentalView().setVisible(true);
            }
        });
    }
}
