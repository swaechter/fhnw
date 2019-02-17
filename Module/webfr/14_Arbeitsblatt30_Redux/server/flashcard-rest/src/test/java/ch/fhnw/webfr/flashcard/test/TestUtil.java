package ch.fhnw.webfr.flashcard.test;

import java.io.IOException;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {

    
    public static class QuestionnaireBuilder {
        private Questionnaire questionnaire;

        public QuestionnaireBuilder(String id) {
        	questionnaire = new Questionnaire();
        	questionnaire.setId(id);
        }
        
        public QuestionnaireBuilder description(String description) {
        	questionnaire.setDescription(description);
        	return this;
        }
        
        public QuestionnaireBuilder title(String title) {
        	questionnaire.setTitle(title);
        	return this;
        }
        
        public Questionnaire build() {
        	return questionnaire;
        }

    }

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}