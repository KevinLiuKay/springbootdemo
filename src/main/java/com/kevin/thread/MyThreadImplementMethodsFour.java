package com.kevin.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ExecutorService、Callable、Future实现有返回结果的多线程
 * @author lzk
 */
@SuppressWarnings("unchecked")
public class MyThreadImplementMethodsFour  {
    public static void main(String[] args) {
        // 创建一个线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        // 创建多个有返回值的任务
        List<Future> futureList = new ArrayList<Future>();
        for (int i = 0; i <5; i++) {
            Callable task = new TestCallable("wq"+i, "phone"+i);
            Future<Object> f = threadPool.submit(task);
            futureList.add(f);
        }
        // 关闭线程池
        threadPool.shutdown();
        // 获取所有并发任务的运行结果
        for (Future future : futureList) {
            // 从Future对象上获取任务的返回值，并输出到控制台
            try{
                System.out.println(future.get().toString());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class TestCallable implements Callable<Object> {
    private String name;
    private String phone;

    TestCallable(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Object call() throws Exception {
        System.out.println(name + "----" + phone);
        return name + "--return--" + phone;
    }
}
/**
 * 上述代码中Executors类，提供了一系列工厂方法用于创建线程池，返回的线程池都实现了ExecutorService接口。
 * public static ExecutorService newFixedThreadPool(int nThreads)
 * 创建固定数目线程的线程池。
 * public static ExecutorService newCachedThreadPool()
 * 创建一个可缓存的线程池，调用execute 将重用以前构造的线程（如果线程可用）。如果现有线程没有可用的，则创建一个新线程并添加到池中。终止并从缓存中移除那些已有 60 秒钟未被使用的线程。
 * public static ExecutorService newSingleThreadExecutor()
 * 创建一个单线程化的Executor。
 * public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize)
 * 创建一个支持定时及周期性的任务执行的线程池，多数情况下可用来替代Timer类。
 * ExecutoreService提供了submit()方法，传递一个Callable，或Runnable，返回Future。如果Executor后台线程池还没有完成Callable的计算，这调用返回Future对象的get()方法，会阻塞直到计算完成。
 */

