package com.kevin.thread;

/**
 * @author lzk
 */
public class ResourceDemo {
    public static void main(String [] args)throws InterruptedException{
        long start = System.currentTimeMillis();
        System.out.println("----------程序开始运行---------");
        System.out.println("读取A文件开始...");
        Thread.sleep(5000);
        System.out.println("读取A文件结束，耗时："+(System.currentTimeMillis()-start)/1000+"秒...开始处理A文件");
        Thread.sleep(2000);
        System.out.println("A文件处理完成...，耗时："+(System.currentTimeMillis()-start)/1000+"秒");
        System.out.println("读取B文件开始...");
        Thread.sleep(5000);
        System.out.println("读取B文件结束，耗时："+(System.currentTimeMillis()-start)/1000+"秒...开始处理B文件");
        Thread.sleep(2000);
        System.out.println("B文件处理完成...，耗时："+(System.currentTimeMillis()-start)/1000+"秒");
    }
}
/**
 *----------程序开始运行---------
 * 读取A文件开始...
 * 读取A文件结束，耗时：5秒...开始处理A文件
 * A文件处理完成...，耗时：7秒
 * 读取B文件开始...
 * 读取B文件结束，耗时：12秒...开始处理B文件
 * B文件处理完成...，耗时：14秒
 */
/**
 * Java中Thread对象有一个优先级的概念，优先级被划分10个级别，创建线程的时候，如果没有指定优先级，默认是5。主线程的优先级也是5。优先级高的线程会比优先级低的线程获得更多的运行机会。
 * Thread类定义了3个整形常量MAX_PRIORITY、NORM_PRIORITY、MIN_PRIORITY分别用于表示支持的最高优先级，正常优先级和最低优先级。同时提供了一个getPriority()方法来获取当前线程优先级。
 */