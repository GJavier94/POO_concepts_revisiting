package com.revisiting;

/*
* What if i wanted to call a member class without instanciating a class
* (creating an object) so that I can call and use the member class without having
* objects?
* This is done by using the word static within the declaration of the class
* member
* -> It's a way to declare global variables due to the fact that classes are public
* so it's only one value and other classes and object can use the value of the static variable
* in their own code
*
* */
public class StaticDemo {
    int x;
    static  int y;

    static int potenciador = 10;

    public int sum(){
        return x + y ;
    }

    /*We can also have static methods and these methods can be called
    * by using the name of the class and the the dot operator iwhtin our program*/
    static int sumaEstatica(int a , int b ){
        //we can only call static members in static methods ( makes senes they're created by using object of the class
         // ---> cannot call this.sum()
        // ---> cannot call variable this.x
        // this is a pointer who holds reference to this object class so it cannnot also be used

        return (a + b)*potenciador;
    }

    static{
        //class can require some type of initialization before creating any object
        // initialize static variables
        //when creating a connectio for example
        StaticDemo.y = 10;
        System.out.println("Im a static block called when the class is loaded by java ");
    }

    public static void main(String[] args) {

        StaticDemo demo1 = new StaticDemo();
        StaticDemo demo2 = new StaticDemo();
        demo1.x = 10;
        demo2.x = 20;
        System.out.println("value of demo1.x is "+ demo1.x);
        System.out.println("value of demo2.x is "+ demo1.x);

        //setting the value of y in StaticDemo instances
        StaticDemo.y = 19;

        System.out.println("value of sum in demo1 " + demo1.sum());
        System.out.println("value of sum in demo2 " + demo1.sum());
        //changing the value of the static variable

        StaticDemo.y = 100;
        System.out.println("value of sum in demo1 " + demo1.sum());
        System.out.println("value of sum in demo2 " + demo1.sum());


        //calling an static method
        int value = StaticDemo.sumaEstatica(2,3);
        System.out.println("sumaEstatica(2,3) "+  value );
    }
}








