package main.java.com.revisiting.MultiThreading.ThreadV3;
/*
Implementation of thread by extending Thread class
it has attribute name so that super is called to set it
also the thread object creation is moved to the factory methods
* */
public class MyThread extends Thread{
    private int count;

    MyThread(String name){
        super(name);
        this.count = 0;
    }
    /*
    Using a factory method to create and start a Thread object
    so that it's easier when creating multiple objects of this class

    * */
    public static MyThread createAndStart(String name){
        MyThread myThread = new MyThread(name);
        myThread.start();
        return myThread;
    }
    @Override
    public void run() {
        for(int i = 0; i < 10 ; i++){
            try{
                Thread.sleep(400);
                System.out.println(super.getName() + " "+ (++count));
            }catch (InterruptedException e ){
                System.out.println(super.getName() + " "  + "interruption occurred");
            }

        }
        System.out.println(super.getName() + " terminating...");
    }
}
