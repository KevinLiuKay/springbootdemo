package com.kevin.thread;

/**
 * 静态方法同步
 * @author lzk
 */
public class ThreadCompetitionDemo3 {
    public static void main(String [] args) throws InterruptedException{
        CounterOne counterA = new CounterOne();
        CounterOne counterB = new CounterOne();
        CounterThreadOne counterThreadA = new CounterThreadOne(counterA);
        CounterThreadOne counterThreadB = new CounterThreadOne(counterB);
        Thread t1 = new Thread(counterThreadA);
        Thread t2 = new Thread(counterThreadB);
        t1.start();
        t2.start();
    }
}

/**
 *  计数器
 */
class CounterOne{
    static long count = 0;
    public static synchronized void add(long value){
        count += value;
        System. out.println(Thread. currentThread().getName()+":"+ count);
    }
}

/**
 * 计数线程
 */
class CounterThreadOne implements Runnable{
    protected CounterOne counterOne;
    public CounterThreadOne(CounterOne counterOne){
        this.counterOne = counterOne;
    }
    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            CounterOne.add(i);
        }
    }
}
/**
 * 静态方法的同步是指同步在该方法所在的类对象上。因为在Java虚拟机中一个类只能对应一个类对象，所以同时只允许一个线程执行同一个类中的静态同步方法。
 *
 * 对于不同类中的静态同步方法，一个线程可以执行每个类中的静态同步方法而无需等待。不管类中的那个静态同步方法被调用，一个类只能由一个线程同时执行。
 */