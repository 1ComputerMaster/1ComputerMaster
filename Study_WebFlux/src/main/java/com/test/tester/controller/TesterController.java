package com.test.tester.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
@Slf4j
@RestController
public class TesterController {
    @GetMapping("/")
    Mono<String> hello() {
/*
        log.info("pos1");
        Mono m = Mono.just(generateHello()).doOnNext(c -> log.info(c)).log();
        log.info("pos2");
*/

        log.info("pos1");
        Mono<String> m = Mono
                .fromSupplier(() -> generateHello()) //Parameter는 없고 Return 값만 존재하는 객체
                .doOnNext(c -> log.info(c)).log();
        String msg2 = m.block();
        log.info("pos2 " + msg2);
        return m;
    }

    private String generateHello() {
        log.info("method generateHello()");
        return "Hello Message";
    }
}
