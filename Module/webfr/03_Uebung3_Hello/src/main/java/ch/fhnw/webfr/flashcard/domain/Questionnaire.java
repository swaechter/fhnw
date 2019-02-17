package ch.fhnw.webfr.flashcard.domain;

public class Questionnaire {

    private Integer id;

    private String title;

    private String description;

    public Questionnaire(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
