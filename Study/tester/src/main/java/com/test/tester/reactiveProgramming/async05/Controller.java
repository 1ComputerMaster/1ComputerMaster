package com.test.tester.reactiveProgramming.async05;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * 비동기 RestTemplate과 비동기 MVC의 결합
 *
 *  - Back의 많은 서비스를 호출 할 때 효율적인 해결방법을 알아봅시다.
 *
 * - AsyncRestTemplate을 활용해서 쓸 수 있다 비동기 통신을
 *
 * 그런데, 그냥 사용하게 되면 워커 스레드가 100개 생성이 되어버리고 사실상 스레드를 무한정 늘릴 뿐이다.
 * 따라서, Netty4ClientHttpRequestFactory를 가져와서 NioEventLoopGruop을 사용해서 스레드 하나를 가져와서
 * 비동기의 스레드 1개로 몇 백개의 요청을 리턴한다.
 */
@EnableAsync
@RestController
public class Controller {
//    private RestTemplate rt = new RestTemplate();

//    private AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new ));
//    @GetMapping("/rest")
/*
    public String rest(int idx){

        String res = rt.getForObject("http://localhost:8081/service?idx={idx}",String.class,idx);
        return "rest " + idx;
    }
*/
/*    @GetMapping("/rest")
    public ListenableFuture<ResponseEntity<String>> asyncRest (int idx) {
        return rt.getForEntity("http://localhost:8081/service?idx={idx}",String.class,idx);
    }*/


/*
    @GetMapping("/rest")
    public DeferredResult<String> asyncRest (int idx) {
        DeferredResult<String> dr = new DeferredResult<>();
        ListenableFuture<ResponseEntity<String>> f1 =
                rt.getForEntity("http://localhost:8081/service?idx={idx}", String.class, idx);
        f1.addCallback(s -> {
            dr.setResult(s.getBody() + "/work");
        }, e -> {
            dr.setErrorResult(e.getMessage());
        });
        return dr;
    }
*/
    MyService myService;

    public Controller(MyService myService){
        this.myService = myService;
    }
/*
    @GetMapping("/rest")
    public DeferredResult<String> asyncRest (int idx) {
        DeferredResult<String> dr = new DeferredResult<>();
        ListenableFuture<ResponseEntity<String>> f1 =
                rt.getForEntity("http://localhost:8081/service?idx={idx}", String.class, idx);
        f1.addCallback(s -> {
            ListenableFuture<ResponseEntity<String>> f2 =
                    rt.getForEntity("http://localhost:8081/service?idx={idx}", String.class, idx);
            f2.addCallback(s2 -> {
                dr.setResult(s2.getBody());
            }, e -> {
                dr.setErrorResult(e.getMessage());
            });
        }, e -> {
            dr.setErrorResult(e.getMessage());
        });
        return dr;
    }
*/


    /**
     *
     * 비동기 작업을 거침 Controller -> Controller -> Service
     *
     * 4.2s 정도
     *
     * 백그라운드에서 논 블로킹으로 동작 하는 방식을 알아봤다.
     *
     * @param idx
     * @return
     */
    /*@GetMapping("/rest")
    public DeferredResult<String> asyncRest (int idx) {
        DeferredResult<String> dr = new DeferredResult<>();
        ListenableFuture<ResponseEntity<String>> f1 =
                rt.getForEntity("http://localhost:8081/service?idx={idx}", String.class, idx);
        f1.addCallback(s -> {
            ListenableFuture<ResponseEntity<String>> f2 =
                    rt.getForEntity("http://localhost:8081/service?idx={idx}", String.class, idx);
            f2.addCallback(s2 -> {
                ListenableFuture<String> f3 = myService.work(s2.getBody());
                f3.addCallback(s3 -> {
                    dr.setResult(s3);
                },e -> {
                    dr.setErrorResult(e.getMessage());
                });
            }, e -> {
                dr.setErrorResult(e.getMessage());
            });
        }, e -> {
            dr.setErrorResult(e.getMessage());
        });
        return dr;
    }*/


    @Service
    public static class MyService {
        @Async
        public ListenableFuture<String> work (String req){
            return new AsyncResult<>(req + "/asyncwork");
        }
    }

    @Bean
    public ThreadPoolTaskExecutor myThreadPool() {
        ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();

        te.setCorePoolSize(1);
        te.setMaxPoolSize(10);
        te.initialize();
        return te;
    }

}
