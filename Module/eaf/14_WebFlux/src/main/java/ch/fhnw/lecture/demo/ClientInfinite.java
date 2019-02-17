package ch.fhnw.lecture.demo;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

public class ClientInfinite {

	public static void main(String[] args) throws Exception {
		WebClient client = WebClient.create("http://localhost:8080");
		Flux<String> lines = client.get().uri("infinite").retrieve().bodyToFlux(String.class);
		lines.log().take(10).subscribe();

		System.in.read();
	}
}
