package ch.fhnw.edu.eaf.springioc.renderer;

import ch.fhnw.edu.eaf.springioc.providers.MessageProvider;

public class StandardOutRenderer implements MessageRenderer {

    private MessageProvider messageProvider;

    @Override
    public String render() {
        String message = messageProvider.getMessage();
        System.out.println(message);
        return message;
    }

    @Override
    public void setMessageProvider(MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }
}
