package com.revisiting.NestedClasses;

public class NestedClassDemo {
    public static void main(String[] args) {
        int[] numeros = {1,2,3,4,5};
        Outer out1 = new Outer(numeros);
        out1.analyze();
        //inner class cannot be called here Outer.Inner


        // it can also be possible to declare a nested class within a block scope
        //example

        class Myclass {
            private String name;
            Myclass(String name){
                this.name = name;
            }
            public void printName(){
                System.out.println(name);
            }
        }

        Myclass mc1 = new Myclass("Javier");
    }
}
