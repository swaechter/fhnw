package ch.fhnw.reactor;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.Test;

import ch.fhnw.reactor.Part02Transform.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Part02TransformTest {

    Part02Transform workshop = new Part02Transform();

    @Test
    public void transform() {
        Flux<Integer> flux = workshop.transformToLength(Flux.just("foo", "bar"));
        StepVerifier.create(flux).expectNext(3, 3).verifyComplete();
    }

    @Test
    public void characters() {
        Flux<String> flux = workshop.characters(Flux.just("foo", "bar"));
        StepVerifier.create(flux).expectNextCount(6).verifyComplete();
    }

    @Test
    public void combineInEmissionOrder() {
        Flux<String> flux1 = Flux.just("foo", "bar").delayElements(Duration.ofMillis(1));
        Flux<String> flux2 = Flux.just("a", "b", "c").delayElements(Duration.ofMillis(1));

        StepVerifier.create(workshop.combineInEmissionOrder(flux1, flux2)).expectNextCount(5).verifyComplete();
    }

    @Test
    public void pairValues() {
        Flux<String> flux1 = Flux.just("a", "b", "c");
        Flux<Integer> flux2 = Flux.just(1, 2, 3, 4);

        StepVerifier.create(workshop.pairValues(flux1, flux2))
                .assertNext(tuple2 -> {
                    assertThat(tuple2.getT1()).isEqualTo("a");
                    assertThat(tuple2.getT2()).isEqualTo(1);
                })
                .assertNext(tuple2 -> {
                    assertThat(tuple2.getT1()).isEqualTo("b");
                    assertThat(tuple2.getT2()).isEqualTo(2);
                })
                .assertNext(tuple2 -> {
                    assertThat(tuple2.getT1()).isEqualTo("c");
                    assertThat(tuple2.getT2()).isEqualTo(3);
                })
                .verifyComplete();
    }

    @Test
    public void combineValues() {
        Mono<String> phoneNumber = Mono.just("076123456");
        Mono<String> deliveryAddress = Mono.just("Paradeplatz Zurich");

        Mono<Order> order = workshop.combineValues(phoneNumber, deliveryAddress);
        StepVerifier.create(order).expectNext(new Order("076123456", "Paradeplatz Zurich"));

        order = workshop.combineValues(Mono.empty(), deliveryAddress);
        StepVerifier.create(order).verifyComplete();

        order = workshop.combineValues(Mono.error(new RuntimeException()), deliveryAddress);
        StepVerifier.create(order).verifyError();
    }

}
