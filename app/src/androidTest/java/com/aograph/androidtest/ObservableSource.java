package com.aograph.androidtest;

/**
 * @author tangqipeng
 * @date 2020/8/10 10:18 AM
 * @email tangqipeng@aograph.com
 */

/**
 * 抽象被观察者
 */
public interface ObservableSource<T> {

    void subscribe(Observer<T> observer);

}
