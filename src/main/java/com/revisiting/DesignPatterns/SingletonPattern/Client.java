package main.java.com.revisiting.DesignPatterns.SingletonPattern;

public class Client {
    public static void main(String[] args) {
        SingletonClass obj1 = SingletonClass.getInstance();
        SingletonClass obj2 = SingletonClass.getInstance();
        SingletonClass obj3 = SingletonClass.getInstance();

        System.out.println(obj1);
        System.out.println(obj2);
        System.out.println(obj3);

    }
}
