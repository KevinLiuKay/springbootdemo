package com.kevin.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author lzk
 */
public class DaemonThreadDemo2 {
    private static int queueCapacity = 10;
    private static BlockingQueue<String> logQueue = new ArrayBlockingQueue<>(queueCapacity);
    public static void main(String [] args) {
        LogWriter writer = new LogWriter();
        LogCleaner cleaner = new LogCleaner();
        Thread t1 = new Thread(writer);
        Thread t2 = new Thread(cleaner);
        t2.setDaemon(true);
        t1.start();
        t2.start();
    }

    /**
     * 模拟不停写日志（直到队列写满）
     */
    public static class LogWriter implements Runnable{

        @Override
        public void run() {
            for(int i = 0; i < queueCapacity; i++) {
                try {
                    logQueue.put("" + i);
                    System.out.println("日志已写入，当前日志内容：" + logQueue);
                    Thread.sleep(500);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 模拟在空闲时清理日志（仅保留5条日志）
     */
    public static class LogCleaner implements Runnable{

        @Override
        public void run() {
            while(true) {
                if(logQueue.size() > 5) {
                    try{
                        logQueue.take();
                        System.out.println("多余日志被清理，当前日志内容：" + logQueue);
                        Thread.sleep(500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
