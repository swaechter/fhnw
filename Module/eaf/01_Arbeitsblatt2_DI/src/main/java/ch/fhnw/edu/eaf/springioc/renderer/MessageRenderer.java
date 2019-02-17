package ch.fhnw.edu.eaf.springioc.renderer;

import ch.fhnw.edu.eaf.springioc.providers.MessageProvider;

public interface MessageRenderer {

    void render();

    void setMessageProvider(MessageProvider messageProvider);
}
