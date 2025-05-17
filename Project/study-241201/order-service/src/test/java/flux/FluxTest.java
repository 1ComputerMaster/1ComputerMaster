package flux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

public class FluxTest {
    @Test
    void test(){
        Flux<?> fluxTC = mergeTest();
        fluxTC.subscribe();
        fluxTC.blockLast();

        Flux<?> flatMapTC = flatMapTest();
        flatMapTC.blockLast();

    }
    
    Flux<String> mergeTest(){
        Flux<String> f1 = Flux.just("A","B","C")
                .delayElements(Duration.ofMillis(100))
                .doOnSubscribe(s -> System.out.println("Subscribed f1"));
        Flux<String> f2 = Flux.just("1","2","3")
                .delayElements(Duration.ofMillis(150))
                .doOnSubscribe(s -> System.out.println("Subscribed f2"));
        return Flux.merge(f1, f2);
    }
    Flux<String> flatMapTest(){
        List<Mono<String>> monos = List.of(
                Mono.just("A").delayElement(Duration.ofMillis(100)),
                Mono.just("1").delayElement(Duration.ofMillis(10)) //1이 먼저 나옴
        );
        Flux<String> flux = Flux.fromIterable(monos)
                .flatMap(m -> m.doOnSubscribe(s -> System.out.println("flatMap subscribe " + m)));
        flux.subscribe(s -> System.out.println("flatMap onNext " + s));
        return flux;
    }
}
