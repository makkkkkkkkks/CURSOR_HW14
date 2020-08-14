package main.java.com.makkkkkkkks;

import main.java.com.makkkkkkkks.semaphore.CustomSemaphore;

public class Executor {
    public static void start(){
        CustomSemaphore selfServiceArea = new CustomSemaphore(4);
        Runnable buyerBehavior = () -> {
            try {
                selfServiceArea.acquire();
                System.out.println("Buying something...");
                Thread.sleep(2000);
                System.out.println("Purchase completed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            selfServiceArea.release();
        };
        for (int i = 0; i < 10; i++) {
            new Thread(buyerBehavior).start();
        }
    }
}