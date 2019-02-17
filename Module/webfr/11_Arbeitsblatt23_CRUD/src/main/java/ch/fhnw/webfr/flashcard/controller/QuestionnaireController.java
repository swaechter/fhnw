package ch.fhnw.webfr.flashcard.controller;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;
import ch.fhnw.webfr.flashcard.persistence.QuestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/questionnaires")
public class QuestionnaireController {

    private final QuestionnaireRepository questionnaireRepository;

    @Autowired
    public QuestionnaireController(QuestionnaireRepository questionnaireRepository) {
        this.questionnaireRepository = questionnaireRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Questionnaire>> findAll() {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        List<Questionnaire> questionnaires = questionnaireRepository.findAll(sort);
        return new ResponseEntity<>(questionnaires, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Questionnaire> findById(@PathVariable String id) {
        Optional<Questionnaire> questionnaire = questionnaireRepository.findById(id);
        if (questionnaire.isPresent()) {
            return new ResponseEntity<>(questionnaire.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Questionnaire> createQuestionnaire(@Valid @RequestBody Questionnaire questionnaire, BindingResult result) {
        if (!result.hasErrors()) {
            questionnaire = questionnaireRepository.save(questionnaire);
            return new ResponseEntity<>(questionnaire, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Questionnaire> updateQuestionnaire(@PathVariable String id, @Valid @RequestBody Questionnaire questionnaire, BindingResult result) {
        if (!result.hasErrors()) {
            Optional<Questionnaire> tempQuestionnaireOptional = questionnaireRepository.findById(id);
            if (tempQuestionnaireOptional.isPresent()) {
                Questionnaire tempQuestionnaire = tempQuestionnaireOptional.get();
                tempQuestionnaire.setTitle(questionnaire.getTitle());
                tempQuestionnaire.setDescription(questionnaire.getDescription());
                questionnaireRepository.save(tempQuestionnaire);
                return new ResponseEntity<>(tempQuestionnaire, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestionnaire(@PathVariable String id) {
        Optional<Questionnaire> questionnaire = questionnaireRepository.findById(id);
        if (questionnaire.isPresent()) {
            questionnaireRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
