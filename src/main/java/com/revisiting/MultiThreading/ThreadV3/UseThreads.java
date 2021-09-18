package main.java.com.revisiting.MultiThreading.ThreadV3;

import main.java.com.revisiting.MultiThreading.ThreadV2.MyThread;

public class UseThreads {
    public static void main(String[] args) {
        //Creating and running one thread from the main thread
        // in this case main thread makes execution ona  for which goes to sleep state
        // so that the main thread finished later than the Thread #1
        // changes make on the MyThread class about the construction of the object are hidden to user classes like in this
        //case main
        MyThread myThread = MyThread.createAndStart("Thread#1");

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
