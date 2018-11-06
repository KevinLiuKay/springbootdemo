package com.kevin.thread;

/**
 * 自旋锁解决StackOverflowError案例
 * @author lzk
 */
public class SpinLockDemo {
    public static  void main(String [] args){
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        //这段代码其实就是一个自旋锁
        while(spinLockDemo.count != 1000000){
            spinLockDemo.incr();
        }
        System.out.println("执行其他代码...");
    }
    int count = 0;
    public void incr () {
        count ++;
        System.out.println("执行了:"+count+"次");
    }
}
