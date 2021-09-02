package com.revisiting.DesignPatterns.FactoryPattern;

public class Client {
    public static void main(String[] args) {

        /**
         * It is part of the Creational patterns
         * Factory pattern allows you to create new instances of some interface or class
         *
         * First principle:
         *  We create a logic without exposing the creation logic to the client
         *  and we refer to a common (The factory)
         *  interface which exposes the methods(factory Methods) to create the objects
         *
         *  in this case we will use an interface Shape
         *  the classes which implement interface are circle, triangle,  square so all are shapes
         *
         *  We create a Factory Interface with a static method
         *  The method will be the Factory method which return interface class objects (shape)
         *  depending on the call arguments of the factory methods it will create different instances
         *
         *  We are exposing a way to create objects hiding the implementation
         *  and also doing semi- polymorphism because different constructors are called within factoryMethod
         *
         */

        Triangle  t =(Triangle) ShapeFactory.createShape("Triangle");
        Circle  c =(Circle) ShapeFactory.createShape("Circle");
        Square  s =(Square) ShapeFactory.createShape("Square");


        Shape  ti = ShapeFactory.createShape("Triangle");
        Shape  ci = ShapeFactory.createShape("Circle");
        Shape  si = ShapeFactory.createShape("Square");

        t.draw();
        c.draw();
        s.draw();

        ti.draw();
        ci.draw();
        si.draw();

    }
}
