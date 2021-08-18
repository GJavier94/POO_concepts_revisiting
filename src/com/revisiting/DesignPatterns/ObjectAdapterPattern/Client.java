package com.revisiting.DesignPatterns.ObjectAdapterPattern;

public class Client {
    public static void main(String[] args) {
        /*
        The adapter pattern works as in real life adapter
        like the USB-ethernet adapter
        a usb port offers a usb interface which is not compatible with the interface
        that offers ethernat output of a modem (for example)
        so an adapter wire is desing so that it implements the USB interface
        an conects the method of acquiring data to the funionalities of the ethernet

        so a Client class has a target class which implements some behaviour in an interface
        you have other class adaptee which offer similar behaviour by giving it with a differen
        interface
        An adapter class needs to be created that implements the interface of the target
        and has a reference to the adaptee class to that in the methods from the target
        the methods of the adaptee are called
        Let's create those classes

        Target -> interface which is implemented by ClassTarget
        Adaptee -> interface which is implemented by ClassAdaptee
        ClassTarget -> implements the methods of target
        ClassAdaptee -> implements the method of Adaptee
        Adapter -> implements interface of Target and has a ref to ClassTarget object
        Client-> client class which is making instances
         */
        Adaptee obj1 = new ClassAdaptee();
        obj1.methodAdaptee1();
        obj1.methodAdaptee2();
        Target obj2 = new ClassTarget();
        obj2.methodTarget1();
        obj2.methodTarget2();

        Target obj3 = new Adapter(obj1);
        obj3.methodTarget1();
        obj3.methodTarget2();
    }
}
