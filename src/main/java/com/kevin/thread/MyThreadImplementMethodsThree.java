package com.kevin.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 实现Callable接口通过FutureTask包装器来创建Thread线程
 * @author lzk
 */
public class MyThreadImplementMethodsThree implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for(int i = 0; i <= 100; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
            sum += i;
        }
        return sum;
    }
    public static void main(String [] args) {
        MyThreadImplementMethodsThree myThreadImplementMethodsThree = new MyThreadImplementMethodsThree();
        // 1.执行Callable方式，需要FutureTask实现类的支持，用于接收运算结果
        FutureTask<Integer> result = new FutureTask<>(myThreadImplementMethodsThree);
        new Thread(result).start();
        // 2.接收线程运算后的结果
        Integer sum;
        try{
            //等所有线程执行完，获取值，因此FutureTask 可用于闭锁
            sum = result.get();
            System.out.println("-----------------------------");
            System.out.println(sum);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
