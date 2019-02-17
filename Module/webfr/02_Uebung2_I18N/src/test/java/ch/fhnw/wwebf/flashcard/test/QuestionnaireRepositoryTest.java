package ch.fhnw.wwebf.flashcard.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;
import ch.fhnw.webfr.flashcard.persistence.QuestionnaireRepository;

public class QuestionnaireRepositoryTest {
	private QuestionnaireRepository questionnaireRepository;

	@Before
	public void setUp() throws Exception {
		questionnaireRepository = new QuestionnaireRepository();
		questionnaireRepository.clear();
	}

	@Test
	public void testCreate() {
		Questionnaire q1 = new Questionnaire("MyTitle 1", "MyDescription 1");
		Long id = questionnaireRepository.save(q1);
		assertNotNull(id);
	}

	@Test
	public void testFindAll() {
		Questionnaire q1 = new Questionnaire("MyTitle 1", "MyDescription 1");
		questionnaireRepository.save(q1);
		assertEquals(1, questionnaireRepository.findAll().size());
		
		Questionnaire q2 = new Questionnaire("MyTitle 2", "MyDescription 2");
		questionnaireRepository.save(q2);
		assertEquals(2, questionnaireRepository.findAll().size());
	}

	@Test
	public void testFindById() {
		Questionnaire q1 = new Questionnaire("MyTitle 1", "MyDescription 1");
		Long id = questionnaireRepository.save(q1);
		Questionnaire q = questionnaireRepository.findById(id);
		assertNotNull(q);
		assertEquals("MyTitle 1", q.getTitle());
		assertEquals("MyDescription 1", q.getDescription());
		assertEquals(1, questionnaireRepository.findAll().size());
	}

}
