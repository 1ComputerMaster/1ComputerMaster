package com.test.tester;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Slf4j
@EnableAsync
@SpringBootApplication
public class TesterApplication03 {
    /**
     * Webflux의 경우 : Netty 기반의 웹 서버를 사용함
     */
    private static final String URL1 = "http://localhost:8081/service?req={req}";
    private static final String URL2 = "http://localhost:8081/service2?req={req}";

    @Service
    public static class MyService {
        @Async
        public CompletableFuture<String> work(String req) {
            return CompletableFuture.completedFuture(req + "/asyncwork");
        }
    }
    @RestController
    public static class MyController {
        @Autowired
        MyService myService;
        WebClient client = WebClient.create();
        @GetMapping("/rest")
        public Mono<String> rest(int idx) {
            return client.get().uri(URL1, idx)//요청
                    .exchange()//응답으로 바꾼다.
                    .flatMap(c -> c.bodyToMono(String.class))
                    .doOnNext(c -> log.info(c))
//                    .flatMap(clientResponse -> clientResponse.bodyToMono(String.class))
                    .flatMap(res1 -> client.get().uri(URL2,res1).exchange())
                    .flatMap(c -> c.bodyToMono(String.class))
                    .flatMap(res2 -> Mono.fromCompletionStage(myService.work(res2)))
                    .doOnNext(c -> log.info(c));

        }

    }

    @Bean
    public ThreadPoolTaskExecutor myThreadPool() {
        ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
        te.setThreadNamePrefix("TESTER-THREAD");
        te.setCorePoolSize(10);
        te.setMaxPoolSize(100);
        te.initialize();
        return te;
    }

    public static void main(String[] args) {
        SpringApplication.run(TesterApplication03.class, args);
    }
}