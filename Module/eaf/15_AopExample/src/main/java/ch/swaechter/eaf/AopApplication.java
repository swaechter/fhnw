package ch.swaechter.eaf;

import ch.swaechter.eaf.user.User;
import ch.swaechter.eaf.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class AopApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class, args);
    }

    @Service
    public class UserRunner implements CommandLineRunner {

        @Autowired
        private UserRepository userRepository;

        @Override
        public void run(String... args) {
            for (int i = 0; i < 10; i++) {
                userRepository.save(new User("User " + i));
            }
        }
    }
}
