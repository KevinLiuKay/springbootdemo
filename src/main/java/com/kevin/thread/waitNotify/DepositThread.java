package com.kevin.thread.waitNotify;

/**
 * 模拟用户存钱操作
 * @author lzk
 */
public class DepositThread extends Thread {
    private  Account  account;
    /**
     * 当前存钱线程需要的钱数
     */
    private  double  depositAmount;
    public DepositThread(String  name,Account account,double  depositAmount){
        super(name);
        this.account=account;
        this.depositAmount=depositAmount;
    }
    @Override
    public  void  run(){
        for (int i = 0; i < 100; i++) {
            account.deposit(depositAmount);
        }
    }
}
