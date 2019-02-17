package ch.fhnw.webfr.flashcard.test.web;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;
import ch.fhnw.webfr.flashcard.persistence.QuestionnaireRepository;
import ch.fhnw.webfr.flashcard.test.web.TestUtil.QuestionnaireBuilder;
import ch.fhnw.webfr.flashcard.web.QuestionnaireController;

@RunWith(SpringRunner.class)
@WebMvcTest(QuestionnaireController.class)
public class QuestionnaireControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private QuestionnaireRepository questionnaireRepositoryMock;

	@Before
    public void setUp() {
		Mockito.reset(questionnaireRepositoryMock);
    }
	
	@Test
    public void findAll_QuestionnairesFound_ShouldReturnFound() throws Exception {
		Questionnaire q1 = new QuestionnaireBuilder("1")
				.description("MyDescription 1")
				.title("MyTitle 1")
				.build();
		
		Questionnaire q2 = new QuestionnaireBuilder("2")
				.description("MyDescription 2")
				.title("MyTitle 2")
				.build();

		when(questionnaireRepositoryMock.findAll()).thenReturn(Arrays.asList(q1, q2));
		
	    mockMvc.perform(get("/questionnaires")
        )
        		.andExpect(status().isOk())
        		.andExpect(view().name("questionnaires/list"))
        		.andExpect(model().attribute("questionnaires", hasSize(2)))
        		.andExpect(model().attribute("questionnaires", hasItem(
        				allOf(
        						hasProperty("id", is("1")),
        						hasProperty("title", is("MyTitle 1")),
        						hasProperty("description", is("MyDescription 1"))
        				)
        		)))
        		.andExpect(model().attribute("questionnaires", hasItem(
        				allOf(
        						hasProperty("id", is("2")),
        						hasProperty("title", is("MyTitle 2")),
        						hasProperty("description", is("MyDescription 2"))
        				)
        		)));
    }
	
	@Test
	public void create_NewQuestionnaire_ShouldReturnOK() throws Exception {
		Questionnaire q1 = new QuestionnaireBuilder("1")
				.description("MyDescription 1")
				.title("MyTitle 1")
				.build();
		when(questionnaireRepositoryMock.save(q1)).thenReturn(q1);
		mockMvc.perform(post("/questionnaires")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("description", "MyDescription 1")
                .param("title", "MyTitle 1")
        )
        	.andExpect(status().is3xxRedirection())
        	.andExpect(view().name("redirect:/questionnaires"));
	}
	
	@Test
	public void create_NewQuestionnaireWithTitleNull_ShouldReturnErrors() throws Exception {	
		mockMvc.perform(post("/questionnaires")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("description", "MyDescription 1")
                .param("title", (String) null)
        )
    	.andExpect(status().isOk())
    	.andExpect(view().name("questionnaires/create"))
    	.andExpect(model().attributeExists("questionnaire"));
	}
	
	@Test
	public void create_NewQuestionnaireWithTitleTooSmall_ShouldReturnErrors() throws Exception {	
		mockMvc.perform(post("/questionnaires")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("description", "MyDescription 1")
                .param("title", "1")
        )
    	.andExpect(status().isOk())
    	.andExpect(view().name("questionnaires/create"))
    	.andExpect(model().attributeExists("questionnaire"));
	}
	
	@Test
	public void create_NewQuestionnaireWithTitleTooBig_ShouldReturnErrors() throws Exception {	
		mockMvc.perform(post("/questionnaires")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("description", "MyDescription 1")
                .param("title", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore ")
        )
    	.andExpect(status().isOk())
    	.andExpect(view().name("questionnaires/create"))
    	.andExpect(model().attributeExists("questionnaire"));
	}
	
	@Test
	public void create_NewQuestionnaireWithDescriptionNull_ShouldReturnErrors() throws Exception {	
		mockMvc.perform(post("/questionnaires")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("description", (String) null)
                .param("title", "MyTitle 1")
        )
    	.andExpect(status().isOk())
    	.andExpect(view().name("questionnaires/create"))
    	.andExpect(model().attributeExists("questionnaire"));
	}
	
	@Test
	public void create_NewQuestionnaireWithDescriptionTooSmall_ShouldReturnErrors() throws Exception {	
		mockMvc.perform(post("/questionnaires")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("description", "Small")
                .param("title", "MyTitle 1")
        )
        	.andExpect(status().isOk())
        	.andExpect(view().name("questionnaires/create"))
        	.andExpect(model().attributeExists("questionnaire"));
	}		
}
