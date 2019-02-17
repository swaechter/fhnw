package ch.fhnw.jpa.inheritance;

import javax.persistence.Basic;
import javax.persistence.Entity;

@Entity
public class Post extends Topic {

    @Basic()
    private String content;

    protected Post() {
    }

    public Post(String title, String owner) {
        this(title, owner, null);
    }

    public Post(String title, String owner, String content) {
        super(title, owner);
        this.content = content;
    }

}
