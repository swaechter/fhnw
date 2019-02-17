package ch.fhnw.edu.eaf.springioc.providers;

public class ExternalizedHelloWorldMessageProvider implements MessageProvider {

    private final String message;

    public ExternalizedHelloWorldMessageProvider(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
