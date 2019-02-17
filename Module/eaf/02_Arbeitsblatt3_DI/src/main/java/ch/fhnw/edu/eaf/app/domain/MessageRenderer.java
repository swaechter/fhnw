package ch.fhnw.edu.eaf.app.domain;

public interface MessageRenderer {
	void setMessageProvider(MessageProvider mp);
	MessageProvider getMessageProvider();
	void render();
}
