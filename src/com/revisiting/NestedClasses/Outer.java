package com.revisiting.NestedClasses;

/*
Since java1.1 nested classes are allowed:
there exists two types of nested classes
    1) static nested classes
    2) non-static nested classes (classes which need to be instantiated as objects)
 the number two are also called Inner Classes

 */
public class Outer {
    private int []num;
    Outer(int []num){
        this.num = num;
    }

    public void analyze(){
        Inner inner = new Inner();
        System.out.println("min: " + inner.min());
        System.out.println("max: " + inner.max());
        System.out.println("AVG: " + inner.avg());
    }

    //puporses:
    //1 to provide a service to the outer class
    //2 outer class delegate some task to the inner class
    //Depend on the outer class , cannot be instantiated outside the outer one
    public class Inner{
        //scope of the inner class is the whole outerclass

        int val = num[0];
        int min() {
            int min = val;
            for(int i = 0; i < num.length;i++){
                if (num[i] < min ) {
                    min = num[i];
                }
            }

            return min;
        }

        int max() {
            int max = val;
            for(int i = 0; i < num.length;i++){
                if (num[i] > max ) {
                    max = num[i];
                }
            }
            return max;
        }

        double avg() {
            int sum = 0;
            for(int i = 0; i < num.length;i++){
                sum += num[i];
            }
            return sum/ num.length;
        }
    }
}
