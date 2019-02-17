package ch.fhnw.lecture.demo;

import java.time.Duration;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {

	@PostMapping(path = "/echo", produces={MediaType.APPLICATION_STREAM_JSON_VALUE, MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<String> upperCase(@RequestBody Flux<String> body) {
		return body.map(String::toUpperCase);
	}

	@GetMapping(path = "/infinite", produces= {MediaType.APPLICATION_STREAM_JSON_VALUE, MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<String> infinite() {
		Stream<String> s = Stream.iterate(0, i -> i + 1).map(i -> "Hello " + i + "\n");
		return Flux.fromStream(s);
	}

	@GetMapping(path = "/tick")
	public Flux<Tick> tick() {
		return Flux.interval(Duration.ofSeconds(1)).map(t -> new Tick(t));
	}


	@GetMapping("/get")
	public @ResponseBody ResponseEntity<String> get() {
	    return new ResponseEntity<String>("GET Response", HttpStatus.OK);
	}
	
	@GetMapping("/helloworld")
	public Mono<String> getGreeting(@RequestParam(defaultValue = "World") String name) {
		return Mono.just("Hello").flatMap(s -> Mono.just(s + ", " + name + "!\n"));
	}

}
