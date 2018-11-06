package com.kevin.thread;

/**
 * 实例方法中的同步块
 * @author lzk
 */
public class ThreadCompetitionDemo4 {
    public static void main(String [] args) throws InterruptedException{
        CounterTwo counterA = new CounterTwo();
        CounterThreadTwo counterThreadA = new CounterThreadTwo(counterA);
        Thread t1 = new Thread(counterThreadA);
        Thread t2 = new Thread(counterThreadA);
        t1.start();
        t2.start();
    }
}
/**
 *  计数器
 */
class CounterTwo{
    long count = 0;
    public void add(long value){
        synchronized (this) {
            this.count += value;
        }
        System. out.println(Thread. currentThread().getName()+":"+ count);
    }
}

/**
 * 计数线程
 */
class CounterThreadTwo implements Runnable{
    protected CounterTwo counterTwo;
    public CounterThreadTwo(CounterTwo counterTwo){
        this.counterTwo = counterTwo;
    }
    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            counterTwo.add(i);
        }
    }
}
