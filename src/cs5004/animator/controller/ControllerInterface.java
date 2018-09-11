package cs5004.animator.controller;

/**
 * A controller interface consisting of the methods used by the controller.
 */
public interface ControllerInterface {

  /**
   * The controllerGo takes in a speed and a printout and gives them along with the models list of
   * shapes and actions to the view.
   *
   * @param time the speed at which to adjust the animation.
   * @param out  the print output for the animation.
   */
  void controllerGo(int time, Appendable out);
}
