package ch.fhnw.edu.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import ch.fhnw.edu.rental.gui.BusinessLogic;
import ch.fhnw.edu.rental.gui.MovieRentalApplicationGui;

@SpringBootApplication
public class MovieRentalApplication implements CommandLineRunner {

	public static void main(String[] args) {
		new SpringApplicationBuilder(MovieRentalApplication.class)
			.headless(false) // we start a GUI and are not headless though
			.web(WebApplicationType.SERVLET) // set to SERVLET if h2 console should be enabled
			.run(args);
	}

	@Value("${gui:true}")
	boolean gui;

	@Autowired
	BusinessLogic logic;

	@Override
	public void run(String... args) throws Exception {
		if (gui) {
			java.awt.EventQueue.invokeLater(() -> new MovieRentalApplicationGui(logic).setVisible(true));
		}
	}
}
