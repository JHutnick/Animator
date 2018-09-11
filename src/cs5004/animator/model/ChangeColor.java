package cs5004.animator.model;

import java.awt.Color;

/**
 * ChangeColor is an action that changes the color of a shape.
 */
public class ChangeColor extends Actions {

  private Color startingColor;
  private Color endingColor;

  /**
   * The following is a constructor to hold onto the action of ChangeColor.  It consists of shape
   * name, start time, end time, start color and end color.
   *
   * @param nameOfShape   is the name of a shape.
   * @param startTime     is the time in which the shape will begin to change color.
   * @param endTime       is the time in which the shape will cease to change color.
   * @param startingColor the original color of the shape.
   * @param endingColor   the new color of the shape.
   */
  ChangeColor(String nameOfShape, int startTime, int endTime,
              Color startingColor, Color endingColor) {
    super(nameOfShape, startTime, endTime);
    this.startingColor = startingColor;
    this.endingColor = endingColor;
  }

  /**
   * Returns the starting color.
   *
   * @return the starting color.
   */
  public Color getStartingColor() {

    return this.startingColor;
  }

  /**
   * Returns the new color.
   *
   * @return the new color.
   */
  public Color getEndingColor() {

    return this.endingColor;
  }

  /**
   * colorChangeActionMethod will be changing a color based on the time.
   *
   * @param currentTime is where the color will be changed based on the time.
   * @return color of the shape at the given time.
   */
  public Color colorChangeActionMethod(double currentTime) {
    double time = endTime - startTime;
    double startRed = startingColor.getRed();
    double startGreen = startingColor.getGreen();
    double startBlue = startingColor.getBlue();
    double endRed = endingColor.getRed();
    double endGreen = endingColor.getGreen();
    double endBlue = endingColor.getBlue();
    double redDifference = endRed - startRed;
    double greenDifference = endGreen - startGreen;
    double blueDifference = endBlue - startBlue;

    double redChangePerTime = redDifference / time;
    double greenChangePerTime = greenDifference / time;
    double blueChangePerTime = blueDifference / time;

    if (currentTime > endTime) {
      return new Color(endingColor.getRed(), endingColor.getGreen(), endingColor.getBlue());
    } else if (currentTime < startTime) {
      return new Color(startingColor.getRed(), startingColor.getGreen(), startingColor.getBlue());
    } else {
      return new Color((float) (startingColor.getRed()
              + (redChangePerTime * (currentTime - startTime))),
              (float) (startingColor.getGreen() + (greenChangePerTime * (currentTime - startTime))),
              (float) (startingColor.getBlue() + (blueChangePerTime * (currentTime - startTime))));
    }
  }
}

