package cs5004.animator;

import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cs5004.animator.controller.Controller;
import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.AnimationModelImpl;
import cs5004.animator.util.AnimationFileReader;
import cs5004.animator.view.View;


/**
 * EasyAnimator is a class which consists of a main method to run the program.
 */
public class EasyAnimator {

  /**
   * Main method which takes in arguments from the commandline, creates objects and passes
   * the view, view, speed and output to the controller to take control.
   *
   * @param args the arguments being accepted from the command line consisting of filename,
   *             viewtype output and speed.
   * @throws IOException if no valid PrintStream is given.
   */
  public static void main(String[] args) throws IOException {
    String fileName = "";
    Appendable output = System.out;
    String view = "text";
    int speed = 1;
    JFrame frame = new JFrame();
    Boolean fileCheck = true;
    Boolean typeCheck = true;

    //Parses the command line.
    if (args.length == 0) {
      JOptionPane.showMessageDialog(frame, "No inputs given");
      System.exit(0);
    }

    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-if")) {
        fileCheck = false;
        if (i + 1 > args.length) {
          JOptionPane.showMessageDialog(frame, "No filename given");
          System.exit(0);
        }
        fileName = args[i + 1];
      }
      if (args[i].equals("-iv")) {
        typeCheck = false;
        if (i + 1 > args.length) {
          JOptionPane.showMessageDialog(frame, "No file type given");
          System.exit(0);
        }
        if (args[i + 1].equals("text") || args[i + 1].equals("svg")
                || args[i + 1].equals("visual")) {
          view = args[i + 1];
        } else {
          JOptionPane.showMessageDialog(frame, "Wrong file type, " +
                  "please enter text, svg or visual");
          System.exit(0);
        }
      }
      if (args[i].equals("-o")) {
        if (i + 1 > args.length) {
          JOptionPane.showMessageDialog(frame, "No output given");
          System.exit(0);
        } else if (!(args[i + 1].equals("out"))) {
          output = new PrintStream(args[i + 1]);
        }
      }
      if (args[i].equals("-speed")) {
        if (i + 1 > args.length) {
          JOptionPane.showMessageDialog(frame, "No Speed given");
          System.exit(0);
        }
        speed = Integer.parseInt(args[i + 1]);
      }
    }

    // Error if if or iv not included.
    if (fileCheck || typeCheck) {
      JOptionPane.showMessageDialog(frame, "Need a -if and -iv");
      System.exit(0);
    }

    //Create builder object.
    AnimationModelImpl.Builder build = new AnimationModelImpl.Builder();

    // Create file object and pass it filename and build view.
    AnimationFileReader file = new AnimationFileReader();
    file.readFile(fileName, build);
    AnimationModel model = build.build();

    // Creates cs5004.animator.view.View instance.
    View display = AnimationModelImpl.Viewer.getView(view);

    // Give controller view and view and control.
    Controller controller = new Controller(model, display);
    controller.controllerGo(speed, output);
  }
}
