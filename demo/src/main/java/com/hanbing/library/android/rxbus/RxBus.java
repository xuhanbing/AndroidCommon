package com.hanbing.library.android.rxbus;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.hanbing.library.android.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;


/**
 * Created by hanbing on 2016/12/15
 */

public class RxBus {


    static RxBus mInstance = new RxBus();

    public static RxBus getInstance() {
        return mInstance;
    }

    final Map<Class<?>, List<Class<?>>> registerTypes;
    final Map<Class<?>, Publisher> registerSubscribers;

    public RxBus() {
        registerTypes = new HashMap<>();
        registerSubscribers = new HashMap<>();
    }


    private boolean isAvailableType(Class type) {
        if (Object.class.equals(type)
                || FragmentActivity.class.equals(type)
                || Activity.class.equals(type)
                || Fragment.class.equals(type)
                || android.support.v4.app.Fragment.class.equals(type))
            return false;
        return true;
    }


    public void register(Object subscriber) {

        Class<?> type = subscriber.getClass();

        if (!isAvailableType(type))
            return;


        List<Class<?>> registerTypes = findTypes(type);

        if (null == registerTypes) {
            registerTypes = new ArrayList<>();
            this.registerTypes.put(type, registerTypes);
        }


        List<Method> methods = findMethods(type);

        if (null != methods && !methods.isEmpty()) {
            for (Method method : methods) {
                Class<?>[] parameterTypes = method.getParameterTypes();

                if (parameterTypes.length == 1) {

                    Class<?> parameterType = parameterTypes[0];

                    Publisher publisher = registerSubscribers.get(parameterType);

                    if (null == publisher) {
                        publisher = new Publisher(PublishSubject.create());
                        registerSubscribers.put(parameterType, publisher);
                    }

                    //添加订阅者
                    publisher.subscribe(new Subscriber(subscriber, method));

                    //添加注册的消息类型
                    if (!registerTypes.contains(parameterType))
                        registerTypes.add(parameterType);

                } else {
                    throw new IllegalArgumentException("@Subscribe method only support 1 argument, current is " + parameterTypes.length);
                }

            }
        }


    }

    private List<Class<?>> findTypes(Class<?> clazz) {
        return registerTypes.get(clazz);
    }

    private List<Method> findMethods(Class<?> clazz) {
        Method[] declaredMethods = clazz.getMethods();
        if (null == declaredMethods || declaredMethods.length == 0)
            return null;

        List<Method> methods = new ArrayList<>();

        for (Method method : declaredMethods) {
            if (null == method)
                continue;

            Subscribe annotation = method.getAnnotation(Subscribe.class);
            if (null != annotation) {
                methods.add(method);
            }
        }

        return methods;
    }

    public void unregister(Object subscriber) {

        Class<?> type = subscriber.getClass();

        if (!isAvailableType(type))
            return;

        List<Class<?>> registerTypes = findTypes(type);

        if (null == registerTypes || registerTypes.isEmpty())
            return;

        List<Method> methods = findMethods(type);

        if (null != methods && !methods.isEmpty()) {
            for (Method method : methods) {
                Class<?>[] parameterTypes = method.getParameterTypes();

                if (parameterTypes.length == 1) {

                    Class<?> parameterType = parameterTypes[0];

                    Publisher publisher = registerSubscribers.get(parameterType);

                    if (null == publisher)
                        continue;


                    publisher.dispose(new Subscriber(subscriber, method));

                } else {
                    throw new IllegalArgumentException("@Subscribe method only support 1 argument, current is " + parameterTypes.length);
                }
            }
        }


    }

    public void post(final Object event) {

        Class<?> type = event.getClass();

        Publisher publisher = registerSubscribers.get(type);

        publisher.publishSubject.onNext(event);

    }


    class Publisher {

        final PublishSubject publishSubject;
        final List<Subscription> observers;

        public Publisher(PublishSubject publishSubject) {
            this.publishSubject = publishSubject;

            observers = new ArrayList<>();
        }

        public void subscribe(Subscriber observer) {

            if (observers.contains(observer)) {
                Subscription observer1 = CollectionUtils.getExist(observers, observer);
                throw new IllegalArgumentException("You can not register method twice. Current is " + observer1);
            }


            Subscription subscribe = publishSubject.subscribe(observer);
            observer.setDisposable(subscribe);

            observers.add(observer);

        }

        public void dispose(Subscriber observer) {
            if (null == observer)
                return;

            Subscription observer1 = CollectionUtils.getExist(observers, observer);
            if (null == observer1) {
                observer.unsubscribe();
                return;
            }

            observer1.unsubscribe();
            observers.remove(observer1);
        }
    }


    class Subscriber implements Subscription, Action1 {
        Object target;
        Method method;

        Subscription disposable;

        public Subscriber(Object target, Method method) {
            this.target = target;
            this.method = method;
        }

        public Subscription getDisposable() {
            return disposable;
        }

        public void setDisposable(Subscription disposable) {
            this.disposable = disposable;
        }

        public void invoke(Object event) {
            try {
                method.invoke(target, event);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Subscriber [target : " + target + ",method : " + method + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Subscriber) {
                if (null == target) {
                    return null == ((Subscriber) o).target && CollectionUtils.equals(method, ((Subscriber) o).method);
                } else if (null == method) {
                    return null == ((Subscriber) o).method && CollectionUtils.equals(target, ((Subscriber) o).target);
                } else {
                    return target.equals(((Subscriber) o).target) && method.equals(((Subscriber) o).method);
                }

            }

            return false;
        }

        @Override
        public int hashCode() {

            int hashCode = 0;

            if (null != target) {
                hashCode = target.hashCode();

                hashCode <<= 16;
            }

            if (null != method) {
                hashCode |= method.hashCode();
            }

            return hashCode;
        }

        @Override
        public void unsubscribe() {
            if (null != disposable) disposable.unsubscribe();
        }

        @Override
        public boolean isUnsubscribed() {
            return null != disposable && disposable.isUnsubscribed();
        }

        @Override
        public void call(Object o) {
            invoke(o);
        }


//        @Override
//        public void accept(Object o) throws Exception {
//            invoke(o);
//        }
//
//
//        @Override
//        public void dispose() {
//            if (null != disposable) disposable.dispose();
//        }
//
//        @Override
//        public boolean isDisposed() {
//            if (null != disposable) return disposable.isDisposed();
//            return false;
//        }
    }

}
