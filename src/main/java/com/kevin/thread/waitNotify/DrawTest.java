package com.kevin.thread.waitNotify;

public class DrawTest {
    public static void main(String[] args) {
        //创建账户
        Account  acct  =new Account("1234567",0);
        new DrawThread("取钱者", acct, 800).start();
        new DepositThread("存钱者1", acct, 800).start();
        new DepositThread("存钱者2", acct, 800).start();
        new DepositThread("存钱者3", acct, 800).start();
    }
}
