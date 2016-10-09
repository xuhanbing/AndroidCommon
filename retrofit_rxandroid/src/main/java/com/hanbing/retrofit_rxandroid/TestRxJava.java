package com.hanbing.retrofit_rxandroid;


import com.google.gson.Gson;
import com.hanbing.library.android.util.TimeUtils;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.math.operators.OperatorAverageInteger;
import rx.observables.BlockingObservable;
import rx.observables.ConnectableObservable;
import rx.observables.MathObservable;
import rx.schedulers.Schedulers;
import rx.schedulers.TimeInterval;
import rx.schedulers.Timestamped;

/**
 * Created by hanbing on 2016/9/8
 */
public class TestRxJava {



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


    static final Observable<Integer> integerObservable = Observable.range(1, 10);
    static final Observable<Integer> integerObservable2 = Observable.range(11, 10);

    static final Subscriber<String> stringSubscriber = new MySubscriber<String>() {
        @Override
        public void onNext(String s) {
            print("String onNext : " + s);
        }
    };

    static final Subscriber<Integer> integerSubscriber = new MySubscriber<Integer>() {
        @Override
        public void onNext(Integer integer) {
            print("Integer onNext : " + integer);
        }
    };

    static final Subscriber<List<Integer>> integersSubscriber = new MySubscriber<List<Integer>>() {
        @Override
        public void onNext(List<Integer> integers) {

            StringBuilder stringBuilder = new StringBuilder();

            for (Integer i :
                    integers) {

                stringBuilder.append(i);
                stringBuilder.append(",");
            }

            print(stringBuilder.toString());
        }
    };

    static  final Subscriber<? super Boolean> booleanSubscriber = new MySubscriber<Boolean>() {
        @Override
        public void onNext(Boolean aBoolean) {
            print("Boolean onNext : " + aBoolean);
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
//        testTimeInterval();
//        testTimeOut();
//        testTimestamp();
//        testUsing();
//        testTo();
//        testAll();
//        testMath();
//        testCombine();
//        testConnect();



        String[] dates = {"2016-10-31", "1999-01-01", "1999-03-01", "2003-03-01", "2004-03-01", "2016-01-01"};

        for (String date : dates) {
            System.out.println(date + "->" + calcDate(date));

        }
    }


    public static String calcDate(String date) {

        long days = 30;
        long timeInMillis = TimeUtils.getTimeInMillis("yyyy-MM-dd", date);


        return TimeUtils.getTime("yyyy-MM-dd hh:mm:ss", timeInMillis + (29 * 24 * 3600 * 1000L));

    }

    static class Test1 {
        public int value = 20;

    }

    private static void testConnect() {

        final ConnectableObservable<Integer> observable = Observable.range(1, 10)
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return integer;
                    }
                })
                .replay(4)
                .publish()
                ;

