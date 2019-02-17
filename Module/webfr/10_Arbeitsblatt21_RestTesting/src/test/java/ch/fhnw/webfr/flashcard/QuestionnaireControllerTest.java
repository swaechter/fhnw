package ch.fhnw.webfr.flashcard;

import ch.fhnw.webfr.flashcard.TestUtil.QuestionnaireBuilder;
import ch.fhnw.webfr.flashcard.controller.QuestionnaireController;
import ch.fhnw.webfr.flashcard.domain.Questionnaire;
import ch.fhnw.webfr.flashcard.persistence.QuestionnaireRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void findById_QuestionnaireNotExisting_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/questionnaires/{id}", 2L)
            .header("Accept", "application/json")
        )
            .andExpect(status().isNotFound());
        Mockito.verify(questionnaireRepositoryMock, times(0)).findById("1");
    }

    @Test
    public void findAll_QuestionnairesFound_ShouldReturnFoundQuestionnaires() throws Exception {
        Questionnaire questionnaire1 = new QuestionnaireBuilder(Long.toString(1))
            .description("MyDescription1")
            .title("MyTitle1")
            .build();

        Questionnaire questionnaire2 = new QuestionnaireBuilder(Long.toString(2))
            .description("MyDescription2")
            .title("MyTitle2")
            .build();

        when(questionnaireRepositoryMock.findAll(Sort.by("id"))).thenReturn(Arrays.asList(questionnaire1, questionnaire2));

        mockMvc.perform(get("/questionnaires")
            .header("Accept", "application/json")
        )
            //.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is("1")))
            .andExpect(jsonPath("$[0].title", is("MyTitle1")))
            .andExpect(jsonPath("$[0].description", is("MyDescription1")))
            .andExpect(jsonPath("$[1].id", is("2")))
            .andExpect(jsonPath("$[1].title", is("MyTitle2")))
            .andExpect(jsonPath("$[1].description", is("MyDescription2")));
        Mockito.verify(questionnaireRepositoryMock, times(1)).findAll(Sort.by("id"));
    }

    @Test
    public void create_NewQuestionnaire_ShouldReturnOK() throws Exception {
        Questionnaire questionnaire = new QuestionnaireBuilder(Long.toString(1))
            .description("My Description")
            .title("My Title")
            .build();

        // Important: You must override Questionnaire.equals() to be able to execute these mock calls!
        when(questionnaireRepositoryMock.save(questionnaire)).thenReturn(questionnaire);

        mockMvc.perform(post("/questionnaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionnaire)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is("1")))
            .andExpect(jsonPath("$.title", is("My Title")))
            .andExpect(jsonPath("$.description", is("My Description")));
        Mockito.verify(questionnaireRepositoryMock, times(1)).save(questionnaire);
    }

    @Test
    public void create_NewQuestionnaireWithEmptyTitle_ShouldReturnNOK() throws Exception {
        Questionnaire questionnaire = new QuestionnaireBuilder(Long.toString(1))
            .description("MyDescription")
            .build();

        // Important: You must override Questionnaire.equals() to be able to execute these mock calls!
        when(questionnaireRepositoryMock.save(questionnaire)).thenReturn(questionnaire);

        mockMvc.perform(post("/questionnaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionnaire)))
            .andExpect(status().isPreconditionFailed());
        Mockito.verify(questionnaireRepositoryMock, times(0)).save(questionnaire);
    }
}
