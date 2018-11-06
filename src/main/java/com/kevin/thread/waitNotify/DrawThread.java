package com.kevin.thread.waitNotify;

/**
 * 模拟用户取钱操作
 * @author lzk
 */
public class DrawThread extends Thread {

    private  Account  account;
    /**
     * 当前取钱线程需要的钱数
     */
    private  double  drawAmount;
    public DrawThread(String  name,Account account,double  drawAmount){
        super(name);
        this.account = account;
        this.drawAmount = drawAmount;
    }
    @Override
    public  void  run(){
        for (int i = 0; i < 100; i++) {
            account.draw(drawAmount);
        }
    }

}
