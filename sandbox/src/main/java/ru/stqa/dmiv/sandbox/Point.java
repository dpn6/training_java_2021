package ru.stqa.dmiv.sandbox;

public class Point {
  public double x;
  public double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double distance(Point otherPoint){
    return Math.sqrt((otherPoint.x - this.x)*(otherPoint.x - this.x) + (otherPoint.y - this.y)*(otherPoint.y - this.y));
  }
}