package com.revisiting.MultiThreading.Synchronization;

public class MyThread implements Runnable{

    // creating a reference of this class
    private Thread thr;
    static int[] a ;
    static SumArray sumArray = new SumArray();

    MyThread(String name, int[] nums){

        this.thr = new Thread(this,name);
        a = nums;
    }
    /*
    Using a factory method to create and start a Thread object
    so that it's easier when creating multiple objects of this class

    * */
    public static MyThread createAndStart(String name, int[] nums){
        MyThread myThrd = new MyThread(name, nums );
        myThrd.thr.start();
        return myThrd;

    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread() + " is starting..." );
        int sum = sumArray.sumArray(MyThread.getA());

        System.out.println(this.thr.getName() + " terminating...");
    }

    public static int[] getA() {
        return a;
    }

    public Thread getThr() {
        return thr;
    }
}
