package com.test.tester.reactiveProgramming.Schedulers;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FluxScEx {
    public static void main(String[] args) throws InterruptedException {
//        test01();
        test02();

    }

    private static void test02() throws InterruptedException {
        //Daemon Thread - 실행시키면 JVM이 알아서 삭제 할 수 있음
        //User Thread - 유저가 직접 삭제하지 않으면 GC할 수 없음
        Flux.interval(Duration.ofMillis(200))
                .take(10) //원하는 데이터 만큼만 잘라서 쓸 수 있음
                .subscribe(s -> log.debug("onNext:{}",s));
        System.out.println("EXIT");

        TimeUnit.SECONDS.sleep(5);
    }

    /**
     * EXIT
     * 22:55:40.107 [sub-1] INFO reactor.Flux.PublishOn.1 - | onSubscribe([Fuseable] FluxPublishOn.PublishOnSubscriber)
     * 22:55:40.108 [sub-1] INFO reactor.Flux.PublishOn.1 - | request(unbounded)
     * 22:55:40.108 [pub-2] INFO reactor.Flux.PublishOn.1 - | onNext(1)
     * 1
     * 22:55:40.109 [pub-2] INFO reactor.Flux.PublishOn.1 - | onNext(2)
     * 2
     * 22:55:40.109 [pub-2] INFO reactor.Flux.PublishOn.1 - | onNext(3)
     * 3
     * 22:55:40.109 [pub-2] INFO reactor.Flux.PublishOn.1 - | onNext(4)
     * 4
     * 22:55:40.109 [pub-2] INFO reactor.Flux.PublishOn.1 - | onNext(5)
     * 5
     * 22:55:40.109 [pub-2] INFO reactor.Flux.PublishOn.1 - | onNext(6)
     * 6
     * 22:55:40.109 [pub-2] INFO reactor.Flux.PublishOn.1 - | onNext(7)
     * 7
     * 22:55:40.109 [pub-2] INFO reactor.Flux.PublishOn.1 - | onNext(8)
     * 8
     * 22:55:40.109 [pub-2] INFO reactor.Flux.PublishOn.1 - | onNext(9)
     * 9
     * 22:55:40.109 [pub-2] INFO reactor.Flux.PublishOn.1 - | onNext(10)
     * 10
     * 22:55:40.109 [pub-2] INFO reactor.Flux.PublishOn.1 - | onComplete()
     */

    private static void test01() {
        Flux.range(1,10)
                .publishOn(Schedulers.newSingle("pub"))
                .log()
                .subscribeOn(Schedulers.newSingle("sub"))
                .subscribe(System.out::println);

        System.out.println("EXIT");
    }
}
