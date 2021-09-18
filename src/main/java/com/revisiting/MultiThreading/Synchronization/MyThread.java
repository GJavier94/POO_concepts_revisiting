package main.java.com.revisiting.MultiThreading.Synchronization;

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

        /*
        If there's no access to the class implementation which we want to call in a synchronized way
        it's possible to make the object call synchronizable
        by using
        synchronyzed(objRef){
            //statements to be synchronized
        }
         */
        //int sum = sumArray.sumArray(MyThread.getA());
        synchronized (sumArray){
            int sum = sumArray.sumArray(a);
        }

        System.out.println(this.thr.getName() + " terminating...");
    }

    public static int[] getA() {
        return a;
    }

    public Thread getThr() {
        return thr;
    }
}
