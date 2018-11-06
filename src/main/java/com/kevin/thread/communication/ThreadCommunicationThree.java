package com.kevin.thread.communication;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程间的通信方式(3.wait/notify机制)
 * @author lzk
 */
public class ThreadCommunicationThree {
    public static void main(String [] args) {
        try{
            Object lock = new Object();
            ThreadC threadC = new ThreadC(lock);
            Thread t1 = new Thread(threadC);
            t1.start();
            Thread.sleep(50);
            ThreadD threadD = new ThreadD(lock);
            Thread t2 = new Thread(threadD);
            t2.start();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class MyCommunicationList{
    private static  List<String> list = new ArrayList<String>();
    public static void addA(int i){
        list.add("Hello"+ i);
        System.out.println(Thread. currentThread().getName()+":"+ list.size());
    }
    public static int listSize(){
        System.out.println(Thread. currentThread().getName()+":"+ list.size());
        return list.size();
    }
}

class ThreadC implements Runnable{
    private Object lock;

    public ThreadC(Object lock){
        super();
        this.lock = lock;
    }
    @Override
    public void run() {
        try{
           synchronized (lock) {
               if(MyCommunicationList.listSize() != 5) {
                   System.out.println("wait begin " + System.currentTimeMillis());
                   lock.wait();
                   System.out.println("wait end  "  + System.currentTimeMillis());
               }
           }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ThreadD implements Runnable{
    private Object lock;

    public ThreadD(Object lock){
        super();
        this.lock = lock;
    }
    @Override
    public void run() {
        try{
            synchronized (lock) {
                for(int i = 0; i < 10; i++) {
                    MyCommunicationList.addA(i);
                    if(MyCommunicationList.listSize() == 5) {
                        lock.notify();
                        System.out.println("已经发出了通知");
                        throw new InterruptedException();
                    }
                    System.out.println("添加了" + (i + 1) + "个元素!");
                    Thread.sleep(1000);
                }
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 线程A要等待某个条件满足时(list.size()==5)，才执行操作。线程B则向list中添加元素，改变list 的size。
 *
 * C,D之间如何通信的呢？也就是说，线程A如何知道 list.size() 已经为5了呢？
 *
 * 这里用到了Object类的 wait() 和 notify() 方法。
 *
 * 当条件未满足时(list.size() !=5)，线程C调用wait() 放弃CPU，并进入阻塞状态。---不像②while轮询那样占用CPU
 *
 * 当条件满足时，线程D调用 notify()通知 线程C，所谓通知线程C，就是唤醒线程C，并让它进入可运行状态。
 *
 * 这种方式的一个好处就是CPU的利用率提高了。
 *
 * 但是也有一些缺点：比如，线程D先执行，一下子添加了5个元素并调用了notify()发送了通知，而此时线程C还执行；当线程C执行并调用wait()时，那它永远就不可能被唤醒了。
 * 因为，线程D已经发了通知了，以后不再发通知了。这说明：通知过早，会打乱程序的执行逻辑。
 */