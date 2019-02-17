package ch.fhnw.reactor;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Part04HandlingErrorsTest {

    Part04HandlingErrors workshop = new Part04HandlingErrors();

    @Test
    public void errorIsTerminal() {
        Flux<String> flux = Flux.just("31", "12", "2", "A", "5", "6");

        Flux<Integer> numbers = workshop.errorIsTerminal(flux);

        StepVerifier.create(numbers).expectNext(31, 12, 2).expectError().verify();
    }

    @Test
    public void handleErrorWithFallback() {
        Flux<String> flux = Flux.just("31", "12", "2", "A", "5", "6");

        Flux<Integer> numbers = workshop.handleErrorWithFallback(flux);

        StepVerifier.create(numbers).expectNext(31, 12, 2, 0).expectComplete().verify();
    }

    @Test
    public void handleErrorAndContinue() {
        Flux<String> flux = Flux.just("31", "12", "2", "A", "5", "6");

        Flux<Integer> numbers = workshop.handleErrorAndContinue(flux);

        StepVerifier.create(numbers).expectNext(31, 12, 2, 0, 5, 6).expectComplete().verify();
    }

    @Test
    public void handleErrorWithEmptyMonoAndContinue() {
        Flux<String> flux = Flux.just("31", "12", "2", "A", "5", "6");

        Flux<Integer> numbers = workshop.handleErrorWithEmptyMonoAndContinue(flux);

        StepVerifier.create(numbers).expectNext(31, 12, 2, 5, 6).expectComplete().verify();
    }

    @Test
    public void timeOutWithRetry() {
        Flux<String> colors = Flux.just("red", "black", "tan");

        Flux<String> results = workshop.timeOutWithRetry(colors);

        StepVerifier.create(results).expectNext("processed red", "default", "processed tan").verifyComplete();
    }

}
