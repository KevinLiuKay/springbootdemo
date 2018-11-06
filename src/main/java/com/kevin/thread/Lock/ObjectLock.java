package com.kevin.thread.Lock;

/**
 * 编写一个启动类ObjectLock
 * @author lzk
 */
public class ObjectLock {
    public static void main(String [] args) {
        System.out.println("start time = " + System.currentTimeMillis()+"ms");
        LockTestClass test = new LockTestClass();
        for (int i = 0; i < 3; i++) {
            Thread thread = new ObjThread(test, i);
            thread.start();
        }
    }
}
