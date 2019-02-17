package ch.fhnw.lecture.demo;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class ClientTick {

	public static void main(String[] args) throws Exception {
		WebClient client = WebClient.create("http://localhost:8080");
		client.get().uri("tick").accept(MediaType.APPLICATION_STREAM_JSON).retrieve().bodyToFlux(Tick.class).log().subscribe();

		System.in.read();
	}
}
