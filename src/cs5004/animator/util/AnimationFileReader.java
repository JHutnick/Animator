package cs5004.animator.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * This class represents a file reader for the animation file. This reads in the
 * file in the prescribed file format, and relies on a view builder interface.
 * The user of this class should create a view builder that implements this
 * interface.
 */

public class AnimationFileReader {

  /**
   * Read the animation file and use the builder to build a view.
   *
   * @param fileName the path of the file to be read
   * @param builder  the builder used to build the view
   * @param <T>      the type of view
   * @return the view
   * @throws FileNotFoundException  if the specified file cannot be read
   * @throws InputMismatchException if some data value is not of the expected
   *                                type
   * @throws IllegalStateException  if an illegal token is read from the file
   */
  public <T> T readFile(String fileName, TweenModelBuilder<T> builder) throws
          FileNotFoundException, IllegalStateException, InputMismatchException {
    Scanner sc;

    sc = new Scanner(new FileInputStream(fileName));

    while (sc.hasNext()) {
      String command = sc.next();
      ShapeInfo shapeInfo;
      switch (command) {
        case "rectangle":
          RectangleInfo rinfo = readRectangleInfo(sc);
          builder.addRectangle(
                  rinfo.getName(),
                  rinfo.getX(), rinfo.getY(),
                  rinfo.getWidth(), rinfo.getHeight(),
                  rinfo.getR(), rinfo.getG(), rinfo.getB(),
                  rinfo.getStart(), rinfo.getEnd());
          break;
        case "oval":
          OvalInfo cinfo = readOvalInfo(sc);
          builder.addOval(
                  cinfo.getName(),
                  cinfo.getX(), cinfo.getY(),
                  cinfo.getXRadius(), cinfo.getYRadius(),
                  cinfo.getR(), cinfo.getG(), cinfo.getB(),
                  cinfo.getStart(), cinfo.getEnd());
          break;
        case "move":
          MoveInfo minfo = readMoveInfo(sc);
          builder.addMove(
                  minfo.getName(),
                  minfo.getFromX(),
                  minfo.getFromY(),
                  minfo.getToX(),
                  minfo.getToY(),
                  minfo.getStart(),
                  minfo.getEnd());
          break;
        case "change-color":
          ChangeColorInfo colorInfo = readChangeColorInfo(sc);
          builder.addColorChange(colorInfo.name,
                  colorInfo.getFromR(),
                  colorInfo.getFromG(),
                  colorInfo.getFromB(),
                  colorInfo.getToR(),
                  colorInfo.getToG(),
                  colorInfo.getToB(),
                  colorInfo.getStart(),
                  colorInfo.getEnd());
          break;
        case "scale":
          ScaleByInfo scaleByInfo = readScaleByInfo(sc);
          builder.addScaleToChange(scaleByInfo.name,
                  scaleByInfo.getFromXScale(),
                  scaleByInfo.getFromYScale(),
                  scaleByInfo.getToXScale(),
                  scaleByInfo.getToYScale(),
                  scaleByInfo.getStart(),
                  scaleByInfo.getEnd());
          break;
        default:
          throw new IllegalStateException("Unidentified token " + command + " "
                  + "read from file");

      }
    }
    return builder.build();
  }

