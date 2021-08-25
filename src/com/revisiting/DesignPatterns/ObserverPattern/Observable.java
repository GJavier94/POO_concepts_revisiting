package com.revisiting.DesignPatterns.ObserverPattern;

public interface Observable {
    boolean addObserver(Observer observer);
    boolean removeObserver(int pos);
    void notifyObservers();
}
