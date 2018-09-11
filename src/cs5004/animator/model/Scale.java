package cs5004.animator.model;

/**
 * Scale extends actions interface which includes methods to
 * enlarge or shorten a given dimension of a shape.
 */
public class Scale extends Actions {
  private float toSx;
  private float toSy;
  private float fromSx;
  private float fromSy;
  private ShapeType type;

  /**
   * Scale will allow us to manipulate our shape to a shape we so wish.
   *
   * @param nameOfShape name of the shape.
   * @param startTime   the time in which the action begins.
   * @param endTime     the time in which the action ends.
   * @param fromSx      starting width.
   * @param fromSy      starting height.
   * @param toSx        ending width.
   * @param toSy        ending height.
   */
  Scale(String nameOfShape, int startTime, int endTime, float fromSx,
        float fromSy, float toSx, float toSy, ShapeType type)
          throws IllegalArgumentException {
    super(nameOfShape, startTime, endTime);
    this.fromSx = fromSx;
    this.fromSy = fromSy;
    this.toSx = toSx;
    this.toSy = toSy;
    this.type = type;

    if (fromSx < 0 || fromSy < 0 || toSx < 0 || toSy < 0) {
      throw new IllegalArgumentException("Error: dimensions cannot be less than zero.");
    }
  }

  /**
   * Returns the starting width of the shape.
   *
   * @return the starting width of the shape.
   */
  public float getFromSx() {

    return fromSx;
  }

  /**
   * Returns the starting height of the shape.
   *
   * @return the starting height of the shape.
   */
  public float getFromSy() {

    return fromSy;
  }

  /**
   * Returns the ending width of the shape.
   *
   * @return the ending width of the shape.
   */
  public float getToSx() {

    return toSx;
  }

  /**
   * Returns the ending height of the shape.
   *
   * @return the ending height of the shape.
   */
  public float getToSy() {

    return toSy;
  }

  /**
   * Returns the cs5004.animator.model.ShapeType of the shape.
   *
   * @return the cs5004.animator.model.ShapeType of the shape.
   */
  public ShapeType getType() {

    return type;
  }

  /**
   * scaleMethodAction is the method that will actually scale a specific part of a shape
   * to a different dimension.
   *
   * @param currentTime time given, this is how the shape will gradually be changed.
   * @return the new dimension of the shape at the given time.
   */
  public double scaleMethodAction(double currentTime) {
    double time;
    time = endTime - startTime;
    double dimensionChange;
    double dimensionChangePerTime;
    double newDimension = 0;

    if (currentTime < endTime) {
      if (fromSx < toSx || fromSx > toSx) {
        dimensionChange = toSx - fromSx;
        dimensionChangePerTime = dimensionChange / time;

        if (currentTime > endTime) {
          newDimension = toSx;
          return newDimension;
        } else if (currentTime < startTime) {
          newDimension = fromSx;
          return newDimension;
        } else {
          newDimension = fromSx + (dimensionChangePerTime * (currentTime - startTime));
          return newDimension;
        }
      }

      if (fromSy < toSy || fromSy > toSy) {
        dimensionChange = toSy - fromSy;
        dimensionChangePerTime = dimensionChange / time;

        if (currentTime > endTime) {
          newDimension = toSy;
          return newDimension;
        } else if (currentTime < startTime) {
          newDimension = fromSy;
          return newDimension;
        } else {
          newDimension = fromSy + (dimensionChangePerTime * (currentTime - startTime));
          return newDimension;
        }
      }
    }

    return newDimension;
  }
}

