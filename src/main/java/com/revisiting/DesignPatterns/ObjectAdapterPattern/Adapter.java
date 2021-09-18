package main.java.com.revisiting.DesignPatterns.ObjectAdapterPattern;

public class Adapter implements Target {
    private Adaptee adaptee;
    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void methodTarget1() {
        adaptee.methodAdaptee1();
    }

    @Override
    public void methodTarget2() {
        adaptee.methodAdaptee2();
    }
}
