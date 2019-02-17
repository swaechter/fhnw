package ch.fhnw.webfr.flashcard.web;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;
import ch.fhnw.webfr.flashcard.persistence.QuestionnaireRepository;
import ch.fhnw.webfr.flashcard.util.Globals;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = {"/*"})
public class BasicServlet extends HttpServlet {

    private QuestionnaireRepository questionnaireRepository;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");

        String[] pathElements = request.getRequestURI().split("/");
        if (isPathElementQuestionnare(pathElements)) {
            handleQuestionnaireRequest(request, response, pathElements);
        } else if (isLastPathElementQuestionnaires(pathElements)) {
            handleQuestionnairesRequest(request, response);
        } else {
            handleIndexRequest(request, response);
        }
    }

    private boolean isPathElementQuestionnare(String[] pathElements) {
        try {
            String questionnareString = pathElements[pathElements.length - 2];
            Integer.valueOf(pathElements[pathElements.length - 1]);
            return questionnareString.equals("questionnaires");
        } catch (Exception exception) {
            return false;
        }
    }

    private boolean isLastPathElementQuestionnaires(String[] pathElements) {
        try {
            String last = pathElements[pathElements.length - 1];
            return last.equals("questionnaires");
        } catch (Exception exception) {
            return false;
        }
    }

    private void handleQuestionnaireRequest(HttpServletRequest request, HttpServletResponse response, String[] pathElements) throws IOException {
        try {
            String url = request.getContextPath() + request.getServletPath() + "/questionnaires/";
            Long questionnareIndex = Long.valueOf(pathElements[pathElements.length - 1]);
            Questionnaire questionnaire = questionnaireRepository.findById(questionnareIndex);
            PrintWriter writer = response.getWriter();
            writer.append("<html><head><title>Example</title></head><body>");
            writer.append("<h3>" + questionnaire.getTitle() + "</h3>");
            writer.append("<p>" + questionnaire.getDescription() + "</p>");
            writer.append("<p><a href='" + response.encodeURL(url) + "'>Back</a></p>");
            writer.append("</body></html>");
        } catch (Exception exception) {
            String url = request.getContextPath() + request.getServletPath() + "/questionnaires/";
            PrintWriter writer = response.getWriter();
            writer.append("<html><head><title>Example</title></head><body>");
            writer.append("<h3>Error</h3>");
            writer.append("<p>No questionnare found!</p>");
            writer.append("<p><a href='" + response.encodeURL(url) + "'>Back</a></p>");
            writer.append("</body></html>");
        }
    }

    private void handleQuestionnairesRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Questionnaire> questionnaires = questionnaireRepository.findAll();
        PrintWriter writer = response.getWriter();
        writer.append("<html><head><title>Example</title></head><body>");
        writer.append("<h3>Fragebögen</h3>");
        for (Questionnaire questionnaire : questionnaires) {
            String url = request.getContextPath() + request.getServletPath();
            url = url + "/questionnaires/" + questionnaire.getId().toString();
            writer.append("<p><a href='" + response.encodeURL(url) + "'>" + questionnaire.getTitle() + "</a></p>");
        }
        writer.append("</body></html>");
    }

    private void handleIndexRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.append("<html><head><title>Example</title></head><body>");
        writer.append("<h3>Welcome</h3>");
        String url = request.getContextPath() + request.getServletPath();
        writer.append("<p><a href='" + response.encodeURL(url) + "/questionnaires'>All Questionnaires</a></p>");
        writer.append("</body></html>");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext servletContext = config.getServletContext();
        questionnaireRepository = (QuestionnaireRepository) servletContext.getAttribute(Globals.KEY_NAME_REPOSITORY);
    }
}
