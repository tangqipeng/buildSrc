package com.aograph.androidtest;

/**
 * @author tangqipeng
 * @date 2020/8/10 11:50 AM
 * @email tangqipeng@aograph.com
 */
public interface Function<T,R> {
    //对输入的数据t进行一些操作，然后向下传递
    R apply(T t);
}
