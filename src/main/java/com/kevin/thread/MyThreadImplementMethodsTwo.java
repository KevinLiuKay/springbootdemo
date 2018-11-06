package com.kevin.thread;

/**
 * 实现Runnable接口创建线程
 * @author lzk
 */
public class MyThreadImplementMethodsTwo implements Runnable{
    @Override
    public void run() {
        System.out.println("MyThreadImplementMethodsTwo running");
    }
    public static void main(String[] args) {
        MyThreadImplementMethodsTwo myThreadImplementMethodsTwo = new MyThreadImplementMethodsTwo();
        Thread thread = new Thread(myThreadImplementMethodsTwo);
        thread.start();
    }
}
