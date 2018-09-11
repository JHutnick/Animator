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
 * Creates a SVGView object which implements View interface.  This class is responsible for
 * displaying the animation in a String based SVG, XML code view.
 */
public class SvgView implements View {

  /**
   * Describe will create a string XML code representation of the animation
   * that is currently happening with the view to create an SVG animation.
   */
  @Override
  public void describe(List<Shape> shape, List<Actions> action, int time, Appendable out) {
    System.setOut((PrintStream) out);

    int count1 = 0;
    // Starting line of our svg file.
    System.out.println("<svg width=\"1100\" height=\"1100\" version=\"1.1\"\n" +
            "xmlns=\"http://www.w3.org/2000/svg\">");


    while (count1 < shape.size()) {
      if (shape.get(count1).getShapeType().equals(ShapeType.RECTANGLE)) {
        System.out.format("<rect id=\"%s\" x=\"%.1f\" y=\"%.1f\" width=\"%.1f\" " +
                        "height=\"%.1f\" fill=\"rgb(%d,%d,%d)\" visibility=\"visible\" >\n",
                shape.get(count1).getNameOfShape(),
                shape.get(count1).getReference().getX(),
                shape.get(count1).getReference().getY(),
                shape.get(count1).getWidth(),
                shape.get(count1).getHeight(),
                shape.get(count1).getColor().getRed(),
                shape.get(count1).getColor().getGreen(),
                shape.get(count1).getColor().getBlue());

      } else {
        System.out.format("<ellipse id=\"%s\" cx=\"%.1f\" cy=\"%.1f\" rx=\"%.1f\" ry=\"%.1f\" " +
                        "fill=\"rgb(%d,%d,%d)\" visibility=\"visible\" >\n",
                shape.get(count1).getNameOfShape(),
                shape.get(count1).getReference().getX(),
                shape.get(count1).getReference().getY(),
                shape.get(count1).getWidth(),
                shape.get(count1).getHeight(),
                shape.get(count1).getColor().getRed(),
                shape.get(count1).getColor().getGreen(),
                shape.get(count1).getColor().getBlue());


      }

      int count2 = 0;
      while (count2 < action.size()) {
        if (action.get(count2) instanceof Moves
                && action.get(count2).getNameOfShape().equals(shape.get(count1).getNameOfShape())) {

          if (shape.get(count1).getShapeType().equals(ShapeType.RECTANGLE)) {

            System.out.format("<animate attributeType=\"xml\" begin=\"%.1fms\" " +
                            "dur=\"%.1fms\" attributeName=\"x\" from=\"%.1f\" " +
                            "to=\"%.1f\" fill=\"freeze\" />\n",
                    (action.get(count2).getStartTime() / (float) time) * 1000,
                    (((action.get(count2).getEndTime()) - action.get(count2).getStartTime()) /
                            (float) time)
                            * 1000,
                    ((Moves) action.get(count2)).getStartingPoint().getX(),
                    ((Moves) action.get(count2)).getEndingPoint().getX());

            System.out.format("<animate attributeType=\"xml\" begin=\"%.1fms\" " +
                            "dur=\"%.1fms\" attributeName=\"y\" from=\"%.1f\" " +
                            "to=\"%.1f\" fill=\"freeze\" />\n",
                    (action.get(count2).getStartTime() / (float)time) * 1000,
                    (((action.get(count2).getEndTime()) - action.get(count2).getStartTime()) /
                            (float) time)
                            * 1000,
                    ((Moves) action.get(count2)).getStartingPoint().getY(),
                    ((Moves) action.get(count2)).getEndingPoint().getY());

          }

          if (shape.get(count1).getShapeType().equals(ShapeType.OVAL)) {

            System.out.format("<animate attributeType=\"xml\" begin=\"%.1fms\" " +
                            "dur=\"%.1fms\" attributeName=\"cx\" from=\"%.1f\" " +
                            "to=\"%.1f\" fill=\"freeze\" />\n",
                    (action.get(count2).getStartTime() / (float) time) * 1000,
                    (((action.get(count2).getEndTime()) - action.get(count2).getStartTime()) /
                            (float) time)
                            * 1000,
                    ((Moves) action.get(count2)).getStartingPoint().getY(),
                    ((Moves) action.get(count2)).getEndingPoint().getY());

            System.out.format("<animate attributeType=\"xml\" begin=\"%.1fms\" " +
                            "dur=\"%.1fms\" attributeName=\"cy\" from=\"%.1f\" " +
                            "to=\"%.1f\" fill=\"freeze\" />\n",
                    (action.get(count2).getStartTime() / (float) time) * 1000,
                    (((action.get(count2).getEndTime()) - action.get(count2).getStartTime()) /
                            (float) time)
                            * 1000,
                    ((Moves) action.get(count2)).getStartingPoint().getY(),
                    ((Moves) action.get(count2)).getEndingPoint().getY());

          }


        }
        if (action.get(count2) instanceof Scale
                && action.get(count2).getNameOfShape().equals(shape.get(count1).getNameOfShape())) {

          if (shape.get(count1).getShapeType().equals(ShapeType.RECTANGLE)) {

            System.out.format("<animate attributeName=\"width\" attributeType=\"XML\" " +
                            "begin=\"%.1fms\" dur=\"%.1fms\" fill=\"freeze\" from=\"%.1f\" " +
                            "to=\"%.1f\" />\n",
                    (action.get(count2).getStartTime() / (float) time) * 1000,
                    (((action.get(count2).getEndTime()) - action.get(count2).getStartTime())
                            / (float) time)
                            * 1000,
                    ((Scale) action.get(count2)).getFromSx(),
                    ((Scale) action.get(count2)).getToSx());

            System.out.format("<animate attributeName=\"height\" attributeType=\"XML\" " +
                            "begin=\"%.1fms\" dur=\"%.1fms\" fill=\"freeze\" from=\"%.1f\" " +
                            "to=\"%.1f\" />\n",
                    (action.get(count2).getStartTime() / (float) time) * 1000,
                    (((action.get(count2).getEndTime()) - action.get(count2).getStartTime()) /
                            (float) time)
                            * 1000,
                    ((Scale) action.get(count2)).getFromSy(),
                    ((Scale) action.get(count2)).getToSy());
          } else {

            System.out.format("<animate attributeName=\"rx\" attributeType=\"XML\" " +
                            "begin=\"%.1fms\" dur=\"%.1fms\" fill=\"freeze\" from=\"%.1f\" " +
                            "to=\"%.1f\" />\n",
                    (action.get(count2).getStartTime() / (float) time) * 1000,
                    (((action.get(count2).getEndTime()) - action.get(count2).getStartTime()) /
                            (float) time)
                            * 1000,
                    ((Scale) action.get(count2)).getFromSx(),
                    ((Scale) action.get(count2)).getToSx());

            System.out.format("<animate attributeName=\"ry\" attributeType=\"XML\" " +
                            "begin=\"%.1fms\" dur=\"%.1fms\" fill=\"freeze\" from=\"%.1f\" " +
                            "to=\"%.1f\" />\n",
                    (action.get(count2).getStartTime() / (float) time) * 1000,
                    (((action.get(count2).getEndTime()) - action.get(count2).getStartTime()) /
                            (float) time)
                            * 1000,
                    ((Scale) action.get(count2)).getFromSy(),
                    ((Scale) action.get(count2)).getToSy());
          }


        }
        if (action.get(count2) instanceof ChangeColor
                && action.get(count2).getNameOfShape().equals(shape.get(count1).getNameOfShape())) {

          System.out.format("<animateColor attributeName=\"fill\" attributeType=\"XML\" " +
                          "from=\"rgb(%d,%d,%d)\" to=\"rgb(%d,%d,%d)\" " +
                          "begin=\"%.1fms\" dur=\"%.1fms\" fill=\"freeze\"/>\n",
                  ((ChangeColor) action.get(count2)).getStartingColor().getRed(),
                  ((ChangeColor) action.get(count2)).getStartingColor().getGreen(),
                  ((ChangeColor) action.get(count2)).getStartingColor().getBlue(),
                  ((ChangeColor) action.get(count2)).getEndingColor().getRed(),
                  ((ChangeColor) action.get(count2)).getEndingColor().getGreen(),
                  ((ChangeColor) action.get(count2)).getEndingColor().getBlue(),
                  (action.get(count2).getStartTime() / (float) time) * 1000,
                  (((action.get(count2).getEndTime()) - action.get(count2).getStartTime()) /
                          (float) time)
                          * 1000);
        }
        count2++;
      }
      if (shape.get(count1).getShapeType().equals(ShapeType.RECTANGLE)) {
        System.out.println("</rect>");
      } else {
        System.out.println("</ellipse>");
      }
      count1++;
    }

    System.out.println("</svg>");

  }
}




