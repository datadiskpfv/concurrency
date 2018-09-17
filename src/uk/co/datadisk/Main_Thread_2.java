package uk.co.datadisk;

public class Main_Thread_2 {

    public static void main(String[] args) {

        // We will synchronize the countdown object inside the class
        Countdown countdown = new Countdown();

        CountDownThread t1 = new CountDownThread(countdown);
        t1.setName("Thread 1");
        CountDownThread t2 = new CountDownThread(countdown);
        t2.setName("Thread 2");

        t1.start();
        t2.start();
    }
}

class Countdown {

    // Class variable (global)
    // This variable is created on the heap and thus shared among Threads
    // Shared resources can result in race conditions
    private int i;

    // You can synchronise an entire method
    //public synchronized void doCountdown() {
    public void doCountdown() {
        String color;

        switch (Thread.currentThread().getName()) {
            case "Thread 1":
                color = ThreadColor.ANSI_CYAN;
                break;
            case "Thread 2":
                color = ThreadColor.ANSI_PURPLE;
                break;
            default:
                color = ThreadColor.ANSI_GREEN;
                break;
        }

        // You are synchronizing the the Countdown object by using this
        synchronized (this) {
            for (i = 5; i > 0; i--) {
                System.out.println(color + Thread.currentThread().getName() + ": i = " + i);
            }
        }
    }
}

class CountDownThread extends Thread {
    private Countdown threadCountDown;

    public CountDownThread(Countdown countdown) {
        threadCountDown = countdown;
    }

    @Override
    public void run() {
        threadCountDown.doCountdown();
    }
}