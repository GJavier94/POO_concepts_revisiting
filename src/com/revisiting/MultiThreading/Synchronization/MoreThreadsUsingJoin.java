package com.revisiting.MultiThreading.Synchronization;

public class MoreThreadsUsingJoin {
    public static void main(String[] args) {

        int []nums = {1,2,3,4,5};
        MyThread th1 = MyThread.createAndStart("Th#1", nums);
        MyThread th2 = MyThread.createAndStart("Th#2",nums);
        MyThread th3 = MyThread.createAndStart("Th#3",nums);
        MyThread th4 = MyThread.createAndStart("Th#4",nums);
        //The join methods corresponds to Thread class so,
        // if there is a class which has been impleted the Runnable inteace
        //it will only have run() method not the others
        //a Thread reference needs to be store as an attribute of this class
        // so that those methods like join() can be called

        //Also join can throw an Exception, we need a try and catch
        try{
            th1.getThr().join();
            th2.getThr().join();
            th3.getThr().join();
            th4.getThr().join();

        }catch(InterruptedException e){
            System.out.println("Main Thread was interrupted");
        }


        System.out.println("Main Thread is terminating S");
    }
}
