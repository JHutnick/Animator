package cs5004.animator.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.awt.Font;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.WindowConstants;

import cs5004.animator.model.Action;
import cs5004.animator.model.Actions;
import cs5004.animator.model.ChangeColor;
import cs5004.animator.model.Moves;
import cs5004.animator.model.Scale;
import cs5004.animator.model.Shape;
import cs5004.animator.model.ShapeType;

/**
 * VisualView is a view described in the form of pop up visual with the use of
 * JFrame and JPanels. This view will allow the user to manipulate speed, press play and pause,
 * as well as download an SVG file based on the path the user inputs.
 */
public class VisualView extends JPanel implements View, ActionListener {
  private float timeCount;
  private List<Shape> shapeList;
  private List<Actions> actionsList;
  private List<Shape> restartShape;
  private Timer tm;
  private JTextField file;
  private String pathI;
  private int speed;
  private Boolean flag;

  /**
   * The VisualView constructor creates the JFrame and JPanel along with the button implementation
   * to represent the animation in a GUI form to the user to interact with.
   */
  public VisualView() {
    this.flag = false;
    JFrame f = new JFrame("THE EASY ANIMATOR");
    JScrollPane scroller = new JScrollPane(this);
    f.getContentPane().add(scroller);
    setPreferredSize(new Dimension(1000, 1000));

    JButton play = new JButton("Play");
    play.addActionListener(e -> tm.start());
    add(play);

    JButton pause = new JButton("Pause");
    pause.addActionListener(e -> tm.stop());
    add(pause);

    JButton restart = new JButton("Restart");
    restart.addActionListener(e -> {
      tm.stop();
      reset();
    });
    add(restart);

    JButton slow = new JButton("Speed-");
    slow.addActionListener(e -> {
      tm.stop();
      speed -= 5;
      tm.setDelay((1000 / speed));
      flag = true;
      tm.start();
    });
    add(slow);

    JButton fast = new JButton("Speed+");
    fast.addActionListener(e -> {
      tm.stop();
      speed += 5;
      tm.setDelay((1000 / speed));
      flag = true;
      tm.start();
    });
    add(fast);

    file = new JTextField("Enter file name + path", 12);
    JButton svg = new JButton("Download");
    add(svg);
    add(file);
    svg.addActionListener(e -> {
      pathI = file.getText();
      View svgOut = new SvgView();
      try {
        svgOut.describe(restartShape, actionsList, speed,
                new PrintStream(pathI));
      } catch (FileNotFoundException e1) {
        e1.printStackTrace();
      }

    });

    f.setSize(900, 800);
    f.setLocation(200, 200);
    f.setVisible(true);
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

  }

  /**
   * Describe creates our view with a list of shapes and actions, and starts the running of
   * the program.
   *
   * @param shape  the list of shapes to be represented.
   * @param action the list of shape actions to be represented.
   * @param time   the time at which to alter the speed of the animation.
   * @param out    the output type for the string representation.
   */
  @Override
  public void describe(List<Shape> shape, List<Actions> action, int time, Appendable out) {
    this.timeCount = 0;
    this.shapeList = new LinkedList<>();
    this.actionsList = new LinkedList<>();

    for (Shape s : shape) {
      shapeList.add(new Shape(s.getNameOfShape(), s.getShapeType(), s.getReference(), s.getColor(),
              s.getWidth(), s.getHeight(), s.getAppears(), s.getDisappears()));
    }
    this.actionsList = action;
    this.restartShape = shape;
    this.speed = time;

    this.tm = new Timer((1000 / speed), this);
  }

  /**
   * Paint component allows us to create visuals for the user on the screen.
   *
   * @param g is the graphic to be painted.
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);


    for (Shape s : shapeList) {
      if (s.getAppears() <= timeCount && s.getDisappears() >= timeCount) {

        // Draws oval.
        if (s.getShapeType() == ShapeType.OVAL) {
          g.setColor(new Color(s.getColor().getRed(),
                  s.getColor().getGreen(),
                  s.getColor().getBlue()));
          g.fillOval((int) s.getReference().getX(),
                  (int) s.getReference().getY(),
                  (int) s.getWidth(),
                  (int) s.getHeight());

        }
        // Draws rectangle.
        else {
          g.setColor(new Color(s.getColor().getRed(),
                  s.getColor().getGreen(),
                  s.getColor().getBlue()));
          g.fillRect((int) s.getReference().getX(),
                  (int) s.getReference().getY(),
                  (int) s.getWidth(),
                  (int) s.getHeight());
        }
      }
    }
    if (flag) {
      g.setColor(Color.BLACK);
      g.setFont(new Font("Arial", Font.PLAIN, 20));
      g.drawString("Speed: " + speed, 400, 50);
    }
  }

  /**
   * Action performed creates and performs the actions on our shape list.  This will inturn
   * be repainted once the new data is calculated to be animated.
   *
   * @param e the action to be performed.
   */
  @Override
  public void actionPerformed(ActionEvent e) {

    if ((shapeList.get(shapeList.size() - 1).getDisappears() - 1) > timeCount) {
      // call actions here.
      for (Actions anAction : actionsList) {
        if (anAction.getStartTime() <= timeCount && anAction.getEndTime() >= timeCount) {
          if (anAction instanceof Moves) {
            Point2D newPoint = moves(anAction, timeCount);
            for (int i = 0; i < shapeList.size(); i++) {
              if (shapeList.get(i).getNameOfShape().equals(anAction.getNameOfShape())) {
                if (newPoint.getX() != 0) {
                  shapeList.set(i, new Shape(shapeList.get(i).getNameOfShape(),
                          shapeList.get(i).getShapeType(), newPoint, shapeList.get(i).getColor(),
                          shapeList.get(i).getWidth(), shapeList.get(i).getHeight(),
                          shapeList.get(i).getAppears(), shapeList.get(i).getDisappears()));
                }
              }
            }
          } else if (anAction instanceof Scale) {
            Point2D newScale = scales(anAction, timeCount);
            for (int i = 0; i < shapeList.size(); i++) {
              if (shapeList.get(i).getNameOfShape().equals(anAction.getNameOfShape())) {
                shapeList.set(i, new Shape(shapeList.get(i).getNameOfShape(),
                        shapeList.get(i).getShapeType(), shapeList.get(i).getReference(),
                        shapeList.get(i).getColor(), newScale.getX(),
                        newScale.getY(), shapeList.get(i).getAppears(),
                        shapeList.get(i).getDisappears()));
              }
            }
          } else {
            Color newColor = changingColor(anAction, timeCount);
            for (int i = 0; i < shapeList.size(); i++) {
              if (shapeList.get(i).getNameOfShape().equals(anAction.getNameOfShape())) {
                shapeList.set(i, new Shape(shapeList.get(i).getNameOfShape(),
                        shapeList.get(i).getShapeType(), shapeList.get(i).getReference(),
                        newColor, shapeList.get(i).getWidth(),
                        shapeList.get(i).getHeight(), shapeList.get(i).getAppears(),
                        shapeList.get(i).getDisappears()));
              }
            }
          }
        }
      }
    } else {
      tm.stop();
    }

    repaint();
    timeCount += 1;
  }

