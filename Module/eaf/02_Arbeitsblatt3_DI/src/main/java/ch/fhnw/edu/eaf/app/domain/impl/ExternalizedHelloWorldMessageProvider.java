package ch.fhnw.edu.eaf.app.domain.impl;

import org.springframework.beans.factory.annotation.Value;

import ch.fhnw.edu.eaf.app.domain.MessageProvider;
import org.springframework.stereotype.Component;

@Component
public class ExternalizedHelloWorldMessageProvider implements MessageProvider {
	@Value("${helloworld.message}")
	private String message;

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
