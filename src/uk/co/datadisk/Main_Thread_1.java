package uk.co.datadisk;

import static uk.co.datadisk.ThreadColor.ANSI_GREEN;
import static uk.co.datadisk.ThreadColor.ANSI_PURPLE;

public class Main_Thread_1 {

    public static void main(String[] args) {
        System.out.println(ANSI_PURPLE + "Hi from the main thread.");

        // Use a class that extends Thread class
        Thread anotherThread = new AnotherThread();
        anotherThread.setName("== AnotherThread ==");
        anotherThread.start();
        //anotherThread.run();        // you are running main Thread

        // Anonymous Thread class
//        new Thread() {
//            public void run() {
//                System.out.println("Hello from the Anonymous thread class");
//            }
//        }.start();

        new Thread(() -> System.out.println(ANSI_GREEN + "Hello from the Anonymous thread class")).start();

        Thread myRunnableThread = new Thread(new MyRunnable());
        myRunnableThread.start();

        System.out.println(ANSI_PURPLE + "Hello again from the main thread");

    }
}