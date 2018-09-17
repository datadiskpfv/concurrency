package uk.co.datadisk;

import java.util.Random;

public class Main_Thread_3 {

    // Classic Producer and Consumer Thread demo
    public static void main(String[] args) {

        Message message = new Message();
        (new Thread(new Writer(message))).start();
        (new Thread(new Reader(message))).start();

    }
}

class Message {
    private String message;
    private boolean empty = true;  // shared variable between threads

    public synchronized String read() {
        while(empty) {
            try {
                System.out.println("==== read is waiting");
                wait();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        empty = true;
        System.out.println("=== read is notifying");
        notifyAll();
        return message;
    }

    public synchronized void write(String message) {
        while(!empty) {
            try {
                System.out.println("==== write is waiting");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        empty = false;
        this.message = message;
        System.out.println("=== write is notifying");
        notifyAll();
    }
}

class Writer implements Runnable {
    private Message message;

    public Writer(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        String[] messages = {
                "Humpty Dumpty sat on the wall",
                "Humpty Dumpty had a great fall",
                "All the king's horses and all the king's men",
                "Couldn't put Humpty together again"
        };

        Random random = new Random();

        for (int i = 0; i < messages.length; i++) {
            message.write(messages[i]);

            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        message.write("Finished");
    }
}


class Reader implements Runnable {
    private Message message;

    public Reader(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        Random random = new Random();
        for(String latestMessage = message.read(); !latestMessage.equals("Finished"); latestMessage = message.read()){

            System.out.println(latestMessage);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }

    }
}