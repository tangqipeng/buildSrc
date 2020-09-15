package com.aograph.androidtest;

/**
 * @author tangqipeng
 * @date 2020/8/10 10:28 AM
 * @email tangqipeng@aograph.com
 */

/**
 * 发射器，用于发送消息
 */
public interface Emitter<T> {
    //发送消息
    void onNext(T t);
    //发送异常
    void onError(Throwable throwable);
    //发送完成
    void onComplete();

}
