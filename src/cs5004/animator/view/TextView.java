package cs5004.animator.view;

import java.io.PrintStream;
import java.util.List;

import cs5004.animator.model.Actions;
import cs5004.animator.model.ChangeColor;
import cs5004.animator.model.Moves;
import cs5004.animator.model.Scale;
import cs5004.animator.model.Shape;
import cs5004.animator.model.ShapeType;

/**
 * Creates a TextView object which implements View interface.  This class is responsible for
 * displaying the animation in a String based view.
 */
public class TextView implements View {

  /**
   * Describe will create a string representation of the animation that is currently happening
   * with the view.
   */
  @Override
  public void describe(List<Shape> shape, List<Actions> action, int time, Appendable out) {

    StringBuilder shapeResult = new StringBuilder();
    StringBuilder actionResult = new StringBuilder();
    String finalResult;
    List<Shape> shapeList;
    List<Actions> actionList;

    shapeList = shape;
    actionList = action;

    for (Shape aShapeList : shapeList) {

      if (aShapeList.getShapeType().equals(ShapeType.RECTANGLE)) {
        shapeResult.append("Name: " + aShapeList.getNameOfShape() + ("\n"));
        shapeResult.append("Type: " + "rectangle" + ("\n"));
        shapeResult.append("Min corner: " + "(" + aShapeList.getReference().getX() + ", "
                + aShapeList.getReference().getY() + "), Width: " + aShapeList.getWidth()
                + ", Height: " + aShapeList.getHeight() + ", Color: ("
                + (float) aShapeList.getColor().getRed() + ", "
                + (float) aShapeList.getColor().getGreen()
                + ", " + (float) aShapeList.getColor().getBlue() + ")\n");
        shapeResult.append("Appears at t=" + (float) aShapeList.getAppears() / time + "s\n");
        shapeResult.append("Disappears at t=" + (float) aShapeList.getDisappears() / time + "s\n");
        shapeResult.append("\n");
      } else {
        shapeResult.append("Name: " + aShapeList.getNameOfShape() + ("\n"));
        shapeResult.append("Type: " + "oval" + ("\n"));
        shapeResult.append("Center: " + "(" + aShapeList.getReference().getX() + ", "
                + aShapeList.getReference().getY() + "), X radius: " + aShapeList.getWidth()
                + ", Y radius: " + aShapeList.getHeight() + ", Color: ("
                + (float) aShapeList.getColor().getRed() + ", "
                + (float) aShapeList.getColor().getGreen()
                + ", " + (float) aShapeList.getColor().getBlue() + ")\n");
        shapeResult.append("Appears at t=" + (float) aShapeList.getAppears() / time + "s\n");
        shapeResult.append("Disappears at t=" + (float) aShapeList.getDisappears() / time + "s\n");
        shapeResult.append("\n");
      }

    }

    for (Actions anActionList : actionList) {
      if (anActionList instanceof Moves) {
        actionResult.append("Shape " + anActionList.getNameOfShape() + " moves from ("
                + ((Moves) anActionList).getStartingPoint().getX() + ", "
                + ((Moves) anActionList).getStartingPoint().getY() + ") to "
                + "(" + ((Moves) anActionList).getStartingPoint().getX() + ", "
                + ((Moves) anActionList).getStartingPoint().getY() + ") from t="
                + (float) anActionList.getStartTime() + "s to t="
                + (float) anActionList.getEndTime() + "s\n");
      } else if (anActionList instanceof ChangeColor) {
        actionResult.append("Shape " + anActionList.getNameOfShape() + " changes color from ("
                + (float) ((ChangeColor) anActionList).getStartingColor().getRed() + ", " +
                +(float) ((ChangeColor) anActionList).getStartingColor().getGreen() + ", " +
                +(float) ((ChangeColor) anActionList).getStartingColor().getBlue() + ") to ("
                + (float) ((ChangeColor) anActionList).getEndingColor().getRed() + ", " +
                +(float) ((ChangeColor) anActionList).getEndingColor().getGreen() + ", " +
                +(float) ((ChangeColor) anActionList).getEndingColor().getBlue() +
                ") from t="
                + (float) anActionList.getStartTime() + "s to t="
                + (float) anActionList.getEndTime() + "s\n");
      } else {
        if (anActionList instanceof Scale) {
          if (((Scale) anActionList).getType().equals(ShapeType.RECTANGLE)) {
            actionResult.append("Shape " + anActionList.getNameOfShape() + " scales from Width: "
                    + ((Scale) anActionList).getFromSx() + ", Height: "
                    + ((Scale) anActionList).getFromSy()
                    + " to Width: " + ((Scale) anActionList).getToSx() + ", Height: "
                    + ((Scale) anActionList).getToSy() + " from t=" +
                    (float) anActionList.getStartTime()
                    + "s to t=" + (float) anActionList.getEndTime() + "s\n");
          } else {
            actionResult.append("Shape " + anActionList.getNameOfShape() + " scales from X radius: "
                    + ((Scale) anActionList).getFromSx() + ", Y radius: "
                    + ((Scale) anActionList).getFromSy()
                    + " to X radius: " + ((Scale) anActionList).getToSx() + ", to Y radius: "
                    + ((Scale) anActionList).getToSy() + " from t=" +
                    (float) anActionList.getStartTime()
                    + "s to t=" + (float) anActionList.getEndTime() + "s\n");
          }
        }

      }
    }


    finalResult = "Shapes:\n" + shapeResult + actionResult;
    createAText(finalResult, out);
    System.setOut((PrintStream) out);

  }

  /**
   * A helper method that creates and prints out the final String based representation
   * of the animation.
   *
   * @param finalResult the resulting string from the describe method.
   * @param out         the PrintStream for the information to be displayed.
   */
  private void createAText(String finalResult, Appendable out) {
    System.setOut((PrintStream) out);
    String[] array1 = finalResult.split("/");
    for (String temp : array1) {
      System.out.println(temp);
    }
  }
}
