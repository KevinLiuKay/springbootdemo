package com.kevin.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * java程序启动至少会启动几个线程
 * @author lzk
 */
public class ThreadNumDemo {
    public static void main(String[] args) {
        ThreadMXBean threadMXBean =ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos=threadMXBean.dumpAllThreads(false,false);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(threadInfo.getThreadId()+"-"+threadInfo.getThreadName());
        }
    }
}
/**
 * 运行结果
 * 6-Monitor Ctrl-Break //堆栈跟踪
 * 5-Attach Listener    //添加事件
 * 4-Signal Dispatcher  //分发处理发送给JVM信号的线程
 * 3-Finalizer  //调用对象的finalize方法的线程，就是垃圾回收的线程
 * 2-Reference Handler   //清除reference的线程
 * 1-main //主线程
 */
