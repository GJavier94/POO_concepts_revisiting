package com.revisiting.MultiThreading.ThreadV1;

public class UseThreads {
    public static void main(String[] args) {
        //Creating and running one thread from the main thread
        // in this case main thread makes execution ona  for which goes to sleep state
        // so that the main thread finished later than the Thread #1
        MyThread m1 = new MyThread("Thread # 1");
        Thread t1 = new Thread(m1);
        t1.start();
        for(int i = 0 ; i < 50 ; i++){
            System.out.println("...");
            try{
                Thread.sleep(100);
            }catch(InterruptedException e ){
                System.out.println("Main Thread Interrupted..");
            }
        }
        System.out.println("Main thread ending....");
    }

}
