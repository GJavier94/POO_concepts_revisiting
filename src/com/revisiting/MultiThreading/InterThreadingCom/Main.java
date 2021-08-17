package com.revisiting.MultiThreading.InterThreadingCom;

public class Main {
    public static void main(String[] args) {
        /*
        Threads can communicate between them so that its lifecyle can change
        according to the cycle of the other on via an object they're trying to access
        them.
        Let's define the following situation
        while using a method from an object sychronously
        other object that wants access to the object will need to wait for the thread in course
        but what if the thread in use needs another resource which is unavailable in at the moment
        it has a lock on that object an also is wating for other object

        It should be better that the Thread relinquish its control over the object and
        put itself to a "wait" stage and wait for a notification which will be sent
        by another Thread with the method "notify".

        The previous two methods belong to any object in java as the class which implements it
        is the Object class

        Let's build an app Tick Tick
        with two Threads
        1.-Tick-> in Charge to print Tick message
        2.-Tock-> in Charge to print Tock message
        and one Object whose methods are executed by both thread sync.
        1.- TickTock class: with Tik()  and Toks Methods

        Let's define the behaviour of threads
        They just need to print 5 times the message they are in charge to
        Synchronization part
        Tick() method Thread needs to wait for its turn, do its process and then notify he has finished using it
        Tock() method Thread needs to wait for its turn, do its process and then notify he has finished using it

        Also it is necessary to take into account that sometime Threads awake for some
        unexpected reason so that wait() need to be enclose in while()

        Also in order to know that it was really a notify from the other object
        the one which wake the thread
        we will need to be sure by changing an static variable which is saying which method and thread is
        being used at that time
         */
        TickTock tc = new TickTock();
        MyThread th1 = MyThread.createAndStart("Tick", tc);
        MyThread th2 = MyThread.createAndStart("Tock", tc);

        try {
            th1.getThr().join();
            th2.getThr().join();
        } catch (InterruptedException e) {
            System.out.println("main thread was interrupted");
        }


        System.out.println("terminating main thread");

    }
}