  /**
   * rectangleInfo reads in information pertaining to a rectangle.
   *
   * @param sc is a scanner that will take in scanned variables.
   * @return a rectangle's information in order to create a rectangle object.
   * @throws IllegalStateException  if a variable is incorrect or an unmatched attribute of an
   *                                object than an exception will be thrown.
   * @throws InputMismatchException if the input does not exist an exception will be thrown
   */
  private RectangleInfo readRectangleInfo(Scanner sc) throws
          IllegalStateException, InputMismatchException {
    RectangleInfo info = new RectangleInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "min-x":
          info.setX(sc.nextFloat());
          break;
        case "min-y":
          info.setY(sc.nextFloat());
          break;
        case "width":
          info.setWidth(sc.nextFloat());
          break;
        case "height":
          info.setHeight(sc.nextFloat());
          break;
        case "color":
          info.setR(sc.nextFloat());
          info.setG(sc.nextFloat());
          info.setB(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
                  + "rectangle");
      }
    }

    return info;
  }

  /**
   * ovalInfo reads in information pertaining to an oval.
   *
   * @param sc is a scanner that will take in scanned variables.
   * @return a ovals's information in order to create a rectangle object.
   * @throws IllegalStateException  if a variable is incorrect or an unmatched attribute of an
   *                                object than an exception will be thrown.
   * @throws InputMismatchException if the input does not exist an exception will be thrown
   */
  private OvalInfo readOvalInfo(Scanner sc) throws
          IllegalStateException, InputMismatchException {
    OvalInfo info = new OvalInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "center-x":
          info.setX(sc.nextFloat());
          break;
        case "center-y":
          info.setY(sc.nextFloat());
          break;
        case "x-radius":
          info.setXRadius(sc.nextFloat());
          break;
        case "y-radius":
          info.setYRadius(sc.nextFloat());
          break;
        case "color":
          info.setR(sc.nextFloat());
          info.setG(sc.nextFloat());
          info.setB(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
                  + "oval");
      }
    }

    return info;
  }

  /**
   * Reads in actions pertaining to the cs5004.animator.model.Moves action class.
   *
   * @param sc is a scanner that will take in scanned variables.
   * @return a cs5004.animator.model.Moves information in order to create a moves object.
   * @throws IllegalStateException  if a variable is incorrect or an unmatched attribute of an
   *                                object than an exception will be thrown.
   * @throws InputMismatchException if the input does not exist an exception will be thrown
   */
  private MoveInfo readMoveInfo(Scanner sc) throws
          IllegalStateException, InputMismatchException {
    MoveInfo info = new MoveInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "moveto":
          info.setFromX(sc.nextFloat());
          info.setFromY(sc.nextFloat());
          info.setToX(sc.nextFloat());
          info.setToY(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
                  + "move");
      }
    }

    return info;
  }

  /**
   * Reads in actions pertaining to the ChangeColor action class.
   *
   * @param sc is a scanner that will take in scanned variables.
   * @return a ChangeColor's information in order to create a changeColor object.
   * @throws IllegalStateException  if a variable is incorrect or an unmatched attribute of an
   *                                object than an exception will be thrown.
   * @throws InputMismatchException if the input does not exist an exception will be thrown
   */
  private ChangeColorInfo readChangeColorInfo(Scanner sc) throws
          IllegalStateException, InputMismatchException {
    ChangeColorInfo info = new ChangeColorInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "colorto":
          info.setFromR(sc.nextFloat());
          info.setFromG(sc.nextFloat());
          info.setFromB(sc.nextFloat());
          info.setToR(sc.nextFloat());
          info.setToG(sc.nextFloat());
          info.setToB(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
                  + "change-color");
      }
    }

    return info;
  }

  /**
   * Reads in actions pertaining to the Scales action class.
   *
   * @param sc is a scanner that will take in scanned variables.
   * @return a Scales information in order to create a scales object.
   * @throws IllegalStateException  if a variable is incorrect or an unmatched attribute of an
   *                                object than an exception will be thrown.
   * @throws InputMismatchException if the input does not exist an exception will be thrown
   */
  private ScaleByInfo readScaleByInfo(Scanner sc) throws
          IllegalStateException, InputMismatchException {
    ScaleByInfo info = new ScaleByInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "scaleto":
          info.setFromXScale(sc.nextFloat());
          info.setFromYScale(sc.nextFloat());
          info.setToXScale(sc.nextFloat());
          info.setToYScale(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
                  + "scale-to");
      }
    }

    return info;
  }

  /**
   * Inputable holds the flags that pertains to a protected hash map.
   */
  class Inputable {
    protected Map<String, Boolean> valueFlags;

    public Inputable() {
      valueFlags = new HashMap<String, Boolean>();

    }

    /**
     * checks to see if all has been initialized in the map.
     *
     * @return if all has been initialized method will return true, false if not.
     */

    public boolean isAllInitialized() {
      for (Map.Entry<String, Boolean> entry : valueFlags.entrySet()) {
        if (!entry.getValue()) {
          return false;
        }
      }
      return true;
    }
  }

  /**
   * ShapeInfo will grab information pertaining to the shape and extends the inputable class.
   */
  class ShapeInfo extends Inputable {
    private String name;
    private float r;
    private float g;
    private float b;
    private int start;
    private int end;


    /**
     * ShapeInfo constructor will grab information pertaining to a shape.
     */
    ShapeInfo() {
      super();
      valueFlags.put("name", false);
      valueFlags.put("r", false);
      valueFlags.put("g", false);
      valueFlags.put("b", false);
      valueFlags.put("start", false);
      valueFlags.put("end", false);
    }

    /**
     * sets the name of the shape.
     *
     * @param name is the name of the shape.
     */
    void setName(String name) {
      this.name = name;
      valueFlags.replace("name", true);
    }

    /**
     * sets the red color of the shape.
     *
     * @param r the red float version of the color.
     */
    void setR(float r) {
      this.r = r;
      valueFlags.replace("r", true);
    }

    /**
     * sets the green color of the shape.
     *
     * @param g the green float version of the color.
     */
    void setG(float g) {
      this.g = g;
      valueFlags.replace("g", true);
    }

    /**
     * sets the blue color of the shape.
     *
     * @param b the blue float version of the color.
     */
    void setB(float b) {
      this.b = b;
      valueFlags.replace("b", true);
    }

    /**
     * is when the starting time in which the shape will appear.
     *
     * @param start start time in which the shape will appear.
     */
    void setStart(int start) {
      this.start = start;
      valueFlags.replace("start", true);
    }

    /**
     * end time in which the shape will disappear from the screen.
     *
     * @param end time in which the shape will disappear.
     */
    void setEnd(int end) {
      this.end = end;
      valueFlags.replace("end", true);
    }

    /**
     * returns the red value of color in float form.
     *
     * @return the red value of color in float form.
     */
    float getR() {
      return r;
    }

    /**
     * returns the green value of color in float form.
     *
     * @return the green value of color in float form.
     */
    float getG() {
      return g;
    }

    /**
     * returns the blue value of color in float form.
     *
     * @return the blue value of color in float form.
     */
    float getB() {
      return b;
    }

    /**
     * returns the name of the shape.
     *
     * @return the name of the shape.
     */
    String getName() {
      return name;
    }

    /**
     * returns the start time of the shape.
     *
     * @return the start time of the shape.
     */
    public int getStart() {
      return start;
    }

    /**
     * Returns the end time of the shape.
     *
     * @return the end time of the shape.
     */
    public int getEnd() {
      return end;
    }


  }

  /**
   * RectangleInfo a class stores and holds the rectangle shape object information.
   */
  class RectangleInfo extends ShapeInfo {
    private float x;
    private float y;
    private float width;
    private float height;

    /**
     * Constructor to create the information with regards to a rectangle.
     */
    RectangleInfo() {
      super();
      valueFlags.put("x", false);
      valueFlags.put("y", false);
      valueFlags.put("width", false);
      valueFlags.put("height", false);
    }

    /**
     * sets the X value of the minimum corner of the rectangle.
     *
     * @param x value of the minimum corner of the rectangle.
     */
    void setX(float x) {
      this.x = x;
      valueFlags.replace("x", true);
    }

    /**
     * sets the Y value of the minimum corner of the rectangle.
     *
     * @param y value of the minimum corner of the rectangle.
     */
    void setY(float y) {
      this.y = y;
      valueFlags.replace("y", true);
    }

    /**
     * Sets the width of the rectangle.
     *
     * @param width the width of the rectangle to be set.
     */
    void setWidth(float width) {
      this.width = width;
      valueFlags.replace("width", true);
    }

    /**
     * Sets the height of the rectangle.
     *
     * @param height height width of the rectangle to be set.
     */
    void setHeight(float height) {
      this.height = height;
      valueFlags.replace("height", true);
    }

    /**
     * Returns the X value of the minimum corner of the rectangle.
     *
     * @return x value of the minimum corner of the rectangle.
     */
    float getX() {
      return x;
    }

    /**
     * Returns the Y value of the minimum corner of the rectangle.
     *
     * @return y value of the minimum corner of the rectangle.
     */
    float getY() {
      return y;
    }

    /**
     * Returns the width value of the minimum corner of the rectangle.
     *
     * @return width value of the minimum corner of the rectangle.
     */
    float getWidth() {
      return width;
    }

    /**
     * Returns the height value of the minimum corner of the rectangle.
     *
     * @return height value of the minimum corner of the rectangle.
     */
    float getHeight() {
      return height;
    }
  }

  /**
   * OvalInfo a class stores and holds the oval shape object information.
   */
  class OvalInfo extends ShapeInfo {
    private float cx;
    private float cy;
    private float xradius;
    private float yradius;

    /**
     * Constructor to create the information with regards to an oval.
     */
    OvalInfo() {
      super();
      valueFlags.put("cx", false);
      valueFlags.put("cy", false);
      valueFlags.put("xradius", false);
      valueFlags.put("yradius", false);
    }

    /**
     * sets the X value of the center of the oval.
     *
     * @param x value of the center of the oval.
     */
    void setX(float x) {
      this.cx = x;
      valueFlags.replace("cx", true);
    }

    /**
     * sets the Y value of the center of the oval.
     *
     * @param y value of the center of the oval.
     */
    void setY(float y) {
      this.cy = y;
      valueFlags.replace("cy", true);
    }

    /**
     * sets the cx radius value of the oval.
     *
     * @param radius cx radius value of the oval.
     */
    void setXRadius(float radius) {
      this.xradius = radius;
      valueFlags.replace("xradius", true);
    }

    /**
     * sets the cy radius value of the oval.
     *
     * @param radius cy radius value of the oval.
     */
    void setYRadius(float radius) {
      this.yradius = radius;
      valueFlags.replace("yradius", true);
    }

    /**
     * Gets the cx point value of the oval.
     *
     * @return cx point value of the oval.
     */
    float getX() {
      return cx;
    }

    /**
     * Gets the cy point value of the oval.
     *
     * @return cy point value of the oval.
     */
    float getY() {
      return cy;
    }

    /**
     * Returns the x-radius of the oval.
     *
     * @return the x-radius of the oval.
     */
    float getXRadius() {
      return xradius;
    }

    /**
     * Returns the y-radius of the oval.
     *
     * @return the y-radius of the oval.
     */
    float getYRadius() {
      return yradius;
    }

  }

  /**
   * Holds and stores the cs5004.animator.model.Moves class's information in order to create
   * the action Moves.
   */
  class MoveInfo extends Inputable {
    private String name;
    private float fromX;
    private float fromY;
    private float toX;
    private float toY;
    private int start;
    private int end;

    /**
     * Constructor to hold and store the cs5004.animator.model.Moves class's information
     * in order to create the action cs5004.animator.model.Moves.
     */
    MoveInfo() {
      super();

      valueFlags.put("name", false);
      valueFlags.put("fromx", false);
      valueFlags.put("fromy", false);
      valueFlags.put("tox", false);
      valueFlags.put("toy", false);
      valueFlags.put("start", false);
      valueFlags.put("end", false);

    }

    /**
     * Sets the name of the shape.
     *
     * @param name the name of the shape.
     */
    void setName(String name) {
      this.name = name;
      valueFlags.replace("name", true);
    }

    /**
     * Sets the beginning x position value of the shape.
     *
     * @param x the beginning x position value of the shape.
     */
    void setFromX(float x) {
      this.fromX = x;
      valueFlags.replace("fromx", true);
    }

    /**
     * Sets the beginning y position value of the shape.
     *
     * @param y the beginning y position value of the shape.
     */
    void setFromY(float y) {
      this.fromY = y;
      valueFlags.replace("fromy", true);
    }

    /**
     * Sets the ending x position value of the shape.
     *
     * @param x the ending x position value of the shape.
     */
    void setToX(float x) {
      this.toX = x;
      valueFlags.replace("tox", true);
    }

    /**
     * Sets the ending y position value of the shape.
     *
     * @param y the ending y position value of the shape.
     */
    void setToY(float y) {
      this.toY = y;
      valueFlags.replace("toy", true);
    }

    /**
     * Sets the starting time in which the action begins to move the shape.
     *
     * @param start the starting time in which the action begins to move the shape.
     */
    void setStart(int start) {
      this.start = start;
      valueFlags.replace("start", true);
    }

    /**
     * Sets the ending time in which the action ceases to move the shape.
     *
     * @param end the ending time in which the action ceases to move the shape.
     */
    void setEnd(int end) {
      this.end = end;
      valueFlags.replace("end", true);
    }

    /**
     * Returns the name of the shape.
     *
     * @return name, the name of the shape.
     */
    String getName() {
      return name;
    }

    /**
     * Returns the x position starting value of the shape.
     *
     * @return the x position starting value of the shape.
     */
    float getFromX() {
      return fromX;
    }

    /**
     * Returns the y position starting value of the shape.
     *
     * @return the y position starting value of the shape.
     */
    float getFromY() {
      return fromY;
    }

    /**
     * Returns the x position ending value of the shape.
     *
     * @return the x position ending value of the shape.
     */
    float getToX() {
      return toX;
    }

    /**
     * Returns the y position ending value of the shape.
     *
     * @return the y position ending value of the shape.
     */
    float getToY() {
      return toY;
    }

    /**
     * Returns the start time when the shape will begin to move.
     *
     * @return the start time when the shape will begin to move.
     */
    int getStart() {
      return start;
    }

    /**
     * Returns the end time when the shape will cease to move.
     *
     * @return the end time when the shape will cease to move.
     */
    int getEnd() {
      return end;
    }
  }

  /**
   * Holds and stores the ChangeColor class's information
   * in order to create the action ChangeColor.
   */
  class ChangeColorInfo extends Inputable {
    private String name;
    private float fromR;
    private float fromG;
    private float fromB;
    private float toR;
    private float toG;
    private float toB;
    private int start;
    private int end;

    /**
     * Constructor to hold and store the ChangeColor class's information
     * in order to create the action ChangeColor.
     */
    ChangeColorInfo() {
      super();

      valueFlags.put("name", false);
      valueFlags.put("tor", false);
      valueFlags.put("tog", false);
      valueFlags.put("tob", false);
      valueFlags.put("fromr", false);
      valueFlags.put("fromg", false);
      valueFlags.put("fromb", false);
      valueFlags.put("start", false);
      valueFlags.put("end", false);

    }

    /**
     * Sets the name of the shape.
     *
     * @param name the name of the shape.
     */
    void setName(String name) {
      this.name = name;
      valueFlags.replace("name", true);
    }

    /**
     * Sets the red starting color value of the shape.
     *
     * @param r the red starting color value of the shape.
     */
    void setFromR(float r) {
      this.fromR = r;
      valueFlags.replace("fromr", true);
    }

    /**
     * Sets the green starting color value of the shape.
     *
     * @param g the green starting color value of the shape.
     */
    void setFromG(float g) {
      this.fromG = g;
      valueFlags.replace("fromg", true);
    }

    /**
     * Sets the blue starting color value of the shape.
     *
     * @param b the blue starting color value of the shape.
     */
    void setFromB(float b) {
      this.fromB = b;
      valueFlags.replace("fromb", true);
    }

    /**
     * Sets the red ending color value of the shape.
     *
     * @param r the red ending color value of the shape.
     */
    void setToR(float r) {
      this.toR = r;
      valueFlags.replace("tor", true);
    }

    /**
     * Sets the green ending color value of the shape.
     *
     * @param g the green ending color value of the shape.
     */
    void setToG(float g) {
      this.toG = g;
      valueFlags.replace("tog", true);
    }

    /**
     * Sets the blue ending color value of the shape.
     *
     * @param b the blue ending color value of the shape.
     */
    void setToB(float b) {
      this.toB = b;
      valueFlags.replace("tob", true);
    }

    /**
     * Sets the starting time in which the action ChangeColor begins.
     *
     * @param start the starting time in which the action ChangeColor begins.
     */
    void setStart(int start) {
      this.start = start;
      valueFlags.replace("start", true);
    }

    /**
     * Sets the ending time in which the action ChangeColor ceases.
     *
     * @param end the starting time in which the action ChangeColor ceases.
     */
    void setEnd(int end) {
      this.end = end;
      valueFlags.replace("end", true);
    }

    /**
     * Gets the name of the shape.
     *
     * @return the name of the shape.
     */
    String getName() {
      return name;
    }

    /**
     * Gets the starting Red color value.
     *
     * @return the starting Red color value.
     */
    float getFromR() {
      return fromR;
    }

    /**
     * Gets the starting Green color value.
     *
     * @return the starting Green color value.
     */
    float getFromG() {
      return fromG;
    }

    /**
     * Gets the starting Blue color value.
     *
     * @return the starting Blue color value.
     */
    float getFromB() {
      return fromB;
    }


    /**
     * Gets the ending Red color value.
     *
     * @return the ending Red color value.
     */
    float getToR() {
      return toR;
    }

    /**
     * Gets the ending Green color value.
     *
     * @return the ending Green color value.
     */
    float getToG() {
      return toG;
    }

    /**
     * Gets the ending Blue color value.
     *
     * @return the ending Blue color value.
     */
    float getToB() {
      return toB;
    }

    /**
     * Gets the start time in which the action will begin.
     *
     * @return the start time of when the action will begin.
     */
    int getStart() {
      return start;
    }

    /**
     * Gets the end time in which the action will end.
     *
     * @return the end time of when the action will end.
     */
    int getEnd() {
      return end;
    }
  }

  /**
   * Holds and stores the Scales class's information
   * in order to create the action Scale .
   */
  class ScaleByInfo extends Inputable {
    private String name;
    private float fromSx;
    private float fromSy;
    private float toSx;
    private float toSy;
    private int start;
    private int end;

    /**
     * Constructor to hold and store the Scale class's information
     * in order to create the action Scale.
     */
    ScaleByInfo() {
      super();

      valueFlags.put("name", false);
      valueFlags.put("fromsx", false);
      valueFlags.put("fromsy", false);
      valueFlags.put("tosx", false);
      valueFlags.put("tosy", false);
      valueFlags.put("start", false);
      valueFlags.put("end", false);

    }

    /**
     * Sets the name of the shape.
     *
     * @param name the name of the shape.
     */
    void setName(String name) {
      this.name = name;
      valueFlags.replace("name", true);
    }

    /**
     * Sets the starting value of sx of the shape.
     *
     * @param sx the starting value of sx of the shape.
     */
    void setFromXScale(float sx) {
      this.fromSx = sx;
      valueFlags.replace("fromsx", true);
    }

    /**
     * Sets the starting value of sy of the shape.
     *
     * @param sy the starting value of sy of the shape.
     */
    void setFromYScale(float sy) {
      this.fromSy = sy;
      valueFlags.replace("fromsy", true);
    }

    /**
     * Sets the ending value of sx of the shape.
     *
     * @param sx the ending value of sx of the shape.
     */
    void setToXScale(float sx) {
      this.toSx = sx;
      valueFlags.replace("tosx", true);
    }

    /**
     * Sets the ending value of sy of the shape.
     *
     * @param sy the ending value of sy of the shape.
     */
    void setToYScale(float sy) {
      this.toSy = sy;
      valueFlags.replace("tosy", true);
    }


    /**
     * Sets the start time in which the action will begin.
     *
     * @param start the start time in which the action will begin.
     */
    void setStart(int start) {
      this.start = start;
      valueFlags.replace("start", true);
    }

    /**
     * Sets the end time in which the action will stop.
     *
     * @param end the end time in which the action will stop.
     */
    void setEnd(int end) {
      this.end = end;
      valueFlags.replace("end", true);
    }

    /**
     * A getter to return the name of the shape.
     *
     * @return the name of the shape.
     */
    String getName() {
      return name;
    }

    /**
     * A getter to return the starting width value of the shape.
     *
     * @return the starting width value of the shape.
     */
    float getFromXScale() {
      return fromSx;
    }

    /**
     * A getter to return the starting height value of the shape.
     *
     * @return the starting height value of the shape.
     */
    float getFromYScale() {
      return fromSy;
    }


    /**
     * A getter to return the ending width value of the shape.
     *
     * @return the ending width value of the shape.
     */
    float getToXScale() {
      return toSx;
    }

    /**
     * A getter to return the ending height value of the shape.
     *
     * @return the ending height value of the shape.
     */
    float getToYScale() {
      return toSy;
    }

    /**
     * A getter to return the starting time of the action.
     *
     * @return returns the starting time of the action.
     */
    int getStart() {
      return start;
    }

    /**
     * A getter to return the ending time of the action.
     *
     * @return returns the ending time of the action.
     */
    int getEnd() {

      return end;
    }
  }
}