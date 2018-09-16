package uk.co.datadisk;

import static uk.co.datadisk.ThreadColor.ANSI_RED;

public class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println(ANSI_RED + "Hello from the runnable thread");
    }
}
