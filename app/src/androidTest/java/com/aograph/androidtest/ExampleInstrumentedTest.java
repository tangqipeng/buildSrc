package com.aograph.androidtest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.aograph.agent.observer.base.Observable;
import com.aograph.agent.observer.listener.Emitter;
import com.aograph.agent.observer.listener.IObserver;
import com.aograph.agent.observer.listener.ObservableOnSubscrible;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.aograph.androidtest", appContext.getPackageName());

        String a = "232";
        a = addZeroForString(a, 8);
        System.out.println(a);

//        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//
//        Map<String,String> map = new HashMap<>();
//        map.put("a", "1");
//        System.out.println("map is "+map);
//        for (String str : map.keySet()){
//            System.out.println("str is "+str);
//        }
//        System.out.println("iterator is "+ map.get(Arrays.asList(map.keySet()).get(0)));
//        JSONObject jsonObject = new JSONObject(map);
//        System.out.println("jsonObject is "+jsonObject);
//        map.clear();
//        System.out.println("jsonObject1 is "+jsonObject);
//        Observable.create(new ObservableOnSubscrible<Object>() {
//
//            @Override
//            public void subscrible(Emitter<Object> emitter) throws Exception {
//                System.out.println("1111111");
//                emitter.onNext(2);
//            }
//        }).delay(2000, TimeUnit.MILLISECONDS, scheduler).subscribe(new IObserver<Object>() {
//
//            @Override
//            public void onSubscribe() {
//
//            }
//
//            @Override
//            public void onNext(Object o) throws Exception {
//                System.out.println("1111111"+o);
//            }
//
//            @Override
//            public void onError(String error) {
//
//            }
//
//            @Override
//            public void onCompleted(ScheduledFuture<?> future) {
//
//            }
//        });


//        String a = "s";
//        List<String> list = new ArrayList<>();
//        list.add(a);
//        a = "";
//        System.out.println(list);
//
//        Observable.create(new ObservableOnSubscrible<String>() {
//            @Override
//            public void subscrible(Emitter<String> emitter) throws Exception {
//                emitter.onNext("你");
//            }
//        }).map(new Function() {
//            @Override
//            public Object apply(Object o) {
//                return o+"好";
//            }
//        }).map(new Function() {
//            @Override
//            public Object apply(Object o) {
//                return o+"牛";
//            }
//        }).map(new Function() {
//            @Override
//            public Object apply(Object o) {
//                return o+"逼";
//            }
//        }).map(new Function() {
//            @Override
//            public Object apply(Object o) {
//                return o+"啊";
//            }
//        }).subscribe(new Observer() {
//            @Override
//            public void onSubscribe() {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//                System.out.println("消息="+o.toString());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onCompleted() {
//
//            }
//        });


//        final Ta ta = new Ta();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                testA();
//            }
//        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                testB();
//            }
//        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                testC();
//            }
//        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                testD();
//            }
//        }).start();
//        System.out.println("234234234234234");
    }

    private ReadWriteLock lock = new ReentrantReadWriteLock(true);

    public void testA(){
        try {
            lock.readLock().lock();
            Thread.sleep(5000);
            System.out.println("a");
        }catch (Exception e){

        }finally {
            lock.readLock().unlock();
        }
    }

    public void testB(){
        try {
            lock.readLock().lock();
            Thread.sleep(1000);
            System.out.println("b");
        }catch (Exception e){}finally {
            lock.readLock().unlock();
        }
    }

    public void testC(){
        try {
            lock.readLock().lock();
            Thread.sleep(2000);
            System.out.println("c");
        }catch (Exception e){}finally {
            lock.readLock().unlock();
        }
    }

    public void testD(){
        try {
            lock.writeLock().lock();
            System.out.println("d");
        }catch (Exception e){

        }finally {
            lock.writeLock().unlock();
        }
    }

    public static String addZeroForString(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            StringBuilder strBuilder = new StringBuilder(str);
            while (strLen < strLength) {
                strBuilder.insert(0, "0");// 左补0
                strLen = strBuilder.length();
            }
            str = strBuilder.toString();
        }

        return str;
    }


    static class Ta {

        private ReadWriteLock lock = new ReentrantReadWriteLock(true);

        public void testA(){
            try {
                lock.readLock().lock();
                Thread.sleep(5000);
                System.out.println("a");
            }catch (Exception e){}finally {
                lock.readLock().unlock();
            }
        }

        public void testB(){
            try {
                lock.readLock().lock();
                Thread.sleep(1000);
                System.out.println("b");
            }catch (Exception e){}finally {
                lock.readLock().unlock();
            }
        }

        public void testC(){
            try {
                lock.readLock().lock();
                Thread.sleep(2000);
                System.out.println("c");
            }catch (Exception e){}finally {
                lock.readLock().unlock();
            }
        }

        public void testD(){
            try {
                lock.writeLock().lock();
                System.out.println("d");
            }catch (Exception e){}finally {
                lock.writeLock().unlock();
            }
        }

//        public static void main(String[] args) {
//            final Ta ta = new Ta();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    ta.testA();
//                }
//            }).start();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    ta.testB();
//                }
//            }).start();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    ta.testC();
//                }
//            }).start();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    ta.testD();
//                }
//            }).start();
//        }


    }
}
