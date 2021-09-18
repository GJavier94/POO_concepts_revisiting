package main.java.com.revisiting.MultiThreading.Priority;

public class MoreThreadsUsingJoin {
    public static void main(String[] args) {
        /*
        Instead of making processing and sending main thread to sleep such that it will finish
        later than child Threads there exists the method Join
        join(): The thread where the method join is executed will join to the execution
        of the thread which it was called , it will be wating until finally
        The other Thread finishes
         */
        MyThread th1 = MyThread.createAndStart("Th#1", Thread.NORM_PRIORITY+2);
        MyThread th2 = MyThread.createAndStart("Th#2",Thread.NORM_PRIORITY-2);
        MyThread th3 = MyThread.createAndStart("Th#3",Thread.NORM_PRIORITY);
        MyThread th4 = MyThread.createAndStart("Th#4",Thread.NORM_PRIORITY);
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

        System.out.println("MAX_PRIORITY thread: " + th1.getCount());
        System.out.println("MIN_PRIORITY thread: " + th2.getCount());
        System.out.println("NORMAL_PRIORITY thread: " + th3.getCount());
        System.out.println("NORMAL_PRIORITY thread: " + th4.getCount());

        System.out.println("Main Thread is terminating S");
    }
}
