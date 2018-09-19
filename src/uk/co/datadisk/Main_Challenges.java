package uk.co.datadisk;

public class Main_Challenges {

    public static void main(String[] args) {

        BankAccount account = new BankAccount("12345", 1000.00);

//        Thread t1 = new Thread() {
//            @Override
//            public void run() {
//                account.deposit(300.00);
//                account.printBalance();
//                account.withdraw(50.00);
//                account.printBalance();
//            }
//        };
//
//        Thread t2 = new Thread() {
//            @Override
//            public void run() {
//                account.deposit(203.75);
//                account.printBalance();
//                account.withdraw(100.00);
//                account.printBalance();
//            }
//        };

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.deposit(300.00);
                account.printBalance();
                account.withdraw(50.00);
                account.printBalance();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.deposit(203.75);
                account.printBalance();
                account.withdraw(100.00);
                account.printBalance();
            }
        });

        t1.start();
        t2.start();

    }
}
