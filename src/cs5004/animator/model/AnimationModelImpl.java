package cs5004.animator.model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

import cs5004.animator.util.TweenModelBuilder;
import cs5004.animator.view.SvgView;
import cs5004.animator.view.TextView;
import cs5004.animator.view.View;
import cs5004.animator.view.VisualView;


/**
 * animationModelImpl implements our animationModel interface.
 */
public final class AnimationModelImpl implements AnimationModel {
  // shapeList is going to be our created list that will remain unchanged.
  private List<Shape> shapeList = new LinkedList<>();
  //  manipulatedShapeList is a list that will be manipulated by our methods.
  private List<Shape> manipulatedShapeList = new LinkedList<>();
  // actionList will hold all of our actions to be applied to our manipulated List.
  private List<Actions> actionList = new LinkedList<>();


  /**
   * addShape will add a new shape to our shapeList.
   *
   * @param shape will be added to our shapeList.
   * @throws IllegalArgumentException when two shapes share the same name.
   */

  @Override
  public void addShape(Shape shape) throws IllegalArgumentException {

    for (Shape aShapeList : shapeList) {
      if (shape.nameOfShape.equals(aShapeList.getNameOfShape())) {
        throw new IllegalArgumentException("Error: no two shapes can share the same name.");
      }
    }

    shapeList.add(shape);
    manipulatedShapeList.add(shape);
  }

  /**
   * addAction will add an action to our actionList.
   *
   * @param action will be added to our actionList.
   */
  @Override
  public void addAction(Actions action) throws IllegalArgumentException {
    int count = 0;

    /*
     * Errors when :
     *  - Action start time is before the shape appears.
     *  - Action end time cannot be greater than before the shape disappears.
     *  - The same action cannot occur while that instance of action is currently occurring.
     */


    for (Shape aShapeList : shapeList) {
      if (action.nameOfShape.equals(aShapeList.nameOfShape)) {
        count++;
        if (action.startTime < aShapeList.getAppears()) {
          throw new IllegalArgumentException("Error: action start time cannot be before the"
                  + " shape appears on screen");
        }
        if (action.endTime > aShapeList.getDisappears()) {
          throw new IllegalArgumentException("Error: action end time cannot be greater than"
                  + " shape disappear time.");
        }
      }
    }

    for (Actions anotherLoop : actionList) {
      if (action.nameOfShape.equals(anotherLoop.nameOfShape)) {

        if (action instanceof Moves && anotherLoop instanceof Moves) {
          if ((action.startTime >= anotherLoop.startTime
                  && action.startTime <= anotherLoop.endTime) ||
                  (action.endTime >= anotherLoop.startTime
                          && action.endTime <= anotherLoop.endTime)) {
            throw new IllegalArgumentException("Error: Move action could not be added"
                    + " because it is trying to take place while another move action is already"
                    + " in motion.");
          }
        }

        if (action instanceof Scale && anotherLoop instanceof Scale) {
          if ((action.startTime >= anotherLoop.startTime
                  && action.startTime <= anotherLoop.endTime) ||
                  (action.endTime >= anotherLoop.startTime
                          && action.endTime <= anotherLoop.endTime)) {
            throw new IllegalArgumentException("Error: Scale action could not be added"
                    + " because it is trying to take place while another scale action is already"
                    + " in motion.");
          }
        }
        if (action instanceof ChangeColor && anotherLoop instanceof ChangeColor) {
          if ((action.startTime >= anotherLoop.startTime
                  && action.startTime <= anotherLoop.endTime) ||
                  (action.endTime >= anotherLoop.startTime
                          && action.endTime < anotherLoop.endTime)) {
            throw new IllegalArgumentException("Error: ChangeColor action could not be added"
                    + " because it is trying to take place while another change color action"
                    + " is already in motion.");
          }
        }
      }
    }


    if (count != 1) {
      throw new IllegalArgumentException("Error: shape name not found within the list. Action"
              + " could not be applied to a shape.");
    }

    actionList.add(action);

  }

