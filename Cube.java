
public class Cube {

	//Stores the state of the cube as an object of 26 cubies
	private static Cubie[][][] cubiePos = new Cubie[3][3][3];

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
		//Since we are essentially rotating a matrix, we need to save the first cubie that will be replaced
		//(Take a look at the first case as an example).
		Cubie tempCubie;
		//We will retrieve the colors of each cubie, change the direction of certain colors as necessary,
		//and pass the new set of colors back to the cubie since turning a face changes the directions of many colors.
		CubieColor[] tempColors;

		switch(turn) {

		case "B":
			//Save BUL for later
			tempCubie = cubiePos[0][2][0];

			//Move BRU to BUL
			cubiePos[0][2][0] = cubiePos[2][2][0];
			tempColors = cubiePos[0][2][0].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('U');
				}
				else if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][2][0].setColors(tempColors);

			//Move BRD to BUR
			cubiePos[2][2][0] = cubiePos[2][2][2];
			tempColors = cubiePos[2][2][0].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('U');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][2][0].setColors(tempColors);

			//Move BLD to BDR
			cubiePos[2][2][2] = cubiePos[0][2][2];
			tempColors = cubiePos[2][2][2].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('D');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][2][2].setColors(tempColors);

			//Move BUL to BLD
			cubiePos[0][2][2] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('L');
				}
				else if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('D');
				}
			}
			cubiePos[0][2][2].setColors(tempColors);

			//Save BD for later
			tempCubie = cubiePos[1][2][2];

			//Move BL to BD
			cubiePos[1][2][2] = cubiePos[0][2][1];
			tempColors = cubiePos[1][2][2].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('D');
				}
			}
			cubiePos[1][2][2].setColors(tempColors);

			//Move BU to BL
			cubiePos[0][2][1] = cubiePos[1][2][0];
			tempColors = cubiePos[0][2][1].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][2][1].setColors(tempColors);

			//Move BR to BU
			cubiePos[1][2][0] = cubiePos[2][2][1];
			tempColors = cubiePos[1][2][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('U');
				}
			}
			cubiePos[1][2][0].setColors(tempColors);

			//Move BD to BR
			cubiePos[2][2][1] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][2][1].setColors(tempColors);
			break;


		case "B'":
			//Save ULB for later
			tempCubie = cubiePos[0][2][0];

			//Move BLD to BUL
			cubiePos[0][2][0] = cubiePos[0][2][2];
			tempColors = cubiePos[0][2][0].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('U');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][2][0].setColors(tempColors);

			//Move BRD to BDL
			cubiePos[0][2][2] = cubiePos[2][2][2];
			tempColors = cubiePos[0][2][2].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('D');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][2][2].setColors(tempColors);

			//Move BRU to BDR
			cubiePos[2][2][2] = cubiePos[2][2][0];
			tempColors = cubiePos[2][2][2].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('D');
				}
				else if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][2][2].setColors(tempColors);

			//Move BUL to BRU
			cubiePos[2][2][0] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('R');
				}
				else if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('U');
				}
			}
			cubiePos[2][2][0].setColors(tempColors);

			//Save BD for later
			tempCubie = cubiePos[1][2][2];

			//Move BR to BD
			cubiePos[1][2][2] = cubiePos[2][2][1];
			tempColors = cubiePos[1][2][2].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('D');
				}
			}
			cubiePos[1][2][2].setColors(tempColors);

			//Move BU to BR
			cubiePos[2][2][1] = cubiePos[1][2][0];
			tempColors = cubiePos[2][2][1].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][2][1].setColors(tempColors);

			//Move BL to BU
			cubiePos[1][2][0] = cubiePos[0][2][1];
			tempColors = cubiePos[1][2][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('U');
				}
			}
			cubiePos[1][2][0].setColors(tempColors);

			//Move BD to BL
			cubiePos[0][2][1] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][2][1].setColors(tempColors);
			break;


		case "D":
			tempCubie = cubiePos[2][0][2];

			//Move DLF to DFR
			cubiePos[2][0][2] = cubiePos[0][0][2];
			tempColors = cubiePos[2][0][2].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('F');
				}
				else if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][0][2].setColors(tempColors);

			//Move DLB to DFL
			cubiePos[0][0][2] = cubiePos[0][2][2];
			tempColors = cubiePos[0][0][2].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('F');
				}
				else if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][0][2].setColors(tempColors);

			//Move DBR to DLB
			cubiePos[0][2][2] = cubiePos[2][2][2];
			tempColors = cubiePos[0][2][2].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('L');
				}
				else if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('B');
				}
			}
			cubiePos[0][2][2].setColors(tempColors);

			//Move DFR to DRB
			cubiePos[2][2][2] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('B');
				}
				else if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][2][2].setColors(tempColors);

			tempCubie = cubiePos[1][0][2];

			//Move DL to DF
			cubiePos[1][0][2] = cubiePos[0][1][2];
			tempColors = cubiePos[1][0][2].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[1][0][2].setColors(tempColors);

			//Move DB to DL
			cubiePos[0][1][2] = cubiePos[1][2][2];
			tempColors = cubiePos[0][1][2].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][1][2].setColors(tempColors);

			//Move DR to DB
			cubiePos[1][2][2] = cubiePos[2][1][2];
			tempColors = cubiePos[1][2][2].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('B');
				}
			}
			cubiePos[1][2][2].setColors(tempColors);

			//Move DF to DR
			cubiePos[2][1][2] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][1][2].setColors(tempColors);
			break;



		case "D'":
			tempCubie = cubiePos[2][0][2];

			//Move DBR to DRF
			cubiePos[2][0][2] = cubiePos[2][2][2];
			tempColors = cubiePos[2][0][2].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('R');
				}
				else if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[2][0][2].setColors(tempColors);

			//Move DLB to DBR
			cubiePos[2][2][2] = cubiePos[0][2][2];
			tempColors = cubiePos[2][2][2].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('B');
				}
				else if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][2][2].setColors(tempColors);

			//Move DLF to DBL
			cubiePos[0][2][2] = cubiePos[0][0][2];
			tempColors = cubiePos[0][2][2].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('B');
				}
				else if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][2][2].setColors(tempColors);

			//Move DFR to DLF
			cubiePos[0][0][2] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('F');
				}
				else if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][0][2].setColors(tempColors);

			tempCubie = cubiePos[1][0][2];

			//Move DR to DF
			cubiePos[1][0][2] = cubiePos[2][1][2];
			tempColors = cubiePos[1][0][2].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[1][0][2].setColors(tempColors);

			//Move DB to DR
			cubiePos[2][1][2] = cubiePos[1][2][2];
			tempColors = cubiePos[2][1][2].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][1][2].setColors(tempColors);

			//Move DL to DB
			cubiePos[1][2][2] = cubiePos[0][1][2];
			tempColors = cubiePos[1][2][2].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('B');
				}
			}
			cubiePos[1][2][2].setColors(tempColors);

			//Move DF to DL
			cubiePos[0][1][2] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][1][2].setColors(tempColors);
			break;

		case "E":
			tempCubie = cubiePos[2][0][1];

			//Move LF to FR
			cubiePos[2][0][1] = cubiePos[0][0][1];
			tempColors = cubiePos[2][0][1].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('F');
				}
				else if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][0][1].setColors(tempColors);

			//Move LB to FL
			cubiePos[0][0][1] = cubiePos[0][2][1];
			tempColors = cubiePos[0][0][1].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('F');
				}
				else if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][0][1].setColors(tempColors);

			//Move BR to LB
			cubiePos[0][2][1] = cubiePos[2][2][1];
			tempColors = cubiePos[0][2][1].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('L');
				}
				else if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('B');
				}
			}
			cubiePos[0][2][1].setColors(tempColors);

			//Move FR to RB
			cubiePos[2][2][1] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('B');
				}
				else if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][2][1].setColors(tempColors);

			tempCubie = cubiePos[1][0][1];

			//Move L to F
			cubiePos[1][0][1] = cubiePos[0][1][1];
			tempColors = cubiePos[1][0][1].getColors();
			tempColors[0].setDir('F');
			cubiePos[1][0][1].setColors(tempColors);

			//Move B to L
			cubiePos[0][1][1] = cubiePos[1][2][1];
			tempColors = cubiePos[0][1][1].getColors();
			tempColors[0].setDir('L');
			cubiePos[0][1][1].setColors(tempColors);

			//Move R to B
			cubiePos[1][2][1] = cubiePos[2][1][1];
			tempColors = cubiePos[1][2][1].getColors();
			tempColors[0].setDir('B');
			cubiePos[1][2][1].setColors(tempColors);

			//Move F to R
			cubiePos[2][1][1] = tempCubie;
			tempColors = tempCubie.getColors();
			tempColors[0].setDir('R');
			cubiePos[2][1][1].setColors(tempColors);
			break;	

		case "E'" :
			tempCubie = cubiePos[2][0][1];

			//Move BR to RF
			cubiePos[2][0][1] = cubiePos[2][2][1];
			tempColors = cubiePos[2][0][1].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('R');
				}
				else if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[2][0][1].setColors(tempColors);

			//Move LB to BR
			cubiePos[2][2][1] = cubiePos[0][2][1];
			tempColors = cubiePos[2][2][1].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('B');
				}
				else if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][2][1].setColors(tempColors);

			//Move LF to BL
			cubiePos[0][2][1] = cubiePos[0][0][1];
			tempColors = cubiePos[0][2][1].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('B');
				}
				else if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][2][1].setColors(tempColors);

			//Move FR to LF
			cubiePos[0][0][1] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('F');
				}
				else if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][0][1].setColors(tempColors);

			tempCubie = cubiePos[1][0][1];

			//Move R to F
			cubiePos[1][0][1] = cubiePos[2][1][1];
			tempColors = cubiePos[1][0][1].getColors();
			tempColors[0].setDir('F');
			cubiePos[1][0][1].setColors(tempColors);

			//Move B to R
			cubiePos[2][1][1] = cubiePos[1][2][1];
			tempColors = cubiePos[2][1][1].getColors();
			tempColors[0].setDir('R');
			cubiePos[2][1][1].setColors(tempColors);

			//Move L to B
			cubiePos[1][2][1] = cubiePos[0][1][1];
			tempColors = cubiePos[1][2][1].getColors();
			tempColors[0].setDir('B');
			cubiePos[1][2][1].setColors(tempColors);

			//Move F to L
			cubiePos[0][1][1] = tempCubie;
			tempColors = tempCubie.getColors();
			tempColors[0].setDir('L');
			cubiePos[0][1][1].setColors(tempColors);
			break;

		case "F":
			tempCubie = cubiePos[0][0][0];

			//Move FLD to FUL
			cubiePos[0][0][0] = cubiePos[0][0][2];
			tempColors = cubiePos[0][0][0].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('U');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][0][0].setColors(tempColors);

			//Move FRD to FDL
			cubiePos[0][0][2] = cubiePos[2][0][2];
			tempColors = cubiePos[0][0][2].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('D');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][0][2].setColors(tempColors);

			//Move FRU to FDR
			cubiePos[2][0][2] = cubiePos[2][0][0];
			tempColors = cubiePos[2][0][2].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('D');
				}
				else if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][0][2].setColors(tempColors);

			//Move FUL to FRU
			cubiePos[2][0][0] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('R');
				}
				else if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('U');
				}
			}
			cubiePos[2][0][0].setColors(tempColors);

			tempCubie = cubiePos[1][0][2];

			//Move FR to FD
			cubiePos[1][0][2] = cubiePos[2][0][1];
			tempColors = cubiePos[1][0][2].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('D');
				}
			}
			cubiePos[1][0][2].setColors(tempColors);

			//Move FU to FR
			cubiePos[2][0][1] = cubiePos[1][0][0];
			tempColors = cubiePos[2][0][1].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][0][1].setColors(tempColors);

			//Move FL to FU
			cubiePos[1][0][0] = cubiePos[0][0][1];
			tempColors = cubiePos[1][0][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('U');
				}
			}
			cubiePos[1][0][0].setColors(tempColors);

			//Move FD to FL
			cubiePos[0][0][1] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][0][1].setColors(tempColors);
			break;

		case "F'":
			tempCubie = cubiePos[0][0][0];

			//Move FRU to FUL
			cubiePos[0][0][0] = cubiePos[2][0][0];
			tempColors = cubiePos[0][0][0].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('U');
				}
				else if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][0][0].setColors(tempColors);

			//Move FRD to FUR
			cubiePos[2][0][0] = cubiePos[2][0][2];
			tempColors = cubiePos[2][0][0].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('U');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][0][0].setColors(tempColors);

			//Move FLD to FDR
			cubiePos[2][0][2] = cubiePos[0][0][2];
			tempColors = cubiePos[2][0][2].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('D');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][0][2].setColors(tempColors);

			//Move FUL to FLD
			cubiePos[0][0][2] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('L');
				}
				else if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('D');
				}
			}
			cubiePos[0][0][2].setColors(tempColors);

			tempCubie = cubiePos[1][0][2];

			//Move FL to FD
			cubiePos[1][0][2] = cubiePos[0][0][1];
			tempColors = cubiePos[1][0][2].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('D');
				}
			}
			cubiePos[1][0][2].setColors(tempColors);

			//Move FU to FL
			cubiePos[0][0][1] = cubiePos[1][0][0];
			tempColors = cubiePos[0][0][1].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][0][1].setColors(tempColors);

			//Move FR to FU
			cubiePos[1][0][0] = cubiePos[2][0][1];
			tempColors = cubiePos[1][0][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('U');
				}
			}
			cubiePos[1][0][0].setColors(tempColors);

			//Move FD to FR
			cubiePos[2][0][1] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][0][1].setColors(tempColors);
			break;


		case "L":
			tempCubie = cubiePos[0][2][0];

			//Move LBD to LUB
			cubiePos[0][2][0] = cubiePos[0][2][2];
			tempColors = cubiePos[0][2][0].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('U');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('B');
				}
			}
			cubiePos[0][2][0].setColors(tempColors);

			//Move LFD to LDB
			cubiePos[0][2][2] = cubiePos[0][0][2];
			tempColors = cubiePos[0][2][2].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('D');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('B');
				}
			}
			cubiePos[0][2][2].setColors(tempColors);

			//Move LUF to LFD
			cubiePos[0][0][2] = cubiePos[0][0][0];
			tempColors = cubiePos[0][0][2].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('D');
				}
				else if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[0][0][2].setColors(tempColors);

			//Move LUB to LFU
			cubiePos[0][0][0] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('F');
				}
				else if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('U');
				}
			}
			cubiePos[0][0][0].setColors(tempColors);

			tempCubie = cubiePos[0][0][1];

			//Move LU to LF
			cubiePos[0][0][1] = cubiePos[0][1][0];
			tempColors = cubiePos[0][0][1].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[0][0][1].setColors(tempColors);

			//Move LB to LU
			cubiePos[0][1][0] = cubiePos[0][2][1];
			tempColors = cubiePos[0][1][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('U');
				}
			}
			cubiePos[0][1][0].setColors(tempColors);

			//Move LD to LB
			cubiePos[0][2][1] = cubiePos[0][1][2];
			tempColors = cubiePos[0][2][1].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('B');
				}
			}
			cubiePos[0][2][1].setColors(tempColors);

			//Move LF to LD
			cubiePos[0][1][2] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('D');
				}
			}
			cubiePos[0][1][2].setColors(tempColors);
			break;

		case "L'":
			tempCubie = cubiePos[0][2][0];

			//Move LUF to LBU
			cubiePos[0][2][0] = cubiePos[0][0][0];
			tempColors = cubiePos[0][2][0].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('B');
				}
				else if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('U');
				}
			}
			cubiePos[0][2][0].setColors(tempColors);

			//Move LFD to LUF
			cubiePos[0][0][0] = cubiePos[0][0][2];
			tempColors = cubiePos[0][0][0].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('U');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[0][0][0].setColors(tempColors);

			//Move LBD to LDF
			cubiePos[0][0][2] = cubiePos[0][2][2];
			tempColors = cubiePos[0][0][2].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('D');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[0][0][2].setColors(tempColors);

			//Move LUB to LBD
			cubiePos[0][2][2] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('B');
				}
				else if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('D');
				}
			}
			cubiePos[0][2][2].setColors(tempColors);

			tempCubie = cubiePos[0][2][1];

			//Move LU to LB
			cubiePos[0][2][1] = cubiePos[0][1][0];
			tempColors = cubiePos[0][2][1].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('B');
				}
			}
			cubiePos[0][2][1].setColors(tempColors);

			//Move LF to LU
			cubiePos[0][1][0] = cubiePos[0][0][1];
			tempColors = cubiePos[0][1][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('U');
				}
			}
			cubiePos[0][1][0].setColors(tempColors);

			//Move LD to LF
			cubiePos[0][0][1] = cubiePos[0][1][2];
			tempColors = cubiePos[0][0][1].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[0][0][1].setColors(tempColors);

			//Move LB to LD
			cubiePos[0][1][2] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('D');
				}
			}
			cubiePos[0][1][2].setColors(tempColors);
			break;

		case "M":
			tempCubie = cubiePos[1][2][0];

			//Move BD to UB
			cubiePos[1][2][0] = cubiePos[1][2][2];
			tempColors = cubiePos[1][2][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('U');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('B');
				}
			}
			cubiePos[1][2][0].setColors(tempColors);

			//Move FD to DB
			cubiePos[1][2][2] = cubiePos[1][0][2];
			tempColors = cubiePos[1][2][2].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('D');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('B');
				}
			}
			cubiePos[1][2][2].setColors(tempColors);

			//Move UF to FD
			cubiePos[1][0][2] = cubiePos[1][0][0];
			tempColors = cubiePos[1][0][2].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('D');
				}
				else if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[1][0][2].setColors(tempColors);

			//Move UB to FU
			cubiePos[1][0][0] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('F');
				}
				else if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('U');
				}
			}
			cubiePos[1][0][0].setColors(tempColors);

			tempCubie = cubiePos[1][0][1];

			//Move U to F
			cubiePos[1][0][1] = cubiePos[1][1][0];
			tempColors = cubiePos[1][0][1].getColors();
			tempColors[0].setDir('F');
			cubiePos[1][0][1].setColors(tempColors);

			//Move B to U
			cubiePos[1][1][0] = cubiePos[1][2][1];
			tempColors = cubiePos[1][1][0].getColors();
			tempColors[0].setDir('U');
			cubiePos[1][1][0].setColors(tempColors);

			//Move D to B
			cubiePos[1][2][1] = cubiePos[1][1][2];
			tempColors = cubiePos[1][2][1].getColors();
			tempColors[0].setDir('B');
			cubiePos[1][2][1].setColors(tempColors);

			//Move F to D
			cubiePos[1][1][2] = tempCubie;
			tempColors = tempCubie.getColors();
			tempColors[0].setDir('D');
			cubiePos[1][1][2].setColors(tempColors);
			break;

		case "M'":
			tempCubie = cubiePos[1][2][0];

			//Move UF to BU
			cubiePos[1][2][0] = cubiePos[1][0][0];
			tempColors = cubiePos[1][2][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('B');
				}
				else if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('U');
				}
			}
			cubiePos[1][2][0].setColors(tempColors);

			//Move FD to UF
			cubiePos[1][0][0] = cubiePos[1][0][2];
			tempColors = cubiePos[1][0][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('U');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[1][0][0].setColors(tempColors);

			//Move BD to DF
			cubiePos[1][0][2] = cubiePos[1][2][2];
			tempColors = cubiePos[1][0][2].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('D');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[1][0][2].setColors(tempColors);

			//Move UB to BD
			cubiePos[1][2][2] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('B');
				}
				else if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('D');
				}
			}
			cubiePos[1][2][2].setColors(tempColors);

			tempCubie = cubiePos[1][2][1];

			//Move U to B
			cubiePos[1][2][1] = cubiePos[1][1][0];
			tempColors = cubiePos[1][2][1].getColors();
			tempColors[0].setDir('B');
			cubiePos[1][2][1].setColors(tempColors);

			//Move F to U
			cubiePos[1][1][0] = cubiePos[1][0][1];
			tempColors = cubiePos[1][1][0].getColors();
			tempColors[0].setDir('U');
			cubiePos[1][1][0].setColors(tempColors);

			//Move D to F
			cubiePos[1][0][1] = cubiePos[1][1][2];
			tempColors = cubiePos[1][0][1].getColors();
			tempColors[0].setDir('F');
			cubiePos[1][0][1].setColors(tempColors);

			//Move B to D
			cubiePos[1][1][2] = tempCubie;
			tempColors = tempCubie.getColors();
			tempColors[0].setDir('D');
			cubiePos[1][1][2].setColors(tempColors);
			break;

		case "R":
			tempCubie = cubiePos[2][2][0];

			//Move RUF to RBU
			cubiePos[2][2][0] = cubiePos[2][0][0];
			tempColors = cubiePos[2][2][0].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('B');
				}
				else if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('U');
				}
			}
			cubiePos[2][2][0].setColors(tempColors);

			//Move RFD to RUF
			cubiePos[2][0][0] = cubiePos[2][0][2];
			tempColors = cubiePos[2][0][0].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('U');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[2][0][0].setColors(tempColors);

			//Move RBD to RDF
			cubiePos[2][0][2] = cubiePos[2][2][2];
			tempColors = cubiePos[2][0][2].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('D');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[2][0][2].setColors(tempColors);

			//Move RUB to RBD
			cubiePos[2][2][2] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('B');
				}
				else if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('D');
				}
			}
			cubiePos[2][2][2].setColors(tempColors);

			tempCubie = cubiePos[2][2][1];

			//Move RU to RB
			cubiePos[2][2][1] = cubiePos[2][1][0];
			tempColors = cubiePos[2][2][1].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('B');
				}
			}
			cubiePos[2][2][1].setColors(tempColors);

			//Move RF to RU
			cubiePos[2][1][0] = cubiePos[2][0][1];
			tempColors = cubiePos[2][1][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('U');
				}
			}
			cubiePos[2][1][0].setColors(tempColors);

			//Move RD to RF
			cubiePos[2][0][1] = cubiePos[2][1][2];
			tempColors = cubiePos[2][0][1].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[2][0][1].setColors(tempColors);

			//Move RB to RD
			cubiePos[2][1][2] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('D');
				}
			}
			cubiePos[2][1][2].setColors(tempColors);
			break;

		case "R'":
			tempCubie = cubiePos[2][2][0];

			//Move RBD to RUB
			cubiePos[2][2][0] = cubiePos[2][2][2];
			tempColors = cubiePos[2][2][0].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('U');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('B');
				}
			}
			cubiePos[2][2][0].setColors(tempColors);

			//Move RFD to RDB
			cubiePos[2][2][2] = cubiePos[2][0][2];
			tempColors = cubiePos[2][2][2].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('D');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('B');
				}
			}
			cubiePos[2][2][2].setColors(tempColors);

			//Move RUF to RFD
			cubiePos[2][0][2] = cubiePos[2][0][0];
			tempColors = cubiePos[2][0][2].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('D');
				}
				else if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[2][0][2].setColors(tempColors);

			//Move RUB to RFU
			cubiePos[2][0][0] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('F');
				}
				else if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('U');
				}
			}
			cubiePos[2][0][0].setColors(tempColors);

			tempCubie = cubiePos[2][0][1];

			//Move RU to RF
			cubiePos[2][0][1] = cubiePos[2][1][0];
			tempColors = cubiePos[2][0][1].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[2][0][1].setColors(tempColors);

			//Move RB to RU
			cubiePos[2][1][0] = cubiePos[2][2][1];
			tempColors = cubiePos[2][1][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('U');
				}
			}
			cubiePos[2][1][0].setColors(tempColors);

			//Move RD to RB
			cubiePos[2][2][1] = cubiePos[2][1][2];
			tempColors = cubiePos[2][2][1].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('B');
				}
			}
			cubiePos[2][2][1].setColors(tempColors);

			//Move RF to RD
			cubiePos[2][1][2] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('D');
				}
			}
			cubiePos[2][1][2].setColors(tempColors);
			break;

		case "S":
			tempCubie = cubiePos[0][1][0];

			//Move LD to UL
			cubiePos[0][1][0] = cubiePos[0][1][2];
			tempColors = cubiePos[0][1][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('U');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][1][0].setColors(tempColors);

			//Move RD to DL
			cubiePos[0][1][2] = cubiePos[2][1][2];
			tempColors = cubiePos[0][1][2].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('D');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][1][2].setColors(tempColors);

			//Move RU to DR
			cubiePos[2][1][2] = cubiePos[2][1][0];
			tempColors = cubiePos[2][1][2].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('D');
				}
				else if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][1][2].setColors(tempColors);

			//Move UL to RU
			cubiePos[2][1][0] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('R');
				}
				else if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('U');
				}
			}
			cubiePos[2][1][0].setColors(tempColors);

			tempCubie = cubiePos[1][1][2];

			//Move R to D
			cubiePos[1][1][2] = cubiePos[2][1][1];
			tempColors = cubiePos[1][1][2].getColors();
			tempColors[0].setDir('D');
			cubiePos[1][1][2].setColors(tempColors);

			//Move U to R
			cubiePos[2][1][1] = cubiePos[1][1][0];
			tempColors = cubiePos[2][1][1].getColors();
			tempColors[0].setDir('R');
			cubiePos[2][1][1].setColors(tempColors);

			//Move L to U
			cubiePos[1][1][0] = cubiePos[0][1][1];
			tempColors = cubiePos[1][1][0].getColors();
			tempColors[0].setDir('U');
			cubiePos[1][1][0].setColors(tempColors);

			//Move D to L
			cubiePos[0][1][1] = tempCubie;
			tempColors = tempCubie.getColors();
			tempColors[0].setDir('L');
			cubiePos[0][1][1].setColors(tempColors);
			break;

		case "S'":
			tempCubie = cubiePos[0][1][0];

			//Move RU to UL
			cubiePos[0][1][0] = cubiePos[2][1][0];
			tempColors = cubiePos[0][1][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('U');
				}
				else if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][1][0].setColors(tempColors);

			//Move RD to UR
			cubiePos[2][1][0] = cubiePos[2][1][2];
			tempColors = cubiePos[2][1][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('U');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][1][0].setColors(tempColors);

			//Move LD to DR
			cubiePos[2][1][2] = cubiePos[0][1][2];
			tempColors = cubiePos[2][1][2].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('D');
				}
				else if(tempColors[i].getDir() == 'D'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][1][2].setColors(tempColors);

			//Move UL to LD
			cubiePos[0][1][2] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'U'){
					tempColors[i].setDir('L');
				}
				else if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('D');
				}
			}
			cubiePos[0][1][2].setColors(tempColors);

			tempCubie = cubiePos[1][1][2];

			//Move L to D
			cubiePos[1][1][2] = cubiePos[0][1][1];
			tempColors = cubiePos[1][1][2].getColors();
			tempColors[0].setDir('D');
			cubiePos[1][1][2].setColors(tempColors);

			//Move U to L
			cubiePos[0][1][1] = cubiePos[1][1][0];
			tempColors = cubiePos[0][1][1].getColors();
			tempColors[0].setDir('L');
			cubiePos[0][1][1].setColors(tempColors);

			//Move R to U
			cubiePos[1][1][0] = cubiePos[2][1][1];
			tempColors = cubiePos[1][1][0].getColors();
			tempColors[0].setDir('U');
			cubiePos[1][1][0].setColors(tempColors);

			//Move D to R
			cubiePos[2][1][1] = tempCubie;
			tempColors = tempCubie.getColors();
			tempColors[0].setDir('R');
			cubiePos[2][1][1].setColors(tempColors);
			break;

		case "U":
			tempCubie = cubiePos[2][0][0];

			//Move UBR to URF
			cubiePos[2][0][0] = cubiePos[2][2][0];
			tempColors = cubiePos[2][0][0].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('R');
				}
				else if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[2][0][0].setColors(tempColors);

			//Move ULB to UBR
			cubiePos[2][2][0] = cubiePos[0][2][0];
			tempColors = cubiePos[2][2][0].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('B');
				}
				else if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][2][0].setColors(tempColors);

			//Move ULF to UBL
			cubiePos[0][2][0] = cubiePos[0][0][0];
			tempColors = cubiePos[0][2][0].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('B');
				}
				else if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][2][0].setColors(tempColors);

			//Move UFR to ULF
			cubiePos[0][0][0] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('F');
				}
				else if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][0][0].setColors(tempColors);

			tempCubie = cubiePos[1][0][0];

			//Move UR to UF
			cubiePos[1][0][0] = cubiePos[2][1][0];
			tempColors = cubiePos[1][0][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[1][0][0].setColors(tempColors);

			//Move UB to UR
			cubiePos[2][1][0] = cubiePos[1][2][0];
			tempColors = cubiePos[2][1][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][1][0].setColors(tempColors);

			//Move UL to UB
			cubiePos[1][2][0] = cubiePos[0][1][0];
			tempColors = cubiePos[1][2][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('B');
				}
			}
			cubiePos[1][2][0].setColors(tempColors);

			//Move UF to UL
			cubiePos[0][1][0] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][1][0].setColors(tempColors);
			break;


		case "U'":
			tempCubie = cubiePos[2][0][0];

			//Move ULF to UFR
			cubiePos[2][0][0] = cubiePos[0][0][0];
			tempColors = cubiePos[2][0][0].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('F');
				}
				else if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][0][0].setColors(tempColors);

			//Move ULB to UFL
			cubiePos[0][0][0] = cubiePos[0][2][0];
			tempColors = cubiePos[0][0][0].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('F');
				}
				else if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][0][0].setColors(tempColors);

			//Move UBR to ULB
			cubiePos[0][2][0] = cubiePos[2][2][0];
			tempColors = cubiePos[0][2][0].getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('L');
				}
				else if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('B');
				}
			}
			cubiePos[0][2][0].setColors(tempColors);

			//Move UFR to URB
			cubiePos[2][2][0] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('B');
				}
				else if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][2][0].setColors(tempColors);

			tempCubie = cubiePos[1][0][0];

			//Move UL to UF
			cubiePos[1][0][0] = cubiePos[0][1][0];
			tempColors = cubiePos[1][0][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'L'){
					tempColors[i].setDir('F');
				}
			}
			cubiePos[1][0][0].setColors(tempColors);

			//Move UB to UL
			cubiePos[0][1][0] = cubiePos[1][2][0];
			tempColors = cubiePos[0][1][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'B'){
					tempColors[i].setDir('L');
				}
			}
			cubiePos[0][1][0].setColors(tempColors);

			//Move UR to UB
			cubiePos[1][2][0] = cubiePos[2][1][0];
			tempColors = cubiePos[1][2][0].getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'R'){
					tempColors[i].setDir('B');
				}
			}
			cubiePos[1][2][0].setColors(tempColors);

			//Move UF to UR
			cubiePos[2][1][0] = tempCubie;
			tempColors = tempCubie.getColors();
			for(int i = 0; i<2; i++) {
				if(tempColors[i].getDir() == 'F'){
					tempColors[i].setDir('R');
				}
			}
			cubiePos[2][1][0].setColors(tempColors);
			break;		

		case "x":
			turn("R"); turn("M'"); turn("L'");
			break;

		case "x'":
			turn("R'"); turn("M"); turn("L");
			break;

		case "y":
			turn("U"); turn("E'"); turn("D'");
			break;

		case "y'":
			turn("U'"); turn("E"); turn("D");
			break;

		case "z":
			turn("F"); turn("S"); turn("B'");
			break;

		case "z'":
			turn("F'"); turn("S'"); turn("B");
			break;

		}


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
		return moves;
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

		return moves;
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
		return moves;
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
		return moves;
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
		return moves;
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
		return moves;
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
		
		return moves;
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

	


}
