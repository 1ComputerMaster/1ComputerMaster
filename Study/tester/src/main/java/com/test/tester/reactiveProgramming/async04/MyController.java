package com.test.tester.reactiveProgramming.async04;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

/**
 * Thread가 많으면 어떻게 될까?
 * - Thread를 무한정 늘리면 ContextSwitching이 높아져서 오히려 비용이 증가한다.
 * - 최대한 사용을 빨리하고 Thread를 재사용 하는 것이 좋다.
 *
 * Servlet 3.1 : NonBlocking I/O
 * HTTP 통신시 응답을 처리 해야 함
 */
@Slf4j
@RestController
public class MyController {

    Queue<DeferredResult<String>> results = new ConcurrentLinkedQueue<>();

    /**
     * 대기 상태에서 계속 받을 수 있으니 이 오브젝트 하나로 결과를 넣어주면 결과 리턴 값을 넣어준 것 처럼 바로 리턴 해 줌
     * @return
     * @throws Exception
     */
    @GetMapping("/dr")
    public DeferredResult<String> callable() throws Exception{
        log.info("dr");
        DeferredResult<String> dr = new DeferredResult<>(600000L);
        results.add(dr);
        return dr;
    }
    @GetMapping("/dr/count")
    public String drCount(){
        return String.valueOf(results.size());
    }

    @GetMapping("/dr/event")
    public String drevent(String msg){
        for (DeferredResult<String> dr : results){
            dr.setResult("Hello "+  msg);
            results.remove(dr);
        }
        return "OK";
    }

    @GetMapping("/emitter")
    public ResponseBodyEmitter stream() throws InterruptedException {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                for (int i = 0; i <= 50; i++) {
                    emitter.send("<p>Stream" + i + "</p>");
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return emitter;
    }

    @GetMapping("/async")
    public DeferredResult<String> emitter() throws Exception {
        log.info("dr");
        DeferredResult<String> dr = new DeferredResult<>(600000L);
        results.add(dr);
        return dr;
    }


//    public String callable() throws InterruptedException{
//        log.info("async");
//        Thread.sleep(2000);
//        return "hello";
//    }
}
