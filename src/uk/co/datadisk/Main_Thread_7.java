package uk.co.datadisk;

public class Main_Thread_7 {

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public static void main(String[] args) {

    }

    private static class Thread1 extends Thread {
        @Override
        public void run() {
            synchronized (lock1){
                System.out.println("Thread 1: has lock1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e){}

                System.out.println("Thread1: waiting for lock2");
                synchronized (lock2){
                    System.out.println("Thread1: has lock1 and lock2");
                }
                System.out.println("Thread 1: released lock2");
            }
            System.out.println("Thread1: released lock1. Exiting....");
        }
    }

    private static class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (lock2){
                System.out.println("Thread 2: has lock2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e){}

                System.out.println("Thread1: waiting for lock1");
                synchronized (lock1){
                    System.out.println("Thread2: has lock1 and lock2");
                }
                System.out.println("Thread 2: released lock1");
            }
            System.out.println("Thread1: released lock1. Exiting....");
        }
    }
}


