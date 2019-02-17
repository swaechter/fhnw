package ch.fhnw.reactor;

import java.time.Duration;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Learn about filtering the sequence.
 */
public class Part03Filtering {

    /**
     * TODO 1
     * <p>
     * Return only the even numbers emitted by the given publisher
     */
    Flux<Integer> filterEven(Flux<Integer> flux) {
        return flux.filter(i -> i % 2 == 0).log();
    }

    /**
     * TODO 2
     * <p>
     * Ignore the duplicates emitted by the given publisher
     */
    Flux<Integer> ignoreDuplicates(Flux<Integer> flux) {
        return flux.distinct().log();
    }

    /**
     * TODO 3
     * <p>
     * Emit the last element or 100 if the flux is empty.
     */
    Mono<Integer> emitLast(Flux<Integer> flux) {
        return flux.last(100).log();
    }

    /**
     * TODO 4
     * <p>
     * Ignore items until a value greater than 10 is emitted.
     */
    Flux<Integer> ignoreUntil(Flux<Integer> flux) {
        return flux.skipUntil(integer -> integer > 10).log();
    }

    /**
     * TODO 5
     * <p>
     * Expect at most one or empty and signal error if more.
     */
    Mono<Integer> expectAtMostOneOrEmpty(Flux<Integer> flux) {
        return flux.singleOrEmpty().log();
    }


    /**
     * TODO 6
     *
     * Filter the provided Publisher where the criteria is asynchronously computed via {@link #asyncFilter} method
     */
    Flux<Integer> asyncComputedFilter(Flux<Integer> flux) {
        return flux.filterWhen(integer -> asyncFilter(integer)).log();
    }



    Mono<Boolean> asyncFilter(Integer integer) {
        return Mono.just(integer % 2 == 0).delayElement(Duration.ofMillis(500));
    }



}
