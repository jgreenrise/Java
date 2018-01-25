package a_flux;

import java.time.Duration;
import java.util.Arrays;

import reactor.core.publisher.Flux;

/**
 * @author jatin
 * 
 * O/P
 * FluxEmpty
FluxArray
FluxIterable
FluxError
{ operator : "Take" }

 *
 */
public class Part1Flux {

	public static void main(String[] args) {
		System.out.println(emptyFlux());
		System.out.println(createFluxFromStrings());
		System.out.println(createFluxFromIterable());
		System.out.println(throwExceptionFromFlux());
		System.out.println(counter());

	}

	static Flux<String> emptyFlux() {
		return Flux.empty();
	}

	static Flux<String> createFluxFromStrings() {
		return Flux.just("Apple", "Bannana");
	}

	static Flux<String> createFluxFromIterable() {
		return Flux.fromIterable(Arrays.asList("Apple", "Bannana"));
	}

	static Flux<String> throwExceptionFromFlux() {
		return Flux.error(new IllegalArgumentException());
	}

	static Flux<Long> counter() {
		return Flux.interval(Duration.ofMillis(100)).take(20);
	}

}
