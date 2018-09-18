package uk.co.datadisk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

import static uk.co.datadisk.Main_Thread_6.EOF;

public class Main_Thread_6 {

    public static final String EOF = "EOF";

    public static void main(String[] args) {

        ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<>(6);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        MyProducer6 myProducer = new MyProducer6(buffer, ThreadColor.ANSI_YELLOW);
        MyConsumer6 myConsumer1 = new MyConsumer6(buffer, ThreadColor.ANSI_PURPLE);
        MyConsumer6 myConsumer2 = new MyConsumer6(buffer, ThreadColor.ANSI_CYAN);

        executorService.execute(myProducer);
        executorService.execute(myConsumer1);
        executorService.execute(myConsumer2);

        Future<String> future = executorService.submit(new Callable<String>(){
            @Override
            public String call() throws Exception {
                System.out.println(ThreadColor.ANSI_WHITE + "I'm being printed from the callable class");
                return "This is the callable result";
            }
        });

        try {
            System.out.println(future.get());
        } catch (ExecutionException e) {
            System.out.println("Something went wrong");
        } catch (InterruptedException e){
            System.out.println("Thread running the task was interrupted");
        }

        // You must shutdown the executorService after you have finished with it
        executorService.shutdown();
    }
}

class MyProducer6 implements Runnable {

    private ArrayBlockingQueue<String> buffer;
    private String color;

    public MyProducer6(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5"};

        for (String num : nums) {
            try {
                System.out.println(color + "Adding..." + num);
                // put on a ArrayBlockingQueue will lock the Array for us automatically, hence
                // why we put it in a try catch block
                buffer.put(num);
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                System.out.println("Producer was interrupted");
            }
        }
        System.out.println(color + "Adding EOF and exiting...");

        // put on a ArrayBlockingQueue will lock the Array for us automatically, hence
        // why we put it in a try catch block
        try {
            buffer.put("EOF");
        } catch(InterruptedException e){

        }
    }
}

class MyConsumer6 implements Runnable {

    private ArrayBlockingQueue<String> buffer;
    private String color;

    public MyConsumer6(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
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
