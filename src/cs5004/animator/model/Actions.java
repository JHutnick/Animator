package cs5004.animator.model;

/**
 * Actions is a class that will provide its subclasses with methods that can be used
 * across all subclasses. Our later Actions will be described as methods that will be able to
 * manipulate a shape.
 */

public class Actions implements Action {

  protected String nameOfShape;
  protected int startTime;
  protected int endTime;

  /**
   * Actions constructor that will be used across all subclasses.
   *
   * @param nameOfShape is the name of said shape.
   * @param startTime   start time in which an action will occur.
   * @param endTime     end time in which an action will occur.
   * @throws IllegalArgumentException thrown when start time is less than zero or if
   *                                  end time is less than start time.
   */


  protected Actions(String nameOfShape, int startTime, int endTime) throws
          IllegalArgumentException {
    this.nameOfShape = nameOfShape;
    this.startTime = startTime;
    this.endTime = endTime;

    if (startTime < 0) {
      throw new IllegalArgumentException("Error: action start time cannot be less than zero.");
    }
    if (endTime < startTime) {
      throw new IllegalArgumentException("Error: action end time cannot be less than start time.");
    }
  }

  /**
   * getNameOfShape is a getter that will return the name of the shape.
   *
   * @return the name of said shape.
   */
  public String getNameOfShape() {
    return this.nameOfShape;
  }

  /**
   * getStartTime will retrieve the start time in which the action will begin.
   *
   * @return start time in which the action will begin.
   */
  public int getStartTime() {
    return this.startTime;
  }

  /**
   * getEndTime will retrieve the end time in which the action will end.
   *
   * @return end time in which the action will end.
   */
  public int getEndTime() {
    return this.endTime;
  }

}

