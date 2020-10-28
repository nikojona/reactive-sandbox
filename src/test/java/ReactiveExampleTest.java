import guru.springframework.reactiveexamples.Person;
import guru.springframework.reactiveexamples.PersonCommand;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ReactiveExampleTest {
    
    Person michael = new Person("Michael", "Ricky");
    Person adit = new Person("Aditya", "Rifky");
    Person yudi = new Person("Yudi", "Dwiyanto");
    Person nicko = new Person("Nickolas", "Jonathan");
    Person ali = new Person("Ali", "Irawan");

    @Test
    public void monoTest() throws Exception {
        // create new person mono
        Mono<Person> personMono = Mono.just(michael);

        // get person object from mono publisher
        Person person = personMono.block();

        // output name
        log.info(person.sayMyName());
    }

    @Test
    public void monoTransform() {
        // create new person mono
        Mono<Person> personMono = Mono.just(adit);

        PersonCommand command = personMono
            .map(person ->{ // type transformation
                return new PersonCommand(person);
            }).block();
        
        log.info(command.sayMyName());
    }

    @Test(expected = NullPointerException.class)
    public void monoFilter() throws Exception {

        Mono<Person> personMono = Mono.just(yudi);

        // filter example
        Person yudiDy = personMono
            .filter(person -> person.getFirstName().equalsIgnoreCase("Foo"))
            .block();

        log.info(yudiDy.sayMyName());
    }

    @Test
    public void fluxTest() throws Exception {

        Flux<Person> people = Flux.just(michael, adit, yudi, nicko, ali);
        
        people.subscribe(person -> log.info(person.sayMyName()));
    }

    @Test
    public void fluxFilterTest() throws Exception {

        Flux<Person> people = Flux.just(michael, adit, yudi, nicko, ali);

        people.filter(person -> person.getFirstName().equals(nicko.getFirstName()))
            .subscribe(person -> log.info(person.sayMyName()));
    }

    @Test
    public void fluxDelayNoOutputTest() throws Exception {

        Flux<Person> people = Flux.just(michael, adit, yudi, nicko, ali);

        people.delayElements(Duration.ofSeconds(1))
            .subscribe(person -> log.info(person.sayMyName()));
    }

    @Test
    public void fluxDelayTest() throws Exception {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Flux<Person> people = Flux.just(michael, adit, yudi, nicko, ali);

        people.delayElements(Duration.ofSeconds(2))
            .doOnComplete(countDownLatch::countDown)
            .subscribe(person -> log.info(person.sayMyName()));

        countDownLatch.await();
    }

    @Test
    public void fluxFilterDelayTest() throws Exception {
         
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Flux<Person> people = Flux.just(michael, adit, yudi, nicko, ali);

        people.delayElements(Duration.ofSeconds(1))
            .filter(person -> person.getFirstName().contains("a")) // case sensitive
            .doOnComplete(countDownLatch::countDown)
            .subscribe(person -> log.info(person.sayMyName()));

        countDownLatch.await();
    }
}
