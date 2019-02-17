package ch.fhnw.webfr.flashcard.persistence;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionnaireRepository {

    private static final Log logger = LogFactory.getLog(QuestionnaireRepository.class);

    private List<Questionnaire> questionnaires = new ArrayList<>();

    public Integer save(Questionnaire q) {
        Integer id = questionnaires.size();
        q.setId(id);
        questionnaires.add(q);
        return id;
    }

    public List<Questionnaire> findAll() {
        return questionnaires;
    }

    public Questionnaire findById(Integer id) {
        return questionnaires.get(id);
    }

    public void clear() {
        questionnaires = new ArrayList<>();
    }

    @PostConstruct
    public void initRepoWithTestData() {
        save(new Questionnaire("Test Questionnaire 1", "Lorem ipsum dolor sit amet..."));
        save(new Questionnaire("Test Questionnaire 2", "Lorem ipsum dolor sit amet..."));
        save(new Questionnaire("i18n Test fünf", "Lorem ipsum dolor sit amet..."));
        logger.debug("Questionnaires initialized successfully");
    }
}