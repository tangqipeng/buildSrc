package com.aograph.androidtest;

/**
 * @author tangqipeng
 * @date 2020/8/10 10:20 AM
 * @email tangqipeng@aograph.com
 */

/**
 * 具体的被观察者
 */
public abstract class Observable<T> implements ObservableSource {

    @Override
    public void subscribe(Observer observer) {
        subscribleActual(observer);
    }

    protected abstract void subscribleActual(Observer<T> observer);

    //创造被观察者的实例
    public static<T> Observable create(ObservableOnSubscrible<T> source){
        return new ObservableCreate(source);
    }

    //创建被观察者
    public<U> Observable<U> map(Function<T, U> function){
        return new ObservableMap<>(this, function);
    }
}
