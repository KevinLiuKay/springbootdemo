package com.kevin.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author lzk
 */
public class ThreadState {
    public static void main(String[] args) {
        new Thread(new Running(), "RunningThread").start();
        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
        new Thread(new Waiting(), "WaitingThread-1").start();
        new Thread(new Waiting(), "WaitingThread-2").start();
        // 使用两个Blocked线程，一个获取锁成功，另一个被阻塞
        new Thread(new Blocked(), "BlockedThread-1").start();
        new Thread(new Blocked(), "BlockedThread-2").start();
    }

    /**
     * 表示线程正在运行
     */
    static class Running implements Runnable {
        @Override
        public void run() {
            for(int i = 0; i < Long.MAX_VALUE; i++){
                i++;
                System.out.println("Running线程：" + i);
            }
        }
    }

    /**
     * 该线程不断的进行睡眠
     */
    static class TimeWaiting implements Runnable{
        @Override
        public void run() {
            while (true) {
                SleepUtils.second(100);
                System.out.println("当前线程名称：" + Thread.currentThread().getName());
            }
        }
    }

    /**
     * 该线程在Waiting.class实例上等待
     */
    static class Waiting implements Runnable{
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                        System.out.println("当前线程名称：" + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 该线程在Blocked.class实例上加锁后，不会释放该锁
     */
    static class Blocked implements Runnable {
        @Override
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    SleepUtils.second(100);
                    System.out.println("当前线程名称：" + Thread.currentThread().getName());
                }
            }
        }
    }

    public static class SleepUtils {
        public static final void second(long seconds) {
            try {
                TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
            }
        }
    }
}
/**
 * RunningThread线程运行一个不断自增加法，持续时间会很长，线程一直应该处于RUNNABLE状态。
 *
 * TimeWaiting线程里面是一个死循环，每次休眠100毫秒，因此大部分情况下，应该处于TIME-WAITING状态。
 *
 * WaitingThread-1和WaitingThread-2共同竞争一个类锁：Waiting.class。而同步快里面，又调用了wait()方法，先得到锁的线程会释放锁，因此最终2个线程都处于Waiting状态。
 *
 * BlockedThread-1和BlockedThread-2线程也是共同竞争一个类锁：Blocked.class。而同步快里面，是一个死循环，然后调用了SleepUtils.sleep()方法，因此一直不会释放锁。
 * 所以二者，应该是有一个大部分情况下处于Time-Waiting状态，另一个处于Blocked状态。
 */