  /**
   * Moves creates the new x and y coordinates based on the current time.
   *
   * @param action      a moves action.
   * @param currentTime the time it currently is.
   * @return a new Point2D.
   */
  private Point2D.Double moves(Action action, float currentTime) {

    double x = (((Moves) action).getStartingPoint().getX() *
            ((action.getEndTime() - currentTime) /
                    (action.getEndTime() - (action.getStartTime()))))
            +
            (((Moves) action).getEndingPoint().getX()
                    * ((currentTime - action.getStartTime()) /
                    ((action).getEndTime() - action.getStartTime())));

    double y = (((Moves) action).getStartingPoint().getY() *
            ((action.getEndTime() - currentTime) /
                    (action.getEndTime() - action.getStartTime())))
            +
            (((Moves) action).getEndingPoint().getY()
                    * ((currentTime - (action.getStartTime())) /
                    ((action).getEndTime() - action.getStartTime())));

    return new Point2D.Double(x, y);

  }

  /**
   * ChangeColor creates a new color based on the action and current time being fed into
   * the method.
   *
   * @param action      is a ChangeColor action.
   * @param currentTime is the current time.
   * @return a new Color.
   */
  private Color changingColor(Action action, float currentTime) {


    float r = (((ChangeColor) action).getStartingColor().getRed() *
            ((action.getEndTime() - currentTime) / (action.getEndTime() - action.getStartTime())))
            + (((ChangeColor) action).getEndingColor().getRed() *
            ((currentTime - action.getStartTime()) /
                    (action.getEndTime() - action.getStartTime())));
    float g = (((ChangeColor) action).getStartingColor().getGreen() *
            ((action.getEndTime() - currentTime) /
                    (action.getEndTime() - action.getStartTime())))
            + (((ChangeColor) action).getEndingColor().getGreen() *
            ((currentTime - action.getStartTime()) /
                    (action.getEndTime() - action.getStartTime())));
    float b = (((ChangeColor) action).getStartingColor().getBlue() *
            ((action.getEndTime() - currentTime) /
                    (action.getEndTime() - action.getStartTime())))
            + (((ChangeColor) action).getEndingColor().getBlue() *
            ((currentTime - action.getStartTime()) /
                    (action.getEndTime() - action.getStartTime())));

    return new Color(r / 255.f, g / 255.f, b / 255.f);
  }

  /**
   * Scales changes the scale of a shape based on the current time.
   *
   * @param action      a scales action.
   * @param currentTime is the current time of our program.
   * @return a point2D, (this was very clever thanks to Jon!) we use a point2D because
   *      we needed to return two measurement, and instead of creating a new class we
   *      simply utilized point2D to store both values.
   */
  private Point2D.Double scales(Action action, float currentTime) {
    double x = (((Scale) action).getFromSx() *
            ((action.getEndTime() - currentTime) /
                    (action.getEndTime() - action.getStartTime())))
            + (((Scale) action).getToSx() *
            (currentTime - action.getStartTime() /
                    (action.getEndTime() - action.getStartTime())));

    double y = (((Scale) action).getFromSy() *
            ((action.getEndTime() - currentTime) /
                    (action.getEndTime() - action.getStartTime())))
            + (((Scale) action).getToSy() *
            ((currentTime - action.getStartTime()) /
                    (action.getEndTime() - action.getStartTime())));
    return new Point2D.Double(x, y);
  }

  /**
   * reset resets everything in the program.  It resets the time counter, refreshes the animation
   * panel and reverts the list of shapes back to original.
   */
  private void reset() {
    timeCount = 0;
    repaint();
    resetList();
  }

  /**
   * reset list takes the original copy of the shape list and rebuilds our list holding shapes to
   * be drawn back to the original state.
   */
  private void resetList() {
    shapeList.clear();
    for (Shape s : restartShape) {
      Shape temp = new Shape(s.getNameOfShape(), s.getShapeType(), s.getReference()
              , s.getColor(), s.getWidth(), s.getHeight(), s.getAppears(), s.getDisappears());
      shapeList.add(temp);
    }
  }
}
