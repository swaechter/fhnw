package ch.fhnw.webfr.flashcard.controller;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;
import ch.fhnw.webfr.flashcard.persistence.QuestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/")
public class QuestionnaireController {

    private final QuestionnaireRepository questionnaireRepository;

    @Autowired
    public QuestionnaireController(QuestionnaireRepository questionnaireRepository) {
        this.questionnaireRepository = questionnaireRepository;
    }

    @GetMapping("/")
    public void handleIndexRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.append("<html><head><title>Example</title></head><body>");
        writer.append("<h3>Welcome</h3>");
        String url = request.getContextPath() + "/questionnaires";
        writer.append("<p><a href='" + response.encodeURL(url) + "'>All Questionnaires</a></p>");
        writer.append("</body></html>");
    }

    @GetMapping("/questionnaires")
    public void handleQuestionnairesRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Questionnaire> questionnaires = questionnaireRepository.findAll();
        PrintWriter writer = response.getWriter();
        writer.append("<html><head><title>Example</title></head><body>");
        writer.append("<h3>Fragebögen</h3>");
        for (Questionnaire questionnaire : questionnaires) {
            String url = request.getContextPath() + "/questionnaires/" + questionnaire.getId();
            writer.append("<p><a href='" + response.encodeURL(url) + "'>" + questionnaire.getTitle() + "</a></p>");
        }
        writer.append("</body></html>");
    }

    @GetMapping("/questionnaires/{id}")
    public void handleQuestionnaireRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id) throws IOException {
        try {
            String url = request.getContextPath() + "/questionnaires";
            Questionnaire questionnaire = questionnaireRepository.findById(id).get();
            PrintWriter writer = response.getWriter();
            writer.append("<html><head><title>Example</title></head><body>");
            writer.append("<h3>" + questionnaire.getTitle() + "</h3>");
            writer.append("<p>" + questionnaire.getDescription() + "</p>");
            writer.append("<p><a href='" + response.encodeURL(url) + "'>Back</a></p>");
            writer.append("</body></html>");
        } catch (Exception exception) {
            String url = request.getContextPath() + "/questionnaires";
            PrintWriter writer = response.getWriter();
            writer.append("<html><head><title>Example</title></head><body>");
            writer.append("<h3>Error</h3>");
            writer.append("<p>No questionnare found!</p>");
            writer.append("<p><a href='" + response.encodeURL(url) + "'>Back</a></p>");
            writer.append("</body></html>");
        }
    }
}
