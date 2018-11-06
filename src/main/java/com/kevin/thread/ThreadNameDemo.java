package com.kevin.thread;

/**
 * @author lzk
 */
public class ThreadNameDemo {
    public static void main(String [] args){
        //打印主线程的名称
        System.out.println("主线程名称:" + Thread.currentThread().getName());
        //创建一个线程，不指定名字，JVM会自动给其分配一个名字
        new Thread(){
          @Override
          public void run() {
              //如果运行时代码本身就是在一个线程对象中实现，那么我们可以通过this.getName获取其名称
              System.out.println("JVM自动分配的线程名:"+this.getName());
          }
        }.start();
        //创建一个名字为new Thread的线程并启动
        new Thread(new MyRunable(),"new Thread").start();
    }
    static class MyRunable implements Runnable {

        @Override
        public void run() {
            //如果我们编写的只是运行时代码，那么要获取将会执行这段运行时代码的线程的信息，通过Thread.currentThread()的方式
            System.out.println("执行这段运行时代码的线程名:"+Thread.currentThread().getName());
        }
    }
}
