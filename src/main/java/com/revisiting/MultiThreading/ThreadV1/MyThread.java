package main.java.com.revisiting.MultiThreading.ThreadV1;

public class MyThread implements Runnable{
    private int count;
    private String thrName;
    MyThread(String name){
        this.thrName = name;
        this.count = 0;
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
}
