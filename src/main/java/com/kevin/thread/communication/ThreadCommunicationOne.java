package com.kevin.thread.communication;

/**
 * 线程间的通信方式(1.同步)
 * @author lzk
 */
public class ThreadCommunicationOne {
    public static void main(String [] args){
        Counter counter = new Counter();
        CountThreadA countThreadA = new CountThreadA(counter);
        CountThreadB countThreadB = new CountThreadB(counter);
        Thread t1 = new Thread(countThreadA);
        Thread t2 = new Thread(countThreadB);
        t1.start();
        t2.start();
    }
}

class Counter{
    long count = 0;
    public synchronized void addA(long value){
        this.count += value;
        System.out.println(Thread. currentThread().getName()+":"+ count);
    }
    public synchronized void addB(long value){
        this.count += value;
        System.out.println(Thread. currentThread().getName()+":"+ count);
    }
}


class CountThreadA implements Runnable{
    private Counter counter;

    public CountThreadA(Counter counter){
        this.counter = counter;
    }
    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            counter.addA(i);
        }
    }
}

class CountThreadB implements Runnable{
    private Counter counter;

    public CountThreadB(Counter counter){
        this.counter = counter;
    }
    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            counter.addB(i);
        }
    }
}

/**
 * 由于线程CountThreadA和线程CountThreadB持有同一个Counter类的对象counter，尽管这两个线程需要调用不同的方法，但是它们是同步执行的，
 * 比如：线程CountThreadB需要等待线程CountThreadA执行完了addA()方法之后，它才能执行addB()方法。这样，线程CountThreadA和线程CountThreadB就实现了通信。
 * 这种方式，本质上就是“共享内存”式的通信。多个线程需要访问同一个共享变量，谁拿到了锁（获得了访问权限），谁就可以执行。
 */

