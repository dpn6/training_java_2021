package ru.stqa.dmiv.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testPoint1() {
    Point p1 = new Point(7, 11);
    Point p2 = new Point(4, 7);
//    /Assert.assertEquals(p1.distance(p2), 5.0);
    Assert.assertEquals(p1.distance(p2), 5.1); //испорчен для проверки jenkins
  }

  @Test
  public void testPoint2() {
    Point p1 = new Point(-7, 11);
    Point p2 = new Point(-4, 7);
    Assert.assertEquals(p1.distance(p2), 5.0);
  }

  @Test
  public void testPoint3() {
    Point p1 = new Point(0, 9);
    Point p2 = new Point(0, 9);
    Assert.assertEquals(p1.distance(p2), 0.0);
  }

  @Test
  public void testPoint4() {
    Point p1 = new Point(0, -9);
    Point p2 = new Point(0, -9);
    Assert.assertEquals(p1.distance(p2), 0.0);
  }

  @Test
  public void testPoint5() {
    Point p1 = new Point(0, 1000.9999);
    Point p2 = new Point(0, 1000.9999);
    Assert.assertEquals(p1.distance(p2), 0.0);
  }

  @Test
  public void testPoint6() {
    Point p1 = new Point(7, 0);
    Point p2 = new Point(4, 0);
    Assert.assertEquals(p1.distance(p2), 3.0);
  }

  @Test
  public void testPoint7() {
    Point p1 = new Point(4, 0.2);
    Point p2 = new Point(4, 0.2);
    Assert.assertEquals(p1.distance(p2), 0.0);
  }
}
