package ch.fhnw.webfr.flashcard.persistence;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;

import java.util.ArrayList;
import java.util.List;

public class QuestionnaireRepository {

    //	private static QuestionnaireRepository instance;
    private List<Questionnaire> questionnaires = new ArrayList<Questionnaire>();

//	private QuestionnaireRepository() {}
//
//	public static QuestionnaireRepository getInstance() {
//		if (instance == null) {
//			instance = new QuestionnaireRepository();
//		}
//		return instance;
//	}

    public Long save(Questionnaire q) {
        Long id = new Long(questionnaires.size());
        q.setId(id);
        questionnaires.add(q);
        return id;
    }

    public List<Questionnaire> findAll() {
        return questionnaires;
    }

    public Questionnaire findById(Long id) {
        return questionnaires.get(id.intValue());
    }

    public void clear() {
        questionnaires = new ArrayList<Questionnaire>();
    }
}
