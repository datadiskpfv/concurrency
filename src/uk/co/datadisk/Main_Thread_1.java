package uk.co.datadisk;

import static uk.co.datadisk.ThreadColor.ANSI_GREEN;
import static uk.co.datadisk.ThreadColor.ANSI_PURPLE;
import static uk.co.datadisk.ThreadColor.ANSI_RED;

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

        Thread myRunnableThread = new Thread(new MyRunnable(){
            @Override
            public void run() {
                System.out.println(ANSI_RED + "Hello from the anonymous class's implementation of run()");
                try {
                    anotherThread.join(2000);
                    System.out.println(ANSI_RED + "AnotherThread terminated or timed out, so I'm running again");
                } catch (InterruptedException e){
                    System.out.println("I could not wait after all. I was interrupted.");
                }
            }
        });
        myRunnableThread.start();

        //This kills terminates the thread by causing an exception
        //anotherThread.interrupt();

        System.out.println(ANSI_PURPLE + "Hello again from the main thread");

    }
}