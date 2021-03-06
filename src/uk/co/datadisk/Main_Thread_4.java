package uk.co.datadisk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import static uk.co.datadisk.Main_Thread_4.EOF;

public class Main_Thread_4 {

    public static final String EOF = "EOF";

    public static void main(String[] args) {
        List<String> buffer = new ArrayList<>();
        ReentrantLock bufferLock = new ReentrantLock();

        MyProducer5 myProducer = new MyProducer5(buffer, ThreadColor.ANSI_YELLOW, bufferLock);
        MyConsumer5 myConsumer1 = new MyConsumer5(buffer, ThreadColor.ANSI_PURPLE, bufferLock);
        MyConsumer5 myConsumer2 = new MyConsumer5(buffer, ThreadColor.ANSI_CYAN, bufferLock);

        new Thread(myProducer).start();
        new Thread(myConsumer1).start();
        new Thread(myConsumer2).start();
    }
}

class MyProducer5 implements Runnable {

    private List<String> buffer;
    private String color;
    private ReentrantLock bufferLock;

    public MyProducer5(List<String> buffer, String color, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5"};

        for (String num : nums) {
            try {
                System.out.println(color + "Adding..." + num);
                bufferLock.lock();

                try {
                    buffer.add(num);
                } finally {
                    bufferLock.unlock();
                }

                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                System.out.println("Producer was interrupted");
            }
        }
        System.out.println(color + "Adding EOF and exiting...");
        bufferLock.lock();
        try {
            buffer.add("EOF");
        } finally {
            bufferLock.unlock();
        }
    }
}

class MyConsumer5 implements Runnable {

    private List<String> buffer;
    private String color;
    private ReentrantLock bufferLock;

    public MyConsumer5(List<String> buffer, String color, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    @Override
    public void run() {
        int counter=0;

        while (true) {
            if (bufferLock.tryLock()){
                try {
                    if (buffer.isEmpty()) {
                        continue;
                    }

                    System.out.println(color + "Counter = " + counter);
                    counter = 0;
                    if (buffer.get(0).equals(EOF)) {
                        System.out.println(color + "Exiting");
                        break;
                    } else {
                        System.out.println(color + "Removed " + buffer.remove(0));
                    }
                } finally {
                    bufferLock.unlock();
                }
            }
            counter++;
        }
    }
}