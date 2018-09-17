package uk.co.datadisk;

import static uk.co.datadisk.ThreadColor.ANSI_BLUE;

public class AnotherThread extends Thread {

    @Override
    public void run() {
        System.out.println(ANSI_BLUE + "Hello from " + currentThread().getName());

        try {
            // Check the operating as it may not be supported
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // Any interrupts and terminates it
            System.out.println(ANSI_BLUE + "Another thread woke me up");
            return;
        }

        System.out.println(ANSI_BLUE + "Three seconds have passed and I'm awake " + currentThread().getName());
    }
}
