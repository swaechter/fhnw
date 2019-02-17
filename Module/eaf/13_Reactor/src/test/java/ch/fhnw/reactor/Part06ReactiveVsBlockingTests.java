package ch.fhnw.reactor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;

import org.junit.Test;

import ch.fhnw.reactor.Part06ReactiveVsBlocking.BlockingCustomerRepository;
import ch.fhnw.reactor.Part06ReactiveVsBlocking.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Part06ReactiveVsBlockingTests {

    Part06ReactiveVsBlocking workshop = new Part06ReactiveVsBlocking();

    @Test
    public void monoToCustomer() {
        Customer jane = workshop.monoToCustomer(Mono.just(new Customer("Jane")));
        assertThat(jane).isEqualTo(new Customer("Jane"));
    }

    @Test
    public void fluxToCustomers() {
        Iterable<Customer> customers = workshop.fluxToCustomers(Flux.just(new Customer("Jane"), new Customer("Joe")));
        Iterator<Customer> iterator = customers.iterator();
        assertThat(iterator.next()).isEqualTo(new Customer("Jane"));
        assertThat(iterator.next()).isEqualTo(new Customer("Joe"));
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    public void blockingRepositoryToFlux() {
        BlockingCustomerRepository repository = new BlockingCustomerRepository();
        Flux<Customer> customers = workshop.blockingRepositoryToFlux(repository);

        // The call to findAll must be deferred until the flux is subscribed
        assertThat(repository.getCount()).isEqualTo(0);

        StepVerifier.create(customers)
                .expectNext(new Customer("John"))
                .expectNext(new Customer("Jane"))
                .verifyComplete();
    }

    @Test
    public void fluxToBlockingRepository() {
        BlockingCustomerRepository blockingCustomerRepository = new BlockingCustomerRepository();
        Flux<Customer> customers = Flux.just(new Customer("Joe"), new Customer("Bob"));

        Mono<Void> mono = workshop.fluxToBlockingRepository(customers, blockingCustomerRepository);

        // The call to save must be deferred until the flux is subscribed
        assertThat(blockingCustomerRepository.getCount()).isEqualTo(0);

        StepVerifier.create(mono).verifyComplete();

        Iterator<Customer> iterator = blockingCustomerRepository.findAll().iterator();

        assertThat(iterator.next()).isEqualTo(new Customer("John"));
        assertThat(iterator.next()).isEqualTo(new Customer("Jane"));
        assertThat(iterator.next()).isEqualTo(new Customer("Joe"));
        assertThat(iterator.next()).isEqualTo(new Customer("Bob"));
        assertThat(iterator.hasNext()).isFalse();

    }

}
