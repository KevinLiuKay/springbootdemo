package com.kevin.thread.waitNotify;

public class Account {
    /**
     * 账户编号
     */
    private String accountNo;
    /**
     * 账户余额
     */
    private double balance;
    /**
     * 标识账户中是否有存款的旗标
     */
    private boolean flag = false;

    public Account(String accountNo, double balance){
        this.accountNo = accountNo;
        this.balance = balance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * 账户余额不允许随便修改，所以只为balance提供getter方法
     * @return
     */
    public double getBalance() {
        return this.balance;
    }

    public synchronized void draw(double drawAmount){
        //如果flag为假，表明账户中还没有钱存入进去，取钱方法阻塞
        try{
            if(!flag) {
                wait();
            }else {
                //执行取钱操作
                System.out.println(Thread.currentThread().getName()+ "取钱："+drawAmount);
                balance = balance-drawAmount;
                System.out.println("余额为:"+ balance);
                //将标识账户已有存款旗标设为false
                flag = false;
                //唤醒其他线程
                notifyAll();
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void deposit(double depositAmount){
        //如果flag为真，表明账户有钱，存钱方法阻塞
        try{
            if(flag) {
                wait();
            }else {
                System.out.println(Thread.currentThread().getName()+ "存款"+ depositAmount);
                balance = balance + depositAmount;
                System.out.println("账户余额为："+ balance);
                //将标识账户已有存款的旗标设为true
                flag = true;
                //唤醒其他线程
                notifyAll();
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accountNo == null) ? 0 : accountNo.hashCode());
        long temp;
        temp = Double.doubleToLongBits(balance);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Account other = (Account) obj;
        if (accountNo == null) {
            if (other.accountNo != null){
                return false;
            }
        } else if (!accountNo.equals(other.accountNo)) {
            return false;
        }

        if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance)){
            return false;
        }
        return true;
    }

}

/**
 * 不管是等待线程还是唤醒线程都在同步块里调用wait()和notify()。这是强制性的！
 * 一个线程如果没有持有对象锁，将不能调用 wait()，notify()或者notifyAll()。否则，会抛出IllegalMonitorStateException异常。
 */
