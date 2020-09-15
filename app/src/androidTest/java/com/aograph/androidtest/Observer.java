package com.aograph.androidtest;

/**
 * @author tangqipeng
 * @date 2020/8/10 10:15 AM
 * @email tangqipeng@aograph.com
 */

/**
 * 观察者
 * @param <T>
 */
public interface Observer<T> {
    //订阅成功回调
    void onSubscribe();
    //接收消息
    void onNext(T t);
    //订阅出错
    void onError(Throwable e);
    //订阅完成
    void onCompleted();

}
