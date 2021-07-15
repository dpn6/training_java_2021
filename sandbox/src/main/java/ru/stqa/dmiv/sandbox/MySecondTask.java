package ru.stqa.dmiv.sandbox;

public class MySecondTask {

  public static double distance(Point p1, Point p2){
    return Math.sqrt((p2.x - p1.x)*(p2.x - p1.x) + (p2.y - p1.y)*(p2.y - p1.y));
  }

  public static void main(String [] args){
    Point p1 = new Point(7, 11);
    Point p2 = new Point(4, 7);

    System.out.println("Расстояние между двумя точкам с координатами первой точки x: " + p1.x + ", y: " + p1.y
    + " и координатами второй точки x: "  + p2.x + ", y: " + p2.y + " равняется " + distance(p1, p2) );

    System.out.println("Вычисление через метод, ассоциированный с объектом. Расстояние между двумя точкам с координатами первой точки x: " + p1.x + ", y: " + p1.y
            + " и координатами второй точки x: "  + p2.x + ", y: " + p2.y + " равняется " + p1.distance(p2));

  }
}
