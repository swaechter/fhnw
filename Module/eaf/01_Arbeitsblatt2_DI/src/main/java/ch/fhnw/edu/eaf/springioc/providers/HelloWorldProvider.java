package ch.fhnw.edu.eaf.springioc.providers;

public class HelloWorldProvider implements MessageProvider {

    @Override
    public String getMessage() {
        return "Hello world!";
    }
}
