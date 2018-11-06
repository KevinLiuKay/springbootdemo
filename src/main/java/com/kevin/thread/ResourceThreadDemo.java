package com.kevin.thread;

/**
 * @author lzk
 */
public class ResourceThreadDemo {
    public static void main(String[] args)throws InterruptedException{
        long start = System.currentTimeMillis();
        System.out.println("----------程序开始运行---------");
        System.out.println("读取A文件开始...");
        Thread.sleep(5000);
        System.out.println("读取A文件结束，耗时："+(System.currentTimeMillis()-start)/1000+"秒...开始处理A文件，同时开始读取B文件..");
        Thread thread = new Thread() {
            @Override
            public void run() {
                try{
                    System.out.println("读取B文件开始...");
                    Thread.sleep(5000);
                    System.out.println("读取B文件结束，耗时："+(System.currentTimeMillis()-start)/1000+"秒...开始处理B文件");
                    Thread.sleep(2000);
                    System.out.println("B文件处理完成...");
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        Thread.sleep(2000);
        System.out.println("A文件处理完成...");
        thread.join();
        System.out.println("总耗时:"+(System.currentTimeMillis()-start)/1000+"秒");
    }
}
/**
 * ----------程序开始运行---------
 * 读取A文件开始...
 * 读取A文件结束，耗时：5秒...开始处理A文件，同时开始读取B文件..
 * 读取B文件开始...
 * A文件处理完成...
 * 读取B文件结束，耗时：10秒...开始处理B文件
 * B文件处理完成...
 * 总耗时:12秒
 */
