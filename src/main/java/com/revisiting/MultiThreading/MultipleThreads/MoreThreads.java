package main.java.com.revisiting.MultiThreading.MultipleThreads;


import main.java.com.revisiting.MultiThreading.ThreadV2.MyThread;

public class MoreThreads {
    public static void main(String[] args) {
        /*
        It is possible to crete more than one object thread from the same
        class
         */
        MyThread myThread1 = MyThread.createAndStart("Th#1");
        MyThread myThread2= MyThread.createAndStart("Th#2");
        MyThread myThread3 = MyThread.createAndStart("Th#3");
        MyThread myThread4 = MyThread.createAndStart("Th#4");

        for(int i = 0; i < 50 ; i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Main Thread interrupted");
            }
            System.out.println("...");
        }

        System.out.println("Terminating main thread");

    }
}
