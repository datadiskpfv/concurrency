package uk.co.datadisk;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    // Any Global variables are non thread-safe
    // All block local variables are automatically thread-safe

    private double balance;
    private String accountNumber;

    private Lock lock;

    public BankAccount(String accountNumber, double initialBalance) {
        this.balance = initialBalance;
        this.accountNumber = accountNumber;
        lock = new ReentrantLock();
    }

    //public synchronized void deposit(double amount){
    public void deposit(double amount){
        // use this solution if there are lots of code inside method
        // this will mean only the code that needs to be synchronized will be
        //synchronized (this){
//        lock.lock();
//        try {
//            balance += amount;
//        } finally {
//            lock.unlock();
//        }

        try {
            if(lock.tryLock(1000, TimeUnit.MILLISECONDS)){
                try {
                    balance += amount;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("DEPOSIT: Could not get the lock");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //}
    }

    // public synchronized void withdraw(double amount){
    public void withdraw(double amount){
        //synchronized (this){
//        lock.lock();
//        try {
//            balance -= amount;
//        } finally {
//            lock.unlock();
//        }

        try {
            if(lock.tryLock(1000, TimeUnit.MILLISECONDS)){
                try {
                    balance -= amount;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("WITHDRAW: Could not get the lock");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //}
    }

    public void printBalance() {
        System.out.println("Account: " + accountNumber + " Balance : " + balance);
    }
}
