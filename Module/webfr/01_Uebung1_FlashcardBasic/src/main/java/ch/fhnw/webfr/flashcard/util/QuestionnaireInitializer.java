package ch.fhnw.webfr.flashcard.util;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;
import ch.fhnw.webfr.flashcard.persistence.QuestionnaireRepository;
import org.apache.log4j.Logger;

public class QuestionnaireInitializer {

    private static Logger logger = Logger.getLogger(QuestionnaireInitializer.class);

    public QuestionnaireRepository initRepoWithTestData() {
        QuestionnaireRepository repo = new QuestionnaireRepository();
        repo.save(new Questionnaire("Test Questionnaire 1", "Lorem ipsum dolor sit amet..."));
        repo.save(new Questionnaire("Test Questionnaire 2", "Lorem ipsum dolor sit amet..."));
        repo.save(new Questionnaire("i18n Test fünf", "Lorem ipsum dolor sit amet..."));

        logger.debug("Questionnaires initialized successfully");

        return repo;
    }
}
