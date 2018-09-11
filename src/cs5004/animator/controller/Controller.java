package cs5004.animator.controller;

import java.util.Objects;

import cs5004.animator.model.AnimationModel;
import cs5004.animator.view.View;

/**
 * Creates a controller object which takes objects from the main and runs the program.
 */
public class Controller implements ControllerInterface {
  private AnimationModel model;
  private View view;

  /**
   * Constructor which creates an object of Controller which consists of a view and a view.
   *
   * @param model the view being passed from the main which consists of all data.
   * @param view  the view instance to display the proper view.
   */
  public Controller(AnimationModel model, View view) {
    this.model = model;
    this.view = view;

  }

  /**
   * The controllerGo takes in a speed and a printout and gives them along with the models list of
   * shapes and actions to the view.
   *
   * @param time the speed at which to adjust the animation.
   * @param out  the print output for the animation.
   */
  public void controllerGo(int time, Appendable out) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(view);

    //give the lists to the view type being passed in
    view.describe(model.getShapeList(), model.getActionList(), time, out);
  }
}

