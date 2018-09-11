package cs5004.animator.model;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import cs5004.animator.controller.Controller;
import cs5004.animator.view.SvgView;
import cs5004.animator.view.TextView;
import cs5004.animator.view.View;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class Tests {
  private AnimationModel test1;
  private AnimationModel empty;
  private View svgView;
  private View textView;
  private PrintStream out;
  private PrintStream out2;
  private PrintStream out3;
  private PrintStream out4;
  private ByteArrayOutputStream bytes;
  private ByteArrayOutputStream bytes2;
  private ByteArrayOutputStream bytes3;
  private ByteArrayOutputStream bytes4;

  /**
   * The following is an example of our view that will be used for testing.
   */
  @Before
  public void setup() throws FileNotFoundException {
    test1 = new AnimationModelImpl();
    empty = new AnimationModelImpl();
    // Adding Shapes:
    // Adding a Rectangle object.
    test1.addShape(new Shape("A", ShapeType.RECTANGLE,
            new Point2D.Double(100, 100), new Color(255, 0, 0),
            100, 200, 0, 20));
    // Adding an oval object.
    test1.addShape(new Shape("B", ShapeType.OVAL,
            new Point2D.Double(50, 50), new Color(0, 255, 0),
            100, 200, 0, 20));

    // Adding cs5004.animator.model.Actions:
    // The following is all of the actions being applied to a Rectangle object.
    test1.addAction(new Scale("A", 2, 8,
            100, 200, 300, 400, ShapeType.RECTANGLE));

    test1.addAction(new ChangeColor("A", 2, 8,
            new Color(255, 0, 0), new Color(0, 0, 255)));

    test1.addAction(new Moves("A", 2, 8,
            new Point2D.Double(100, 100), new Point2D.Double(200, 200)));

    // The following is all of the actions being applied to an Oval object.
    test1.addAction(new Scale("B", 2, 8,
            100, 200, 200, 400, ShapeType.OVAL));

    test1.addAction(new ChangeColor("B", 2, 8,
            new Color(0, 255, 0), new Color(255, 0, 0)));

    test1.addAction(new Moves("B", 2, 8,
            new Point2D.Double(0, 0), new Point2D.Double(300, 300)));
    svgView = new SvgView();
    textView = new TextView();
    bytes = new ByteArrayOutputStream();
    bytes2 = new ByteArrayOutputStream();
    bytes3 = new ByteArrayOutputStream();
    bytes4 = new ByteArrayOutputStream();
    out = new PrintStream(bytes);
    out2 = new PrintStream(bytes2);
    out3 = new PrintStream(bytes3);
    out4 = new PrintStream(bytes4);
  }

  /**
   * Tests to see if controller takes the correct information and grabs it and gives it to the
   * proper view instance.
   */
  @Test
  public void testController() {

    // Controller with textView out
    Controller controller = new Controller(test1, textView);
    controller.controllerGo(1, out);
    assertEquals("Shapes:\n" +
                    "Name: A\n" +
                    "Type: rectangle\n" +
                    "Min corner: (100.0, 100.0), Width: 100.0, Height: 200.0, Color: " +
                    "(255.0, 0.0, 0.0)\n" +
                    "Appears at t=0.0s\n" +
                    "Disappears at t=20.0s\n" +
                    "\n" +
                    "Name: B\n" +
                    "Type: oval\n" +
                    "Center: (50.0, 50.0), X radius: 100.0, Y radius: 200.0, Color: " +
                    "(0.0, 255.0, 0.0)\n" +
                    "Appears at t=0.0s\n" +
                    "Disappears at t=20.0s\n" +
                    "\n" +
                    "Shape A scales from Width: 100.0, Height: 200.0 to Width: 300.0, " +
                    "Height: 400.0 from t=2.0s to t=8.0s\n" +
                    "Shape A changes color from (255.0, 0.0, 0.0) to (0.0, 0.0, 255.0) " +
                    "from t=2.0s to t=8.0s\n" +
                    "Shape A moves from (100.0, 100.0) to (100.0, 100.0) from t=2.0s to t=8.0s\n" +
                    "Shape B scales from X radius: 100.0, Y radius: 200.0 to X radius: " +
                    "200.0, to Y " +
                    "radius: 400.0 from t=2.0s to t=8.0s\n" +
                    "Shape B changes color from (0.0, 255.0, 0.0) to (255.0, 0.0, 0.0) " +
                    "from t=2.0s " +
                    "to t=8.0s\n" +
                    "Shape B moves from (0.0, 0.0) to (0.0, 0.0) from t=2.0s to t=8.0s\n\n",
            new String(bytes.toByteArray()));

    //Controller with SVG View out
    Controller controller2 = new Controller(test1, svgView);
    controller2.controllerGo(1, out2);
    assertEquals("<svg width=\"1100\" height=\"1100\" version=\"1.1\"\n" +
                    "xmlns=\"http://www.w3.org/2000/svg\">\n" +
                    "<rect id=\"A\" x=\"100.0\" y=\"100.0\" width=\"100.0\" height=\"200.0\" " +
                    "fill=\"rgb(255,0,0)\" visibility=\"visible\" >\n" +
                    "<animate attributeName=\"width\" attributeType=\"XML\" begin=\"2000.0ms\" " +
                    "dur=\"6000.0ms\" fill=\"freeze\" from=\"100.0\" to=\"300.0\" />\n" +
                    "<animate attributeName=\"height\" attributeType=\"XML\" begin=\"2000.0ms\" " +
                    "dur=\"6000.0ms\" fill=\"freeze\" from=\"200.0\" to=\"400.0\" />\n" +
                    "<animateColor attributeName=\"fill\" attributeType=\"XML\" from=\"" +
                    "rgb(255,0,0)\" to=\"rgb(0,0,255)\" begin=\"2000.0ms\" dur=\"6000.0ms\" " +
                    "fill=\"freeze\"/>\n" +
                    "<animate attributeType=\"xml\" begin=\"2000.0ms\" dur=\"6000.0ms\" " +
                    "attributeName=\"x\" from=\"100.0\" to=\"200.0\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"2000.0ms\" dur=\"6000.0ms\" " +
                    "attributeName=\"y\" from=\"100.0\" to=\"200.0\" fill=\"freeze\" />\n" +
                    "</rect>\n" +
                    "<ellipse id=\"B\" cx=\"50.0\" cy=\"50.0\" rx=\"100.0\" ry=\"200.0\" " +
                    "fill=\"rgb(0,255,0)\" visibility=\"visible\" >\n" +
                    "<animate attributeName=\"rx\" attributeType=\"XML\" begin=\"2000.0ms\" " +
                    "dur=\"6000.0ms\" fill=\"freeze\" from=\"100.0\" to=\"200.0\" />\n" +
                    "<animate attributeName=\"ry\" attributeType=\"XML\" begin=\"2000.0ms\" " +
                    "dur=\"6000.0ms\" fill=\"freeze\" from=\"200.0\" to=\"400.0\" />\n" +
                    "<animateColor attributeName=\"fill\" attributeType=\"XML\" from=\"" +
                    "rgb(0,255,0)\" to=\"rgb(255,0,0)\" begin=\"2000.0ms\" dur=\"6000.0ms\" " +
                    "fill=\"freeze\"/>\n" +
                    "<animate attributeType=\"xml\" begin=\"2000.0ms\" dur=\"6000.0ms\" " +
                    "attributeName=\"cx\" from=\"0.0\" to=\"300.0\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"2000.0ms\" dur=\"6000.0ms\" " +
                    "attributeName=\"cy\" from=\"0.0\" to=\"300.0\" fill=\"freeze\" />\n" +
                    "</ellipse>\n" +
                    "</svg>\n",
            new String(bytes2.toByteArray()));

    //Test empty
    Controller controller3 = new Controller(empty, textView);
    controller3.controllerGo(1, out3);
    assertEquals("Shapes:\n\n", new String(bytes3.toByteArray()));

    // Controller with textView different speed
    Controller controller4 = new Controller(test1, textView);
    controller4.controllerGo(20, out4);
    assertEquals("Shapes:\n" +
                    "Name: A\n" +
                    "Type: rectangle\n" +
                    "Min corner: (100.0, 100.0), Width: 100.0, Height: 200.0, Color: " +
                    "(255.0, 0.0, 0.0)\n" +
                    "Appears at t=0.0s\n" +
                    "Disappears at t=1.0s\n" +
                    "\n" +
                    "Name: B\n" +
                    "Type: oval\n" +
                    "Center: (50.0, 50.0), X radius: 100.0, Y radius: 200.0, Color: " +
                    "(0.0, 255.0, 0.0)\n" +
                    "Appears at t=0.0s\n" +
                    "Disappears at t=1.0s\n" +
                    "\n" +
                    "Shape A scales from Width: 100.0, Height: 200.0 to Width: 300.0, " +
                    "Height: 400.0 from t=2.0s to t=8.0s\n" +
                    "Shape A changes color from (255.0, 0.0, 0.0) to (0.0, 0.0, 255.0) " +
                    "from t=2.0s to t=8.0s\n" +
                    "Shape A moves from (100.0, 100.0) to (100.0, 100.0) from t=2.0s to t=8.0s\n" +
                    "Shape B scales from X radius: 100.0, Y radius: 200.0 to X radius: " +
                    "200.0, to Y radius: 400.0 from t=2.0s to t=8.0s\n" +
                    "Shape B changes color from (0.0, 255.0, 0.0) to (255.0, 0.0, 0.0) " +
                    "from t=2.0s to t=8.0s\n" +
                    "Shape B moves from (0.0, 0.0) to (0.0, 0.0) from t=2.0s to t=8.0s\n" +
                    "\n",
            new String(bytes4.toByteArray()));


  }

  /**
   * The following is a test for the Action constructor. The only variable that should be
   * accessible for testing are the names, start times, and end times. All of the previous listed
   * will be tested in this class.
   */
  @Test
  public void testActionSuperGetters() {
    Actions movesTest = new Moves("A", 0, 8,
            new Point2D.Double(0, 0), new Point2D.Double(100, 100));
    Actions scaleTest = new Scale("B", 9, 10,
            20, 20, 20, 10, ShapeType.OVAL);
    Actions changeColorTest = new ChangeColor("C", 4, 10,
            new Color(255, 0, 0), new Color(200, 100, 255));


    // Getters for all of the Action class.
    assertEquals("A", movesTest.getNameOfShape());
    assertEquals("B", scaleTest.getNameOfShape());
    assertEquals("C", changeColorTest.getNameOfShape());

    assertEquals(0, movesTest.getStartTime());
    assertEquals(9, scaleTest.getStartTime());
    assertEquals(4, changeColorTest.getStartTime());

    assertEquals(8, movesTest.getEndTime());
    assertEquals(10, scaleTest.getEndTime());
    assertEquals(10, changeColorTest.getEndTime());

  }

  /**
   * Tests all the getters in Scale Class.
   */
  @Test
  public void testScaleGetters() {
    // Getters for the Scale class.
    // getFromSx.
    assertEquals(20, new Scale("B", 9, 10,
            20, 20, 20, 10, ShapeType.OVAL).getFromSx(), 0.01);
    assertEquals(20, new Scale("B", 9, 10,
            20, 20, 20, 10,
            ShapeType.RECTANGLE).getFromSx(), 0.01);


    // Testing getFromSy.
    assertEquals(20, new Scale("B", 9, 10,
            20, 20, 20, 10, ShapeType.OVAL).getFromSy(), 0.01);
    assertEquals(20, new Scale("B", 9, 10,
            20, 20, 20, 10,
            ShapeType.RECTANGLE).getFromSy(), 0.01);

    // Testing getToSx.
    assertEquals(20, new Scale("B", 9, 10,
            20, 20, 20, 10, ShapeType.OVAL).getToSx(), 0.01);
    assertEquals(20, new Scale("B", 9, 10,
            20, 20, 20, 10, ShapeType.RECTANGLE).getToSx(), 0.01);

    // Testing getToSy.
    assertEquals(10, new Scale("B", 9, 10,
            20, 20, 20, 10, ShapeType.OVAL).getToSy(), 0.01);
    assertEquals(10, new Scale("B", 9, 10,
            20, 20, 20, 10, ShapeType.RECTANGLE).getToSy(), 0.01);

    // Testing getType.
    assertEquals(ShapeType.OVAL, new Scale("B", 9, 10,
            20, 20, 20, 10, ShapeType.OVAL).getType());
    assertEquals(ShapeType.RECTANGLE, new Scale("B", 9, 10,
            20, 20, 20, 10, ShapeType.RECTANGLE).getType());

    // Testing ScaleActionMethod.
    // Before action time.
    assertEquals(20.0, new Scale("B", 2, 10,
            20, 20, 20, 10, ShapeType.OVAL)
            .scaleMethodAction(0), 0.01);

    // Starting action time.
    assertEquals(20.0, new Scale("B", 2, 10,
            20, 20, 20, 10, ShapeType.OVAL)
            .scaleMethodAction(2), 0.01);

    //After action time.
    assertEquals(0.0, new Scale("B", 2, 10,
            20, 20, 20, 10, ShapeType.OVAL)
            .scaleMethodAction(10), 0.01);


    // Action during time moving positively.
    assertEquals(23.75, new Scale("B", 2, 10,
            20, 20, 20, 30, ShapeType.OVAL)
            .scaleMethodAction(5), 0.01);
    assertEquals(23.75, new Scale("B", 2, 10,
            20, 20, 20, 30, ShapeType.RECTANGLE)
            .scaleMethodAction(5), 0.01);
    assertEquals(23.75, new Scale("B", 2, 10,
            20, 20, 30, 20, ShapeType.OVAL)
            .scaleMethodAction(5), 0.01);
    assertEquals(23.75, new Scale("B", 2, 10,
            20, 20, 30, 20, ShapeType.RECTANGLE)
            .scaleMethodAction(5), 0.01);

    // Action during time moving negatively.
    assertEquals(16.25, new Scale("B", 2, 10,
            20, 20, 20, 10, ShapeType.OVAL)
            .scaleMethodAction(5), 0.01);
    assertEquals(16.25, new Scale("B", 2, 10,
            20, 20, 20, 10, ShapeType.RECTANGLE)
            .scaleMethodAction(5), 0.01);
    assertEquals(16.25, new Scale("B", 2, 10,
            20, 20, 10, 20, ShapeType.OVAL)
            .scaleMethodAction(5), 0.01);
    assertEquals(16.25, new Scale("B", 2, 10,
            20, 20, 10, 20, ShapeType.RECTANGLE)
            .scaleMethodAction(5), 0.01);

  }

  @Test
  public void testMovesGetters() {
    // Getters for Moves class.
    // Starting point.
    assertEquals(new Point2D.Double(0, 0),
            new Moves("A", 0, 8,
                    new Point2D.Double(0, 0),
                    new Point2D.Double(100, 100)).getStartingPoint());

    // Ending point.
    assertEquals(new Point2D.Double(100, 100),
            new Moves("A", 0, 8,
                    new Point2D.Double(0, 0), new Point2D.Double(100, 100)).getEndingPoint());

    // Moves action method.
    // Move during time (positively).
    assertEquals(new Point2D.Double(62.5, 62.5),
            new Moves("A", 0, 8,
                    new Point2D.Double(0, 0), new Point2D.Double(100, 100))
                    .movesActionMethod(5));

    // Move during time (negatively).
    assertEquals(new Point2D.Double(70.0, 70.0),
            new Moves("A", 2, 8,
                    new Point2D.Double(100, 100), new Point2D.Double(40, 40))
                    .movesActionMethod(5));

    // Move before start time.
    assertEquals(new Point2D.Double(0.0, 0.0),
            new Moves("A", 0, 8,
                    new Point2D.Double(0, 0), new Point2D.Double(100, 100))
                    .movesActionMethod(0));

    // move after end time.
    assertEquals(new Point2D.Double(100.0, 100.0),
            new Moves("A", 0, 8,
                    new Point2D.Double(0, 0), new Point2D.Double(100, 100))
                    .movesActionMethod(12));

  }

  /**
   * A test of all the changeColor class methods.
   */
  @Test
  public void testChangeColorsGetters() {

    // get starting color.
    assertEquals(new Color(255, 0, 0) {
    }, new ChangeColor("C", 2, 10,
            new Color(255, 0, 0), new Color(200, 100, 255))
            .getStartingColor());

    // get ending color.
    assertEquals(new Color(200, 100, 255),
            new ChangeColor("C", 2, 10,
                    new Color(255, 0, 0), new Color(200, 100, 255))
                    .getEndingColor());

    // color change action method.
    // color change before change.
    assertEquals(new Color(255, 0, 0),
            new ChangeColor("C", 2, 10,
                    new Color(255, 0, 0), new Color(200, 100, 255))
                    .colorChangeActionMethod(0));

    // Color change after end time.
    assertEquals(new Color(200, 100, 255),
            new ChangeColor("C", 2, 10,
                    new Color(255, 0, 0), new Color(200, 100, 255))
                    .colorChangeActionMethod(12));

  }

  /**
   * testScaleException will assure that an attribute cannot be scaled to anything less than zero.
   */
  @Test
  public void testScaleException() {

    try {
      test1.addAction(new Scale("B", 9, 10,
              20, 20, -1, 20, ShapeType.OVAL));
      fail("IllegalArgumentException: Illegal argument was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }

  }

  /**
   * A test of the Moves classes movesAction.
   */
  @Test
  public void testMovesActionMethod() {
    for (int i = 0; i < test1.getActionList().size(); i++) {
      if (test1.getActionList().get(i) instanceof Moves) {
        assertEquals("Point2D.Double[150.0, 150.0]",
                ((Moves) test1.getActionList().get(i)).movesActionMethod(5).toString());
      }
    }
  }

  /**
   * A test of the Scale's classes action method.
   */
  @Test
  public void testScalesActionMethod() {
    // Rectangle scale test.
    assertEquals(200.0, ((Scale) test1.getActionList().get(0))
            .scaleMethodAction(5), 0.01);
    assertEquals(100.0, ((Scale) test1.getActionList().get(0))
            .scaleMethodAction(0), 0.01);
    assertEquals(0, ((Scale) test1.getActionList().get(0))
            .scaleMethodAction(11), 0.01);
    assertEquals(0, ((Scale) test1.getActionList().get(0))
            .scaleMethodAction(21), 0.01);

    // Oval scale test.
    assertEquals(150.0, ((Scale) test1.getActionList().get(3))
            .scaleMethodAction(5), 0.01);
    assertEquals(100.0, ((Scale) test1.getActionList().get(3))
            .scaleMethodAction(0), 0.01);
    assertEquals(0.0, ((Scale) test1.getActionList().get(3))
            .scaleMethodAction(11), 0.01);
    assertEquals(0.0, ((Scale) test1.getActionList().get(3))
            .scaleMethodAction(21), 0.01);

  }

  /**
   * The following is a test to see if another shape with the same name is added to our list
   * an IllegalArgumentException is thrown.
   */
  @Test
  public void testIllegalAddSameName() {
    Shape newShape = new Shape("A", ShapeType.OVAL, new Point2D.Double(2, 2),
            new Color(50, 100, 200), 2, 10, 10, 12);
    try {
      test1.addShape(newShape);
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }
  }

  /**
   * The following is a test to see if another shape with a different name is added to our list
   * an IllegalArgumentException is not thrown.
   */
  @Test
  public void testNonIllegalAddSameName() {
    Shape newShape = new Shape("D", ShapeType.OVAL, new Point2D.Double(2, 2),
            new Color(50, 100, 200), 2, 10, 10, 12);
    try {
      test1.addShape(newShape);
      System.out.println("Correct, error should not be thrown.");
    } catch (IllegalArgumentException exception) {
      fail("Error was not caught.");
    }
  }

  /**
   * The following is a test to make sure an action start time isn't before the shape disappears.
   */
  @Test
  public void testIllegalStartTimeBeforeShapeAppears() {
    try {
      test1.addAction(new Moves("A", 20, 50,
              new Point2D.Double(100, 100), new Point2D.Double(150, 150)));
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }
  }

  /**
   * The following is a test to make sure an action end time isn't after the shape appears.
   */
  @Test
  public void testIllegalEndTimeBeforeShapeAppears() {
    Actions newAction = new Moves("A", 3, 14,
            new Point2D.Double(100, 100), new Point2D.Double(150, 150));
    try {
      test1.addAction(newAction);
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }
  }

  /**
   * The following is to test that an action isn't added when the shape doesn't exists.
   */
  @Test
  public void testIllegalActionAdd() {
    Actions newAction = new Moves("D", 3, 6,
            new Point2D.Double(100, 100), new Point2D.Double(150, 150));
    try {
      test1.addAction(newAction);
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }
  }

  /**
   * Tests to see if an IllegalArgument is thrown when a time less than 0 is given for
   * our conductActions method.
   */
  @Test
  public void testIllegalConductActionsTime() {
    try {
      test1.conductActions(-1);
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }
  }


  /**
   * The following is a test to ensure that an IllegalArgument exception is thrown when
   * start time is less than 0.
   */
  @Test
  public void testIllegalStartTime() {
    try {
      Actions movesTest = new Moves("A", -1, 8,
              new Point2D.Double(0, 0), new Point2D.Double(100, 100));
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }

  }


  /**
   * The following is a test to ensure that an IllegalArgument exception is thrown when
   * end time is less than start time.
   */
  @Test
  public void testIllegalEndTime() {
    try {
      Actions movesTest = new Moves("A", 5, 4,
              new Point2D.Double(0, 0), new Point2D.Double(100, 100));
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }

  }


  /**
   * The following is a test to make ensure the Shape construct is returning the correct variables.
   */
  @Test
  public void testShapeClass() {
    Shape rectangleTest = new Shape("A", ShapeType.RECTANGLE,
            new Point2D.Double(5, 5), new Color(200, 100, 60), 10,
            20, 5, 10);
    Shape ovalTest = new Shape("B", ShapeType.OVAL, new Point2D.Double(10, 10),
            new Color(100, 200, 250), 5, 5, 100, 200);

    // Testing to see if the names are accurately returned.
    assertEquals("A", rectangleTest.getNameOfShape());
    assertEquals("B", ovalTest.getNameOfShape());

    // Testing to see if the enum of shape type is accurately returned.
    assertEquals(ShapeType.RECTANGLE, rectangleTest.getShapeType());
    assertEquals(ShapeType.OVAL, ovalTest.getShapeType());

    // Testing to see if the reference point is accurately returned.
    assertEquals(new Point2D.Double(5, 5), rectangleTest.getReference());
    assertEquals(new Point2D.Double(10, 10), ovalTest.getReference());

    // Testing to see if the color of the shape is accurately returned.
    assertEquals(new Color(200, 100, 60), rectangleTest.getColor());
    assertEquals(new Color(100, 200, 250), ovalTest.getColor());

    // Testing to see if the width of the shape is accurately returned.
    assertEquals(10, rectangleTest.getWidth(), 0.0001);
    assertEquals(5, ovalTest.getWidth(), 0.0001);

    // Testing to see if the height is accurately returned.
    assertEquals(20, rectangleTest.getHeight(), 0.0001);
    assertEquals(5, ovalTest.getHeight(), 0.0001);

    // Testing to see if the starting time is accurately returned.
    assertEquals(5, rectangleTest.getAppears());
    assertEquals(100, ovalTest.getAppears());

    // testing to see if the end time is accurately returned.
    assertEquals(10, rectangleTest.getDisappears());
    assertEquals(200, ovalTest.getDisappears());
  }

  /**
   * The following is to make certain an IllegalArgumentException is thrown when appears is less
   * than zero.
   */
  @Test
  public void testIllegalAppearsTime() {
    try {
      Shape rectangleTest = new Shape("A", ShapeType.RECTANGLE,
              new Point2D.Double(5, 5), new Color(200, 100, 60), 10,
              20, -1, 10);
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }

  }


  /**
   * The following is to make certain an IllegalArgumentException is thrown when disappears is less
   * than appears.
   */
  @Test
  public void testIllegalDisappearsTime() {
    try {
      Shape rectangleTest = new Shape("A", ShapeType.RECTANGLE,
              new Point2D.Double(5, 5), new Color(200, 100, 60), 10,
              20, 20, 5);
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }

  }


  /**
   * The following is to make certain an IllegalArgumentException is thrown when width is less
   * than zero.
   */
  @Test
  public void testIllegalWidthDimension() {
    try {
      Shape rectangleTest = new Shape("A", ShapeType.RECTANGLE,
              new Point2D.Double(5, 5), new Color(200, 100, 60), -1,
              20, 20, 5);
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }
  }


  /**
   * The following is to make certain an IllegalArgumentException is thrown when height is less
   * than zero.
   */
  @Test
  public void testIllegalHeightDimension() {
    try {
      Shape rectangleTest = new Shape("A", ShapeType.RECTANGLE,
              new Point2D.Double(5, 5), new Color(200, 100, 60), 12,
              -1, 20, 5);
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }

  }

  /**
   * The following is to make certain an IllegalArgumentException is a Moves method is trying to
   * be added during the same time of another moves method.
   */
  @Test
  public void testIllegalMovesAdd1() {
    try {
      test1.addAction(new Moves("A", 3, 10,
              new Point2D.Double(100, 100), new Point2D.Double(200, 200)));
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }

  }

  /**
   * The following is to make certain an IllegalArgumentException is a Moves method is trying to
   * be added during the same time of another moves method.
   */
  @Test
  public void testIllegalMovesAdd2() {
    try {
      test1.addAction(new Moves("A", 2, 8,
              new Point2D.Double(100, 100), new Point2D.Double(200, 200)));
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }

  }

  /**
   * The following is to make certain an IllegalArgumentException is a Moves method is trying to
   * be added during the same time of another moves method.
   */
  @Test
  public void testIllegalMovesAdd3() {
    try {
      test1.addAction(new Moves("A", 1, 7,
              new Point2D.Double(100, 100), new Point2D.Double(200, 200)));
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }

  }

  /**
   * The following is to make certain an IllegalArgumentException is a Scales method is trying to
   * be added during the same time of another scales method.
   */
  @Test
  public void testIllegalScalesAdd1() {
    try {
      test1.addAction(new Scale("A", 3, 9,
              100, 200, 300, 200, ShapeType.RECTANGLE));
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }

  }

  /**
   * The following is to make certain an IllegalArgumentException is a Scales method is trying to
   * be added during the same time of another scales method.
   */
  @Test
  public void testIllegalScalesAdd2() {
    try {
      test1.addAction(new Scale("A", 2, 8,
              100, 200, 300, 200, ShapeType.RECTANGLE));
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }
  }

  /**
   * The following is to make certain an IllegalArgumentException is a Scales method is trying to
   * be added during the same time of another scales method.
   */
  @Test
  public void testIllegalScalesAdd3() {
    try {
      test1.addAction(new Scale("A", 1, 7,
              100, 200, 300, 200, ShapeType.RECTANGLE));
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }

  }

  /**
   * The following is to make certain an IllegalArgumentException is a Moves method is trying to
   * be added during the same time of another moves method.
   */
  @Test
  public void testIllegalChaneColorAdd1() {
    try {
      test1.addAction(new ChangeColor("A", 3, 9,
              new Color(255, 0, 0), new Color(0, 0, 255)));
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }
  }

  /**
   * The following is to make certain an IllegalArgumentException is a Moves method is trying to
   * be added during the same time of another moves method.
   */
  @Test
  public void testIllegalChaneColorAdd2() {
    try {
      test1.addAction(new ChangeColor("A", 2, 8,
              new Color(255, 0, 0), new Color(0, 0, 255)));
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }
  }

  /**
   * The following is to make certain an IllegalArgumentException is a Moves method is trying to
   * be added during the same time of another moves method.
   */
  @Test
  public void testIllegalChaneColorAdd3() {
    try {
      test1.addAction(new ChangeColor("A", 1, 7,
              new Color(255, 0, 0), new Color(0, 0, 255)));
      fail("Error was not caught.");
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
    }
  }
}
