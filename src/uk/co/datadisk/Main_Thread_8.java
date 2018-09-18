package uk.co.datadisk;

public class Main_Thread_8 {

    public static void main(String[] args) {

        final PolitePerson paul = new PolitePerson("Paul");
        final PolitePerson lorraine = new PolitePerson("Lorraine");

        // 1. Thread1 acquires the lock on the paul object and enters the sayHello() method.
        //    It prints to the console, then suspends.
        // 2. Thread2 acquires the lock on the lorraine object and enters the sayHello() method.
        //    It prints to the console, then suspends.
        // 3. Thread1 runs again and wants to say hello back to the lorraine object. It tries to call the sayHelloBack() method
        //    using the lorraine object that was passed into the sayHello() method,
        //    but Thread2 is holding the lorraine lock, so Thread1 suspends.
        // 4. Thread2 runs again and wants to say hello back to the paul object. It tries to call the sayHelloBack() method
        //    using the paul object that was passed into the sayHello() method,
        //    but Thread1 is holding the paul lock, so Thread2 suspends.

        // RESULT = Deadlock

        new Thread(() -> paul.sayHello(lorraine)).start();
        new Thread(() -> lorraine.sayHello(paul)).start();

    }

    static class PolitePerson {
        private final String name;

        public PolitePerson(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public synchronized void sayHello(PolitePerson person){
            System.out.format("%s: %s" + " has said hello to me!\n", this.name, person.getName());
            person.sayHelloBack(this);
        }

        public synchronized void sayHelloBack(PolitePerson person){
            System.out.format("%s: %s" + " has said hello back to me!\n", this.name, person.getName());
        }
    }
}