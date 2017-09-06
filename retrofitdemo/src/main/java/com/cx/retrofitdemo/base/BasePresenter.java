package com.cx.retrofitdemo.base;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/9/6.
 */

public class BasePresenter<V>{

    public V mvpView;
    private CompositeSubscription mComposite;

    //依附于View
    public void attachView(V mvpView){
        this.mvpView=mvpView;
    }

    //分离View
    public void detachView(){
        this.mvpView=null;
    }

    //RxJava 取消注册，以避免内存泄漏
    public void onUnsubscribe(){
        if(mComposite !=null && mComposite.hasSubscriptions()){
            mComposite.unsubscribe();
        }
    }

    /**
     * RxJava 的一些基础概念
     * @param observable
     * @param subscriber
     * @param <T>
     *
     *     RxJava 有4个基本概念：
     *          Observable(可观察者，即被观察者)
     *          Observer（观察者）
     *          subscribe(订阅)事件
     *          Observable 和Observer通过subscribe()方法实现订阅关系
     *          从而Observable 可以在需要的时候发出事件(subscribe)来通知Observerr(观察者)
     *
     *     RxJava 基本实现
     *     1.创建Observer（观察者），除了Observer接口之外，RxJava还内置了一个实现Observer的抽象类Subscriber，两者功能差不多，都可被当成观察者
     *     2.创建Observable（被观察者），它决定什么时候出发事件，以及触发怎样的事件，创建Observable的同时，会传入一个OnSubscribe对象作为参数
     *             OnSubscribe 会被存储在返回的 Observable 对象中，它的作用相当于一个计划表，当 Observable 被订阅的时候，
     *             OnSubscribe 的 call() 方法会自动被调用
     *     3.订阅，等创建了Observable（被观察者）和Observer（观察者）之后，再用subscribe（）方法，将两个对象关联
     *          observable.subscribe(observer)
     *          或者：
     *          observable.subscribe(subscribe)
     *
     *     4.Schedulers 调度器
     *     有了这几个 Scheduler ，就可以使用 subscribeOn() 和 observeOn() 两个方法来对线程进行控制了。
     *     * subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。
     *     * observeOn(): 指定 Subscriber或Observer所运行在的线程。或者叫做事件消费的线程。
     */

    //添加注册mComposite
    public <T> void addSubscribe(Observable<T> observable, Subscriber<T> subscriber){
        if(mComposite==null){
            mComposite=new CompositeSubscription();
        }
        mComposite.add(observable
                    .subscribeOn(Schedulers.io())     //指定subscribe（）订阅处于io线程
                    .observeOn(AndroidSchedulers.mainThread())  //指定observer或subscriber处于主线程
                    .subscribe(subscriber)); //
    }
}
