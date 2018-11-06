package com.kevin.thread;

/**
 * 继承Thread类创建线程
 * @author lzk
 */
public class MyThreadImplementMethodsOne extends Thread {
    @Override
    public void run () {
        System.out.println("MyThreadImplementMethodsOne running");
    }
    public static void main(String[] args) {
        MyThreadImplementMethodsOne t1 = new MyThreadImplementMethodsOne();
        t1.start();
    }
}
