package ch.fhnw.webfr.flashcard.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="questionnaires")
public class Questionnaire {
    @Id
    private String id;
    
    private String  title;
    
    private String description;
    
    public void setId(String id) {
		this.id = id;
	}
    
    public String getId() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}
    
    public String getTitle() {
		return title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
