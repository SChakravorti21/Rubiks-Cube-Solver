import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;

public class Cube {

	//Stores the state of the cube as an object of 26 cubies
	private Cubie[][][] cubiePos = new Cubie[3][3][3];

	/**
	 * Constructs the Cube object by instantiating a Cubie for each position in three-dimensional space
	 * When the cube is held with Yellow facing up and Green facing front, x increases going from left to right,
	 * y increases going from the front to the back, and z increases going from the top to the bottom.
	 * x, y, and z are zero-indexed.
	 * The core of the cube is not an actual cubie, but is instantiated as one to prevent runtime error 
	 */
	public Cube() {
		//Up, Front Row
		cubiePos[0][0][0] = new Cubie(0,0,0, 
				new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('R','L'), new CubieColor('G','F')}, true, false);
		cubiePos[1][0][0] = new Cubie(1,0,0, 
				new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('G','F')}, false, true);
		cubiePos[2][0][0] = new Cubie(2,0,0, 
				new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('G','F'), new CubieColor('O','R')}, true, false);

		//Front, E Row
		cubiePos[0][0][1] = new Cubie(0,0,1, 
				new CubieColor[]{ new CubieColor('R','L'), new CubieColor('G','F')}, false, true);
		cubiePos[1][0][1] = new Cubie(1,0,1, 
				new CubieColor[]{ new CubieColor('G','F')}, false, false);
		cubiePos[2][0][1] = new Cubie(2,0,1, 
				new CubieColor[]{ new CubieColor('G','F'), new CubieColor('O','R')}, false, true);

		//Down, Front Row
		cubiePos[0][0][2] = new Cubie(0,0,2, 
				new CubieColor[]{ new CubieColor('W','D') , new CubieColor('R','L'), new CubieColor('G','F')}, true, false);
		cubiePos[1][0][2] = new Cubie(1,0,2, 
				new CubieColor[]{ new CubieColor('W','D') , new CubieColor('G','F')}, false, true);
		cubiePos[2][0][2] = new Cubie(2,0,2, 
				new CubieColor[]{ new CubieColor('W','D') , new CubieColor('G','F'), new CubieColor('O','R')}, true, false);

		//Up, S Row
		cubiePos[0][1][0] = new Cubie(0,1,0, 
				new CubieColor[]{ new CubieColor('R','L'), new CubieColor('Y','U')}, false, true);
		cubiePos[1][1][0] = new Cubie(1,1,0, 
				new CubieColor[]{ new CubieColor('Y','U')}, false, false);
		cubiePos[2][1][0] = new Cubie(2,1,0, 
				new CubieColor[]{ new CubieColor('Y','U'), new CubieColor('O','R')}, false, true);

		//E, S Row
		cubiePos[0][1][1] = new Cubie(0,1,1, 
				new CubieColor[]{ new CubieColor('R','L')}, false, false);
		cubiePos[1][1][1] = new Cubie(1,1,1, 
				new CubieColor[]{ new CubieColor('A','A')}, //Just giving random, non-legitimate values for color and direction
				false, false);
		cubiePos[2][1][1] = new Cubie(2,1,1, 
				new CubieColor[]{ new CubieColor('O','R')}, false, false);

		//Down, S Row
		cubiePos[0][1][2] = new Cubie(0,1,2, 
				new CubieColor[]{ new CubieColor('R','L'), new CubieColor('W','D')}, false, true);
		cubiePos[1][1][2] = new Cubie(1,1,2, 
				new CubieColor[]{ new CubieColor('W','D')}, false, false);
		cubiePos[2][1][2] = new Cubie(2,1,2, 
				new CubieColor[]{ new CubieColor('W','D'), new CubieColor('O','R')}, false, true);

		//Up, Back Row
		cubiePos[0][2][0] = new Cubie(0,2,0, 
				new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('R','L'), new CubieColor('B','B')}, true, false);
		cubiePos[1][2][0] = new Cubie(1,2,0, 
				new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('B','B')}, false, true);
		cubiePos[2][2][0] = new Cubie(2,2,0, 
				new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('B','B'), new CubieColor('O','R')}, true, false);

		//E, Back Row
		cubiePos[0][2][1] = new Cubie(0,2,1, 
				new CubieColor[]{ new CubieColor('R','L'), new CubieColor('B','B')}, false, true);
		cubiePos[1][2][1] = new Cubie(1,2,1, 
				new CubieColor[]{ new CubieColor('B','B')}, false, false);
		cubiePos[2][2][1] = new Cubie(2,2,1, 
				new CubieColor[]{ new CubieColor('B','B'), new CubieColor('O','R')}, false, true);

		//Down, Back Row
		cubiePos[0][2][2] = new Cubie(0,2,2, 
				new CubieColor[]{ new CubieColor('W','D') , new CubieColor('R','L'), new CubieColor('B','B')}, true, false);
		cubiePos[1][2][2] = new Cubie(1,2,2, 
				new CubieColor[]{ new CubieColor('W','D') , new CubieColor('B','B')}, false, true);
		cubiePos[2][2][2] = new Cubie(2,2,2, 
				new CubieColor[]{ new CubieColor('W','D') , new CubieColor('B','B'), new CubieColor('O','R')}, true, false);

	}

	/**
	 * Takes a String value of a turn or rotation in standard Rubik's Cube notation and applies the turn or rotation to
	 * the cube. Valid turns currently include any turn in the following planes: U, D, F, B, L, R, M, E, S
	 * Valid rotations are x, y, and z rotations.
	 * @param turn
	 */
	public void turn (String turn) {
		char[] preChange;
		char[] postChange;
		Cubie[][] matrix = new Cubie[3][3];
		
		switch(turn) {
		case "B":
			preChange = new char[] {'B', 'U', 'R', 'D', 'L'};
			postChange = new char[] {'B', 'L', 'U', 'R', 'D'};
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					matrix[i][j] = cubiePos[Math.abs(j-2)][2][i];
				}
			}
			matrix = rotateMatrix(matrix, 90, preChange, postChange);
			
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubiePos[Math.abs(j-2)][2][i] = matrix[i][j];
				}
			}
			break;
		
		case "B'":
			preChange = new char[] {'B', 'U', 'R', 'D', 'L'};
			postChange = new char[] {'B', 'R', 'D', 'L', 'U'};
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					matrix[i][j] = cubiePos[Math.abs(j-2)][2][i];
				}
			}
			matrix = rotateMatrix(matrix, -90, preChange, postChange);
			
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubiePos[Math.abs(j-2)][2][i] = matrix[i][j];
				}
			}
			break;
			
		case "D" :
			preChange = new char[] {'D', 'L', 'B', 'R', 'F'};
			postChange = new char[] {'D', 'F', 'L', 'B', 'R'};
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					matrix[i][j] = cubiePos[j][i][2];
				}
			}
			matrix = rotateMatrix(matrix, 90, preChange, postChange);
			
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubiePos[j][i][2] = matrix[i][j];
				}
			}
			break;
			
		case "D'" :
			preChange = new char[] {'D', 'F', 'L', 'B', 'R'};
			postChange = new char[] {'D', 'L', 'B', 'R', 'F'};
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					matrix[i][j] = cubiePos[j][i][2];
				}
			}
			matrix = rotateMatrix(matrix, -90, preChange, postChange);
			
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubiePos[j][i][2] = matrix[i][j];
				}
			}
			break;
			
		case "E" :
			preChange = new char[] {'L', 'B', 'R', 'F'};
			postChange = new char[] {'F', 'L', 'B', 'R'};
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					matrix[i][j] = cubiePos[j][i][1];
				}
			}
			matrix = rotateMatrix(matrix, 90, preChange, postChange);
			
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubiePos[j][i][1] = matrix[i][j];
				}
			}
			break;
			
		case "E'" :
			preChange = new char[] {'F', 'L', 'B', 'R'};
			postChange = new char[] {'L', 'B', 'R', 'F'};
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					matrix[i][j] = cubiePos[j][i][1];
				}
			}
			matrix = rotateMatrix(matrix, -90, preChange, postChange);
			
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubiePos[j][i][1] = matrix[i][j];
				}
			}
			break;
			
		case "F": 
			preChange = new char[] {'F', 'U', 'R', 'D', 'L'};
			postChange = new char[] {'F', 'R', 'D', 'L', 'U'};
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					matrix[i][j] = cubiePos[j][0][i];
				}
			}
			matrix = rotateMatrix(matrix, 90, preChange, postChange);
			
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubiePos[j][0][i] = matrix[i][j];
				}
			}
			break;
			
		case "F'":
			preChange = new char[] {'F', 'U', 'R', 'D', 'L'};
			postChange = new char[] {'F', 'L', 'U', 'R', 'D'};
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					matrix[i][j] = cubiePos[j][0][i];
				}
			}
			matrix = rotateMatrix(matrix, -90, preChange, postChange);
			
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubiePos[j][0][i] = matrix[i][j];
				}
			}
			break;
		
		case "L": 
			preChange = new char[] {'L', 'B', 'D', 'F', 'U'};
			postChange = new char[] {'L', 'U', 'B', 'D', 'F'};
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					matrix[i][j] = cubiePos[0][Math.abs(j-2)][i];
				}
			}
			matrix = rotateMatrix(matrix, 90, preChange, postChange);
			
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubiePos[0][Math.abs(j-2)][i] = matrix[i][j];
				}
			}
			break;
			
		case "L'": 
			preChange = new char[] {'L', 'U', 'B', 'D', 'F'};
			postChange = new char[] {'L', 'B', 'D', 'F', 'U'};
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					matrix[i][j] = cubiePos[0][Math.abs(j-2)][i];
				}
			}
			matrix = rotateMatrix(matrix, -90, preChange, postChange);
			
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubiePos[0][Math.abs(j-2)][i] = matrix[i][j];
				}
			}
			break;
			
		case "M": 
			preChange = new char[] {'B', 'D', 'F', 'U'};
			postChange = new char[] {'U', 'B', 'D', 'F'};
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					matrix[i][j] = cubiePos[1][Math.abs(j-2)][i];
				}
			}
			matrix = rotateMatrix(matrix, 90, preChange, postChange);
			
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubiePos[1][Math.abs(j-2)][i] = matrix[i][j];
				}
			}
			break;
			
		case "M'": 
			preChange = new char[] {'U', 'B', 'D', 'F'};
			postChange = new char[] {'B', 'D', 'F', 'U'};
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					matrix[i][j] = cubiePos[1][Math.abs(j-2)][i];
				}
			}
			matrix = rotateMatrix(matrix, -90, preChange, postChange);
			
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubiePos[1][Math.abs(j-2)][i] = matrix[i][j];
				}
			}
			break;
			
		case "R": 
			preChange = new char[] {'R', 'U', 'B', 'D', 'F'};
			postChange = new char[] {'R', 'B', 'D', 'F', 'U'};
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					matrix[i][j] = cubiePos[2][j][i];
				}
			}
			matrix = rotateMatrix(matrix, 90, preChange, postChange);
			
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubiePos[2][j][i] = matrix[i][j];
				}
			}
			break;
			
		case "R'": 
			preChange = new char[] {'R', 'B', 'D', 'F', 'U'};
			postChange = new char[] {'R', 'U', 'B', 'D', 'F'};
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					matrix[i][j] = cubiePos[2][j][i];
				}
			}
			matrix = rotateMatrix(matrix, -90, preChange, postChange);
			
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubiePos[2][j][i] = matrix[i][j];
				}
			}
			break;
			
		case "S": 
			preChange = new char[] {'U', 'R', 'D', 'L'};
			postChange = new char[] {'R', 'D', 'L', 'U'};
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					matrix[i][j] = cubiePos[j][1][i];
				}
			}
			matrix = rotateMatrix(matrix, 90, preChange, postChange);
			
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubiePos[j][1][i] = matrix[i][j];
				}
			}
			break;
			
		case "S'":
			preChange = new char[] {'U', 'R', 'D', 'L'};
			postChange = new char[] {'L', 'U', 'R', 'D'};
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					matrix[i][j] = cubiePos[j][1][i];
				}
			}
			matrix = rotateMatrix(matrix, -90, preChange, postChange);
			
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubiePos[j][1][i] = matrix[i][j];
				}
			}
			break;
		
		case "U" :
			preChange = new char[] {'U', 'F', 'L', 'B', 'R'};
			postChange = new char[] {'U', 'L', 'B', 'R', 'F'};
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					matrix[i][j] = cubiePos[j][Math.abs(i-2)][0];
				}
			}
			matrix = rotateMatrix(matrix, 90, preChange, postChange);
			
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubiePos[j][Math.abs(i-2)][0] = matrix[i][j];
				}
			}
			break;
			
		case "U'" :
			preChange = new char[] {'U', 'L', 'B', 'R', 'F'};
			postChange = new char[] {'U', 'F', 'L', 'B', 'R'};
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					matrix[i][j] = cubiePos[j][Math.abs(i-2)][0];
				}
			}
			matrix = rotateMatrix(matrix, -90, preChange, postChange);
			
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubiePos[j][Math.abs(i-2)][0] = matrix[i][j];
				}
			}
			break;				

		case "x":
			performMoves("R M' L'");
			//turn("R"); turn("M'"); turn("L'");
			break;

		case "x'":
			performMoves("R' M L");
			//turn("R'"); turn("M"); turn("L");
			break;

		case "y":
			performMoves("U E' D'");
			//turn("U"); turn("E'"); turn("D'");
			break;

		case "y'":
			performMoves("U' E D");
			//turn("U'"); turn("E"); turn("D");
			break;

		case "z":
			performMoves("F S B'");
			//turn("F"); turn("S"); turn("B'");
			break;

		case "z'":
			performMoves("F' S' B");
			//turn("F'"); turn("S'"); turn("B");
			break;

		}


	}

	private Cubie[][] rotateMatrix(Cubie[][] orig, int degrees, char[] preChange,
			char[] postChange) {
		Cubie[][] rotated = new Cubie[3][3];
		if(degrees == 90) {
			//Transpose the matrix
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					rotated[i][j] = orig[j][i];
				}
			}
			//Reverse all the rows
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<rotated[0].length/2; j++) {
					Cubie tempCubie = rotated[i][3-j-1];
					rotated[i][3-j-1] = rotated[i][j];
					rotated[i][j] = tempCubie;
				}
			}
		}
		else if(degrees == -90) {
			//Transpose the matrix
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					rotated[i][j] = orig[j][i];
				}
			}
			
			//Reverse all the columns
			for(int i = 0; i<rotated[0].length/2; i++) {
				for(int j = 0; j<3; j++) {
					Cubie tempCubie = rotated[3-i-1][j];
					rotated[3-i-1][j] = rotated[i][j];
					rotated[i][j] = tempCubie;
				}
			}
		}
		
		//Change the direction of all colors appropriately as well before returning the array
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				CubieColor[] tempColors = rotated[i][j].getColors();
				for(int k = 0; k<tempColors.length; k++) {
					int index = 6;
					for(int x = 0; x < preChange.length; x++) {
						if(tempColors[k].getDir() == preChange[x]) {
							index = x;
						}
					}
					if(index<postChange.length)
						tempColors[k].setDir(postChange[index]);
				}
				rotated[i][j].setColors(tempColors);
			}
		}
		return rotated;
	}
	
	/**
	 * Loops through the characters in a String of standard turning notation to apply the set of moves to the cube
	 * Checks for clockwise, double, and counterclockwise turns
	 * @param moves: the most to be applied to the cube
	 * @return the moves performed on the cube
	 */
	public String performMoves(String moves) {
		for(int i = 0; i<moves.length(); i++) {
			if(moves.substring(i, i+1) != " ") { //Only check if there is a meaningful character
				if(i != moves.length()-1) {
					if(moves.substring(i+1, i+2).compareTo("2") == 0) {
						//Turning twice ex. U2
						turn(moves.substring(i, i+1)); 
						turn(moves.substring(i, i+1));
						i++; //Skip the "2" for the next iteration
					}
					else if(moves.substring(i+1,i+2).compareTo("'") == 0) {
						//Making a counterclockwise turn ex. U'
						turn(moves.substring(i, i+2));
						i++; //Skip the apostrophe for the next iteration
					}
					else {
						//Regular clockwise turning
						turn(moves.substring(i, i+1));
					}
				}
				else {
					//Nothing is after the turn letter, so just perform the turn
					turn(moves.substring(i, i+1));
				}
			}
		}
		return moves;
	}

	/**
	 * Performs the inverse of the moves inputed as parameters. For example, if the parameter is "U' ",
	 * the moves "U" will be applied upon the cube to negate the "U' ". Helper methods for use in the
	 * CubePainter class to rewind moves.
	 * @param moves
	 */
	public void reverseMoves(String moves) {
		for(int i = 0; i<moves.length(); i++) {
			if(moves.substring(i, i+1) != " ") { //Only check if there is a meaningful character
				if(i != moves.length()-1) {
					if(moves.substring(i+1, i+2).compareTo("2") == 0) {
						//Turning twice ex. U2
						turn(moves.substring(i, i+1)); 
						turn(moves.substring(i, i+1));
						i++; //Skip the "2" for the next iteration
					}
					else if(moves.substring(i+1,i+2).compareTo("'") == 0) {
						//Making a clockwise turn ex. U
						turn(moves.substring(i, i+1));
						i++; //Skip the apostrophe for the next iteration
					}
					else {
						//Counterclockwise turning
						turn(moves.substring(i, i+1) + "'");
					}
				}
				else {
					//Nothing is after the turn letter, so perform counterclockwise turn
					turn(moves.substring(i, i+1) + "'");
				}
			}
		}
	}
	
	public String optimizeMoves(String moves) {
		for(int i = 0; i<moves.length(); i++) {
			String move = moves.substring(i, i+1);
			if(!move.equals(" ") && !move.equals("'") && !move.equals("2")) { //Only check if there is a meaningful turn/rotation
				if(i <= moves.length()-3) {
					if(moves.substring(i+1, i+2).compareTo("2") == 0) { //Double turn
						if(i <= moves.length()-4 && moves.charAt(i+3) == moves.charAt(i)) {
							if(i <= moves.length()-5) {
								if(moves.substring(i+4, i+5).compareTo("2") == 0) {
									//Ex. "U2 U2" gets negated
									moves = moves.substring(0, i) + moves.substring(i+5);
									i--;
								} else if(moves.substring(i+4, i+5).compareTo("'") == 0) {
									//Ex. "U2 U'" --> "U"
									moves = moves.substring(0, i) + moves.substring(i, i+1) 
									+ moves.substring(i+5);
									i--;
								} else {
									//Ex. "U2 U" --> "U'"
									moves = moves.substring(0, i) + moves.substring(i, i+1) + "'" 
											+ moves.substring(i+4);
									i--;
								}
							} else {
								//Ex. "U2 U" --> "U'"
								moves = moves.substring(0, i) + moves.substring(i, i+1) + "'" 
										+ moves.substring(i+4);
								i--;
							}
						}
					}
					else if(moves.substring(i+1,i+2).compareTo("'") == 0) { //Clockwise turn
						if(i <= moves.length()-4 && moves.charAt(i+3) == moves.charAt(i)) {
							if(i <= moves.length()-5) {
								if(moves.substring(i+4, i+5).compareTo("2") == 0) {
									//Ex. "U' U2" --> "U"
									moves = moves.substring(0, i) + moves.substring(i, i+1) 
									+ moves.substring(i+5);
									i--;
								} else if(moves.substring(i+4, i+5).compareTo("'") == 0) {
									//Ex. "U' U'" --> "U2"
									moves = moves.substring(0, i) + moves.substring(i, i+1) + "2" 
											+ moves.substring(i+5);
									i--;
								} else {
									//Ex. "U' U" gets negated
									moves = moves.substring(0, i) + moves.substring(i+4);
									i--;
								}
							} else {
								//Ex. "U' U" gets negated
								moves = moves.substring(0, i) + moves.substring(i+4);
								i--;
							}
						}
					}
					else { //Clockwise turn
						if(i <= moves.length()-3 && moves.charAt(i+2) == moves.charAt(i)) {
							if(i <= moves.length()-4) {
								if(moves.substring(i+3, i+4).compareTo("2") == 0) {
									//Ex. "U U2" --> "U' "
									moves = moves.substring(0, i) + moves.substring(i, i+1) + "'" 
											+ moves.substring(i+4);
									i--;
								} else if(moves.substring(i+3, i+4).compareTo("'") == 0) {
									//Ex. "U U'" gets negated
									moves = moves.substring(0, i) + moves.substring(i+4);
									i--;
								} else {
									//Ex. "U U" --> "U2"
									moves = new String(moves.substring(0, i) + moves.substring(i, i+1) + "2" 
											+ moves.substring(i+3));
									i--;
								}
							} else {
								//Ex. "U U" --> "U2"
								moves = new String(moves.substring(0, i) + moves.substring(i, i+1) + "2" 
										+ moves.substring(i+3));
								i--;
							}
						}

					}
				}
			}
		}

		return moves;	
	}
	
	public String randScramble() {
		String scramble = new String();
		char[] possMoves = new char[] {'U', 'D', 'R', 'L', 'F', 'B'};
		char prevMove = possMoves[(int)(Math.random()*6)];
		char secondLastMove = possMoves[(int)(Math.random()*6)];
		for(int numMoves = 0; numMoves < 20; ) {
			char move = possMoves[(int)(Math.random()*6)];
			if(move != prevMove && move!= secondLastMove) {
				int rand = (int)(Math.random()*100);
				if(rand < 33) {
					scramble += move + "2 ";
				} else if(rand < 67) {
					scramble += move + "' ";
				} else {
					scramble += move + " ";
				}
				secondLastMove = prevMove;
				prevMove = move;
				numMoves++;
			}
		}
		scramble(scramble);
		return scramble;
	}
	/**
	 * Scrambles a cube according to WCA rules (White on top, Green in front). 
	 * After scrambling, returns the cube to the original position (Yellow on top, Green in front). 
	 * @param scramble
	 */
	public void scramble(String scramble) {
		//Rotate the cube to get white on top, then return cube to original position at end of scramble
		performMoves("z2 " + scramble + " z2");
	}

	/**
	 * Once the sunflower is made, this method matches white edges to their respective faces and turns them down
	 * one at a time, creating the white cross.
	 * @return the moves used to create the white cross
	 */
	public String makeWhiteCross() {
		String moves = new String();

		while(numWhiteEdgesOriented() != 0) { //Turn sunflower into cross until no white edges remain in the U layer
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(cubiePos[i][j][0].isEdgeCubie()) {
						CubieColor[] tempColors = cubiePos[i][j][0].getColors();
						if(tempColors[0].getColor() == 'W' || tempColors[1].getColor() == 'W') {
							for(int k = 0; k<2; k++) {
								//Check for when the white edge is matched up with the respective face and turn it down
								if((tempColors[k].getColor() == 'R' && tempColors[k].getDir() == 'L') ||
										(tempColors[k].getColor() == 'G' && tempColors[k].getDir() == 'F') ||
										(tempColors[k].getColor() == 'O' && tempColors[k].getDir() == 'R')||
										(tempColors[k].getColor() == 'B' && tempColors[k].getDir() == 'B')) {
									moves+=performMoves(cubiePos[i][j][0].verticalFace(i, j) + "2 ") ;
								}
							}
						}
					}
				}	
			}
			//Turn U to try lining up edges that have not been turned down yet
			moves+=performMoves("U ");
		}
		return optimizeMoves(moves);
	}

	/**
	 * Makes the sunflower (yellow center in the  middle with 4 white edges surrounding it). 
	 * The sunflower can then be used by makeCross() to make the white cross
	 * @return moves used to make sunflower
	 */
	public String makeSunflower() {
		String moves = new String();

		//Brings up white edges in D Layer with white facing down
		if(numWhiteEdgesOriented() < 5) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(cubiePos[i][j][2].isEdgeCubie()) {
						if(cubiePos[i][j][2].getDirOfColor('W') == 'D') {
							moves += prepareSlot(i, j, 0, 'W');
							//Get the vertical plane in which the cubie lies
							char turnToMake = cubiePos[i][j][2].verticalFace(i, j);
							moves += performMoves("" + turnToMake + "2 ");
						}
					}
				}
			}
		}

		//Orients white edges in D Layer with white NOT facing down
		if(numWhiteEdgesOriented() < 5) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(cubiePos[i][j][2].isEdgeCubie()) {
						if(cubiePos[i][j][2].getDirOfColor('W') != 'A' && cubiePos[i][j][2].getDirOfColor('W') != 'D') {
							char vert = cubiePos[i][j][2].verticalFace(i, j);
							moves += prepareSlot(i, j, 0, 'W');
							if(vert == 'F') {
								moves += performMoves("F' U' R ");
							} else if(vert == 'R') {
								moves += performMoves("R' U' B ");
							} else if(vert == 'B') {
								moves += performMoves("B' U' L ");
							} else if(vert == 'L') {
								moves += performMoves("L' U' F ");
							}
						}
					}
				}

			}
		}

		//Brings up white edges in E Layer
		//This one is filled with many if blocks because there are eight different possible orientations for 
		//white edges in the E Layer, with none sharing a common move to bring it into the U layer.
		if(numWhiteEdgesOriented() < 5) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(cubiePos[i][j][1].isEdgeCubie()) {
						CubieColor[] tempColors = cubiePos[i][j][1].getColors();
						for(int k = 0; k<2; k++) {
							if(tempColors[k].getColor() == 'W') {
								/* Depending on the position of the edge, one of the vertical planes it lies
								 * in must be cleared of white edges before bringing it up */
								if(i == 0 && j == 0) {
									if(tempColors[k].getDir() == 'L') {
										moves += prepareSlot(1, 0, 0, 'W') + performMoves("F ");
									} else {
										moves += prepareSlot(0, 1, 0, 'W') + performMoves("L' ");
									}
								}
								else if(i == 2 && j == 0) {
									if(tempColors[k].getDir() == 'F') {
										moves += prepareSlot(2, 1, 0, 'W') + performMoves("R ");
									} else {
										moves += prepareSlot(1, 0, 0, 'W') + performMoves("F' ");
									}
								}
								else if(i == 2 && j == 2) {
									if(tempColors[k].getDir() == 'B') {
										moves += prepareSlot(2, 1, 0, 'W') + performMoves("R' ");
									} else {
										moves += prepareSlot(1, 2, 0, 'W') + performMoves("B ");
									}
								}
								else {
									if(tempColors[k].getDir() == 'B') {
										moves += prepareSlot(0, 1, 0, 'W') + performMoves("L ");
									} else {
										moves += prepareSlot(1, 2, 0, 'W') + performMoves("B' ");
									}
								}
							}
						}
					}
				}

			}
		}

		//Fix any edges that are incorrectly oriented in the U Layer
		//For the sake of reducing movecount, I assigned a set of moves for each position,
		//but a solver may simply make U turns to bring the edge in front and perform "F U' R"
		if(numWhiteEdgesOriented() < 5) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(cubiePos[i][j][0].isEdgeCubie()) {
						if(cubiePos[i][j][0].getDirOfColor('W') != 'A' && cubiePos[i][j][0].getDirOfColor('W') != 'U') {
							char vert = cubiePos[i][j][0].verticalFace(i, j);
							if(vert == 'F') {
								moves += performMoves("F U' R ");
							} else if(vert == 'R') {
								moves += performMoves("R U' B ");
							} else if(vert == 'B') {
								moves += performMoves("B U' L ");
							} else if(vert == 'L') {
								moves += performMoves("L U' F ");
							}
						}
					}
				}

			}
		}

		//If fewer than 4 white edges reached the top layer by the end of this, some white edge was missed
		//(This might happen, say, if bringing an edge up from the E Layer unintentionally brings down an incorrectly
		// oriented edge in the U Layer)
		//Recurse to oriented remaining white edges
		if(numWhiteEdgesOriented() < 4) {
			moves += makeSunflower();
		}

		return optimizeMoves(moves);
	}

	/**
	 * Utility method for makeSunflower()
	 * Prepares a slot in the U face for white edges to be brought up into the U layer without misorienting white
	 * edges already in the U layer
	 * @param x, y, z
	 * @param color
	 * @return moves used to prepare the edge slot
	 */
	public String prepareSlot(int x, int y, int z, char color) {
		int numUTurns = 0;
		CubieColor[] tempColor = cubiePos[x][y][z].getColors();
		while((tempColor[0].getColor() == color || tempColor[1].getColor() == color) && numUTurns < 5){
			//Keep turning U until the position (x, y, z) is not occupied by a white edge
			performMoves("U");
			tempColor = cubiePos[x][y][z].getColors();
			numUTurns++;
		}

		//Return appropriate amount of U turns
		if(numUTurns == 0 || numUTurns == 4) {
			return "";
		}
		else if(numUTurns == 1) {
			return "U ";
		}
		else if (numUTurns == 2) {
			return "U2 ";
		}
		else return "U' ";
	}

	/**
	 * Utility method for makeSunflower()
	 * @return the number of white edges that are currently in the U layer
	 */
	public int numWhiteEdgesOriented() {
		int numOriented = 0;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(cubiePos[i][j][0].isEdgeCubie()) {
					if(cubiePos[i][j][0].getDirOfColor('W') == 'U') {
						numOriented++;
					}
				}
			}
		}
		return numOriented;
	}

	/**
	 * Completes the white layer by inserting any white corners in the U layer and fixing misoriented
	 * white corners until there are no more white corners in the U layer.
	 * @return the moves used to complete the white layer
	 */
	public String finishWhiteLayer() {
		String moves = new String();
		//At least check once for corners to be inserted/fixed, and repeat as necessary
		moves+=insertCornersInU();
		moves+="\n";
		moves+=insertMisorientedCorners();
		moves+="\n";
		while(whiteCornerinU()) {
			moves+=insertCornersInU();
			moves+="\n";
			moves+=insertMisorientedCorners();
			moves+="\n";
		}
		return optimizeMoves(moves);
	}

	/**
	 * Utility method for insertCornersinU()
	 * @return if there are any white corners in the U layer
	 */
	public boolean whiteCornerinU() {
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				if(cubiePos[i][j][0].isCornerCubie()) {
					//If a cubie does not have a color, getDirOfColor returns 'A'
					if(cubiePos[i][j][0].getDirOfColor('W') != 'A') 
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * Inserts any white corners that are in the U layer. First positions them to the position (2, 0, 0), then
	 * makes U turns and y rotations until the white corner is above its respective slot, and finally inserts
	 * the corner by repetitively executing R U R' U'. This is repeated of all whit corners in the U layer.
	 * @return moves used to insert white corners that are in the U layer
	 */
	public String insertCornersInU() {
		String moves = new String();

		for(int y = 0; y<3; y++) {
			for(int x = 0; x<3; x++) {
				if(cubiePos[x][y][0].isCornerCubie() && cubiePos[x][y][0].isWhiteCorner()) {
					//Make U turns until cubie is at (2, 0, 0)
					if(x==0) {
						if(y==0) {
							moves+=performMoves("U' "); 
						}
						else {
							moves+=performMoves("U2 "); 
						}
					}
					else {
						if(y==2) {
							moves+=performMoves("U "); 
						}
					}
					//Set x and y = 0 for the next loop to avoid using while loop
					y=0; x=0;

					//Get cubie above respective slot in first layer
					int numUTurns = 0;
					int yRotations = 0;
					while(!whiteCornerPrepared()) { 
						performMoves("U y'"); numUTurns++; yRotations++;
					}
					if(numUTurns == 1) {
						moves += "U ";
					} else if(numUTurns == 2) {
						moves += "U2 ";
					} else if(numUTurns == 3) {
						moves += "U' ";
					}
					if(yRotations == 1) {
						moves+="y' ";
					} else if(yRotations == 2) {
						moves += "y2 ";
					} else if(yRotations == 3) {
						moves += "y ";
					}

					//Insert the cubie
					int numSexyMoves = 0; 
					//Don't worry, the algorithm "R U R' U'" is commonly referred to as the sexy move in the cubing community
					while(!cornerInserted(2, 0, 2)){ 
						performMoves("R U R' U'"); numSexyMoves++;
					}
					if(numSexyMoves == 5) { //5 sexy moves can be condensed into "U R U' R'"
						moves += "U R U' R' ";
					}
					else {
						for(int k = 0; k<numSexyMoves; k++) {
							moves += "R U R' U' ";
						}
					}
				}
			}
		}

		return moves;
	}

	/**
	 * Properly inserts white corners that are in the first layer but not oriented correctly
	 * @return moves used to properly orient misoriented white corners
	 */
	public String insertMisorientedCorners() {
		String moves = new String();
		for(int i = 0; i<4; i++) {
			moves += performMoves("y ");
			if(!cornerInserted(2,0,2)) {
				if(cubiePos[2][0][2].isWhiteCorner()) {
					if(!cornerInserted(2,0,2)) {
						//Use R U R' U' to get corner to U layer, then insert it in appropriate slot
						moves+=performMoves("R U R' U' ");
						moves+=insertCornersInU();
					}
				}
			}
		}
		return moves;
	}

	/**
	 * Utility method for insertCornersInU(). 
	 * Checks for whether the corner cubie at (2, 0, 0) belongs in (2, 0, 2).
	 * @return true if cubie at (2, 0, 0) belongs in (2, 0, 2), else false
	 */
	public boolean whiteCornerPrepared() {
		boolean whiteUp = false; 

		//Figure out whether the corner cubie is even a white corner
		if(cubiePos[2][0][0].isCornerCubie() && cubiePos[2][0][0].getDirOfColor('W') == 'A') {
			return false;
		}

		//If the cubie is a white corner, figure out whether the white sticker is facing up
		if(cubiePos[2][0][0].getDirOfColor('W') == 'U')
			whiteUp = true;

		//Based on whether white is up, check accordingly if the corner is above the appropriate slot
		if(whiteUp) {
			return (cubiePos[2][0][0].getColorOfDir('R') == cubiePos[1][0][1].getColors()[0].getColor() && 
					cubiePos[2][0][0].getColorOfDir('F') == cubiePos[2][1][1].getColors()[0].getColor()	);
		}
		else {
			/*Either the color on the right of the cubie matches its respective center piece OR
			 *the color on the front of the cubie matches its respective center piece 
			 *It is not possible for both to match because if white is not facing up, it will either be facing front or right
			 */
			return (cubiePos[2][0][0].getColorOfDir('R') == cubiePos[2][1][1].getColors()[0].getColor() || 
					cubiePos[2][0][0].getColorOfDir('F') == cubiePos[1][0][1].getColors()[0].getColor());
		}
	}

	/**
	 * Correctly checks whether the corner at (2, 0, 2) is solved.
	 * @param x, y, z
	 * @return true if corner is solved, else false
	 */
	public boolean cornerInserted(int x, int y, int z) {
		if(cubiePos[x][y][z].isCornerCubie()) {
			return (cubiePos[x][y][z].getColorOfDir('D') == cubiePos[1][1][2].getColors()[0].getColor() && 
					cubiePos[x][y][z].getColorOfDir('F') == cubiePos[1][0][1].getColors()[0].getColor() &&
					cubiePos[x][y][z].getColorOfDir('R') == cubiePos[2][1][1].getColors()[0].getColor());
		}
		return false;
	}

	/**
	 * Utilizes the methods insertEdgesInU() and insertMisorientedEdges() to complete the second layer
	 * @return A String for the moves used to complete the second layer
	 */
	public String insertAllEdges() {
		String moves = new String();
		//At least check once for edges to be inserted/fixed, and repeat as necessary
		moves+=insertEdgesInU();
		moves+="\n";
		moves+=insertMisorientedEdges();
		moves+="\n";
		while(nonYellowEdgesInU()) {
			moves+=insertEdgesInU();
			moves+="\n";
			moves+=insertMisorientedEdges();
			moves+="\n";
		}
		return optimizeMoves(moves);
	}

	/**
	 * Checks whether any non-yellow edges remain in the U layer.
	 * (Any such edges need to be inserted into their respective slot in the second layer)
	 * @return whether there is/are non-yellow edges in the U layer
	 */
	public boolean nonYellowEdgesInU() {
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				if(cubiePos[i][j][0].isEdgeCubie()) {
					//If a cubie does not have a color, getDirOfColor returns 'A'
					if(cubiePos[i][j][0].getDirOfColor('Y') == 'A') 
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * Inserts all non-yellow edges in the U layer into their respective slots in the 
	 * second layer using one of two algorithms
	 * @return moves used to insert non-yellow edges in the U layer
	 */
	public String insertEdgesInU() {
		String moves = new String();
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				if(cubiePos[i][j][0].isEdgeCubie() && cubiePos[i][j][0].getDirOfColor('Y') == 'A') {
					//Make U turns to get cubie to (1, 0, 0)
					if(j==1) {
						if(i==0) {
							moves+=performMoves("U' ");
						} else {
							moves+=performMoves("U ");
						}
					}
					else if(j==2){
						moves+=performMoves("U2 ");
					}

					//Get cubie above respective slot in second layer
					int numUTurns = 0;
					int yRotations = 0;
					while(cubiePos[1][0][0].getColorOfDir('F') != cubiePos[1][0][1].getColors()[0].getColor()) { 
						performMoves("U y' "); numUTurns++; yRotations++;
					}
					//Add appropriate amount of U turns to moves
					if(numUTurns == 1) {
						moves += "U ";
					} else if(numUTurns == 2) {
						moves += "U2 ";
					} else if(numUTurns == 3) {
						moves += "U' ";
					}
					if(yRotations == 1) {
						moves+="y' ";
					} else if(yRotations == 2) {
						moves += "y2 ";
					} else if(yRotations == 3) {
						moves += "y ";
					}

					//Insert the cubie in appropriate direction
					if(cubiePos[1][0][0].getColorOfDir('U') == cubiePos[0][1][1].getColors()[0].getColor()) {
						moves += performMoves("U' L' U L U F U' F' ");
					}
					else if(cubiePos[1][0][0].getColorOfDir('U') == cubiePos[2][1][1].getColors()[0].getColor()){
						moves += performMoves("U R U' R' U' F' U F ");
					}

					//Reset i and j to continue checking for edges to be inserted (foregoes use of while loop)
					i = 0; j = 0;
				}
			}
		}
		return moves;
	}

	/**
	 * If there are any edges in the second layer that were inserted in the incorrect
	 * orientation, this method re-inserts them in the correct orientation
	 * @return moves used to fix edge orientations in second layer
	 */
	public String insertMisorientedEdges() {
		String moves = new String();
		for(int i = 0; i<4; i++) {
			moves += performMoves("y ");
			if(cubiePos[2][0][1].getDirOfColor('Y') == 'A' &&
					cubiePos[2][0][1].getColorOfDir('F') != cubiePos[1][0][1].getColors()[0].getColor()) {
				//If the edge is the the correct slot but oriented incorrectly, perform an algorithm for this special case
				if(cubiePos[2][0][1].getColorOfDir('F') == cubiePos[2][1][1].getColors()[0].getColor() &&
						cubiePos[2][0][1].getColorOfDir('R') == cubiePos[1][0][1].getColors()[0].getColor()) {
					moves += performMoves("R U R' U2 R U2 R' U F' U' F ");
				}
				else {
					//If there is an edge that does not belong in the slot at all, take it out and insert in correct slot
					moves+=performMoves("R U R' U' F' U' F ");
					moves+=insertEdgesInU();
				}
			}
		}
		return moves;
	}

	/**
	 * Utility method for yellowEdgeOrientation() and makeYellowCross()
	 * @return the number of yellow edges that are already oriented in the U layer
	 */
	public int numYellowEdgesOriented(){
		int numOriented = 0;
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				if(cubiePos[i][j][0].isEdgeCubie() && cubiePos[i][j][0].getDirOfColor('Y') == 'U')
					numOriented++;
			}
		}
		return numOriented;
	}

	/**
	 * Utility method for orientLastLayer()
	 * @return the number of yellow corners that are already oriented in the U layer
	 */
	public int numYellowCornersOriented(){
		int numOriented = 0;
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				if(cubiePos[i][j][0].isCornerCubie() && cubiePos[i][j][0].getDirOfColor('Y') == 'U')
					numOriented++;
			}
		}
		return numOriented;
	}

	/**
	 * Utility method for makeYellowCross(). Determines the shape that the oriented
	 * yellow edges make.
	 * @return Dot, L, Bar, or Cross
	 */
	public String yellowEdgeOrientation() {
		String status = new String();
		int numOriented = numYellowEdgesOriented();

		if(numOriented == 4) { //The cross has already been made
			status = "Cross";
		}
		else if(numOriented == 0) { //No edges are oriented
			status = "Dot";
		}
		else if(numOriented == 2) {
			//If two edges are oriented, they either form an L-shape or a Bar
			int[] xValues = new int[2];
			int index = 0;
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					if(cubiePos[i][j][0].isEdgeCubie() && cubiePos[i][j][0].getDirOfColor('Y') == 'U') {
						xValues[index] = i; index++;
					}
				}
			}
			if(Math.abs(xValues[0]-xValues[1])%2 == 0) {
				status = "Bar";
			} else {
				status = "L";
			}
		}

		return status;
	}

	/**
	 * Orients all yellow edges in the U layer based on their current state.
	 * @return moves used to make the yellow cross
	 */
	public String makeYellowCross() {
		String moves = new String();
		String status = yellowEdgeOrientation();

		if(status.compareTo("Dot") == 0) {
			//Make an L and then subsequently use the algorithm to orient the edges
			moves += performMoves("F R U R' U' F' U2 F U R U' R' F' ");
		}
		else if(status.compareTo("L") == 0) {
			//Position the L appropriately first
			while(cubiePos[0][1][0].getDirOfColor('Y') != 'U' || cubiePos[1][2][0].getDirOfColor('Y') != 'U') {
				moves += performMoves("U ");
			}
			moves += performMoves("F U R U' R' F' ");
		}
		else if(status.compareTo("Bar") == 0) {
			//Position the Bar appropriately first
			while(cubiePos[0][1][0].getDirOfColor('Y') != 'U' || cubiePos[2][1][0].getDirOfColor('Y') != 'U') {
				moves += performMoves("U ");
			}
			moves += performMoves("F R U R' U' F' ");
		}
		return optimizeMoves(moves);
	}

	/**
	 * Finishes the step of orienting the last layer by orienting all yellow corners using
	 * a beginner's method algorithm. (This has been left separate from makeYellowCross() 
	 * to help beginners easily follow the steps to orient the last layer completely.)
	 * @return moves used to orient last layer pieces
	 */
	public String orientLastLayer() {
		String moves = new String();
		int numOriented = numYellowCornersOriented();
		//Used while loop since Antisune case requires Sune algorithm to be perform twice for proper orientation
		while(numOriented != 4) {
			if(numOriented == 0){
				//Turn until there is a yellow sticker on the left of the ULF piece
				while(cubiePos[0][0][0].getDirOfColor('Y') != 'L') {
					moves += performMoves("U ");
				}
				//Perform Sune algorithm to orient one corner
				moves += performMoves("R U R' U R U2 R' ");
			}
			else if(numOriented == 1){
				//Sune case
				while(cubiePos[0][0][0].getDirOfColor('Y') != 'U') {
					moves += performMoves("U ");
				}
				moves += performMoves("R U R' U R U2 R' ");
			}
			else if(numOriented == 2){
				//Turn until there is a yellow sticker on the front of the ULF piece
				while(cubiePos[0][0][0].getDirOfColor('Y') != 'F') {
					moves += performMoves("U ");
				}
				//Perform Sune algorithm to orient one corner
				moves += performMoves("R U R' U R U2 R' ");
			}
			//Re-check the number of corners oriented
			numOriented = numYellowCornersOriented();
		}
		return optimizeMoves(moves);
	}

	/**
	 * Permutes the last layer such that all oriented pieces are in the correct positions
	 * relative to each other. First permutes the corners, then the edges.
	 * @return
	 */
	public String permuteLastLayer() {
		String moves = new String();
		//Check the number of "headlights" that exist, i.e. adjacent corners with the same color facing one direction
		//If there are 4 headlights, the corners are already permuted
		int numHeadlights = 0;
		for(int i = 0; i<4; i++) {
			turn("y"); //Since we are rotating 4 times, the cube is unaffected in the end
			if(cubiePos[0][0][0].getColorOfDir('F') == cubiePos[2][0][0].getColorOfDir('F'))
				numHeadlights++;
		}

		//Permute the corners
		if(numHeadlights == 0){ //If no headlights, create headlights first
			moves += performMoves("R' F R' B2 R F' R' B2 R2 ");
			numHeadlights = 1;
		}
		if(numHeadlights == 1) {
			while(cubiePos[0][2][0].getColorOfDir('B') != cubiePos[2][2][0].getColorOfDir('B')) {
				moves += performMoves("U ");
			}
			moves += performMoves("R' F R' B2 R F' R' B2 R2 ");
		}

		//Now permute the edges after finding out how many edges are already solved
		int numSolved = 0;
		for(int i = 0; i<4; i++) {
			turn("y");
			if(cubiePos[0][0][0].getColorOfDir('F') == cubiePos[1][0][0].getColorOfDir('F'))
				numSolved++;
		}
		if(numSolved == 0) { //If no edges are solved, this will solve one edge
			moves += performMoves("R2 U R U R' U' R' U' R' U R' ");
			numSolved = 1;
		}
		if(numSolved == 1){
			//Use either the clockwise or counterclockwise edge rotation algorithm to solve all corners
			while(cubiePos[0][2][0].getColorOfDir('B') != cubiePos[1][2][0].getColorOfDir('B')) {
				moves += performMoves("U ");
			}
			if(cubiePos[1][0][0].getColorOfDir('F') == cubiePos[0][0][0].getColorOfDir('L')) {
				moves += performMoves("R2 U R U R' U' R' U' R' U R' ");
			}
			else {
				moves += performMoves("R U' R U R U R U' R' U' R2 ");
			}
		}

		//Adjust the U layer to finish the cube!
		while(cubiePos[0][0][0].getColorOfDir('F') != cubiePos[1][0][1].getColors()[0].getColor()) {
			moves += performMoves("U ");
		}

		return optimizeMoves(moves);
	}

	/**
	 * This method allows for the painting of the cube in the GUI. 
	 * All 6 faces' colors are stored in 2D arrays as character values, then all 2D arrays 
	 * are inserted into a 3D array so that all faces' colors can be accessed in one method call
	 * instead of having to call 6 different methods.
	 * @return the set of all colors that define the state of the cube
	 */
	public char[][][] getColors() {
		char[][][] allSets = new char[6][3][3];
		//All 2D arrays are row-major
		char[][] left = new char[3][3];
		char[][] up = new char[3][3];
		char[][] front = new char[3][3];
		char[][] back = new char[3][3];
		char[][] right = new char[3][3];
		char[][] down = new char[3][3];

		//NOTE: the logic following may seem confusing because we need to store the colors as *they will be displayed*.
		//This means, for example, that the left side of the cube will be rotated 90 degrees clockwise such that
		//when displayed, it looks as if it is directly "connected" to the yellow (U) face.

		//Populate left colors, constant x
		for(int y = 2; y>=0; y--) {
			for(int z = 2; z>=0; z--) {
				left[Math.abs(y-2)][Math.abs(z-2)] = cubiePos[0][y][z].getColorOfDir('L');
			}
		}
		//Up colors, constant z
		for(int x = 0; x<=2; x++) {
			for(int y = 2; y>=0; y--) {
				up[Math.abs(y-2)][x] = cubiePos[x][y][0].getColorOfDir('U');
			}
		}
		//Front colors, constant y
		for(int z = 0; z<=2; z++) {
			for(int x = 0; x<=2; x++) {
				front[z][x] = cubiePos[x][0][z].getColorOfDir('F');
			}
		}
		//Back colors, constant y
		for(int x = 0; x<=2; x++) {
			for(int z = 2; z>=0; z--) {
				back[Math.abs(z-2)][x] = cubiePos[x][2][z].getColorOfDir('B');
			}
		}
		//Right colors, constant x
		for(int y = 2; y>=0; y--) {
			for(int z = 0; z<=2; z++) {
				right[Math.abs(y-2)][z] = cubiePos[2][y][z].getColorOfDir('R');
			}
		}
		//Down colors, constant z
		for(int x = 2; x>=0; x--) {
			for(int y = 2; y>=0; y--) {
				down[Math.abs(y-2)][Math.abs(x-2)] = cubiePos[x][y][2].getColorOfDir('D');
			}
		}

		allSets[0] = left; allSets[1] = up; allSets[2] = front; allSets[3] = back;
		allSets[4] = right; allSets[5] = down;

		return allSets;
	}

	/**
	 * Changes a single color of a cubie to a new color in the given direction
	 * @param x, y, z: position
	 * @param dir: direction
	 * @param ncolor: new color
	 */
	public void setCubieColor(int x, int y, int z, char dir, char ncolor) {
		cubiePos[x][y][z].setColorOfDir(dir, ncolor);
	}

	/**
	 * Outputs the position, colors, and respective directions of colors of every cubie making up the cube.
	 * Used for debugging purposes prior to GUI development.
	 * Outputs in the format: x, y, z, color1, dir1, color2, dir2, color3, dir3 (number of colors and directions dependent on cubie type)
	 */
	public void testTurning() {
		for(int i = 0; i<cubiePos.length; i++) {
			for(int j = 0; j<cubiePos[0].length; j++) {
				for(int k = 0; k<cubiePos[0][0].length; k++) {
					CubieColor[] tempColor = cubiePos[i][j][k].getColors();
					System.out.print(i + ", " + j + ", " + k + ", ");
					for(int l = 0; l<tempColor.length; l++) {
						System.out.print(tempColor[l].getColor() + ", " + tempColor[l].getDir() + ", ");
					}
					System.out.println();
				}
			}
		}
	}

	/**
	 * Sets all the colors of the cube to the colors inputed by the user during color selection mode.
	 * Invoked from the CubePainter class when user decides to proceed to solution after inputing colors.
	 * The colors inputed as the colors[][][] parameter are in a slightly different state than the colors
	 * produced by the getColors() method. If the side is not the yellow or white side, then the user
	 * inputed the colors when yellow is above and white is below the desired face. If the face is the yellow
	 * face, the user inputed as if blue was above and green was below the yellow face, the blue and green
	 * being in the opposite orientation for when inputing colors on the white face.
	 * @param colors 
	 */
	public void setAllColors(char[][][] colors) {
		//Set Left colors
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				cubiePos[0][Math.abs(j-2)][i].setColorOfDir('L', colors[0][i][j]);
			}
		}
		//Set Up colors
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				cubiePos[j][Math.abs(i-2)][0].setColorOfDir('U', colors[1][i][j]);
			}
		}
		//Set Front colors
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				cubiePos[j][0][i].setColorOfDir('F', colors[2][i][j]);
			}
		}
		//Set Back colors
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				cubiePos[Math.abs(j-2)][2][i].setColorOfDir('B', colors[3][i][j]);
			}
		}
		//Set Right colors
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				cubiePos[2][j][i].setColorOfDir('R', colors[4][i][j]);
				colors[4][i][j] = cubiePos[2][j][i].getColorOfDir('R');
			}
		}
		//Set Down colors
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				cubiePos[j][i][2].setColorOfDir('D', colors[5][i][j]);
			}
		}
	}

	public void paintComponent(Graphics g) {
		//NOTE: the logic following may seem confusing because we need to store the colors as *they will be displayed*.
		//This means, for example, that the left side of the cube will be rotated 90 degrees clockwise such that
		//when displayed, it looks as if it is directly "connected" to the yellow (U) face.

		int xVal = 50;
		int yVal = 300;
		int size = CubePainter.CUBIE_SIZE;
		//Populate left colors, constant x
		for(int y = 2; y>=0; y--) {
			for(int z = 2; z>=0; z--) {
				g.setColor(getColor(cubiePos[0][y][z].getColorOfDir('L')));
				g.fillRect(xVal + Math.abs(z-2)*size, yVal+ Math.abs(y-2)*size, size, size);
				//left[Math.abs(y-2)][Math.abs(z-2)] = cubiePos[0][y][z].getColorOfDir('L');
			}
		}

		//Up colors, constant z
		xVal += size*3;
		for(int x = 0; x<=2; x++) {
			for(int y = 2; y>=0; y--) {
				g.setColor(getColor(cubiePos[x][y][0].getColorOfDir('U')));
				g.fillRect(xVal + x*size, yVal+ Math.abs(y-2)*size, size, size);
				//up[Math.abs(y-2)][x] = cubiePos[x][y][0].getColorOfDir('U');
			}
		}

		//Front colors, constant y
		yVal += size*3;
		for(int z = 0; z<=2; z++) {
			for(int x = 0; x<=2; x++) {
				g.setColor(getColor(cubiePos[x][0][z].getColorOfDir('F')));
				g.fillRect(xVal + x*size, yVal+ z*size, size, size);
				//front[z][x] = cubiePos[x][0][z].getColorOfDir('F');
			}
		}

		//Back colors, constant y
		yVal -= size*6;
		for(int x = 0; x<=2; x++) {
			for(int z = 2; z>=0; z--) {
				g.setColor(getColor(cubiePos[x][2][z].getColorOfDir('B')));
				g.fillRect(xVal + x*size, yVal+ Math.abs(z-2)*size, size, size);
				//back[Math.abs(z-2)][x] = cubiePos[x][2][z].getColorOfDir('B');
			}
		}

		//Right colors, constant x
		xVal += size*3;
		yVal += size*3;
		for(int y = 2; y>=0; y--) {
			for(int z = 0; z<=2; z++) {
				g.setColor(getColor(cubiePos[2][y][z].getColorOfDir('R')));
				g.fillRect(xVal + z*size, yVal+ Math.abs(y-2)*size, size, size);
				//right[Math.abs(y-2)][z] = cubiePos[2][y][z].getColorOfDir('R');
			}
		}

		//Down colors, constant z
		xVal += size*3;
		for(int x = 2; x>=0; x--) {
			for(int y = 2; y>=0; y--) {
				g.setColor(getColor(cubiePos[x][y][2].getColorOfDir('D')));
				g.fillRect(xVal + Math.abs(x-2)*size, yVal+ Math.abs(y-2)*size, size, size);
				//down[Math.abs(y-2)][Math.abs(x-2)] = cubiePos[x][y][2].getColorOfDir('D');
			}
		}

		((Graphics2D)g).setStroke(CubePainter.s);
		g.setColor(Color.BLACK);
		for(int k = 0; k<6; k++) {
			switch(k) {
			case(0): xVal = 50; yVal = 300; break;
			case(1): xVal += size*3; 	break;
			case(2): yVal += size*3; 	break;
			case(3): yVal -= size*6;	break;
			case(4): xVal += size*3;
			yVal += size*3; 	break;
			case(5): xVal += size*3; 	break;
			}
			for(int i = 0; i<3; i++){
				for(int j = 0; j<3; j++) {
					g.drawRect(xVal + j*size, yVal+ i*size, size, size);
				}
			}
		}

	}

	/**
	 * Returns the appropriate Color object based on a cubie's color for appropriate
	 * painting in the paintComponent() method.
	 * @param color: cubie color
	 * @return corresponding Color object
	 */
	private Color getColor(char color) {
		switch(color) {
		case 'W': return Color.WHITE;
		case 'Y': return Color.YELLOW;
		case 'B': return Color.BLUE;
		case 'G': return Color.GREEN;
		case 'R': return Color.RED;
		case 'O': return Color.ORANGE;	
		}
		return Color.BLACK;	
	}

}
