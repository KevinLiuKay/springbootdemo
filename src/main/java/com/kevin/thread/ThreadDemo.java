package com.kevin.thread;

/**
 * 演示多个线程可以并发执行的案例
 * @author lzk
 */
public class ThreadDemo {
    public static void main(String [] args) {
        //创建一个线程对象，覆盖其run方法，传入参数为线程的名字
        Thread thread = new Thread() {
            @Override
            public void run() {
                for(int i = 0; i < 100; i++) {
                    System.out.println("自定义线程循环："+i+"次");
                }
            }
        };
        //调用start方法启动线程
        thread.start();
        for (int i = 1; i <=100 ; i++) {
            System.out.println("主线程循环："+i+"次");
        }
    }
}
/**
 * start()方法与run()方法的区别
 * Thread对象run方法与start()方法的作用不同，start()方法用于启动线程，run()方法用于执行线程的运行时代码。通过start方法启动线程，然后JVM会回调线程的运行时代码。
 * 当我们new出一个Thread对象的时候，其仅仅只是Java中的一个对象而已。只有当我们调用了start方法的时候，其才会真正的成为一个运行的线程，也只有当线程启动后，JVM才会给线程分配相应的资源，例如栈内存空间。
 * 而run方法中的只是线程的运行时代码而已，也就是这个线程要干的事。
 */