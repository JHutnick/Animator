package cs5004.animator.model;

import java.awt.geom.Point2D;

/**
 * Moves extends Actions which contains methods that will allow a shape to move
 * from on point to another.
 */
public class Moves extends Actions {
  private Point2D startingPoint;
  private Point2D endingPoint;

  /**
   * The following is a constructor to hold onto the action of Moves. It consists of name of shape,
   * startTime, endTime, staring location and ending location.
   *
   * @param nameOfShape   is the name of a shape.
   * @param startTime     is the time in which the shape will begin to move.
   * @param endTime       is the time in which the shape will cease to move.
   * @param startingPoint the starting point of the shape.
   * @param endingPoint   the ending point of the move action.
   */
  Moves(String nameOfShape, int startTime, int endTime,
        Point2D startingPoint, Point2D endingPoint) {
    super(nameOfShape, startTime, endTime);
    this.startingPoint = startingPoint;
    this.endingPoint = endingPoint;
  }

  /**
   * Returns the starting point of a shape.
   *
   * @return returns the starting point of a shape.
   */
  public Point2D getStartingPoint() {

    return this.startingPoint;
  }

  /**
   * Returns the ending point of a shape.
   *
   * @return returns the ending point of a shape.
   */
  public Point2D getEndingPoint() {

    return this.endingPoint;
  }

  /**
   * Returns a new point based on the current time.
   *
   * @param currentTime is the current time in which the program will begin.
   * @return new point based on the current time.
   */
  public Point2D.Double movesActionMethod(double currentTime) {

    double time = endTime - startTime;
    double xToGo = endingPoint.getX() - startingPoint.getX();
    double yToGo = endingPoint.getY() - startingPoint.getY();
    double xPerTime = xToGo / time;
    double yPerTime = yToGo / time;
    Point2D.Double currentPosition;

    if (currentTime > endTime) {
      currentPosition = new Point2D.Double(endingPoint.getX(), endingPoint.getY());
      return currentPosition;
    } else if (currentTime < startTime) {
      currentPosition = new Point2D.Double(startingPoint.getX(), startingPoint.getY());
      return currentPosition;
    } else {
      currentPosition = new Point2D.Double(
              startingPoint.getX() + (xPerTime * (currentTime - startTime)),
              startingPoint.getY() + (yPerTime * (currentTime - startTime)));
      return currentPosition;
    }
  }


}

