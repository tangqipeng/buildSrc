package com.aograph.androidtest;

/**
 * @author tangqipeng
 * @date 2020/8/10 10:23 AM
 * @email tangqipeng@aograph.com
 */
public class ObservableCreate<T> extends Observable {

    private ObservableOnSubscrible<T> source;

    public ObservableCreate(ObservableOnSubscrible<T> source) {
        this.source = source;
    }

    @Override
    public void subscribe(Observer observer) {
        super.subscribe(observer);
    }

    @Override
    protected void subscribleActual(Observer observer) {
        //observer.onNext();
        try {
            CreateEmitter<T> parent = new CreateEmitter<>(observer);
            observer.onSubscribe();
            source.subscrible(parent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static final class CreateEmitter<T> implements Emitter<T> {

        Observer<T> observer;

        public CreateEmitter(Observer<T> observer) {
            this.observer = observer;
        }

        @Override
        public void onNext(T t) {
            observer.onNext(t);
        }

        @Override
        public void onError(Throwable throwable) {
            observer.onError(throwable);
        }

        @Override
        public void onComplete() {
            observer.onCompleted();
        }
    }

}
