package com.kevin.thread;

import java.util.Date;

/**
 * @author lzk
 */
public class SleepDemo {
    public static void main(String [] args) {
        new Thread() {
            @Override
            public void run() {
                //我们希望不断的去检查服务器的状态，所以讲将检查的代码放入一个死循环中
                while (true) {
                    //用打印一句话表示检查服务器状态
                    System.out.println("检查服务器状态....当前时间:"+ new Date().toLocaleString());
                    try{
                        //休眠3秒，以免检查台频繁
                        sleep(3000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
/**
 * 注意sleep方法抛出了一个InterruptedException异常。
 * 这个异常是在线程还在休眠的时候，如果其他的线程中断(interrupt)了这个线程的执行抛出的。
 * 中断的作用是，如果一个线程在运行期，我们不想其继续运行下去了，就可以给其发送一个信号，让其停止运行。
 * 注意中断和休眠的区别，中断是让线程停止运行，而休眠只是暂停运行，等到休眠时间过后，可以继续执行
 */
