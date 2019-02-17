package ch.fhnw.reactor;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Part05SubscribeTest {

    Part05Subscribe workshop = new Part05Subscribe();

    @Test
    public void subscribeEmpty() {
        Flux<Integer> flux = workshop.subscribeEmpty();

        StepVerifier.create(flux)
                .expectNext(1, 2, 3).verifyComplete();
    }

    @Test
    public void subscribeWithConsumer() {
        Flux<Integer> flux = workshop.subscribeWithConsumer();

        StepVerifier.create(flux)
                .expectNext(1, 2, 3).verifyComplete();
    }

    @Test
    public void subscribeWithConsumerAndCompleteConsumer() {
        Flux<Integer> flux = workshop.subscribeWithConsumerAndCompleteConsumer();

        StepVerifier.create(flux)
                .expectNext(1, 2, 3).verifyComplete();
    }

    @Test
    public void subscribeWithConsumerAndErrorConsumer() {
        Flux<Integer> flux = workshop.subscribeWithConsumerAndErrorConsumer();

        StepVerifier.create(flux)
                .expectNext(1, 2, 3).verifyError();
    }

    @Test
    public void subscribeWithSubscriptionConsumer() {
        Flux<Integer> flux = workshop.subscribeWithSubscriptionConsumer();

        StepVerifier.create(flux)
                .expectNext(1, 2, 3).verifyComplete();
    }

    @Test
    public void subscribeWithSubscription() {
        Flux<Integer> flux = workshop.subscribeWithSubscription();

        StepVerifier.create(flux)
                .expectNext(1, 2, 3).verifyComplete();
    }

}
