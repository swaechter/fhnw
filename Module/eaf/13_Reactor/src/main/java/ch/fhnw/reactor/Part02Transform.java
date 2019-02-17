package ch.fhnw.reactor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

public class Part02Transform {

    /**
     * TODO 1
     * <p>
     * Create a sequence which transforms the values in the given flux to their length.
     */
    Flux<Integer> transformToLength(Flux<String> flux) {
        return flux.map(s -> s.length());
    }

    /**
     * TODO 2
     * <p>
     * Create a sequence which transforms the provided string sequence into a stream of uppercase characters.
     */
    Flux<String> characters(Flux<String> flux) {
        return flux
                .map(s -> s.toUpperCase())
                .flatMap(s -> Flux.fromArray(s.split("")).log());
    }

    /**
     * TODO 3
     * <p>
     * Merge the values emitted by flux1 and flux2 publishers into an interleaved merged sequence.
     */
    Flux<String> combineInEmissionOrder(Flux<String> flux1, Flux<String> flux2) {
        return Flux.merge(flux1, flux2);
    }

    /**
     * TODO 4
     * <p>
     * Pair the values emitted by the flux1 publisher with the flux2 publisher.
     */
    public Flux<Tuple2<String, Integer>> pairValues(Flux<String> flux1, Flux<Integer> flux2) {
        return Flux.zip(flux1, flux2);
    }

    /**
     * TODO 5
     * <p>
     * When the phoneNumber and deliveryAddress is available pair them into an Order Mono.
     */
    public Mono<Order> combineValues(Mono<String> phoneNumber, Mono<String> deliveryAddress) {
        return Mono.zip(phoneNumber, deliveryAddress, (p, d) -> new Order(p, d));
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Order {
        String phoneNumber;
        String deliveryAddress;
    }
}
