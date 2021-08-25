package com.revisiting.DesignPatterns.ObserverPattern;

public class ObserverClass1 implements Observer {
    private int id;
    private String name;
    private int updates = 0;
    public ObserverClass1(int id, String name, int updates) {
        this.id = id;
        this.name = name;
        this.updates = updates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getUpdates() {
        return updates;
    }

    public void setUpdates(int updates) {
        this.updates = updates;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void notifyOb(){
        System.out.println(this.id + " being notified...");
        //make the updates you want
        this.increaseUpdates();
        System.out.println(this.id + " notified!!!");
    }
    public void increaseUpdates(){
        int value = this.getUpdates();
        this.setUpdates(++value);
        System.out.println("obs " + this + " updates: " + this.getUpdates());
    }

    @Override
    public String toString() {
        return "ObserverClass1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", updates=" + updates +
                '}';
    }
}