  /**
   * conductActions processes actions in the action list.
   *
   * @param time is a variable that will speed up or slow down the speed of the program.
   * @throws IllegalArgumentException when time is less than zero.
   */
  @Override
  public void conductActions(double time) throws IllegalArgumentException {
    if (time < 0) {
      throw new IllegalArgumentException("Error: time cannot less than zero for no action"
              + "will have been conducted.");
    } else {
      int count = 0;
      Point2D.Double newReference;
      Color newColor;
      double newDimension;

      while (count < actionList.size()) {

        if (actionList.get(count) instanceof Moves) {

          // for loop searches to find the shape name in our manipulated list.
          for (int i = 0; i < manipulatedShapeList.size(); i++) {
            if (actionList.get(count).nameOfShape.equals(manipulatedShapeList.get(i).nameOfShape)) {


              // newReference will be a place holder to find the new reference point of our shape.
              newReference =
                      ((Moves) actionList.set(count, new Moves(
                              actionList.get(count).nameOfShape,
                              actionList.get(count).startTime,
                              actionList.get(count).endTime,
                              ((Moves) actionList.get(count)).getStartingPoint(),
                              ((Moves) actionList.get(count)).getEndingPoint())))
                              .movesActionMethod(time);

              // The following will replace the shape within our manipulated list.
              manipulatedShapeList.set(i, new Shape(
                      manipulatedShapeList.get(i).getNameOfShape(),
                      manipulatedShapeList.get(i).getShapeType(),
                      newReference,
                      manipulatedShapeList.get(i).getColor(),
                      manipulatedShapeList.get(i).getWidth(),
                      manipulatedShapeList.get(i).getHeight(),
                      manipulatedShapeList.get(i).getAppears(),
                      manipulatedShapeList.get(i).getDisappears()));
            }
          }
        }

        if (actionList.get(count) instanceof ChangeColor) {

          // for loop searches to find the shape name in our manipulated list.
          for (int i = 0; i < manipulatedShapeList.size(); i++) {
            if (actionList.get(count).nameOfShape.equals(manipulatedShapeList.get(i).nameOfShape)) {

              // newColor will be a place holder to find the new color of our shape.
              newColor =
                      ((ChangeColor) actionList.set(count, new ChangeColor(
                              actionList.get(count).nameOfShape,
                              actionList.get(count).startTime,
                              actionList.get(count).endTime,
                              ((ChangeColor) actionList.get(count)).getStartingColor(),
                              ((ChangeColor) actionList.get(count)).getEndingColor())))
                              .colorChangeActionMethod(time);

              // The following will replace the shape within our manipulated list.
              manipulatedShapeList.set(i, new Shape(
                      manipulatedShapeList.get(i).getNameOfShape(),
                      manipulatedShapeList.get(i).getShapeType(),
                      manipulatedShapeList.get(i).getReference(),
                      newColor,
                      manipulatedShapeList.get(i).getWidth(),
                      manipulatedShapeList.get(i).getHeight(),
                      manipulatedShapeList.get(i).getAppears(),
                      manipulatedShapeList.get(i).getDisappears()));

            }
          }
        }

        if (actionList.get(count) instanceof Scale) {

          // for loop searches to find the shape name in our manipulated list.
          for (int i = 0; i < manipulatedShapeList.size(); i++) {
            if (actionList.get(count).nameOfShape.equals(manipulatedShapeList.get(i).nameOfShape)) {

              // newDimension will be a place holder to find the new dimension of our shape.
              newDimension =
                      ((Scale) actionList.set(count, new Scale(
                              actionList.get(count).nameOfShape,
                              actionList.get(count).startTime,
                              actionList.get(count).endTime,
                              ((Scale) actionList.get(count)).getFromSx(),
                              ((Scale) actionList.get(count)).getFromSy(),
                              ((Scale) actionList.get(count)).getToSx(),
                              ((Scale) actionList.get(count)).getToSy(),
                              ((Scale) actionList.get(count)).getType()))).
                              scaleMethodAction(time);

              manipulatedShapeList.set(i, new Shape(
                      manipulatedShapeList.get(i).getNameOfShape(),
                      manipulatedShapeList.get(i).getShapeType(),
                      manipulatedShapeList.get(i).getReference(),
                      manipulatedShapeList.get(i).getColor(),
                      newDimension,
                      manipulatedShapeList.get(i).getHeight(),
                      manipulatedShapeList.get(i).getAppears(),
                      manipulatedShapeList.get(i).getDisappears()));

              newDimension =
                      ((Scale) actionList.set(count, new Scale(
                              actionList.get(count).nameOfShape,
                              actionList.get(count).startTime,
                              actionList.get(count).endTime,
                              ((Scale) actionList.get(count)).getFromSx(),
                              ((Scale) actionList.get(count)).getFromSy(),
                              ((Scale) actionList.get(count)).getToSx(),
                              ((Scale) actionList.get(count)).getToSy(),
                              ((Scale) actionList.get(count)).getType()))).
                              scaleMethodAction(time);

              manipulatedShapeList.set(i, new Shape(
                      manipulatedShapeList.get(i).getNameOfShape(),
                      manipulatedShapeList.get(i).getShapeType(),
                      manipulatedShapeList.get(i).getReference(),
                      manipulatedShapeList.get(i).getColor(),
                      manipulatedShapeList.get(i).getWidth(),
                      newDimension,
                      manipulatedShapeList.get(i).getAppears(),
                      manipulatedShapeList.get(i).getDisappears()));

            }
          }
        }
        count++;
      }

    }
  }


