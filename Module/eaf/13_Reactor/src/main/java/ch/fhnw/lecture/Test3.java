package ch.fhnw.lecture;

import java.time.Duration;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Test3 {

	public static void main(String[] args) throws Exception {
		Mono.just("Hello")
			.flatMap(s -> Mono.just(s + " world"))
			.subscribe(s -> System.out.println(s));
		
		Mono.just("Hello")
			.concatWith(Flux.just(",", "World", "!"))
			.subscribe(s -> System.out.println(s));
		
		Flux.interval(Duration.ofMillis(1L))
			.subscribeOn(Schedulers.parallel())
			.take(10)
			.map(l -> Long.toString(l))
			.subscribe(s -> System.out.println(s));

		Thread.sleep(1000);
	}

}
