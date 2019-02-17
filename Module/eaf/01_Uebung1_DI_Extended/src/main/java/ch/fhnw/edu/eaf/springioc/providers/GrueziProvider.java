package ch.fhnw.edu.eaf.springioc.providers;

public class GrueziProvider implements MessageProvider {

    @Override
    public String getMessage() {
        return "Gruezi!";
    }
}
