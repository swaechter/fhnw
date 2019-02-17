package ch.fhnw.webfr.flashcard.runner;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;
import ch.fhnw.webfr.flashcard.persistence.QuestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataRunner implements CommandLineRunner {

    private final QuestionnaireRepository questionnaireRepository;

    @Autowired
    public DataRunner(QuestionnaireRepository questionnaireRepository) {
        this.questionnaireRepository = questionnaireRepository;
    }

    @Override
    public void run(String... args) {
        for (int i = 0; i < 5; i++) {
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setTitle("Title " + i);
            questionnaire.setDescription("Description of item " + i);
            questionnaireRepository.save(questionnaire);
        }
    }
}
