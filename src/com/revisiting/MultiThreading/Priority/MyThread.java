package com.revisiting.MultiThreading.Priority;

public class MyThread implements Runnable{
    private int count;
    private String thrName;
    // creating a reference of this class
    private Thread thr;
    static boolean stop;

    MyThread(String name){
        this.thrName = name;
        this.thr = new Thread(this,name);
        this.count = 0;
        stop = false;
    }
    /*
    Using a factory method to create and start a Thread object
    so that it's easier when creating multiple objects of this class

    * */
    public static MyThread createAndStart(String name, int priority){
        MyThread myThrd = new MyThread(name);
        myThrd.thr.setPriority(priority);
        System.out.println(myThrd.thrName + " is starting..." );
        myThrd.thr.start();
        return myThrd;
    }
    @Override
    public void run() {
        do{
            count++;
        }while(stop == false && count < 1000000);
        stop = true;
        System.out.println(this.thrName + " terminating...");
    }

    public int getCount() {
        return count;
    }

    public Thread getThr() {
        return thr;
    }
}
