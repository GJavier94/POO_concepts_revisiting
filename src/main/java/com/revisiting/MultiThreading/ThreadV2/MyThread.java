package main.java.com.revisiting.MultiThreading.ThreadV2;

public class MyThread implements Runnable{
    private int count;
    private String thrName;
    // creating a reference of this class
    private Thread thr;

    MyThread(String name){
        this.thrName = name;
        this.thr = new Thread(this,name);
        this.count = 0;
    }
    /*
    Using a factory method to create and start a Thread object
    so that it's easier when creating multiple objects of this class

    * */
    public static MyThread createAndStart(String name){
        MyThread myThrd = new MyThread(name);
        myThrd.thr.start();
        return myThrd;
    }
    @Override
    public void run() {
        for(int i = 0; i < 10 ; i++){
            try{
                Thread.sleep(400);
                System.out.println(this.thrName + " "+ (++count));
            }catch (InterruptedException e ){
                System.out.println(this.thrName + " "  + "interruption occurred");
            }

        }
        System.out.println(this.thrName + " terminating...");
    }

    public Thread getThr() {
        return thr;
    }
}
