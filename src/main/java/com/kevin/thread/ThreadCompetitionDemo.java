package com.kevin.thread;

/**
 * 多线程共享资源问题
 * @author lzk
 */
public class ThreadCompetitionDemo {
    static int count = 0;
    public static void main(String [] args) throws InterruptedException{
        long start = System.currentTimeMillis();
        new Thread(){
            @Override
            public void run () {
                for(int i = 0; i < 5000000; i++) {
                    synchronized (ThreadCompetitionDemo.class){
                        count ++;
                    }
                }
                System.out.println("自定义线程:计算完成...，耗时"+(System.currentTimeMillis()-start));
            }
        }.start();
        for(int i = 0; i < 5000000; i++) {
            synchronized (ThreadCompetitionDemo.class){
                count ++;
            }
        }
        System.out.println("主线程:计算完成....，耗时"+(System.currentTimeMillis()-start));
        Thread.sleep(10000);
        System.out.println("count:"+count);
    }
}
