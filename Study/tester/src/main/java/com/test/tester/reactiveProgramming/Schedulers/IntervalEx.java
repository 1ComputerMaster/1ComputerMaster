package com.test.tester.reactiveProgramming.Schedulers;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 * FluxScEx의 test02 메서드의 take 함수를 구현해보자.
 *
 * */
@Slf4j
public class IntervalEx {
    public static void main(String[] args) {
        Publisher<Integer> pub = sub -> {
            sub.onSubscribe(new Subscription() {
                int no = 0;
                volatile boolean cancelled = false;
                @Override
                public void request(long n) {
                    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                    executorService.scheduleAtFixedRate(() -> {
                        if(cancelled){
                            executorService.shutdown();
                            return;
                        }
                        sub.onNext(no++);
                    },0,300, TimeUnit.MILLISECONDS);
                }

                @Override
                public void cancel() {
                    cancelled = true;
                }
            });
        };

        Publisher<Integer> takePub = sub ->{

            pub.subscribe(new Subscriber<Integer>() {
                int cnt = 0;
                Subscription subsc;
                @Override
                public void onSubscribe(Subscription s) {
                    subsc = s;
                    sub.onSubscribe(s);
                }

                @Override
                public void onNext(Integer integer) {
                    if(++cnt > 10){
                        subsc.cancel();
                    }
                    sub.onNext(integer);
                }

                @Override
                public void onError(Throwable t) {
                    sub.onError(t);
                }

                @Override
                public void onComplete() {
                    sub.onComplete();
                }
            });
        };

        takePub.subscribe(new Subscriber<Integer>() {
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
    }
}
