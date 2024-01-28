package com.test.tester.reactiveProgramming.async04;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class LoadTest {

    static AtomicInteger counter = new AtomicInteger(0);

    /**
     *
     * Callable을 구현한 컨트롤러의 요청시에는 왜 Thread 20개 밖에 없는데 2초 밖에 걸리지 않는가?
     * 게다가 1 Thread로만해도 2초만에 끝나버린다.
     *
     * 비동기 웹 컨트롤러의 처리이다. Servlet Thread가 받은 값을 스레드 풀에 반환하고 이를 서블릿 스레드가 다시 받아서 리턴하고
     * 이런식으로 반환하기 때문에 1개의 Thread 설정인데도 엄청난 효과를 볼 수 있었다.
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(100);

        RestTemplate rt = new RestTemplate();

        String url = "http://localhost:8080/dr";

        StopWatch main = new StopWatch();

        main.start();

        for (int i = 0; i < 100; i++){
            es.execute(() ->{
                int idx = counter.addAndGet(1);
                log.info("Thread {}", idx);
                StopWatch sw = new StopWatch();

                sw.start();


                rt.getForObject(url,String.class);
                sw.stop();
                log.info("Elasped: {} {}", idx,sw.getTotalTimeSeconds());
            });
        }
        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);

        main.stop();
        log.info("totalTime : {}", main.getTotalTimeSeconds());
    }
}
