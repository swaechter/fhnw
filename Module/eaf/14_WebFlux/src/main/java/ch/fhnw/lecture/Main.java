package ch.fhnw.lecture;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Main implements CommandLineRunner {
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(Main.class).run(args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
	
}