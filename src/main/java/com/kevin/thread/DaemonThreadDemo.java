package com.kevin.thread;

import java.io.*;

/**
 * 守护线程
 * @author lzk
 */
public class DaemonThreadDemo {
    public static void main(String [] args){
        TestRunnable testRunnable = new TestRunnable();
        Thread thread = new Thread(testRunnable);
        //设置守护线程
        thread.setDaemon(true);
        //开始执行分进程
        thread.start();
    }
}
class TestRunnable implements Runnable {

    @Override
    public void run() {
        //1、建立连接
        File file=new File("D:\\test.txt");
        OutputStream os = null;
        try{
            //守护线程阻塞1秒后运行
            Thread.sleep(1000);
            //2、选择输出流,以追加形式(在原有内容上追加) 写出文件 必须为true 否则为覆盖
//            os = new FileOutputStream(file,true);
            //和上一句功能一样，BufferedInputStream是增强流，加上之后能提高输出效率，建议
            os = new BufferedOutputStream(new FileOutputStream(file,true));
            String string = "Hello World!";
            //将字符串转换为字节数组,方便下面写入
            byte[] data = string.getBytes();
            //3、写入文件
            os.write(data,0,data.length);
            //将存储在管道中的数据强制刷新出去
            os.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (InterruptedException e2){
            e2.printStackTrace();
        }finally {
            if(os != null) {
                try {
                    os.close();
                }catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("关闭输出流失败！");
                }
            }
        }
    }
}
/**
 * 运行结果：文件test.txt中没有"Hello World!"字符串。
 * 但是如果把thread.setDaemon(true); //设置守护线程注释掉，文件test.txt是可以被写入Hello World!字符串的
 * JRE判断程序是否执行结束的标准是所有的前台执线程行完毕了，而不管后台线程的状态，因此，在使用后台线程时候一定要注意这个问题。
 */
