package ch.fhnw.edu.eaf.app.domain.impl;

import ch.fhnw.edu.eaf.app.domain.MessageProvider;
import ch.fhnw.edu.eaf.app.domain.MessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StandardOutRenderer implements MessageRenderer {

    @Autowired
    private MessageProvider messageProvider;

    @Override
    public void setMessageProvider(MessageProvider mp) {
        this.messageProvider = mp;
    }

    @Override
    public MessageProvider getMessageProvider() {
        return messageProvider;
    }

    @Override
    public void render() {
        System.out.println(messageProvider.getMessage());
        ;
    }

}
