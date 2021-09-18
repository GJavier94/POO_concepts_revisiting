package main.java.com.revisiting.DesignPatterns.ObserverPattern;

public class MainClass {
    public static void main(String[] args) {
        Subject subject = new Subject( 1, 2 ,"Hello");
        Observer obs1 = new ObserverClass1(1, "observer1", 0 );
        Observer obs2 = new ObserverClass1(2, "observer2", 0 );
        Observer obs3 = new ObserverClass1(3, "observer3", 0 );

        subject.addObserver(obs1);
        subject.addObserver(obs2);

        subject.setAttr1(10);
        subject.setAttr2(20);

        subject.addObserver(obs3);

        subject.setTextoAttr("There's a change in the text....");
        subject.removeObserver(0);
        subject.setTextoAttr("There's a change in the text2....");


    }
}