//        final Observable<Integer> observable1 = observable.refCount();
//        observable1.subscribe(integerSubscriber);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//
//                observable1.subscribe(new MySubscriber<Integer>() {
//                    @Override
//                    public void onNext(Integer integer) {
//                        print("onNext2 " + integer);
//                    }
//                });
//
//
//            }
//        }).start();

        observable.subscribe(integerSubscriber);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                observable.subscribe(new MySubscriber<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        print("onNext2 " + integer);
                    }
                });
            }
        }).start();
        observable.connect();
    }

    private static void testCombine() {

        integerObservable
//                .concatWith(integerObservable2)
//                .mergeWith(integerObservable2)
//                .count()
//                .reduce(11, new Func2<Integer, Integer, Integer>() {
//                    @Override
//                    public Integer call(Integer integer, Integer integer2) {
//                        print("reduce integer = " + integer + ", integer2 = " + integer2);
//                        return integer - integer2;
//                    }
//                })
//                .scan(new Func2<Integer, Integer, Integer>() {
//                    @Override
//                    public Integer call(Integer integer, Integer integer2) {
//                        return integer + integer2;
//                    }
//                })
                .collect(new Func0<List<Integer>>() {
                             @Override
                             public List<Integer> call() {
                                 print("Func0");
                                 return new ArrayList<Integer>();
                             }
                         }
                        ,
                        new Action2<List<Integer>, Integer>() {
                            @Override
                            public void call(List<Integer> integers, Integer integer) {
                                integers.add(integer);
                            }
                        })
                .subscribe(integersSubscriber);


    }

    private static void testMath() {
        Observable<Integer> integerObservable = Observable.range(1, 10);
        MathObservable
//                .averageInteger(integerObservable))
//                .max(integerObservable)
//                .min(integerObservable)
                .sumInteger(integerObservable)
                .subscribe(integerSubscriber);

    }

    private static void testAll() {
//        Observable.range(1, 10)
//                .all(new Func1<Integer, Boolean>() {
//                    @Override
//                    public Boolean call(Integer integer) {
//                        return integer < 5;
//                    }
//                })
//                .subscribe(new MySubscriber<Boolean>() {
//                    @Override
//                    public void onNext(Boolean aBoolean) {
//                        print("onNext : "+ aBoolean);
//                    }
//                });

        Observable<Integer> observable1 = Observable.range(1, 10);
        Observable<Integer> observable2 = Observable.range(11, 10);

//        observable2.ambWith(observable1)
//                .subscribe(integerSubscriber);

//        Observable.amb(observable1, observable2)
//                .subscribe(integerSubscriber);


//        observable1.contains(25)
//                .subscribe(booleanSubscriber);

//        Observable.empty()
//                .isEmpty()
//                .subscribe(booleanSubscriber);

//        observable1
//                .exists(new Func1<Integer, Boolean>() {
//                    @Override
//                    public Boolean call(Integer integer) {
//                        return integer > 5;
//                    }
//                })
//                .subscribe(booleanSubscriber);

//        Observable
//                .empty()
//                .defaultIfEmpty(222)
//                .subscribe(new MySubscriber<Object>() {
//                    @Override
//                    public void onNext(Object o) {
//                        print("" + o);
//                    }
//                });

//        Observable.sequenceEqual(observable1, observable2, new Func2<Integer, Integer, Boolean>() {
//            @Override
//            public Boolean call(Integer integer, Integer integer2) {
//                return integer + 10 == integer2;
//            }
//        })
//                .subscribe(booleanSubscriber);


        observable1.map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return integer;
            }
        })
//                .skipUntil(Observable.range(11, 10).delay(500, TimeUnit.MILLISECONDS))
                .skipWhile(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer < 10;
                    }
                })
//                .takeUntil(Observable.range(11, 10).delay(500, TimeUnit.MILLISECONDS))
//                .takeWhile(new Func1<Integer, Boolean>() {
//                    @Override
//                    public Boolean call(Integer integer) {
//                        return integer < 5;
//                    }
//                })
                .subscribe(integerSubscriber);


    }

    private static void testTo() {
        Observable<Integer> range = Observable.range(1, 10);
        BlockingObservable<Integer> blockingObservable = range.toBlocking();
        final Integer integer = blockingObservable
//                .first()
//                .first(new Func1<Integer, Boolean>() {
//                    @Override
//                    public Boolean call(Integer integer) {
//                        return integer > 5;
//                    }
//                })
//                .firstOrDefault(20)
                .firstOrDefault(20, new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer < 0;
                    }
                })
                ;
//        print("" + Observable.empty().toBlocking().firstOrDefault(123));

//        Iterator<Integer> iterator = blockingObservable.getIterator();
//
//        while (iterator.hasNext()) {
//            Integer next = iterator.next();
//            if (4 == next) {
//            }
//        }



//        integer = blockingObservable.first();
//       print("" +  integer);

//        Future<Integer> integerFuture = Observable.just(1).toBlocking().toFuture();
//        Observable.from(integerFuture).subscribe(integerSubscriber);
//        Observable.from(range.toList().toBlocking().toFuture()).subscribe(new MySubscriber<List<Integer>>() {
//            @Override
//            public void onNext(List<Integer> integers) {
//                for (int i = 0; i < integers.size(); i++) {
//                   print("" + integers.get(i));
//                }
//            }
//        });

//        range.toList().subscribe(new MySubscriber<List<Integer>>() {
//            @Override
//            public void onNext(List<Integer> integers) {
//                for (int i = 0; i < integers.size(); i++) {
//                     print("" + integers.get(i));
//                }
//            }
//        });

//        range.toMap(new Func1<Integer, String>() {
//            @Override
//            public String call(Integer integer) {
//                return "key" + integer;
//            }
//        }).subscribe(new MySubscriber<Map<String, Integer>>() {
//            @Override
//            public void onNext(Map<String, Integer> stringIntegerMap) {
//                String s = "";
//
//                Iterator<Map.Entry<String, Integer>> iterator = stringIntegerMap.entrySet().iterator();
//
//                while (iterator.hasNext()) {
//                    Map.Entry<String, Integer> next = iterator.next();
//
//                    s += next.getKey() + "=" + next.getValue() + "\n";
//                }
//
//                print(s);
//            }
//        });

