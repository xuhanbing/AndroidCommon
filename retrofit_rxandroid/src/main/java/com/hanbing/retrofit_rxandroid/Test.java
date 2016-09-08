package com.hanbing.retrofit_rxandroid;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hanbing on 2016/9/8
 */
public class Test  {


    static final Observable observable =  Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            for (int i = 0; i < 3; i++){
                print("call onNext " + i);
                subscriber.onNext("string " + i);
            }
            subscriber.onCompleted();
        }
    });

    static final Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {
            print("onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            print("onError : " + e);
        }

        @Override
        public void onNext(String s) {
            print("onNext : " + s);
        }
    };

    static final Subscriber<Integer> subscriberInteger = new MySubscriber<Integer>() {
        @Override
        public void onNext(Integer integer) {
            print("onNext : " + integer);
        }
    };


    public static void main(String[] args) {


//        observable.subscribe(subscriber)
//        ;

//        final Observable<String> defer = Observable.defer(new Func0<Observable<String>>() {
//            @Override
//            public Observable<String> call() {
//
//                return observable;
//            }
//        });

//        print("before subscribe");
//        observable.subscribe(subscriber);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                print("after sleep 3s");
//                defer.subscribe(subscriber);
//            }
//        }).start();

//        Observable<String> empty = Observable.empty();
//
//        empty.subscribe(subscriber);

//        Observable<String> never = Observable.never();
//
//        never.subscribe(subscriber);
//
//        Observable<String> error = Observable.error(new IllegalArgumentException("Arguments is null"));
//
//        error.subscribe(subscriber);


//        String[] strings = {"Jim", "Kite", "LinTao", "Tom"};
//
//        List<String> list = Arrays.asList(strings);
//
//        Observable.from(list).subscribe(subscriber);


//        {
//            Observable.from(new MyFuture()).subscribe(new MySubscriber<Integer>() {
//
//                @Override
//                public void onNext(Integer integer) {
//                    print("onNext : " + integer);
//                }
//            });
//
//        }

//        {
//            List<MyFuture> list = new ArrayList<>();
//            for (int i = 0; i < 5; i++){
//                list.add(new MyFuture());
//            }
//
//            Observable.from(list).subscribe(new Subscriber<MyFuture>() {
//                @Override
//                public void onCompleted() {
//                    print("onCompleted ");
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                    print("onError : " + e);
//                }
//
//                @Override
//                public void onNext(MyFuture myFuture) {
//                    try {
//                        print("onNext : " + myFuture.get());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }


            Observable.interval(500, TimeUnit.MILLISECONDS).subscribe(new MySubscriber<Long>() {
                @Override
                public void onNext(Long aLong) {
                    print("onNext " + aLong);
                }
            });

//            Observable.just("1", "2", "3").subscribe(subscriber);
//            Observable.range(1, 10).subscribe(subscriberInteger);
//        Observable.range(1, 10).repeat(2).subscribe(subscriberInteger);

//        Observable.range(1, 10).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
//            @Override
//            public Observable<?> call(Observable<? extends Void> observable) {
//                return observable;
//            }
//        }).subscribe(subscriberInteger);


    }

    static abstract class MySubscriber<T> extends Subscriber<T> {
        @Override
        public void onCompleted() {
            print("onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            print("onError : " + e);
        }
    }

    static class MyFuture implements Future<Integer> {

        boolean mCanceled = false;

        int mPercent = 100;

        public MyFuture(){
            new Thread(new Runnable() {
                @Override
                public void run() {

                    while (!isDone())
                    {
                        mPercent--;

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            if (mayInterruptIfRunning) {
                mCanceled = true;
                return true;
            } else {

            }
            return false;
        }

        @Override
        public boolean isCancelled() {
            return mCanceled;
        }

        @Override
        public synchronized boolean isDone() {
            return 0 == mPercent;
        }

        @Override
        public Integer get() throws InterruptedException, ExecutionException {
            return mPercent;
        }

        @Override
        public Integer get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return get();
        }
    }

    static void print(String s) {
        System.out.println(s);
    }
}
