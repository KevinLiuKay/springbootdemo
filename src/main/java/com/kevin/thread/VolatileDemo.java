package com.kevin.thread;

/**
 * @author lzk
 */
public class VolatileDemo {
    volatile static Boolean flag = true;
    public static void main(String [] args){
        //该线程每隔1毫秒，修改一次flag的值
        new Thread(){
            @Override
            public void run() {
                try {
                    flag=!flag;
                    Thread.sleep(1000);
                    System.out.println(flag);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        //主线程通过死循环不断根据flag进行判断是否要执行某段代码
        while(true){
            if(flag){
                System.out.println("do some thing...");
            }else {
                System.out.println("...");
            }
        }
    }
}
