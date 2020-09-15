package com.aograph.androidtest;

/**
 * @author tangqipeng
 * @date 2020/8/10 11:58 AM
 * @email tangqipeng@aograph.com
 */
public class ObservableMap<T, U> extends AbstractObservableWithUpStream<T, U> {

    final Function<T, U> function;

    public ObservableMap(ObservableSource<T> source, Function<T, U> function) {
        super(source);
        this.function = function;
    }

    @Override
    protected void subscribleActual(Observer<U> observer) {
        source.subscribe(new MapObserver<>(observer, function));
    }

    static final class MapObserver<T, U> extends BaseicFuseableObserver<T, U>{

        final Function<T, U> mapper;

        public MapObserver(Observer<U> actual, Function<T, U> mapper) {
            super(actual);
            this.mapper = mapper;
        }

        @Override
        public void onNext(T t) {
            U apply = mapper.apply(t);
            actual.onNext(apply);
        }
    }
}
