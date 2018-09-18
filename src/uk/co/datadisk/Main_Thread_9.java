package uk.co.datadisk;

import java.util.concurrent.locks.ReentrantLock;

public class Main_Thread_9 {

    //private static Object lock = new Object();
    private static ReentrantLock lock = new ReentrantLock(true); // first come first served

    // Looking at Thread starvation
    public static void main(String[] args) {

        Thread t10 = new Thread(new Worker(ThreadColor.ANSI_RED), "Priority (RED) 10");
        Thread t8 = new Thread(new Worker(ThreadColor.ANSI_BLUE), "Priority (BLUE) 8");
        Thread t6 = new Thread(new Worker(ThreadColor.ANSI_GREEN), "Priority (GREEN) 6");
        Thread t4 = new Thread(new Worker(ThreadColor.ANSI_CYAN), "Priority (CYAN) 4");
        Thread t2 = new Thread(new Worker(ThreadColor.ANSI_PURPLE), "Priority (PURPLE) 2");

        // Priority is a gentlemen's agreement, its not set in stone
        // lower priorities could run before higher priority
        t10.setPriority(10);
        t8.setPriority(8);
        t6.setPriority(6);
        t4.setPriority(4);
        t2.setPriority(2);

        t10.start();
        t8.start();
        t6.start();
        t4.start();
        t2.start();
    }

    private static class Worker implements Runnable {
        private int runCount = 1;
        private String threadColor;

        public Worker(String threadColor) {
            this.threadColor = threadColor;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                lock.lock();
                try {
                    System.out.format(threadColor +"%s runCount = %d\n", Thread.currentThread().getName(), runCount++);
                    // execute critical section of code
                } finally {
                    lock.unlock();
                }
//                synchronized (lock){
//                    System.out.format(threadColor +"%s runCount = %d\n", Thread.currentThread().getName(), runCount++);
//                    // execute critical section of code
//                }
            }
        }
    }
}