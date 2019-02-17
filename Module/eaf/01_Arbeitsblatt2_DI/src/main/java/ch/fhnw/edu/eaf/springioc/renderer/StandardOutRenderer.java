package ch.fhnw.edu.eaf.springioc.renderer;

import ch.fhnw.edu.eaf.springioc.providers.MessageProvider;

public class StandardOutRenderer implements MessageRenderer {

    private MessageProvider messageProvider;

    @Override
    public void render() {
        System.out.println(messageProvider.getMessage());
    }

    @Override
    public void setMessageProvider(MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }
}
