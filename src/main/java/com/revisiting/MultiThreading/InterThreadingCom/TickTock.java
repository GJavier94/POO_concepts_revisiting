package main.java.com.revisiting.MultiThreading.InterThreadingCom;

public class TickTock {
    static final String STATE_TICK = "TICK";
    static final String STATE_TOCK = "TOCK";


    static String prevState;
    TickTock(){
        prevState = TickTock.STATE_TOCK;

    }
    synchronized public void Tick(boolean running){
        if(!running){
            prevState = STATE_TICK;
            notify();
            return;
        }
        if(prevState.compareTo(STATE_TOCK) == 0 ){
            System.out.print("TICK ");
            prevState = STATE_TICK;
            notify();
        }
        do{
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while( prevState.equals("TICK"));
    }
    synchronized public void Tock(boolean running){
        if(!running){
            prevState = STATE_TOCK;
            notify();
            return;
        }
        System.out.println("TOCK");
        prevState = STATE_TOCK;
        notify();

        do{
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while(prevState.equals("TOCK") );
    }

}
