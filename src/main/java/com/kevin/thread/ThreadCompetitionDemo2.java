package com.kevin.thread;

/**
 * 实例方法同步
 * @author lzk
 */
public class ThreadCompetitionDemo2 {
    public static void main(String [] args) throws InterruptedException{
        //同一个实例
        Counter counter = new Counter();
        CounterThread counterThread = new CounterThread(counter);
        Thread t1 = new Thread(counterThread);
        Thread t2 = new Thread(counterThread);
        t1.start();
        t2.start();
    }
}

/**
 *  计数器
 */
class Counter{
    long count = 0;
    public synchronized void add(long value){
        this.count += value;
        System.out.println(Thread. currentThread().getName()+":"+ count);
    }
}

/**
 * 计数线程
 */
class CounterThread implements Runnable{
    protected Counter counter;
    public CounterThread(Counter counter){
        this.counter = counter;
    }
    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            counter.add(i);
        }
    }
}

/**
 * 注意在方法声明中同步（synchronized ）关键字。这告诉Java该方法是同步的。
 * Java实例方法同步是同步在拥有该方法的对象上。这样，每个实例其方法同步都同步在不同的对象上，即该方法所属的实例。只有一个线程能够在实例方法同步块中运行。
 * 如果有多个实例存在，那么一个线程一次可以在一个实例同步块中执行操作。一个实例一个线程。
 *
 * 注意：多个线程要运行的是同一个对象实例的同步方法，如果一个每个线程运行的是不同的对象实例的同步方法，是没有同步效果的，因为每个对象实例是把自身当成锁，就导致没有公用一个锁。
 */