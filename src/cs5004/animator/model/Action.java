package cs5004.animator.model;

/**
 * Action is an interface and stores the methods that our cs5004.animator.model.Actions class uses.
 */

public interface Action {

  /**
   * getNameOfShape is a getter that will return the name of the shape.
   *
   * @return the name of said shape.
   */
  String getNameOfShape();

  /**
   * getStartTime will retrieve the start time in which the action will begin.
   *
   * @return start time in which the action will begin.
   */
  int getStartTime();

  /**
   * getEndTime will retrieve the end time in which the action will end.
   *
   * @return end time in which the action will end.
   */
  int getEndTime();


}
