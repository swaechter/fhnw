package ch.fhnw.webfr.flashcard.web;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;
import ch.fhnw.webfr.flashcard.persistence.QuestionnaireRepository;

@Controller
@RequestMapping("/questionnaires")
public class QuestionnaireController {
	private static final Log logger = LogFactory.getLog(QuestionnaireController.class);

	@Autowired
	private QuestionnaireRepository questionnaireRepository;

	@RequestMapping(method = RequestMethod.GET)
	public String findAll(Model model) {
		model.addAttribute("questionnaires", questionnaireRepository.findAll());
		return "questionnaires/list";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String findById(@PathVariable String id, Model model) {
		model.addAttribute("questionnaire", questionnaireRepository.findById(id).get());
		return "questionnaires/show";
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("questionnaire", new Questionnaire());
		return "questionnaires/create";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Questionnaire questionnaire, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			logger.debug("Binding error: " + bindingResult.getAllErrors());
			return "questionnaires/create";
		}
		questionnaireRepository.save(questionnaire);
		return "redirect:/questionnaires";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable String id) {
		questionnaireRepository.deleteById(id);
		return "redirect:/questionnaires";
	}

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable String id, Model uiModel) {
		Optional<Questionnaire> questionnaireOptional = questionnaireRepository.findById(id);
		if (questionnaireOptional.isPresent()) {
			Questionnaire questionnaire = questionnaireOptional.get();
			uiModel.addAttribute("questionnaire", questionnaire);
			return "questionnaires/update";
		} else {
			logger.info("No entity found with id=" + id);
			return "404";
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable String id, @Valid Questionnaire questionnaire, BindingResult bindingResult,
			Model uiModel) {
		if (bindingResult.hasErrors()) {
			logger.debug("Binding error: " + bindingResult.getAllErrors());
			return "questionnaires/update";
		}
		Optional<Questionnaire> questionnaireOptional = questionnaireRepository.findById(id);
		if (questionnaireOptional.isPresent()) {
			Questionnaire oldQuestionnsaire = questionnaireOptional.get();
			oldQuestionnsaire.setDescription(questionnaire.getDescription());
			oldQuestionnsaire.setTitle(questionnaire.getTitle());
			questionnaireRepository.save(oldQuestionnsaire);
		}
		return "redirect:/questionnaires";
	}

	@RequestMapping(params = "exception", method = RequestMethod.GET)
	public void exception() throws DataAccessException {
		throw new DataIntegrityViolationException("Just Testing");
	}
}
