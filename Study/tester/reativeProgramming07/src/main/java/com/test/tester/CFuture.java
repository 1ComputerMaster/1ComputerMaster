package com.test.tester;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class CFuture {
    public static void main(String[] args) throws Exception{
        ExecutorService es = Executors.newFixedThreadPool(10);

        CompletableFuture
                .supplyAsync(() -> {log.info("runAsync");
//                    if(1 == 1) throw new RuntimeException();
                    return 1;
                },es)
                .thenCompose(s -> { //Stream의 flatMap과 유사함
                    log.info("thenApply {}",s );
                    return CompletableFuture.completedFuture(s + 1);
                })
                .thenApplyAsync(s1 -> {
                    log.info("thenApplyAsync {}", s1);
                    return s1 * 3;
                },es)
                .exceptionally(e -> -10)
                .thenAcceptAsync(s2 ->  {
                    log.info("thenApplyAsync {}",s2 );
                    es.shutdown();
                },es);
        log.info("exit");
        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
    }
}
