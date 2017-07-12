# Rubik's Cube Solver
Java program utilizing a Swing GUI to find a solution to a given scramble for a Rubik's Cube

# v2.0
Incorporated a "Color Selection" mode into GUI. The user is now able to easily input the colors of a valid scrambled cube in the orthodox color scheme. This will allow the user to generate a complete solution and step through it in the same manner as the "Text Scramble" mode. (The Text Scramble mode is still fully functional and usable).

The user will also be told which phase the solution is in (white cross, first layer, second layer, etc.).

# v1.2
Incorporated interactive elements into GUI.

Additions:

 - Start and stop buttons to play/pause animation of cube being solved
 - A slider to adjust the animation speed
 - A text field to input a unique scramble and find the solution
 - Text at bottom of screen to display the moves that need to be performed (black font) and moves that are completed (red font)
 
 Modifications:
 
  - Full eradication of CornerCubie, EdgeCubie, and CenterPiece objects in Cube class (all functionalities for cubies consolidated into Cubie class)

# v1.1
Added CubeDisplayer and CubePainter classes that sequentially step through the moves required to solve a scrambled cube and repaint the cube after each move. Will add functionality to play and pause the animation, as well as enter a custom scramble in a text field.

# v1.0
##### First version that is able to solve the cube completely
Additions:
 - Cube class has methods to orient the last layer
 - Cube class can also finish permuting the last layer

Modifications:
 - inserMisorientedEdges() in Cube class is updated to account for edges that are in the incorrect slot

# v0.2
Additions:
 - Cube class can now generate solution to solve the second layer by inserting non-yellow edges into their respective slots
 - Additionally, misoriented edges in the second layer can be properly oriented using a simple algorithm
 - Methods getColorInDir() and getDirOfColor() in Cubie class allow for simplication of logic in several methods of Cube class

Modifications:
 - Cubie and its respective subclasses have been restructured for ease of problem-solving and (hopefully) quicker outputs.
  
# v0.1
By running the main() method in SolutionFinder, this program is so far able
to solve the first layer using the beginner's method. This includes making the sunflower,
making the cross, and inserting white corners.
