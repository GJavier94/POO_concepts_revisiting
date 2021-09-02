package com.revisiting.DesignPatterns.FactoryPattern;

public interface ShapeFactory {
    static Shape createShape(String shapeType){
        if(shapeType == null ){
            return null ;
        }
        if(Shape.TRIANGLE.equals(shapeType)){
            return new Triangle();
        }
        if(Shape.SQUARE.equals(shapeType)){
            return new Square();
        }
        if(Shape.CIRCLE.equals(shapeType)){
            return new Circle();
        }
        return null;
    }
}
