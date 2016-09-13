package com.hanbing.retrofit_rxandroid;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by hanbing on 2016/9/8
 */
public class Test {



    static Integer[] array = new Integer[]{100, 200, 200, 300, 400, 400, 100, 200, 2200, 1111, 400};

    static final Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            for (int i = 0; i < 3; i++) {
                print("call onNext " + i);
                subscriber.onNext("string " + i);
            }
            subscriber.onCompleted();
        }
    });



    static final Subscriber<String> stringSubscriber = new Subscriber<String>() {
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

    static final Subscriber<Integer> integerSubscriber = new MySubscriber<Integer>() {
        @Override
        public void onNext(Integer integer) {
            print("onNext : " + integer);
        }
    };


    public static void main(final String[] args) {


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
//
//
//            Observable.interval(500, TimeUnit.MILLISECONDS).subscribe(new MySubscriber<Long>() {
//                @Override
//                public void onNext(Long aLong) {
//                    print("onNext " + aLong);
//                }
//            });

//            Observable.just("1", "2", "3").subscribe(subscriber);
//            Observable.range(1, 10).subscribe(subscriberInteger);
//        Observable.range(1, 10).repeat(2).subscribe(subscriberInteger);

//        Observable.range(1, 10).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
//            @Override
//            public Observable<?> call(Observable<? extends Void> observable) {
//                return observable;
//            }
//        }).subscribe(subscriberInteger);


//        Observable.range(1, 10).buffer(new Func0<Observable<?>>() {
//            @Override
//            public Observable<?> call() {
//                return null;
//            }
//        }).subscribe();

//        Observable.range(1, 10).flatMap(new Func1<Integer, Observable<String>>() {
//            @Override
//            public Observable<String> call(final Integer integer) {
//                return Observable.create(new Observable.OnSubscribe<String>() {
//                    @Override
//                    public void call(Subscriber<? super String> subscriber) {
//                        subscriber.onNext("new string " + integer);
//                    }
//                });
//            }
//        }).subscribe(subscriber);


//        Observable.range(1, 10).flatMap(new Func1<Integer, Observable<String>>() {
//            @Override
//            public Observable<String> call(final Integer integer) {
//                return Observable.create(new Observable.OnSubscribe<String>() {
//                    @Override
//                    public void call(Subscriber<? super String> subscriber) {
//                        subscriber.onNext("new string " + integer);
//                    }
//                });
//            }
//        }, new Func2<Integer, String, String>() {
//
//            @Override
//            public String call(Integer integer, String s) {
//                return "Func2 -> " + integer + " -> " + s;
//            }
//        }).subscribe(subscriber);

//        Observable.range(1, 10).flatMapIterable(new Func1<Integer, Iterable<String>>() {
//            @Override
//            public Iterable<String> call(Integer integer) {
//
//                List<String> list = new ArrayList<String>();
//
//                for (int i = 0; i < 5; i++) {
//                    list.add(integer + "->" + i);
//                }
//
//                return list;
//            }
//        }).subscribe(subscriber);

//        Observable.range(1, 10).concatMap(new Func1<Integer, Observable<String>>() {
//            @Override
//            public Observable<String> call(final Integer integer) {
//
//                return createObservable(integer);
//            }
//        }).concatMap(new Func1<String, Observable<String>>() {
//            @Override
//            public Observable<String> call(String s) {
//                return createObservable(s);
//            }
//        }).subscribe(subscriber);

//        Observable.range(1, 10).concatMapIterable(new Func1<Integer, Iterable<String>>() {
//            @Override
//            public Iterable<String> call(final Integer integer) {
//                return createIterable(integer);
//            }
//        }).subscribe(subscriber);


//        Observable.range(1, 10).switchMap(new Func1<Integer, Observable<String>>() {
//            @Override
//            public Observable<String> call(Integer integer) {
//                return createObservable(integer);
//            }
//        }).switchMap(new Func1<String, Observable<String>>() {
//            @Override
//            public Observable<String> call(String s) {
//                return createObservable(s);
//            }
//        }).subscribe(subscriber);


//        Observable.range(1, 10).groupBy(new Func1<Integer, Object>() {
//            @Override
//            public Object call(Integer integer) {
//                return "key" + (integer % 2);
//            }
//        }).subscribe(new Subscriber<GroupedObservable<Object, Integer>>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(GroupedObservable<Object, Integer> objectIntegerGroupedObservable) {
//                print("key = " + objectIntegerGroupedObservable.getKey());
//
//                objectIntegerGroupedObservable.subscribe(subscriberInteger);
//            }
//        });

//        Observable.range(1, 10).map(new Func1<Integer, String>() {
//            @Override
//            public String call(Integer integer) {
//                return "map " + integer + " -> " ;
//            }
//        }).subscribe(subscriber);

//        Observable.create(new Observable.OnSubscribe<B>() {
//            @Override
//            public void call(Subscriber<? super B> subscriber) {
//                subscriber.onNext(new B(12));
//            }
//        }).cast(A.class).subscribe(new MySubscriber<A>() {
//            @Override
//            public void onNext(A a) {
//                print(a.toString());
//            }
//        });

//        Observable.range(1, 10).scan(44, new Func2<Integer, Integer, Integer>() {
//            @Override
//            public Integer call(Integer integer, Integer integer2) {
//                print("integer = " + integer + ", integer2 = " + integer2);
//                return integer + integer2;
//            }
//        }).subscribe(subscriberInteger);

//        Observable.range(1,10)
////                .window(3)
//                .window(3, 3)
//                .subscribe(new MySubscriber<Observable<Integer>>() {
//                    @Override
//                    public void onNext(Observable<Integer> integerObservable) {
//                        integerObservable.subscribe(integerSubscriber);
//                    }
//                });

//        Observable.range(1, 10)
//                .map(new Func1<Integer, Integer>() {
//                    @Override
//                    public Integer call(Integer integer) {
//
//                        long time = (long) (200 + Math.random() * 1000);
//                        try {
//                            Thread.sleep(time);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        return integer;
//                    }
//                })
////                .debounce(1000, TimeUnit.MILLISECONDS)
//                .subscribe(integerSubscriber);

        final HashSet<Integer> hashSet = new HashSet<>();

//        Observable
////                .from(array)
////                .distinct()
////                .distinct(new Func1<Integer, Integer>() {
////                    @Override
////                    public Integer call(Integer integer) {
////                        if (hashSet.add(integer)) {
////                            return integer;
////                        }
////                        return null;
////                    }
////                })
////                .distinctUntilChanged()
////                .distinctUntilChanged(new Func1<Integer, Integer>() {
////                    @Override
////                    public Integer call(Integer integer) {
////                        return ;
////                    }
////                })
//                .from(array)
////                .elementAt(3)
////                .elementAtOrDefault(4, 222)
////                .filter(new Func1<Integer, Boolean>() {
////                    @Override
////                    public Boolean call(Integer integer) {
////                        return integer > 4;
////                    }
////                })
//                .subscribe(integerSubscriber);

//        Observable.just(new B(10), new A(1), new B(22), new B(123), new A(121))
//                .ofType(A.class)
//                .subscribe(new MySubscriber<A>() {
//                    @Override
//                    public void onNext(A a) {
//                        print(a.toString());
//                    }
//                });

//        Observable.range(1, 10)
//                .map(new Func1<Integer, Integer>() {
//                    @Override
//                    public Integer call(Integer integer) {
//                        try {
//                            Thread.sleep(300);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        return integer;
//                    }
//                })
////                .take(3)
////                .take(2000, TimeUnit.MILLISECONDS)
////                .takeLast(3)
////                .takeLast(2000, TimeUnit.MILLISECONDS)
////                .subscribe(integerSubscriber);
//        .takeLastBuffer(3)
//                .subscribe(new MySubscriber<List<Integer>>() {
//                    @Override
//                    public void onNext(List<Integer> integers) {
//                        for (Integer integer: integers
//                             ) {
//                            print("" + integer);
//                        }
//                    }
//                });

//
//        List<Observable<Integer>> list = new ArrayList<>();
//
//        for (int i = 0; i < 10; i ++) {
//            final int value = i;
//            list.add(Observable.create(new Observable.OnSubscribe<Integer>() {
//                @Override
//                public void call(Subscriber<? super Integer> subscriber) {
//
//                    try {
//                        Thread.sleep(300 * value);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    print("value = " + value);
//                    subscriber.onNext(value);
//                }
//            }));
//        }
//
//        Observable.range(1, 10)
//                .combineLatest(list, new FuncN<Integer>() {
//            @Override
//            public Integer call(Object... args) {
//                if (null != args) {
//                    for (Object o :
//                            args) {
//                        print("->" + o+"");
//                    }
//                }
//
//                return 1;
//            }
//        }).subscribe(integerSubscriber);

//        Observable.range(1, 10)
//                .join(createObservable("abc"),
//                        new Func1<Integer, Observable<String>>() {
//                            @Override
//                            public Observable<String> call(Integer integer) {
//                                return createObservable("1->" + integer);
//                            }
//                        }, new Func1<String, Observable<String>>() {
//                            @Override
//                            public Observable<String> call(String s) {
//
//                                return createObservable("2->" + s);
//                            }
//                        }, new Func2<Integer, String, String>() {
//                            @Override
//                            public String call(Integer integer, String s) {
//                                return "3->" + integer + "===" + s;
//                            }
//                        }).subscribe(stringSubscriber);



//        {
//            List<Observable<Integer>> list = new ArrayList<>();
//            for (int i = 0; i < 10; i ++) {
//                final  int value = i;
//                list.add(Observable.create(new Observable.OnSubscribe<Integer>() {
//                    @Override
//                    public void call(Subscriber<? super Integer> subscriber) {
//                        subscriber.onNext(value);
//                    }
//                }));
//            }
//            Observable.zip(list, new FuncN<String>() {
//                @Override
//                public String call(Object... args) {
//
//                    String s = "";
//
//                    for (Object object :
//                            args) {
//                        s = s + object + ",";
//                    }
//                    return s;
//                }
//            }).subscribe(stringSubscriber);
//
//
//        }



//
//        Observable.range(10, 20)
//                .zipWith(Observable.create(new Observable.OnSubscribe<String>() {
//                    @Override
//                    public void call(Subscriber<? super String> subscriber) {
//                        subscriber.onNext("hahaha");
//                    }
//                }), new Func2<Integer, String, String>() {
//                    @Override
//                    public String call(Integer integer, String s) {
//                        return s + "->" + integer ;
//                    }
//                })
//                .subscribe(stringSubscriber);


//       testDelay();
//       testDo();
//        testMaterialize();
//        testObserveOn();
//        testSerialize();
    }

    private static void testSerialize() {
        Observable.range(1, 20)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.immediate())
                .serialize()
                .subscribe(integerSubscriber);
    }

    private static void testObserveOn() {

        ExecutorService executorService = Executors.newCachedThreadPool();
        Scheduler scheduler =
//                Schedulers.io()
//                Schedulers.trampoline()
                Schedulers.newThread();
//                Schedulers.computation()
//                Schedulers.immediate()
//                Schedulers.from(executorService)
                ;

        Observable.range(1, 10)
                .observeOn(scheduler)
                .subscribeOn(Schedulers.trampoline())
                .subscribe(integerSubscriber);
    }

    private static void testMaterialize() {
        Observable.range(1, 10)
                .materialize()
                .dematerialize()


//                .subscribe(new MySubscriber<Notification<Integer>>() {
//                    @Override
//                    public void onNext(Notification<Integer> integerNotification) {
//                        print("materialize onNext : " + integerNotification.getValue());
//                    }
//                })
                .subscribe(new MySubscriber<Object>() {
                    @Override
                    public void onNext(Object o) {
                        print("dematerialize onNext : " + o);
                    }
                })
                ;

    }

    private static void testDo(){
        Observable.range(1, 10)
                .flatMap(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(final Integer integer) {
                        return Observable.create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {
                                if (integer > 5)
                                    subscriber.onError(new Error("integer > 5"));
                                else
                                    subscriber.onNext(integer);
                            }
                        });
                    }
                })
