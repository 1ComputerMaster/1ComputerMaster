//package com.test.tester.reactiveProgramming.async06;
//
//
//import io.netty.channel.nio.NioEventLoopGroup;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.ResponseEntity;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.AsyncResult;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Service;
//import org.springframework.util.concurrent.ListenableFuture;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.request.async.DeferredResult;
//
//import java.util.Objects;
//import java.util.function.Consumer;
//import java.util.function.Function;
//
//@RestController
//public class MyController01 {
////    private RestTemplate rt = new RestTemplate();
////    AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));
//    MyService myService;
//
//    public MyController01(MyService myService){
//        this.myService = myService;
//    }
//
//    /**
//     *
//     * 비동기 작업을 거침 Controller -> Controller -> Service
//     *
//     * 4.2s 정도
//     *
//     * 백그라운드에서 논 블로킹으로 동작 하는 방식을 알아봤다.
//     *
//     * @param idx
//     * @return
//     */
//    @GetMapping("/rest")
//    public DeferredResult<String> asyncRest (int idx) {
//        DeferredResult<String> dr = new DeferredResult<>();
//        /**
//         * 궁극적으로는 아래와 같이 코드를 작성해서 비동기 코드를 완성하고 싶다.
//         *
//         *
//         */
///*
//        Completion
//                .from(rt.getForEntity("http://localhost:8081/service?idx={idx}", String.class, "h"+ idx))
//                .andApply(s ->  rt.getForEntity("http://localhost:8081/service2?idx={idx}", String.class, s.getBody()))
//                .andApply(s -> myService.work(s.getBody()))
//                .andError(e -> dr.setErrorResult(e.toString()))
//                .andAccept(dr::setResult);
//*/
//
//        /**
//         * 아래와 같이 콜백 함수를 부르는 것은 가독성이 좋지 않다.
//         *
//         * 따라서, 위와 같이 리팩토링을 하자
//         */
//
////        ListenableFuture<ResponseEntity<String>> f1 =
////                rt.getForEntity("http://localhost:8081/service?idx={idx}", String.class, idx);
////        f1.addCallback(s -> {
////            ListenableFuture<ResponseEntity<String>> f2 =
////                    rt.getForEntity("http://localhost:8081/service2?idx={idx}", String.class, idx);
////            f2.addCallback(s2 -> {
////                ListenableFuture<String> f3 = myService.work(s2.getBody());
////                f3.addCallback(s3 -> {
////                    dr.setResult(s3);
////                },e -> {
////                    dr.setErrorResult(e.getMessage());
////                });
////            }, e -> {
////                dr.setErrorResult(e.getMessage());
////            });
////        }, e -> {
////            dr.setErrorResult(e.getMessage());
////        });
//        return dr;
//    }
//
//
//    public static class AsyncCompletion<S, T> extends Completion<S, T> {
//        Function<S, ListenableFuture<T>> fn;
//        public AsyncCompletion(Function<S, ListenableFuture<T>> fn) {
//            this.fn = fn;
//        }
//
//        @Override
//        public void run(S value) {
//            ListenableFuture<T> lf = fn.apply(value);
//            lf.addCallback(s -> complete(s), e -> error(e));
//        }
//    }
//
//    public static class ErrorCompletion<T> extends Completion<T, T> {
//        Consumer<Throwable> econ;
//        public ErrorCompletion(Consumer<Throwable> econ) {
//            this.econ = econ;
//        }
//
//        @Override
//        public void run(T value) {
//            if(next != null) next.run(value);
//        }
//
//        @Override
//        public void error(Throwable e) {
//            econ.accept(e);
//        }
//    }
//
//    public static class AcceptCompletion<S> extends Completion<S, Void> {
//        Consumer<S> con;
//        public AcceptCompletion(Consumer<S> con) {
//            this.con = con;
//        }
//
//        @Override
//        public void run(S value) {
//            con.accept(value);
//        }
//    }
//
//    public static class Completion<S, T> {
//        Completion next;
//        public static <S, T> Completion<S, T> from(ListenableFuture<T> lf) {
//            Completion<S, T> c = new Completion<>();
//            lf.addCallback(s -> c.complete(s), e -> c.error(e));
//            return c;
//        }
//
//        public <V> Completion<T, V> andApply(Function<T, ListenableFuture<V>> fn) {
//            Completion<T, V> c = new AsyncCompletion<>(fn);
//            this.next = c;
//            return c;
//        }
//
//        public void andAccept(Consumer<T> con) {
//            Completion<T, Void> c = new AcceptCompletion<>(con);
//            this.next = c;
//        }
//
//        public Completion<T, T> andError(Consumer<Throwable> econ) {
//            Completion<T, T> c = new ErrorCompletion<>(econ);
//            this.next = c;
//            return c;
//        }
//
//        public void complete(T s) {
//            if(next != null) next.run(s);
//        }
//
//        public void error(Throwable e) {
//            if(next != null) next.error(e);
//        }
//
//        public void run(S value) {
//        }
//    }
//
//    @Service
//    public static class MyService {
//        @Async
//        public ListenableFuture<String> work (String req){
//            return new AsyncResult<>(req + "/asyncwork");
//        }
//    }
//
//    @Bean
//    public ThreadPoolTaskExecutor myThreadPool() {
//        ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
//
//        te.setCorePoolSize(1);
//        te.setMaxPoolSize(10);
//        te.initialize();
//        return te;
//    }
//
//}
