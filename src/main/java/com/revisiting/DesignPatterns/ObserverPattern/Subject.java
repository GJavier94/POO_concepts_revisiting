package main.java.com.revisiting.DesignPatterns.ObserverPattern;

import java.util.ArrayList;

public class Subject implements Observable{
    //set of attributes of the subject
    private int attr1;
    private int attr2 ;
    private String textoAttr;
    //attrs associated with the observable interface
    private int size = 0;
    ArrayList<Observer> observers = null;
    public int getSize(){
        return this.size;
    }
    public void increaseSize(){
        this.size++;
    }
    public void decreaseSize(){
        if(this.size-1 >= 0 ){
            this.size--;
        }
    }
    public Subject(int attr1, int attr2, String textoAttr) {
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.textoAttr = textoAttr;
        this.observers = new ArrayList<Observer>();
    }

    @Override
    public boolean addObserver(Observer observer) {
        if( observers.add(observer) ){
            this.increaseSize();
            System.out.println("Observer " + observer + " added");

            return true;
        }
        return false;
    }

    @Override
    public boolean removeObserver(int pos) {
        Observer observer = observers.remove(pos);
        if(observer != null ){
            System.out.println("Observer with " + observer + " wad removed");
            this.decreaseSize();
            return true;
        }
        return false;
    }

    @Override
    public void notifyObservers() {
        if(this.getSize() == 0 ){
            System.out.println("There aren't any observers...");
            return;
        }
        for(int i = 0; i < observers.size();i++) {
            observers.get(i).notifyOb();
        }
    }

    public int getAttr1() {
        return attr1;
    }

    public void setAttr1(int attr1) {
        notifyObservers();
        this.attr1 = attr1;
    }

    public int getAttr2() {

        return attr2;
    }

    public void setAttr2(int attr2) {
        notifyObservers();
        this.attr2 = attr2;
    }

    public String getTextoAttr() {
        return textoAttr;
    }

    public void setTextoAttr(String textoAttr) {
        notifyObservers();
        this.textoAttr = textoAttr;
    }


}
