package com.aograph.androidtest;

/**
 * @author tangqipeng
 * @date 2020/8/10 10:36 AM
 * @email tangqipeng@aograph.com
 */
public interface ObservableOnSubscrible<T> {
    //为每个订阅的观察者提供一个能进行消息发送的功能
    void subscrible(Emitter<T> emitter) throws Exception;

}
