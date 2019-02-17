package ch.fhnw.webfr.flashcard.controller;

import ch.fhnw.webfr.flashcard.persistence.QuestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class QuestionnaireController {

    private final QuestionnaireRepository questionnaireRepository;

    @Autowired
    public QuestionnaireController(QuestionnaireRepository questionnaireRepository) {
        this.questionnaireRepository = questionnaireRepository;
    }

    @GetMapping("/")
    public ModelAndView showIndex() {
        return new ModelAndView("redirect:questionnaires");
    }

    @GetMapping("/questionnaires")
    public ModelAndView findAll() {
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("questionnaires", questionnaireRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/questionnaires/{id}")
    public ModelAndView showQuestionnaire(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("show");
        modelAndView.addObject("questionnaire", questionnaireRepository.findById(id).get());
        return modelAndView;
    }
}
