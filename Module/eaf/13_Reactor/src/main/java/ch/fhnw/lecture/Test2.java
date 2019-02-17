package ch.fhnw.lecture;

import reactor.core.publisher.Flux;

public class Test2 {

	public static void main(String[] args) {
		Flux.just("one", "two", "three", "four")
			.log()
			.map(String::toUpperCase)
			.subscribe();
	}

}
