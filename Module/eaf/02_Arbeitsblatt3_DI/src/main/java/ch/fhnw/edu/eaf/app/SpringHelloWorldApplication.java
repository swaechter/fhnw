package ch.fhnw.edu.eaf.app;

import ch.fhnw.edu.eaf.app.domain.MessageRenderer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringHelloWorldApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringHelloWorldApplication.class, args);
        MessageRenderer renderer = context.getBean(MessageRenderer.class);
        renderer.render();
    }
}
