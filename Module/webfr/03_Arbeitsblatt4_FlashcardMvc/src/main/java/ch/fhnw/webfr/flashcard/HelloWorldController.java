package ch.fhnw.webfr.flashcard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloWorldController {

    @GetMapping("/")
    public String showHelloWorld() {
        return "Hello form the Flashcard MVC application!";
    }
}
