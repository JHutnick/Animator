package cs5004.animator.view;

import java.util.List;

import cs5004.animator.model.Actions;
import cs5004.animator.model.Shape;

/**
 * The View interface consisting of public methods for the view classes.
 */
public interface View {

  /**
   * Creates a proper string view representation of the data being passed into it.
   *
   * @param shape  the list of shapes to be represented.
   * @param action the list of shape actions to be represented.
   * @param time   the time at which to alter the speed of the animation.
   * @param out    the output type for the string representation.
   */
  void describe(List<Shape> shape, List<Actions> action, int time, Appendable out);
}