//                .doOnEach(new Action1<Notification<? super Integer>>() {
//                    @Override
//                    public void call(Notification<? super Integer> notification) {
//                        print("notifi : " + notification.getValue());
//                    }
//                })
//                .doOnEach(new MySubscriber<Integer>() {
//                    @Override
//                    public void onNext(Integer integer) {
//                        print("notify : " + integer);
//                    }
//                })
//                .doOnNext(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        print("doOnNext " + integer);
//                    }
//                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        print("doOnSubscribe ");
                    }
                })
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        print("doOnUnsubscribe ");
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        print("doOnCompleted ");
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        print("doOnError " + throwable);
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        print("doOnTerminate ");
                    }
                })
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        print("doAfterTerminate ");
                    }
                })
                .doOnRequest(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        print("doOnRequest " + aLong);
                    }
                })
                .subscribe(integerSubscriber);
    }


    private static void testDelay(){
        Observable.range(1, 10)
                .flatMap(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(final Integer integer) {
                        return Observable.create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {

                                if (integer > 5)
                                    subscriber.onError(new IllegalArgumentException(System.currentTimeMillis() + "-> integer > 5"));
                                else
                                    subscriber.onNext(integer);
                            }
                        });
                    }
                })
                .onErrorReturn(new Func1<Throwable, Integer>() {
                    @Override
                    public Integer call(Throwable throwable) {
                        return 20;
                    }
                })
                .onErrorResumeNext(Observable.range(11, 10))
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Integer>>() {
                    @Override
                    public Observable<? extends Integer> call(Throwable throwable) {
                        return Observable.range(10, 20);
                    }
                })
                .onExceptionResumeNext(Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        subscriber.onNext(2222);
                        subscriber.onCompleted();
                    }
                }))
                .retry()
                .retry(3)
                .retry(new Func2<Integer, Throwable, Boolean>() {
                    @Override
                    public Boolean call(Integer integer, Throwable throwable) {
                        return integer == 5;
                    }
                })
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                            @Override
                            public Observable<?> call(Throwable throwable) {
                                return Observable.timer(5, TimeUnit.SECONDS);
                            }
                        });
                    }

                })
                .subscribe(integerSubscriber);
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("123");
            }
        })
                .delay(3000,TimeUnit.MILLISECONDS)
                .subscribe(stringSubscriber)

        ;
    }

    static class A {
        int value;

        public A(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "A.class value = " + value;
        }
    }

    static class B extends A {

        public B(int value) {
            super(value);
        }

        @Override
        public String toString() {
            return "B.class value = " + value;
        }
    }


    private static Observable<String> createObservable(final String string) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("String = " + string);
            }
        });
    }


    private static Observable<String> createObservable(final Integer integer) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Integer = " + integer);
            }
        });
    }

    private static Observable<Integer> createIntegerObservable(final Integer integer) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(integer);
            }
        });
    }

    private static Iterable<String> createIterable(Integer integer) {
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < 3; i++) {
            list.add(integer + " -> " + i);
        }

        return list;
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

        public MyFuture() {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    while (!isDone()) {
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
