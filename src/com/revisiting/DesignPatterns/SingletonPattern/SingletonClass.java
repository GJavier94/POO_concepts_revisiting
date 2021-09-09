package com.revisiting.DesignPatterns.SingletonPattern;

public class SingletonClass {
    private static SingletonClass singletonInstance= null;
    private String singletonGreeting;
    private SingletonClass(String greeting) {
        this.singletonGreeting = greeting;
    }

    public static SingletonClass getInstance(){
        if(singletonInstance == null ){
            singletonInstance = new SingletonClass("Hello");
        }
        return singletonInstance;
    }

    @Override
    public String toString() {
        return "SingletonClass{" +
                "reference " + this.hashCode() +
                " singletonGreeting='" + singletonGreeting + '\'' +
                '}';
    }
}
