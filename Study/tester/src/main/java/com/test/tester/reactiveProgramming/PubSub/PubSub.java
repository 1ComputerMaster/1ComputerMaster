package com.test.tester.reactiveProgramming.PubSub;


import com.test.tester.reactiveProgramming.PubSub.delegate.DelegateSub;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * push -> pull
 * 1 - publisher -> Data1 -> Operator1 -> Data2 -> Operator2 -> Subscriber
 * 2. map (d1 -> f -> d2)
 *  - pub -> [Data1] -> mapPub -> [Data2] -> getSub()
 *                   <- subscribe(getSub)
 *                   -> onSubnscribe(s)
 *                   -> onNext()
 *                   -> onComplete()
 *
 *                   - 토비의 봄 TV 6회 스프링 리액티브 프로그래밍(2) 43:12
* */
@Slf4j
public class PubSub {
    public static void main(String[] args) {

/* 1 번 구조안
        Publisher<Integer> pub = getPublisher(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));
        pub.subscribe(getSub());
*/

/* 2번 구조안
        Publisher<Integer> pub = getPublisher(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));
        Publisher<Integer> mapPub = mapPub(pub,(Function<Integer,Integer>) s -> s * 10);
        Publisher<Integer> mapPub2 = mapPub(mapPub,(Function<Integer,Integer>) s -> -s);
        mapPub2.subscribe(getSub());

*/
/*
3번 안
        Publisher<Integer> pub = getPublisher(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));
        Publisher<Integer> sumPub = sumPub(pub);
        sumPub.subscribe(getSub());
*/

/*4번 안
        Publisher<Integer> pub = getPublisher(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));
        Publisher<Integer> reducePub = reducePub(pub,0,(BiFunction<Integer,Integer,Integer>)(a,b) -> a + b);
        reducePub.subscribe(getSub());
*/

        Publisher<Integer> pub = getPublisher(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));
//        Publisher<String> mapPub = mapPub(pub, s -> "[" + s + "]");
        Publisher<StringBuilder> reducePub = reducePub(pub,new StringBuilder(),(a, b) -> a.append(b + ","));
        reducePub.subscribe(getSub());

    }

    private static <T,R>Publisher<R> reducePub(Publisher<T> pub, R init, BiFunction<R, T, R> bf) {
        return new Publisher<R>() {
            @Override
            public void subscribe(Subscriber<? super R> s) {
                pub.subscribe(new DelegateSub<T,R>(s){
                    R result = init;
                    @Override
                    public void onNext(T i) {
                        result = (bf.apply(result,i));
                    }
                    @Override
                    public void onComplete() {
                        s.onNext(result);
                        s.onComplete();
                    }
                });
            }
        };
    }

/*
    private static Publisher<Integer> sumPub(Publisher<Integer> pub) {
        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> s) {
                pub.subscribe(new DelegateSub(s){
                    int sum = 0;
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(Integer i) {
                        sum += i;
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        s.onNext(sum);
                        s.onComplete();
                    }
                });
            }
        };
    }
*/

/*
    private static Publisher<Integer> mapPub(Publisher<Integer> pub, Function<Integer, Integer> integerIntegerFunction) {
        return (Subscriber<? super Integer> sub) -> {
            pub.subscribe(DelegateSub(integerIntegerFunction, sub));
        };
    }
*/

    private static <T,R>Publisher<R> mapPub(Publisher<T> pub, Function<T, R> integerIntegerFunction) {
        return (Subscriber<? super R> sub) -> {
            pub.subscribe(new DelegateSub<T,R>(sub){
                @Override
                public void onNext(T integer) {
                    sub.onNext(integerIntegerFunction.apply(integer));
                }
            });
        };
    }


    private static Subscriber<Integer> DelegateSub(Function<Integer, Integer> integerIntegerFunction, Subscriber<? super Integer> sub) {
        return new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                sub.onSubscribe(s);
            }

            @Override
            public void onNext(Integer integer) {
                sub.onNext(integerIntegerFunction.apply(integer));
            }

            @Override
            public void onError(Throwable t) {
                sub.onError(t);
            }

            @Override
            public void onComplete() {
                sub.onComplete();
            }
        };
    }

    private static <T>Subscriber<T> getSub() {
        return new Subscriber<T>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.debug("onSubscribe");
                s.request(Long.MAX_VALUE);
            }
            @Override
            public void onNext(T integer) {
                log.debug("onNext : {}", integer);
            }
            @Override
            public void onError(Throwable t) {
                log.debug("onError : {}", t);

            }
            @Override
            public void onComplete() {
                log.debug("onComplete");

            }
        };
    }

    private static Publisher<Integer> getPublisher(Iterable<Integer> iter1) {
        return (sub) -> {
            Iterable<Integer> iter = iter1;
            sub.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {
                    try {
                        iter.forEach(s -> sub.onNext(s));
                        sub.onComplete();
                    } catch (Throwable t) {
                        sub.onError(t);
                    }
                }

                @Override
                public void cancel() {

                }
            });
        };
    }
}
