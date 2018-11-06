package com.kevin.thread.communication;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程间的通信方式(2.while轮询的方式)
 * @author lzk
 */
public class ThreadCommunicationTwo {
    public static void main(String [] args) {
        MyList myList = new MyList();
        ThreadA threadA = new ThreadA(myList);
        ThreadB threadB = new ThreadB(myList);
        Thread t1 = new Thread(threadA);
        Thread t2 = new Thread(threadB);
        t1.setName("A");
        t1.start();
        t2.setName("B");
        t2.start();
    }
}

class MyList{
    private List<String> list = new ArrayList<String>();
    public void addA(int i){
        list.add("Hello"+ i);
        System.out.println(Thread. currentThread().getName()+":"+ list.size());
    }
    public int listSize(){
        System.out.println(Thread. currentThread().getName()+":"+ list.size());
        return list.size();
    }
}

class ThreadA implements Runnable{
    private MyList myList;

    public ThreadA(MyList myList){
        this.myList = myList;
    }
    @Override
    public void run() {
        try{
            for(int i = 0; i < 10; i++) {
                myList.addA(i);
                Thread.sleep(1000);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ThreadB implements Runnable{
    private MyList myList;

    public ThreadB(MyList myList){
        this.myList = myList;
    }
    @Override
    public void run() {
      try{
        while(true) {
            if(myList.listSize() == 5) {
                System.out.println("==5, 线程b准备退出了");
                throw new InterruptedException();
            }
        }
      }catch (InterruptedException e) {
          e.printStackTrace();
      }
    }
}

/**
 * 在这种方式下，线程A不断地改变条件，线程ThreadB不停地通过while语句检测这个条件(list.size()==5)是否成立 ，从而实现了线程间的通信。
 * 但是这种方式会浪费CPU资源。之所以说它浪费资源，是因为JVM调度器将CPU交给线程B执行时，它没做啥“有用”的工作，只是在不断地测试 某个条件是否成立。
 * 就类似于现实生活中，某个人一直看着手机屏幕是否有电话来了，而不是： 在干别的事情，当有电话来时，响铃通知TA电话来了。关于线程的轮询的影响
 */