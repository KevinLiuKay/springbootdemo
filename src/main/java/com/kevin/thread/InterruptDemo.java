package com.kevin.thread;

import java.util.Date;

/**
 * @author lzk
 */
public class InterruptDemo {
    public static void main(String [] args) throws InterruptedException{
        Thread thread = new Thread(){
            @Override
            public void run() {
                for(int i = 0; i < 10; i++){
                    try{
                        Thread.sleep(4000);
                        System.out.println("自定义线程:当前时间："+new Date().toLocaleString());
                    }catch (InterruptedException e) {//这个异常由sleep方法抛出
                        e.printStackTrace();
                        System.out.println("自定义线程:收到中断信号，总共循环了"+i+"次...");
                        //接受到中断信号时，停止运行
                        return;
                    }
                }
            }
        };
        thread.start();
        //主线程休眠9秒
        Thread.sleep(9000);
        System.out.println("主线程：等待9秒后发送中断信号...");
        thread.interrupt();
    }
}
/**
 * 运行结果：
 * 自定义线程:当前时间：2018-10-11 14:34:30
 * 自定义线程:当前时间：2018-10-11 14:34:34
 * 主线程：等待9秒后发送中断信号...
 * 自定义线程:收到中断信号，总共循环了2次...
 * java.lang.InterruptedException: sleep interrupted
 * 	at java.lang.Thread.sleep(Native Method)
 * 	at com.kevin.thread.InterruptDemo$1.run(InterruptDemo.java:15)
 * 可以看到自定义线程的确是打印出两次当前时间后就停止了运行，
 * 根本原因在于，我们在收到中断信号后，在catch代码中使用了return，结束了方法。
 * 可以尝试将return去掉，这个时候，即使收到了中断信号，也会继续打印10次！
 */