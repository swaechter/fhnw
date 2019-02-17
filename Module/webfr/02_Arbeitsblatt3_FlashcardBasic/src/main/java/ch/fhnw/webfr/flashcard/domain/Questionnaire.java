package ch.fhnw.webfr.flashcard.domain;

public class Questionnaire {

    private Long id;

    private String title;

    private String description;

    public Questionnaire(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
