package com.aograph.androidtest;

/**
 * @author tangqipeng
 * @date 2020/8/10 11:53 AM
 * @email tangqipeng@aograph.com
 */
public abstract class AbstractObservableWithUpStream<T, R> extends Observable<R> {

    protected final ObservableSource<T> source;

    public AbstractObservableWithUpStream(ObservableSource<T> source) {
        this.source = source;
    }

//    @Override
//    protected void subscribleActual(Observer<R> observer) {
//
//    }
}
