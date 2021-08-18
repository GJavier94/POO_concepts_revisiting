package com.revisiting.DesignPatterns.ObjectAdapterPattern;

public class ClassTarget implements Target {
    @Override
    public void methodTarget1() {
        System.out.println("methodTarget1()...");
    }

    @Override
    public void methodTarget2() {
        System.out.println("methodTarget2()...");
    }
}