  /**
   * Returns the list of shapes.
   *
   * @return the list of shapes.
   */
  @Override
  public List<Shape> getShapeList() {
    return this.shapeList;
  }

  /**
   * Returns a list of actions.
   *
   * @return a list of actions.
   */
  @Override
  public List<Actions> getActionList() {
    return this.actionList;
  }

  /**
   * The Builder class is a static final class which acts as an adapter between the
   * AnimationFileReader and our AnimationModelImpl class.  This allows the file reader to easy
   * adapt to how we structured our model.
   */
  public static final class Builder implements TweenModelBuilder<AnimationModel> {
    AnimationModel impl = new AnimationModelImpl();

    /**
     * Add a new oval to the view with the given specifications.
     *
     * @param name        the unique name given to this shape.
     * @param cx          the x-coordinate of the center of the oval.
     * @param cy          the y-coordinate of the center of the oval.
     * @param xRadius     the x-radius of the oval.
     * @param yRadius     the y-radius of the oval.
     * @param red         the red component of the color of the oval.
     * @param green       the green component of the color of the oval.
     * @param blue        the blue component of the color of the oval.
     * @param startOfLife the time tick at which this oval appears.
     * @param endOfLife   the time tick at which this oval disappears.
     * @return the builder object by adding an oval object.
     */
    @Override
    public TweenModelBuilder<AnimationModel> addOval(String name,
                                                     float cx,
                                                     float cy,
                                                     float xRadius,
                                                     float yRadius,
                                                     float red,
                                                     float green,
                                                     float blue,
                                                     int startOfLife,
                                                     int endOfLife) {

      impl.addShape(new Shape(name, ShapeType.OVAL,
              new Point2D.Float(cx, cy), new Color(red, green, blue),
              xRadius, yRadius, startOfLife, endOfLife));
      return this;
    }

    /**
     * Add a new rectangle to the view with the given specifications.
     *
     * @param name        the unique name given to this shape.
     * @param lx          the minimum x-coordinate of a corner of the
     *                    rectangle.
     * @param ly          the minimum y-coordinate of a corner of the
     *                    rectangle.
     * @param width       the width of the rectangle.
     * @param height      the height of the rectangle.
     * @param red         the red component of the color of the rectangle.
     * @param green       the green component of the color of the rectangle.
     * @param blue        the blue component of the color of the rectangle.
     * @param startOfLife the time tick at which this rectangle appears.
     * @param endOfLife   the time tick at which this rectangle disappears.
     * @return the builder object by adding a rectangle object.
     */
    @Override
    public TweenModelBuilder<AnimationModel> addRectangle(String name,
                                                          float lx,
                                                          float ly,
                                                          float width,
                                                          float height,
                                                          float red,
                                                          float green,
                                                          float blue,
                                                          int startOfLife,
                                                          int endOfLife) {
      impl.addShape(new Shape(name, ShapeType.RECTANGLE, new Point2D.Float(lx, ly),
              new Color(red, green, blue), width, height, startOfLife, endOfLife));
      return this;
    }

