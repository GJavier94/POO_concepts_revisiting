package main.java.com.revisiting.MultiThreading.InterThreadingCom;

public class MyThread implements Runnable{


    private Thread thr;
    private TickTock tc;
    MyThread(String name){
        this.thr = new Thread(this,name);
    }

    public void setTc(TickTock tc) {
        this.tc = tc;
    }

    public static MyThread createAndStart(String name, TickTock  tc ){
        MyThread myThrd = new MyThread(name);
        myThrd.setTc(tc);
        myThrd.thr.start();
        return myThrd;

    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread() + " is starting..." );
        if(getThr().getName().compareTo("Tick") == 0){
            for(int i = 0 ; i < 5 ; i ++){
                this.tc.Tick(true);
            }
            this.tc.Tick(false);
        }
        else{
            for(int i = 0 ; i < 5 ; i ++){
                this.tc.Tock(true);
            }
            this.tc.Tock(false);
        }

        System.out.println(this.thr.getName() + " terminating...");
    }



    public Thread getThr() {
        return thr;
    }
}
