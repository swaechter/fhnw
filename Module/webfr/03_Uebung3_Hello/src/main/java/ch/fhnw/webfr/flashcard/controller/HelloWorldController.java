package ch.fhnw.webfr.flashcard.controller;

import ch.fhnw.webfr.flashcard.persistence.QuestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/")
public class HelloWorldController {

    private final QuestionnaireRepository questionnaireRepository;

    @Autowired
    public HelloWorldController(QuestionnaireRepository questionnaireRepository) {
        this.questionnaireRepository = questionnaireRepository;
    }

    @GetMapping("/hello")
    public void sayHello(HttpServletRequest request, HttpServletResponse response, @RequestParam("name") String userName) throws IOException {
        String url = request.getContextPath() + "/questionnaires";
        PrintWriter writer = response.getWriter();
        writer.append("<html><head><title>Example</title></head><body>");
        writer.append("<h3>Welcome " + userName + "!</h3>");
        writer.append("<p>Quiostionnaires found: " + questionnaireRepository.findAll().size() + "</p>");
        writer.append("<p><a href='" + response.encodeURL(url) + "'>Back</a></p>");
        writer.append("</body></html>");
    }
}
