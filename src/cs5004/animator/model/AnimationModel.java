package cs5004.animator.model;

import java.util.List;

/**
 * AnimationModel is our version of a view in an MVC.
 */
public interface AnimationModel {


  /**
   * addShape will add a new shape to our shapeList.
   *
   * @param shape will be added to our shapeList.
   */
  void addShape(Shape shape);

  /**
   * addAction will add an action to our actionList.
   *
   * @param action will be added to our actionList.
   */
  void addAction(Actions action);

  /**
   * conductActions will manipulate the manipulatedShapeList based on the actions to be
   * conducted on them.
   */
  void conductActions(double time);

  /**
   * Returns a list of shapes.
   *
   * @return a list of shapes.
   */
  List<Shape> getShapeList();


  /**
   * Returns a list of actions.
   *
   * @return a list of actions.
   */
  List<Actions> getActionList();
}