    /**
     * Move the specified shape to the given position during the given time
     * interval.
     *
     * @param name      the unique name of the shape to be moved
     * @param moveFromX the x-coordinate of the initial position of this shape.
     *                  What this x-coordinate represents depends on the shape.
     * @param moveFromY the y-coordinate of the initial position of this shape.
     *                  what this y-coordinate represents depends on the shape.
     * @param moveToX   the x-coordinate of the final position of this shape. What
     *                  this x-coordinate represents depends on the shape.
     * @param moveToY   the y-coordinate of the final position of this shape. what
     *                  this y-coordinate represents depends on the shape.
     * @param startTime the time tick at which this movement should start
     * @param endTime   the time tick at which this movement should end
     * @return the builder object by adding a cs5004.animator.model.Moves action.
     */
    @Override
    public TweenModelBuilder<AnimationModel> addMove(String name,
                                                     float moveFromX,
                                                     float moveFromY,
                                                     float moveToX,
                                                     float moveToY,
                                                     int startTime,
                                                     int endTime) {
      impl.addAction(new Moves(name, startTime, endTime,
              new Point2D.Float(moveFromX, moveFromY), new Point2D.Float(moveToX, moveToY)));
      return this;
    }

    /**
     * Change the color of the specified shape to the new specified color in the
     * specified time interval.
     *
     * @param name      the unique name of the shape whose color is to be changed.
     * @param oldR      the r-component of the old color.
     * @param oldG      the g-component of the old color.
     * @param oldB      the b-component of the old color.
     * @param newR      the r-component of the new color.
     * @param newG      the g-component of the new color.
     * @param newB      the b-component of the new color.
     * @param startTime the time tick at which this color change should start.
     * @param endTime   the time tick at which this color change should end.
     * @return the builder object by adding a cs5004.animator.model.ChangeColor action.
     */
    @Override
    public TweenModelBuilder<AnimationModel> addColorChange(String name,
                                                            float oldR,
                                                            float oldG,
                                                            float oldB,
                                                            float newR,
                                                            float newG,
                                                            float newB,
                                                            int startTime,
                                                            int endTime) {
      //  cs5004.animator.model.ChangeColor(String nameOfShape, int startTime, int endTime,
      //                        Color startingColor, Color endingColor)
      impl.addAction(new ChangeColor(name, startTime, endTime, new Color(oldR, oldG, oldB),
              new Color(newR, newG, newB)));
      return this;
    }

    /**
     * Scales the width and or height lengths of a shape.
     *
     * @param name      the unique name of the shape whose color is to be changed
     * @param fromSx    the starting width length of a shape.
     * @param fromSy    the starting height length of a shape.
     * @param toSx      the ending width length of a shape.
     * @param toSy      the ending height length of a shape.
     * @param startTime the time tick at which this color change should start
     * @param endTime   the time tick at which this color change should end
     * @return the builder object by adding a Scales action.
     */
    @Override
    public TweenModelBuilder<AnimationModel> addScaleToChange(String name,
                                                              float fromSx,
                                                              float fromSy,
                                                              float toSx,
                                                              float toSy,
                                                              int startTime,
                                                              int endTime) {


      for (int i = 0; i < impl.getShapeList().size(); i++) {
        if (impl.getShapeList().get(i).toString().contains(name) &&
                impl.getShapeList().get(i).toString().contains("RECTANGLE")) {
          impl.addAction(new Scale(name, startTime, endTime,
                  fromSx, fromSy, toSx, toSy, ShapeType.RECTANGLE));
        }
        if (impl.getShapeList().get(i).toString().contains(name) &&
                impl.getShapeList().get(i).toString().contains("OVAL")) {
          impl.addAction(new Scale(name, startTime, endTime,
                  fromSx, fromSy, toSx, toSy, ShapeType.OVAL));
        }
      }

      return this;
    }

    /**
     * Creates and returns an cs5004.animator.view.cs5004.animator.model.AnimationModel.
     *
     * @return an cs5004.animator.view.cs5004.animator.model.AnimationModel.
     */
    @Override
    public AnimationModel build() {
      return impl;
    }

  }

  /**
   * Viewer returns proper view instance determined by user.
   */
  public static final class Viewer {

    /**
     * Viewer returns proper view instance determined by user.
     *
     * @param str the view to be created.
     * @return an instance of selected view.
     * @throws IllegalArgumentException when improper view is requested.
     */
    public static View getView(String str) throws IllegalArgumentException {
      if (str.contains("svg")) {
        return new SvgView();
      } else if (str.contains("text")) {
        return new TextView();
      } else if (str.contains("visual")) {
        return new VisualView();
      } else {
        throw new IllegalArgumentException("Error: wrong string passed. Please try text or svg.");
      }
    }
  }
}

