package com.revisiting.DesignPatterns.BuilderPattern;

public class Person {
    private String name;
    private String surname;
    private int age;
    private double height;
    public Person(){

    }
    public Person(Builder builder) {
        this.name = builder.name;
        this.surname = builder.surname;
        this.age = builder.age;
        this.height = builder.height;
    }

    /*
    Method chaining modifies attributes of one object instances in one single statement
    the methods need to return the reference of the object in that way they can be chained.
    But it has  a problem
    * */

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public Person setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Person setAge(int age) {
        this.age = age;
        return this;
    }

    public double getHeight() {
        return height;
    }

    public Person setHeight(double height) {
        this.height = height;
        return this;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", height=" + height +
                '}';
    }


    /**
     * Let's create the builder class
     * it needs to be static if we don't need the superior class to be created
     *
     */
    public static class Builder{
        private String name;
        private String surname;
        private int age;
        private double height;

        public Builder() {

        }


        public Builder setName(String name) {
            this.name = name;
            return this;
        }


        public Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }


        public Builder setAge(int age) {
            this.age = age;
            return this;
        }



        public Builder setHeight(double height) {
            this.height = height;
            return this;
        }

        /**
         * This method is esential for the builder class
         * the subject class requires a constructor from the builder class
         * and the build() methods uses new operator to create a new instance
         * of the subject class
         */
        public Person build(){
            return new Person(this);
        }
    }
}

