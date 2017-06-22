# cube-solver
Java program to find a solution to a given scramble for a Rubik's Cube

# Version 0.2
Additions:
 - Cube class can now generate solution to solve the second layer by inserting non-yellow edges into their respective slots
 - Additionally, misoriented edges in the second layer can be properly oriented using a simple algorithm
 - Methods getColorInDir() and getDirOfColor() in Cubie class allow for simplication of logic in several methods of Cube class

Modifications:
 - Cubie and its respective subclasses have been restructured for ease of problem-solving and (hopefully) quicker outputs.
  
# Version 0.1
By running the main() method in SolutionFinder, this program is so far able
to solve the first layer using the beginner's method. This includes making the sunflower,
making the cross, and inserting white corners.
