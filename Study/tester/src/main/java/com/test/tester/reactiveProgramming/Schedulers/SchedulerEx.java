package com.test.tester.reactiveProgramming.Schedulers;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 토비의 봄 강의 - Reactive Stream (3)
 */
@Slf4j
public class SchedulerEx {
    //Reactive Streams
    //상당히 추상화가 되어 있어서 사실 리엑티브를 모두 이해하려면 복잡한 비동기 작업을 한 번 구현 해보는 것이 좋습니다.
    //따라서, 복잡한 작업 과정을 거쳐서 큰 그림을 그려봅시다.

    // Publisher.subscribe(Subscriber) : 퍼블리셔의 결과물을 구독 주체가 구독한다.

    public static void main(String[] args) {
        Publisher<Integer> pub = (sub) -> {
            sub.onSubscribe(new Subscription() { //구독시 동작 방식 정의
                @Override
                public void request(long n) { //가지고 있는 거 내놔 n 만큼
                    log.debug("request");
                    sub.onNext(1);
                    sub.onNext(2);
                    sub.onNext(3);
                    sub.onNext(4);
                    sub.onNext(5);
                    sub.onComplete();
                }

                @Override
                public void cancel() {

                }
            });
        };
        /**
         * pub - main thread의 블로킹을 막았다.
         * 
         * 출력문
         * 
         * EXIT
         * 22:37:28.677 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onSubscribe
         * 22:37:28.678 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:1
         * 22:37:28.679 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:2
         * 22:37:28.679 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:3
         * 22:37:28.679 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:4
         * 22:37:28.679 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:5
         * 22:37:28.679 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onComplete
         * 
         * 메인 스레드는 이미 끝나버렸고 우리는 이후에 다른 스레드에서 작업을 수행했습니다. subscribeOn해서
         * 
        */
        
       /*
        Publisher<Integer> subOnPub = sub -> {
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.execute(() -> pub.subscribe(sub));
        }; //중계형 pub
        */

        /**
         * pub - subOnPub -  pubOnPub
         *
         * 출력문
         *
         *
         * EXIT
         * 22:47:45.416 [pool-2-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onSubscribe
         * 22:47:45.417 [pool-2-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - request
         * 22:47:45.418 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:1
         * 22:47:45.419 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:2
         * 22:47:45.419 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:3
         * 22:47:45.419 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:4
         * 22:47:45.419 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:5
         * 22:47:45.419 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onComplete
         *
         * 메인 스레드는 이미 끝나버렸고 우리는 이후에 다른 스레드에서 작업을 수행했습니다. subscribeOn해서
         *
         */
//        Publisher<Integer> subOnPub = sub -> {
//            ExecutorService es = Executors.newSingleThreadExecutor(new CustomizableThreadFactory(){
//                @Override
//                protected String getDefaultThreadNamePrefix() {
//                    return "subOn-";
//                }
//            });
//            es.execute(() -> pub.subscribe(sub));
//        }; //중계형 pub

        /**
         *
         * SingleThread가 하나의 Publisher가 데이터 생성해서 실행
         *
         * 알수 있는 건 데이터 생성은 빠르게 할 수 있으니 publisher가 데이터 모두 생성해두고
         *
         * Subscriber를 실행한다.
         *
         * 22:43:42.818 [main] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onSubscribe
         * 22:43:42.820 [main] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - request
         * EXIT
         * 22:43:42.820 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:1
         * 22:43:42.821 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:2
         * 22:43:42.821 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:3
         * 22:43:42.821 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:4
         * 22:43:42.821 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:5
         * 22:43:42.821 [pool-1-thread-1] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onComplete
         *
         * */
        Publisher<Integer> pubOnPub = sub -> {
            pub.subscribe(new Subscriber<Integer>() {
                ExecutorService es = Executors.newSingleThreadExecutor(new CustomizableThreadFactory(){
                    @Override
                    protected String getDefaultThreadNamePrefix() {
                        return "pubOn-";
                    }
                });
                @Override
                public void onSubscribe(Subscription s) {
                    sub.onSubscribe(s);
                }

                @Override
                public void onNext(Integer integer) {
                    es.execute(() -> sub.onNext(integer));
                }

                @Override
                public void onError(Throwable t) {
                    es.execute(() -> sub.onError(t));
                    es.shutdown();
                }

                @Override
                public void onComplete() {
                    es.execute(() -> sub.onComplete());
                    es.shutdown();
                }
            });
        };
        pubOnPub.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.debug("onSubscribe");
                s.request(Long.MAX_VALUE); //서브 스크립션의 요청
            }

            @Override
            public void onNext(Integer integer) {
                log.debug("onNext:{}",integer); //데이터 하나 올때마다 데이터 찍음

            }

            @Override
            public void onError(Throwable t) {
                log.debug("onError:{}",t);

            }

            @Override
            public void onComplete() {
                log.debug("onComplete");
            }
        });


//        subOnPub.subscribe(new Subscriber<Integer>() {
//            @Override
//            public void onSubscribe(Subscription s) {
//                log.debug("onSubscribe");
//                s.request(Long.MAX_VALUE); //서브 스크립션의 요청
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                log.debug("onNext:{}",integer); //데이터 하나 올때마다 데이터 찍음
//
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                log.debug("onError:{}",t);
//
//            }
//
//            @Override
//            public void onComplete() {
//                log.debug("onComplete");
//            }
//        });

        //sub
        /**
         *  출력시 아래와 같이 나온다.
         *
         *  main thread 하나만 실행이 됩니다.
         *
         *  object에 s.request() -> sub.onNext(1~5)의 -> 메서드 호출에 의해서 onSubscribe이 실행이 끝남
         *
         *  22:25:32.328 [main] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onSubscribe
         *  22:25:32.330 [main] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:1
         *  22:25:32.331 [main] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:2
         *  22:25:32.331 [main] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:3
         *  22:25:32.331 [main] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:4
         *  22:25:32.331 [main] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onNext:5
         *  22:25:32.331 [main] DEBUG com.test.tester.reactiveProgramming.Schedulers.SchedulerEx - onComplete
         *
         *  thread를 각 스레드마다 동기적으로 실행되면 당연히 레이턴시가 떨어진다.
         *
         *  실제 프로그램에서는 이런식으로 직렬적으로 쓰지 않습니다.
         *
         *  flux.subscribeOn()하면 한 thread에서 subscribeOn을 걸고 실행 시킬 수 있습니다.
         *
         */
//        pub.subscribe(new Subscriber<Integer>() {
//            @Override
//            public void onSubscribe(Subscription s) {
//                log.debug("onSubscribe");
//                s.request(Long.MAX_VALUE); //서브 스크립션의 요청
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                log.debug("onNext:{}",integer); //데이터 하나 올때마다 데이터 찍음
//
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                log.debug("onError:{}",t);
//
//            }
//
//            @Override
//            public void onComplete() {
//                log.debug("onComplete");
//            }
//        });

        System.out.println("EXIT");
    }
}
