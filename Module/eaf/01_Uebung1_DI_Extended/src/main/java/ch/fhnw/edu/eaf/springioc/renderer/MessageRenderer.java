package ch.fhnw.edu.eaf.springioc.renderer;

import ch.fhnw.edu.eaf.springioc.providers.MessageProvider;

public interface MessageRenderer {

    String render();

    void setMessageProvider(MessageProvider messageProvider);
}
