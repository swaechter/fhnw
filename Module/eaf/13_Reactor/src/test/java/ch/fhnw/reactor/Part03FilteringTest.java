package ch.fhnw.reactor;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Part03FilteringTest {

    Part03Filtering workshop = new Part03Filtering();

    @Test
    public void filterEven() {
        Flux<Integer> flux = workshop.filterEven(Flux.just(1, 2, 3, 4, 5));

        StepVerifier.create(flux)
                .expectNext(2, 4)
                .verifyComplete();
    }

    @Test
    public void ignoreDuplicates() {
        Flux<Integer> flux = workshop.ignoreDuplicates(Flux.just(1, 1, 2, 2, 3, 4));

        StepVerifier.create(flux)
                .expectNext(1, 2, 3, 4)
                .verifyComplete();

    }


    @Test
    public void takeAtMostOne() {
        Mono<Integer> mono = workshop.emitLast(Flux.just(51, 61, 12));

        StepVerifier.create(mono)
                .expectNext(12)
                .verifyComplete();


        mono = workshop.emitLast(Flux.empty());

        StepVerifier.create(mono).expectNext(100).verifyComplete();
    }

    @Test
    public void ignoreUntil() {
        Flux<Integer> flux = workshop.ignoreUntil(Flux.just(1, 3, 15, 5, 10));

        StepVerifier.create(flux)
                .expectNext(15, 5, 10)
                .verifyComplete();

        StepVerifier.create(workshop.ignoreUntil(Flux.just(1, 3, 5))).verifyComplete();

        StepVerifier.create(workshop.ignoreUntil(Flux.empty())).verifyComplete();

    }

    @Test
    public void expectAtMostOneOrEmpty() {
        Mono<Integer> mono = workshop.expectAtMostOneOrEmpty(Flux.just(1, 2, 3));

        StepVerifier.create(mono)
                .expectError()
                .verify();

        StepVerifier.create(Flux.just(1)).expectNext(1).verifyComplete();

        StepVerifier.create(Flux.empty()).verifyComplete();

    }

    @Test
    public void asyncFilter() {
        Flux<Integer> flux = workshop.asyncComputedFilter(Flux.just(1, 2, 3, 4, 5));

        StepVerifier.create(flux)
                .expectNext(2, 4)
                .verifyComplete();
    }


}
