package com.revisiting.MultiThreading.Synchronization;

public class SumArray {

    private int sum;

    /*
    Because threads are running concurrently  the might try to access a resource at the same time
    provoking undesirable or unexpected results
    Every object in java can hold a lock by using sth monitor
    by applying the keyword 'synchronized' to a method's object the thread which executes the methods locked the object
    so that no other thread can access syncronzed methods, the will be put in  a wait state so that
    the execution wil be secuencial

    * */

    synchronized public int sumArray(int []a){
        sum = 0;
        for(int i = 0; i < a.length; i++){
            sum += a[i];
            System.out.println(Thread.currentThread().getName()+ " " + sum);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread() + "+Thread interrupted");
            }
        }
        return sum;
    }
}
