package com.revisiting.DesignPatterns.BuilderPattern;


public class ClientThreads{

    private  Person person1 = new Person();

    Thread  th1;
    Thread  th2;

    public Thread getTh1() {
        return th1;
    }


    public Thread getTh2() {
        return th2;
    }


    public ClientThreads(){

    }

    public void startThreads(){
        th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                person1.setName("Javier").setSurname("Armenta Garcia ").setAge(27).setHeight(1.71);
                System.out.println(Thread.currentThread() + " Done..." );
            }
        });

        th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                person1.setName("Nicolas").setSurname("Armenta Garcia ").setAge(31).setHeight(1.80);
                System.out.println(Thread.currentThread() + " Done..." );
            }
        });
        th1.start();
        th2.start();
    }
    public void startThreadsWithBuilder(){
        th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                person1 =  (new Person.Builder()).setName("Javier").setSurname("Armenta Garcia ").setAge(27).setHeight(1.71).build();
                System.out.println(Thread.currentThread() + " Done..." );
            }
        });

        th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                person1 = (new Person.Builder()).setName("Nicolas").setSurname("Armenta Garcia ").setAge(31).setHeight(1.80).build();
                System.out.println(Thread.currentThread() + " Done..." );
            }
        });
        th1.start();
        th2.start();
    }

    public Person getPerson() {
        return this.person1;
    }
}

