package main.java.com.revisiting.DesignPatterns.ObjectAdapterPattern;

public class ClassAdaptee implements Adaptee {
    @Override
    public void methodAdaptee1() {
        System.out.println("methodAdaptee1...");
    }
    public void methodAdaptee2() {
        System.out.println("methodAdaptee2...");
    }
}
