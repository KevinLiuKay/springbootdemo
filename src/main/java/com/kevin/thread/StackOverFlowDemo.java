package com.kevin.thread;

/**
 * 栈内存溢出，解决方案见SpinLockDemo
 * @author lzk
 */
public class StackOverFlowDemo {
    public static  void main(String [] args){
        StackOverFlowDemo stackOverFlowDemo = new StackOverFlowDemo();
        stackOverFlowDemo.recursiveMethod();
        System.out.println("执行其他代码...");
    }
    int count = 0;
    public void recursiveMethod () {
        if(count == 1000000) {
            return;
        }
        count ++;
        System.out.println("执行了:"+count+"次");
        //递归调用
        recursiveMethod();
    }
}
