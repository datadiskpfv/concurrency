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
            System.out.println("Thread1: realesed lock1. Exiting....");
        }
    }
}


