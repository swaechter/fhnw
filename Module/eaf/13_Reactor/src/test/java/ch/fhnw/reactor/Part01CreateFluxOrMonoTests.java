package ch.fhnw.reactor;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.Test;
import org.reactivestreams.Publisher;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Part01CreateFluxOrMonoTests {

    Part01CreateFluxOrMono workshop = new Part01CreateFluxOrMono();

    @Test
    public void createFromOptional() {
        StepVerifier.create(workshop.createFromOptional(Optional.empty())).verifyComplete();
        StepVerifier.create(workshop.createFromOptional(Optional.of("foo"))).expectNext("foo").verifyComplete();
    }

    @Test
    public void createFromPotentialNull() {
        StepVerifier.create(workshop.createFromPotentiallyNull(null)).verifyComplete();
        StepVerifier.create(workshop.createFromPotentiallyNull("foo")).expectNext("foo").verifyComplete();
    }

    @Test
    public void createFromSupplier() {
        StepVerifier.create(workshop.createFromSupplier(() -> "foo")).expectNext("foo").verifyComplete();
        StepVerifier.create(workshop.createFromSupplier(() -> {
            throw new RuntimeException("error");
        })).expectErrorMessage("error").verify();
    }

    @Test
    public void createFromRunnable() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "foo");
        StepVerifier.create(workshop.createFromFuture(future)).expectNext("foo").verifyComplete();
    }

    @Test
    public void createFromCallable() {
        StepVerifier.create(workshop.createFromCallable(() -> "foo")).expectNext("foo").verifyComplete();
    }

    @Test
    public void createFooBar() {
        Publisher<String> fooBar = workshop.createFooBar();
        StepVerifier.create(fooBar).expectNext("foo", "bar").verifyComplete();
    }

    @Test
    public void createFromArray() {
        Publisher<String> publisher = workshop.createFromArray(new String[]{"foo", "bar"});
        StepVerifier.create(publisher).expectNext("foo", "bar").verifyComplete();
    }

    @Test
    public void createFromList() {
        Publisher<String> publisher = workshop.createFromList(Arrays.asList("foo", "bar"));
        StepVerifier.create(publisher).expectNext("foo", "bar").verifyComplete();
    }

    @Test
    public void createFromStream() {
        Publisher<String> publisher = workshop.createFromStream(Stream.of("foo", "bar"));
        StepVerifier.create(publisher).expectNext("foo", "bar").verifyComplete();
        StepVerifier.create(publisher).expectErrorMessage("stream has already been operated upon or closed").verify();
    }

    @Test
    public void createEmpty() {
        StepVerifier.create(workshop.createEmpty()).verifyComplete();
    }

    @Test
    public void createError() {
        StepVerifier.create(workshop.createError()).verifyError(IllegalStateException.class);
    }

    @Test
    public void neverEmit() {
        Publisher<Void> publisher = workshop.neverEmit();

        StepVerifier.create(publisher)
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(1))
                .thenCancel()
                .verify();
    }

    @Test
    public void lazy() {
        Random random = new Random();

        Supplier<Publisher<Integer>> supplier = () -> Mono.just(random.nextInt(1000));

        Integer integer1 = workshop.lazy(supplier).blockFirst();
        Integer integer2 = workshop.lazy(supplier).blockFirst();

        assertThat(integer1).isNotEqualTo(integer2);
    }

    @Test
    public void fromRange() {
        Publisher<Integer> publisher = workshop.fromRange();

        StepVerifier.create(publisher)
                .expectNext(5, 6, 7, 8, 9, 10)
                .verifyComplete();
    }

    @Test
    public void counter() {
        Publisher<Long> publisher = workshop.counter();

        StepVerifier.create(publisher)
                .expectNext(0L, 1L, 2L, 3L, 4L)
                .verifyComplete();
    }


}
