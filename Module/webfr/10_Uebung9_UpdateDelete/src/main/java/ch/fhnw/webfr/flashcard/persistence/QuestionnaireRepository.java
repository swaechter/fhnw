package ch.fhnw.webfr.flashcard.persistence;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;

public interface QuestionnaireRepository extends MongoRepository<Questionnaire, String> {
	List<Questionnaire> findByTitle(String title);
}
