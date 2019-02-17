package ch.fhnw.lecture;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import reactor.core.publisher.Mono;

public class Test1 {

	public static void main(String[] args) {
		Mono<String> m = Mono.just("Hello World");
		
		m.subscribe(System.out::println);
		m.subscribe(System.out::println);

//		m.subscribe(new Subscriber<>() {	// works in Java11
		m.subscribe(new Subscriber<String>() {
			@Override
			public void onSubscribe(Subscription s) {
				s.request(3);
				
			}
			@Override
			public void onNext(String t) {
				System.out.println(t);
			}
			@Override
			public void onError(Throwable t) {
				System.out.println(t);
			}
			@Override
			public void onComplete() {
				System.out.println("onComplete");
			}
		});
	}

}
