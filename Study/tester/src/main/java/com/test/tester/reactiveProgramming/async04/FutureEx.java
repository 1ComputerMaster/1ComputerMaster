package com.test.tester.reactiveProgramming.async04;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;


/**
 * Toby의 리엑티브 스트림 - 08화
 */
@Slf4j
public class FutureEx {
    interface SuccessCallback{
        void onSuccess(String result);
    }

    /**
     * 아래의 done() 메서드 내부의 Exception을 받아서 처리
     */
    interface ExceptionCallback{
        void onError(Throwable t);
    }

    public static class CallbackFutureTask extends FutureTask<String>{
        private SuccessCallback sc;
        private ExceptionCallback ec;
        public CallbackFutureTask(Callable<String> callable, SuccessCallback sc) {
            super(callable);
            this.sc = Objects.requireNonNull(sc);
        }
        public CallbackFutureTask(Callable<String> callable, SuccessCallback sc, ExceptionCallback ec) {
            super(callable);
            this.sc = Objects.requireNonNull(sc);
            this.ec = Objects.requireNonNull(ec);
        }

        @Override
        protected void done() {
            try {
                sc.onSuccess(get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                ec.onError(e.getCause());
            }
        }
    }

    //비동기 작업의 결과물은 Future, Callback을 통해서 가져온다.
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //Future - 비동기 연산의 결과를 가지고 있는 인터페이스
//        test01();
//        test02();
//        test03();
//        test04();
//        test05();
//        test06();
        test07();
    }

    /**
     * 하기 출력문
     * 09:30:26.447 [main] INFO com.test.tester.reactiveProgramming.async04.FutureEx - EXIT
     * 09:30:28.457 [pool-1-thread-1] INFO com.test.tester.reactiveProgramming.async04.FutureEx - HELLO
     */
    private static void test01() {
        ExecutorService es = Executors.newCachedThreadPool();

        es.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            log.info("HELLO");
        });

        log.info("EXIT");
    }


    /**
     * Future라는 것이 main thread가 퓨처로 싼 결과가 나올때 까지 기다려 버린다.
     * 즉, Future.get()이 blocking 상태를 만들어버린다.
     * [하기 출력문]
     * 09:32:51.366 [pool-1-thread-1] INFO com.test.tester.reactiveProgramming.async04.FutureEx - Async
     * HELLO
     * 09:32:51.367 [main] INFO com.test.tester.reactiveProgramming.async04.FutureEx - EXIT
     * 그러나, log.info("EXIT") 를 Sysout 보다 위에 두면
     *
     * 09:35:47.588 [main] INFO com.test.tester.reactiveProgramming.async04.FutureEx - EXIT
     * 09:35:49.595 [pool-1-thread-1] INFO com.test.tester.reactiveProgramming.async04.FutureEx - Async
     * HELLO
     *
     * 이런식으로 나오는데 이것이 병렬적으로 실행 시킬 수 있다는 시나리오를 보여준다.
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void test02() throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        Future<String> f = es.submit(() -> {
            Thread.sleep(2000);
            log.info("Async");
            return "HELLO";
        });
        System.out.println(f.get()); //blocking 방식
        log.info("EXIT");
    }


    /**
     *
     * [하기 출력문]
     * false
     * 09:37:51.955 [pool-2-thread-1] INFO com.test.tester.reactiveProgramming.async04.FutureEx - Async
     * 09:37:52.048 [main] INFO com.test.tester.reactiveProgramming.async04.FutureEx - EXIT
     * true
     * HELLO
     *
     */
    private static void test03() throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        Future<String> f = es.submit(() -> {
            Thread.sleep(2000);
            log.info("Async");
            return "HELLO";
        });
        System.out.println(f.isDone());
        Thread.sleep(2100);
        log.info("EXIT");
        System.out.println(f.isDone());
        System.out.println(f.get()); //blocking 방식
    }

    /**
     * [하기 출력문]
     * false
     * 09:41:37.112 [pool-1-thread-1] INFO com.test.tester.reactiveProgramming.async04.FutureEx - Async
     * 09:41:37.220 [main] INFO com.test.tester.reactiveProgramming.async04.FutureEx - EXIT
     * true
     * HELLO
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void test04() throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();


        FutureTask<String> f = new FutureTask<String>(() -> {
            Thread.sleep(2000);
            log.info("Async");
            return "HELLO";
        });
        es.execute(f);
        es.shutdown();

        System.out.println(f.isDone());
        Thread.sleep(2100);
        log.info("EXIT");
        System.out.println(f.isDone());
        System.out.println(f.get()); //blocking 방식
    }

    /**
     * 
     * [하기 출력문]
     * 09:43:25.354 [pool-2-thread-1] INFO com.test.tester.reactiveProgramming.async04.FutureEx - Async
     * HELLO
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void test05() throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        FutureTask<String> f = new FutureTask<String>(() -> {
            Thread.sleep(2000);
            log.info("Async");
            return "HELLO";
        }) {
            @Override
            protected void done() {
                try{
                    System.out.println(get());
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        es.execute(f);
        es.shutdown();

    }


    /**
     * 09:48:15.462 [pool-1-thread-1] INFO com.test.tester.reactiveProgramming.async04.FutureEx - Async
     * HELLO
     *
     * Callback으로 result 값을 넘겨줘서 done()의 get()이 가다렸다가 넘겨진 값을 받네요.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void test06() throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        CallbackFutureTask f = new CallbackFutureTask(() -> {
            Thread.sleep(2000);
            log.info("Async");
            return "HELLO";

        }, result -> System.out.println(result));
        es.execute(f);
        es.shutdown();
    }

    /**
     * [하기 출력문]
     * Error : Async Error
     *
     * 예외를 위에서 구현한 ExceptionCallback의 onError를 잡는다.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void test07() throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        CallbackFutureTask f = new CallbackFutureTask(() -> {
            Thread.sleep(2000);
            if(1 == 1) throw new RuntimeException("Async Error");
            log.info("Async");
            return "HELLO";

        }, result -> System.out.println(result), e -> System.out.println("Error : " + e.getMessage()));
        es.execute(f);
        es.shutdown();
    }
}