//        Observable.from(array)
//        .toMultimap(new Func1<Integer, String>() {
//            @Override
//            public String call(Integer integer) {
//                return "key" + integer;
//            }
//        }).subscribe(new MySubscriber<Map<String, Collection<Integer>>>() {
//            @Override
//            public void onNext(Map<String, Collection<Integer>> stringCollectionMap) {
//                String s = "";
//
//                Iterator<Map.Entry<String, Collection<Integer>>> iterator = stringCollectionMap.entrySet().iterator();
//
//                while (iterator.hasNext()) {
//                    Map.Entry<String, Collection<Integer>> next = iterator.next();
//
//
//                    Collection<Integer> value = next.getValue();
//
//                    s += next.getKey() + "={";
//                    if (null != value && !value.isEmpty()) {
//                        for (Integer i :
//                                value) {
//                            s += i + ",";
//                        }
//                    }
//                    s += "}\n"
//                    ;
//                }
//
//                print(s);
//            }
//        });

//        Observable.from(array)
////                .toSortedList()
////                .toSortedList(3)
//                .toSortedList(new Func2<Integer, Integer, Integer>() {
//                    @Override
//                    public Integer call(Integer integer, Integer integer2) {
//                        return integer2 - integer;
//                    }
//                })
//                .subscribe(new MySubscriber<List<Integer>>() {
//                    @Override
//                    public void onNext(List<Integer> integers) {
//                        for (int i = 0; i < integers.size(); i++) {
//                            print("" + integers.get(i));
//                        }
//                    }});

        Observable.just(323)
        .nest()
        .subscribe(new MySubscriber<Observable<Integer>>() {
            @Override
            public void onNext(Observable<Integer> integerObservable) {
                integerObservable.subscribe(integerSubscriber);
            }
        })
        ;



    }

    private static void testUsing() {
        Observable.using(new Func0<Integer>() {
                             @Override
                             public Integer call() {
                                 return 122;
                             }
                         },
                new Func1<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> call(Integer integer) {
                        print("Func1 " + integer);
//                        return Observable.just("Func1->" + integer);
                        return Observable.error(new Error("Error " + integer));
                    }
                }
                ,
                new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        print("release " + integer);
                    }
                }
        ).subscribe(stringSubscriber);

    }

    private static void testTimestamp() {
        Observable.range(1, 10)
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        try {
                            Thread.sleep(100 * integer);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return integer;
                    }
                })
                .timestamp(Schedulers.computation())
                .subscribe(new MySubscriber<Timestamped<Integer>>() {
                    @Override
                    public void onNext(Timestamped<Integer> integerTimestamped) {
                        print("onNext " + integerTimestamped);
                    }
                });
    }

    private static void testTimeOut() {
        Observable<Integer> range = Observable.range(1, 10).flatMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(final Integer integer) {
                return Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        try {
                            Thread.sleep(integer *  500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        subscriber.onNext(integer);
                    }
                });
            }
        });

        range
//                .timeout(3000, TimeUnit.MILLISECONDS)
//                .timeout(3000, TimeUnit.MILLISECONDS, Observable.range(11, 10))
                .timeout(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(final Integer integer) {
                        return Observable.create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {

                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                print("Func1 call " + integer);
                                subscriber.onNext(integer);


                            }
                        });
                    }
                }
//                        , Observable.range(11, 10)
                )
                .observeOn(Schedulers.immediate())
                .subscribe(integerSubscriber);
    }

    private static void testTimeInterval() {
        Observable<Integer> range = Observable.range(1, 10).flatMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(final Integer integer) {
                return Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        subscriber.onNext(integer);
                    }
                });
            }
        });

        print("before sleep");

        range.timeInterval().subscribe(new MySubscriber<TimeInterval<Integer>>() {
            @Override
            public void onNext(TimeInterval<Integer> integerTimeInterval) {

                print("onNext : " + integerTimeInterval.toString());
            }
        });
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

    static class MyCollectionClass<T> {
        List<T> list;

        public MyCollectionClass(){
            list = new ArrayList<>();
        }

        public MyCollectionClass(T t) {
            list = new ArrayList<>();
            list.add(t);
        }

        public void add(T t) {
            list.add(t);
        }

        public void remove(T t) {
            list.remove(t);
        }

        public List<T> getList() {
            return list;
        }

        public void setList(List<T> list) {
            this.list = list;
        }
    }
    static class MyClass<T> {
        T value;

        public MyClass() {
        }
        public MyClass(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "value = " + value;
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
