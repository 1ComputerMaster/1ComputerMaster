package com.test.tester.reactiveProgramming.PubSub.delegate;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class DelegateSub<T,R> implements Subscriber<T>{
    Subscriber<T> sub;

    public DelegateSub(Subscriber<? super R> sub){
        this.sub = (Subscriber<T>) sub;
    }
    public void onSubscribe(Subscription s){
        sub.onSubscribe(s);
    }


    public void onNext(T i){
        sub.onNext(i);
    }

    public void onError(Throwable t){
        sub.onError(t);
    }

    public void onComplete(){
        sub.onComplete();
    }

}
