package com.test.tester.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.tester.domain.TestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.core.io.buffer.DataBuffer;

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
    public Mono<TestDTO> getBodyAsTestDTO(Flux<DataBuffer> body) {
        return DataBufferUtils.join((Flux<DataBuffer>) body)
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        return objectMapper.readValue(bytes, TestDTO.class);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @PostMapping("/test")
    public Mono<Void> handleRequest(ServerWebExchange serverWebExchange) {
        ServerHttpRequest serverHttpRequest = serverWebExchange.getRequest();
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        return DataBufferUtils.join(body)
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    return Mono.just(bytes);
                })
                .flatMap(res -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        TestDTO testDTO = objectMapper.readValue(res, TestDTO.class);
                        log.info(testDTO.getX() + " " + serverHttpRequest.getHeaders().get("TEST"));
                        return Mono.empty();
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }

    private String generateHello() {
        log.info("method generateHello()");
        return "Hello Message";
    }
}
