package com.test.tester.reactiveProgramming.async04;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Service
public class ReactiveComponents {
    private MyService myService;
    public ReactiveComponents(MyService myService){
        this.myService = myService;
    }
    @Async(value = "tp") //AOP로 비동기 작업을 간단히 짤 수 있음 - 허나 이렇게 쓰면 안되는게 Thread 재사용이 안됨
    // Async를 걸 거면 ThreadPoolTaskExecutor로 빈 구현해서 써야 함
    @Component
    public static class MyService{
        public ListenableFuture<String> hello() throws InterruptedException{
            log.info("hello()");
            Thread.sleep(1000);
            return new AsyncResult<String>("HELLO");
        }
    }
    @Bean
    ThreadPoolTaskExecutor tp(){
        ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
        te.setCorePoolSize(10);
        te.setMaxPoolSize(100); // 100개 풀 더 내줌
        te.setQueueCapacity(200); //200개 큐 다 쌓이면
        te.setKeepAliveSeconds(300); //300초 정도는 Thread 살려둠
        te.setThreadNamePrefix("myThread");
        te.initialize();
        return te;
    }

    /**
     *
     * 왜 일까요? -> f.get()은 결국 끝까지 기다리니 끝을 기다리고 나머지는 비동기 작업을 수행하기 위해서 main 스레드는 끝났네요
     *
     * 2024-01-28T10:04:10.209+09:00  INFO 7616 --- [  restartedMain] com.test.tester.TesterApplication        : run()
     * 2024-01-28T10:04:10.211+09:00  INFO 7616 --- [  restartedMain] com.test.tester.TesterApplication        : EXIT : false
     * 2024-01-28T10:04:10.211+09:00  INFO 7616 --- [         task-1] com.test.tester.TesterApplication        : hello()
     * 2024-01-28T10:04:11.223+09:00  INFO 7616 --- [  restartedMain] com.test.tester.TesterApplication        : result HELLO
     *
     * @return
     */
    @Bean
    ApplicationRunner run(){
        return args -> {
            log.info("run()");
            ListenableFuture<String> f = myService.hello();
            log.info("EXIT : " + f.isDone());
            f.addCallback(s -> System.out.println(s), e -> System.out.println(e.getMessage()));
            log.info("result " + f.get()); //만일, 장시간 비동기를 기다려야 한다면 어떻게 하냐...
            // get을 쓰는 건 좋지 않고 보통 이전에는 DB에 결과 값을 넣었다고 한다.
            //또는, HTTP SESSION에 저장해서 Future 값을 가져와서 수행해서 비동기 작업을 체크하는 식으로 썼습니다.
        };
    }
}
