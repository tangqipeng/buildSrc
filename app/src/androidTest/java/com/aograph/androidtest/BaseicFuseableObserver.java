package com.aograph.androidtest;

/**
 * @author tangqipeng
 * @date 2020/8/10 12:01 PM
 * @email tangqipeng@aograph.com
 */
public abstract class BaseicFuseableObserver<T, R> implements Observer<T> {

    protected final Observer<R> actual;

    public BaseicFuseableObserver(Observer<R> actual) {
        this.actual = actual;
    }

    @Override
    public void onSubscribe() {
        actual.onSubscribe();
    }

    @Override
    public void onError(Throwable e) {
        actual.onError(e);
    }

    @Override
    public void onCompleted() {
        actual.onCompleted();
    }
}
