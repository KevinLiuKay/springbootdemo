package com.kevin.thread;

/**
 * @author lzk
 */
public class PriorityDemo {
    public static void main(String [] args){
        System.out.println("最大优先级;"+Thread.MAX_PRIORITY);
        System.out.println("正常优先级;"+Thread.NORM_PRIORITY);
        System.out.println("最小优先级;"+Thread.MIN_PRIORITY);
        System.out.println("主线程优先级;"+Thread.currentThread().getPriority());
        Thread thread = new Thread();
        System.out.println("创建一个线程默认的优先级:"+thread.getPriority());
    }
}
/**
 * 运行结果：
 * 最大优先级;10
 * 正常优先级;5
 * 最小优先级;1
 * 主线程优先级;5
 * 创建一个线程默认的优先级:5
 */