package ch.fhnw.lecture;

import java.time.Duration;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import reactor.core.publisher.Flux;

public class Test4 {

	public static void main(String[] args) throws Exception {
		Flux.interval(Duration.ofMillis(1L))
			.take(10)
			.subscribe(new Subscriber<Long>() {
				Subscription s;
				@Override
				public void onSubscribe(Subscription s) {
					this.s = s;
					s.request(3);
				}
				@Override
				public void onNext(Long t) {
					System.out.println(t);
					if(t == 1) s.request(2);
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

		Thread.sleep(1000);
	}

}
