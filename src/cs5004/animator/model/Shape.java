package cs5004.animator.model;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Shape class will represent a 2D shape.  Implements ShapeInterface which consists of all the
 * public methods for gathering Shapes information.
 */
public class Shape implements ShapeInterface {
  private Point2D reference;
  protected String nameOfShape;
  private Color color;
  private int appears;
  private int disappears;
  private double width;
  private double height;
  private ShapeType typeOfShape;

  /**
   * Shape constructor which consists of the shape name, type, starting point, color,
   * width, height, start time and end time.
   *
   * @param nameOfShape is the specific name of a shape.
   * @param typeOfShape is the specific type of a shape.
   * @param reference   is the starting point of the shape.
   * @param color       is the starting color of a shape.
   * @param width       is the starting width of a shape.
   * @param height      is the starting height of the shape.
   * @param appears     time in which the shape appears on a background.
   * @param disappears  time in which a shape disappears from a background.
   * @throws IllegalArgumentException is thrown when appears, height, and width is less than 0,
   *                                  and when disappears is less than appears.
   */
  public Shape(String nameOfShape, ShapeType typeOfShape, Point2D reference, Color color,
               double width, double height, int appears, int disappears)
          throws IllegalArgumentException {
    this.nameOfShape = nameOfShape;
    this.typeOfShape = typeOfShape;
    this.reference = reference;
    this.color = color;
    this.appears = appears;
    this.disappears = disappears;
    this.width = width;
    this.height = height;

    if (appears < 0) {
      throw new IllegalArgumentException("Error: appears cannot be less than zero since it is a "
              + "time frame.");
    }
    if (disappears < appears) {
      throw new IllegalArgumentException("Error: appears cannot be less than disappear, this time"
              + "frame does not exist.");
    }
    if (height < 0) {
      throw new IllegalArgumentException("Error: height cannot be less than zero since it a "
              + "measurement, and measurements cannot be nothing or negative.");
    }
    if (width < 0) {
      throw new IllegalArgumentException("Error: height cannot be less than zero since it a "
              + "measurement, and measurements cannot be nothing or negative.");
    }
  }

  /**
   * Returns the name of the shape.
   *
   * @return the name of the shape.
   */
  public String getNameOfShape() {

    return nameOfShape;
  }

  /**
   * Returns the starting point of a shape.
   *
   * @return the starting point of a shape.
   */
  public Point2D getReference() {

    return this.reference;
  }

  /**
   * Returns the starting color of a shape.
   *
   * @return the starting color of a shape.
   */
  public Color getColor() {

    return this.color;
  }

  /**
   * Starting time in which a shape appears on a background.
   *
   * @return the starting time in which a shape appears on a background.
   */
  public int getAppears() {

    return this.appears;
  }

  /**
   * Starting time in which a shape disappears on a background.
   *
   * @return the starting time in which a shape disappears on a background.
   */
  public int getDisappears() {

    return this.disappears;
  }

  /**
   * Getter to return the width of the shape.
   *
   * @return the width of the shape.
   */
  public double getWidth() {

    return this.width;
  }

  /**
   * Getter to return the height of the shape.
   *
   * @return the height of the shape.
   */
  public double getHeight() {

    return this.height;
  }

  /**
   * Getter for returning the type of shape.
   *
   * @return the type of shape.
   */
  public ShapeType getShapeType() {

    return this.typeOfShape;
  }

}

