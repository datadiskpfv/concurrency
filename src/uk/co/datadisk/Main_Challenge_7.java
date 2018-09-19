package uk.co.datadisk;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main_Challenge_7 {

    public static void main(String[] args) {
        NewBankAccount account1 = new NewBankAccount("12345-678", 500.00);
        NewBankAccount account2 = new NewBankAccount("98765-432", 1000.00);

        new Thread(new Transfer(account1, account2, 10.00), "Transfer1").start();
        new Thread(new Transfer(account2, account1, 50.00), "Transfer2").start();
    }
}

class NewBankAccount {
    private double balance;
    private String accountNumber;
    private Lock lock = new ReentrantLock();

    public double getBalance() {
        return balance;
    }

    NewBankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public boolean withdraw(double amount) {

        // this is slightly different than the deposit method, as I use a boolean flag variable
        boolean flag = false;

        try {
            flag = lock.tryLock(1000, TimeUnit.MILLISECONDS);
            if (flag) {
                try {
                    balance -= amount;
                    System.out.println(Thread.currentThread().getName() + " withdraw " + amount + " balance = " + balance);
                    // should the return of true really be here ???????
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Could not get the WITHDRAW lock");
            }
        } catch (InterruptedException e) {}

        return flag;
    }

    public boolean deposit(double amount) {

        try {
            if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                try {
                    balance += amount;
                    System.out.println(Thread.currentThread().getName() + " deposited " + amount + " balance = " + balance);
                    return true;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Could not get the DEPOSIT lock");
            }
        } catch (InterruptedException e) {}

        return false;
    }

    public boolean transfer(NewBankAccount destinationAccount, double amount) {
        if (withdraw(amount)) {
            if (destinationAccount.deposit(amount)) {
                return true;
            } else {
                // The deposit failed. Refund the money back into the account.
                System.out.printf("%s: Destination account busy. Refunding money\n",
                        Thread.currentThread().getName());
                deposit(amount);
            }
        }

        return false;
    }
}

class Transfer implements Runnable {
    private NewBankAccount sourceAccount, destinationAccount;
    private double amount;

    Transfer(NewBankAccount sourceAccount, NewBankAccount destinationAccount, double amount) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public void run() {
        while (!sourceAccount.transfer(destinationAccount, amount)) {
            System.out.println(Thread.currentThread().getName() + " looping for lock to be released");
            continue;
        }

        System.out.printf("%s completed\n", Thread.currentThread().getName());
    }
}