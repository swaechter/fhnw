package ch.fhnw.webfr.flashcard.persistence;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionnaireRepository extends MongoRepository<Questionnaire, String> {

    List<Questionnaire> findByTitle(String title);
}
