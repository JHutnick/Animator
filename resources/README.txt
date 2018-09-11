README:

Controller Directory classes: Controller
Model Directory classes: Actions, Action, AnimationModel, AnimationModelImpl, ChangeColor, Moves, Scale, Shape, ShapeInterface, ShapeType.
Util Directory classes: Animation File Reader, TweenModelBuilder
View Directory classes: View (interface), TextView, SVGView
Lastly, EasyAnimator is practically our main class. 

Model Directory Classes:

Action is new, we decided to implement an interface for our abstract class because we found that the following methods were used in both our Actions (abstract class), and
all of our action classes like Scale, Moves, and ChangeColor. Methods within the interface:
   - String nameOfShape
   - int startime
   - int endTime

Actions (implements Action):
Actions is an abstract class that has a constructor(and its methods) used by all actions that can extend to new actions that are in need of being applied to our program. It is current the parent to three actions (Moves, Scale, ChangeColor). We decided to go with an abstract class because we wanted to keep our actions private. Things
Variables Actions hold:
   - String nameOfShape
   - int startime
   - int endTime

Moves (extends actions):
Moves is a class that creates an action with its constructor. The basis of this action is that it allows a shape to move from one point to another. Within this class it constructs the action, but it also has the method as to how the actual shape will move (MovesActionMethod). Our idea for moves: a time will be given an all of the shapes that have this applied action will be updated based on what time it currently is.
Variables Moves hold:
   -Point2D.Double startingPoint
   -Point2D.Double endingPoint


ChangeColor (extends Actions):
ChangeColor is a class that extends Actions and is created using its constructor. The basis of this action is to allow a shape to change color over a period of time. Since this action is abstracted it receives all of the constructor variables from the Actions class. The method to change a shapes color is also within the class ()colorChangeActionMethod), and will allow a shape to gradually change color over time.
Variables ChangeColor holds:
   -Color startingColor
   -Color endingColor

Scale (extends Actions):
Scale extends Actions and is created using its constructor. The basis of this action allows a shape to change one of its dimensions per action over the course of a time period. Since it is abstracted it receives all of its parent class’s variables from the Actions class. We decided to change this up with regards to the variables hold, because we have now realized that both dimensions of a shape can change at a given time.
Variables Scale holds:
   -float fromSx
   -float fromSy
   -float toSx
   -float toSy
   -ShapeType type

Shape:
Shape is currently a public class that has the ability of constructing a shape. Shape will eventually allow a user to pick a shape from an enum ShapeType, this way it restricts the user to only a certain selection of shapes, but if we wanted to add more they could simply be applied. All of the variables within the constructor are used in creating a shape. A to-do for the future when more shapes are implemented may be attach methods to the enums of the shape that will restrict a square or circle for example to be required to have the same width and height.
Variables Shape holds:
   -String nameOfShape
   -ShapeType typeOfShape
   -Point2D.Double reference
   -Color color
   -int appears
   -int disappears
   -double width
   -double height

AnimationModel (Interface)
AnimationModel is an interface that includes all of the methods we wanted our AnimationModelImpl to receive. This will hold action like being able to add a shape to a list or adding an action to a list, and really leaves this open to used by our implements classes.

AnimationModelImpl(implements AnimationModel)
We have three lists that will store the shapes as they are created and will hold their original values, a list that will contain the original shapes but will be manipulated as time passes, and the third list to store all of the actions to be applied to the manipulated list. Our goal for model is our controller will take in a text file and actions and shapes will be added accordingly, then based on the time from 0 to whenever the time ends a conductActions method will be fed the time and actively produce the shapes in current time. We have a few other methods that will be able to reproduce the created shapes and actions from the original creation, and another to describe how the actions and shapes are being changed as the time changes.In here we also have a builder method. The builder model implements TweenModelBuilder<AnimationModel> and has an instance of our AnimationModelImpl. The builder methods this class utilizes allows for the controller to give information to our model and correctly input the information in a fashion that will store information so it can be accessed by the controller. It will also guide the controller to create views based off of the information that has been received. 

Util Directory classes:
Animation File Reader:
Given to us by Dr. Shesh, this class will allow us to pass information correctly from the model to the controller.

TweenModelBuilder:
Given to us by Dr. Shesh, is an interface that has been implemented by the build class in our animationModelImpl. It helps to read a file containing the animation and builds a view.
This interface truly helps organize information that can be accessed via the controller to best assist the view. 

View Classes:

View (Interface):
View is an interface of a single method (describe) that will be used and implemented by both views. It is done in such a way that it keeps the same format for each of the classes that implement this interface. VisualView is a view described in the form of pop up visual with the use of JFrame and JPanels. This view will allow the user to manipulate speed, press play and pause, as well as download an SVG file based on the path the user inputs.

TxtView class:
This class implements the View interface and properly displays information of all of the shapes and actions into a text file or the system. 

SVGView class:
This class implements the view interface to display information not as a text document but as a SVG. It properly loops through both lists of actions and shapes to produce an appended string in the form that will produce a SVG file. This sag file if written in XML so it still looks like a giant string when printed to screen, but when written to a file it gives us the opportunity to drag our SVG file into a web browser to produce an image read in by the controller. 

VisualView class:
This class implements the view interface and the ActionListener. The view interface allows the VisualView to use the describe method which allows access from the controller's access to the shape and action lists. This method uses Java Swing, JPanel and JFrame in order to
Animate and visualize the program to the user.

Controller:
Our controller has both an instance of a model and a view. I will return a model by calling the describe method to generate information to be prepared for our view. 

Easy Animator (The main of our program):
The easy animator will read in the file from a command line and will decipher program inputs to direct what out of the view will occur. 






