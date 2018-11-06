package com.kevin.thread;

/**
 * 静态方法中的同步块
 * @author lzk
 */
public class ThreadCompetitionDemo5 {
    public static void main(String [] args) throws InterruptedException{
        CounterThree counterA = new CounterThree();
        CounterThree counterB = new CounterThree();
        CounterThreadThree counterThreadA = new CounterThreadThree(counterA);
        CounterThreadThree counterThreadB = new CounterThreadThree(counterB);
        Thread t1 = new Thread(counterThreadA);
        Thread t2 = new Thread(counterThreadB);
        t1.start();
        t2.start();
    }
}

/**
 *  计数器
 */
class CounterThree{
    static long count = 0;
    public static void add(long value){
        synchronized (CounterThree.class) {
            count += value;
        }
        System. out.println(Thread. currentThread().getName()+":"+ count);
    }
}

/**
 * 计数线程
 */
class CounterThreadThree implements Runnable{
    protected CounterThree counterThree;
    public CounterThreadThree(CounterThree counterThree){
        this.counterThree = counterThree;
    }
    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            CounterThree.add(i);
        }
    }
}
