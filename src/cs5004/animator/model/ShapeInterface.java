package cs5004.animator.model;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Creates a ShapeInterface which includes all the public methods to be used by the Shape classes.
 */
public interface ShapeInterface {

  /**
   * Returns the name of the current shape object.
   *
   * @return the name of the current shape object.
   */
  String getNameOfShape();

  /**
   * Return the starting point of a shape.
   *
   * @return the starting point of a shape.
   */
  Point2D getReference();

  /**
   * Returns the starting color of a shape.
   *
   * @return the starting color of a shape.
   */
  Color getColor();

  /**
   * Starting time in which a shape appears on a background.
   *
   * @return the starting time in which a shape appears on a background.
   */
  int getAppears();

  /**
   * Starting time in which a shape disappears on a background.
   *
   * @return the starting time in which a shape disappears on a background.
   */
  int getDisappears();

  /**
   * Returns the width of the shape.
   *
   * @return the width of the shape.
   */
  double getWidth();

  /**
   * Returns the height of the shape.
   *
   * @return the height of the shape.
   */
  double getHeight();

  /**
   * Returns the type of shape.
   *
   * @return the type of shape.
   */
  ShapeType getShapeType();

}
