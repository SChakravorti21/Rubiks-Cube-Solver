
public class Cube {

	//Stores the state of the cube as an object made up of several cubies
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
		cubiePos[0][0][0] = new CornerCubie(0,0,0,'Y','U','R','L','G','F');
		cubiePos[1][0][0] = new EdgeCubie(1,0,0,'Y','U','G','F');
		cubiePos[2][0][0] = new CornerCubie(2,0,0,'Y','U','G','F','O','R');

		//Front E Row
		cubiePos[0][0][1] = new EdgeCubie(0,0,1,'R','L','G','F');
		cubiePos[1][0][1] = new CenterPiece(1,0,1,'G','F');
		cubiePos[2][0][1] = new EdgeCubie(2,0,1,'G','F','O','R');

		//Down, Front Row
		cubiePos[0][0][2] = new CornerCubie(0,0,2,'R','L','G','F','W','D');
		cubiePos[1][0][2] = new EdgeCubie(1,0,2,'G','F','W','D');
		cubiePos[2][0][2] = new CornerCubie(2,0,2,'G','F','W','D','O','R');

		//Up S Row
		cubiePos[0][1][0] = new EdgeCubie(0,1,0,'R','L','Y','U');
		cubiePos[1][1][0] = new CenterPiece(1,1,0,'Y','U');
		cubiePos[2][1][0] = new EdgeCubie(2,1,0,'Y','U','O','R');

		//E S Row
		cubiePos[0][1][1] = new CenterPiece(0,1,1,'R','L');
		cubiePos[1][1][1] = new CenterPiece(1,1,1,'A','A'); //Just giving random, non-legitimate values for color and direction
		cubiePos[2][1][1] = new CenterPiece(2,1,1,'O','R');

		//Down S Row
		cubiePos[0][1][2] = new EdgeCubie(0,1,2,'R','L','W','D');
		cubiePos[1][1][2] = new CenterPiece(1,1,2,'W','D');
		cubiePos[2][1][2] = new EdgeCubie(2,1,2,'W','D','O','R');

		//Up Back Row
		cubiePos[0][2][0] = new CornerCubie(0,2,0,'Y','U','R','L','B','B');
		cubiePos[1][2][0] = new EdgeCubie(1,2,0,'Y','U','B','B');
		cubiePos[2][2][0] = new CornerCubie(2,2,0,'Y','U','B','B','O','R');

		//E Back Row
		cubiePos[0][2][1] = new EdgeCubie(0,2,1,'R','L','B','B');
		cubiePos[1][2][1] = new CenterPiece(1,2,1,'B','B');
		cubiePos[2][2][1] = new EdgeCubie(2,2,1,'B','B','O','R');

		//Down Back Row
		cubiePos[0][2][2] = new CornerCubie(0,2,2,'R','L','B','B','W','D');
		cubiePos[1][2][2] = new EdgeCubie(1,2,2,'B','B','W','D');
		cubiePos[2][2][2] = new CornerCubie(2,2,2,'B','B','W','D','O','R');

	}

	/**
	 * Takes a String value of a turn or rotation in standard Rubik's Cube notation and applies the turn or rotation to
	 * the cube. Valid turns currently include any turn in the following planes: U, D, F, B, L, R, M, E, S
	 * Valid rotations are x, y, and z rotations.
	 * @param turn
	 */
	public void turn (String turn) {
		Cubie tempCubie;
		CubieColor[] tempColors;

		switch(turn) {

		case "B":
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
			if(moves.substring(i, i+1) != " ") { 
				if(i!=moves.length()-1) {
					if(moves.substring(i+1, i+2).compareTo("2") == 0) {
						//Turning twice ex. U2
						turn(moves.substring(i, i+1));
						turn(moves.substring(i, i+1));
						i++;
					}
					else if(moves.substring(i+1,i+2).compareTo("'") == 0) {
						//Making a counterclockwise turn ex. U'
						turn(moves.substring(i, i+2));
						i++;
					}
					else {
						//Clockwise turning
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
	 * Scrambles a cube according to WCA rules (White on top, Green in front)
	 * After scrambling, returns the cube to the original position (Yellow on top, Green in front)
	 * @param scramble
	 * @return the scramble performed on the cube
	 */
	public String scramble(String scramble) {
		performMoves("z2 " + scramble + " z2");
		return scramble;
	}

	/**
	 * Once the sunflower is made, this method matches white edges to their respective faces and turns them down
	 * one at a time, creating the white cross.
	 * @return the moves used to create the white cross
	 */
	public String makeCross() {
		String moves = new String();
		int numOriented = numWhiteEdgesOriented();

		//Turn sunflower into cross until no white edges remain in the U layer
		while(numOriented!=0) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(cubiePos[i][j][0].isEdgeCubie()) {
						EdgeCubie tempCubie = (EdgeCubie)cubiePos[i][j][0];
						CubieColor[] tempColors = tempCubie.getColors();
						if(tempColors[0].getColor() == 'W' || tempColors[1].getColor() == 'W')
							for(int k = 0; k<2; k++) {
								//Check for when the white edge is matched up with the respective face
								if((tempColors[k].getColor() == 'R' && tempColors[k].getDir() == 'L') ||
										(tempColors[k].getColor() == 'G' && tempColors[k].getDir() == 'F') ||
										(tempColors[k].getColor() == 'O' && tempColors[k].getDir() == 'R')||
										(tempColors[k].getColor() == 'B' && tempColors[k].getDir() == 'B')) {
									moves+=performMoves(tempCubie.verticalFace(i, j) + "2 ") ;
									numOriented = numWhiteEdgesOriented();
								}
							}
					}
				}	
			}
			moves+=performMoves("U ");
		}
		return moves;
	}

	/**
	 * Makes the sunflower (yellow center in the  middle with 4 white edges surrounding it)
	 * The sunflower can then be used by makeCross() to make the white cross
	 * @return moves used to make sunflower
	 */
	public String makeSunflower() {
		String moves = new String();


		//Orients white edges in D Layer with white facing down
		//Works correctly
		if(numWhiteEdgesOriented() < 5) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(cubiePos[i][j][2].isEdgeCubie()) {
						CubieColor[] tempColors = cubiePos[i][j][2].getColors();
						for(int k = 0; k<2; k++) {
							if(tempColors[k].getColor() == 'W' && tempColors[k].getDir() == 'D') {
								EdgeCubie tempCubie = (EdgeCubie)cubiePos[i][j][2];
								moves += prepareSlot(i, j, 0, 'W');
								char turnToMake = tempCubie.verticalFace(i, j);
								moves += performMoves("" + turnToMake + "2 ");
							}
						}
					}
				}
			}
		}

		//Orients white edges in D Layer with white NOT facing down
		//Works correctly (or so it seems)
		if(numWhiteEdgesOriented() < 5) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(cubiePos[i][j][2].isEdgeCubie()) {
						CubieColor[] tempColors = cubiePos[i][j][2].getColors();
						for(int k = 0; k<2; k++) {
							if(tempColors[k].getColor() == 'W' && tempColors[k].getDir() != 'D') {
								EdgeCubie tempCubie = (EdgeCubie)cubiePos[i][j][2];
								char vert = tempCubie.verticalFace(i, j);

								moves += prepareSlot(i, j, 0, 'W');

								if(vert == 'F') {
									performMoves("F' U' R ");
									moves+= "F' U' R ";
								}
								else if(vert == 'R') {
									performMoves("R' U' B ");
									moves+= "R' U' B ";
								}
								else if(vert == 'B') {
									performMoves("B' U' L ");
									moves+= "B' U' L ";
								}
								else if(vert == 'L') {
									performMoves("L' U' F ");
									moves+= "L' U' F ";
								}
							}
						}
					}
				}

			}
		}

		//Check white pieces in E Layer
		//Seems to work correctly
		if(numWhiteEdgesOriented() < 5) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(cubiePos[i][j][1].isEdgeCubie()) {
						CubieColor[] tempColors = cubiePos[i][j][1].getColors();
						for(int k = 0; k<2; k++) {
							if(tempColors[k].getColor() == 'W') {
								if(i == 0 && j == 0) {
									if(tempColors[k].getDir() == 'L') {
										moves += prepareSlot(1, 0, 0, 'W') + performMoves("F");
									}
									else {
										moves += prepareSlot(0, 1, 0, 'W') + performMoves("L'");
									}
								}
								else if(i == 2 && j == 0) {
									if(tempColors[k].getDir() == 'F') {
										moves += prepareSlot(2, 1, 0, 'W') + performMoves("R");
									}
									else {
										moves += prepareSlot(1, 0, 0, 'W') + performMoves("F'");
									}
								}
								else if(i == 2 && j == 2) {
									if(tempColors[k].getDir() == 'B') {
										moves += prepareSlot(2, 1, 0, 'W') + performMoves("R'");
									}
									else {
										moves += prepareSlot(1, 2, 0, 'W') + performMoves("B");
									}
								}
								else {
									if(tempColors[k].getDir() == 'B') {
										moves += prepareSlot(0, 1, 0, 'W')+ performMoves("L");
									}
									else {
										moves += prepareSlot(1, 2, 0, 'W')+ performMoves("B'");
									}
								}
								moves+= " ";
							}
						}
					}
				}

			}
		}
		
		//else check incorrectly oriented in U Layer {
		if(numWhiteEdgesOriented() < 5) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(cubiePos[i][j][0].isEdgeCubie()) {
						CubieColor[] tempColors = cubiePos[i][j][0].getColors();
						for(int k = 0; k<2; k++) {
							if(tempColors[k].getColor() == 'W' && tempColors[k].getDir() != 'U') {
								EdgeCubie tempCubie = (EdgeCubie)cubiePos[i][j][0];
								char vert = tempCubie.verticalFace(i, j);

								if(vert == 'F') {
									performMoves("F U' R");
									moves+= "F U' R";
								}
								else if(vert == 'R') {
									performMoves("R U' B");
									moves+= "R U' B";
								}
								else if(vert == 'B') {
									performMoves("B U' L");
									moves+= "B U' L";
								}
								else if(vert == 'L') {
									performMoves("L U' F");
									moves+= "L U' F";
								}
								moves+= " ";
							}
						}
					}
				}

			}
		}

		//If fewer than 4 white edges reached the top layer by the end of this, some white edge was missed
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
					CubieColor[] tempColors = cubiePos[i][j][0].getColors();
					for(int k = 0; k<2; k++) {
						if(tempColors[k].getColor() == 'W' && tempColors[k].getDir() == 'U') {
							numOriented++;
						}
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
					CornerCubie tempCubie = (CornerCubie)cubiePos[i][j][0];
					if(tempCubie.isWhiteCorner()) return true;
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
		while(whiteCornerinU()) {
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					if(cubiePos[i][j][0].isCornerCubie()) {
						CornerCubie tempCubie = (CornerCubie)cubiePos[i][j][0];
						if(tempCubie.isWhiteCorner()) {
							//Make U turns until cubie is at (2, 0, 0)
							if(i==0) {
								if(j==0) {
									moves+=performMoves("U' "); i=0; j=0;
								}
								else {
									moves+=performMoves("U2 "); i=0; j=0;
								}
							}
							else {
								if(j==2) {
									moves+=performMoves("U "); i=0; j=0;
								}
							}
							
							//Get cubie above respective slot in first layer
							int numUTurns = 0;
							int yRotations = 0;
							while(!whiteCornerPrepared()) { 
								performMoves("U y'"); numUTurns++; yRotations++;
							}
							if(numUTurns == 1) {
								moves += "U ";
							}
							else if(numUTurns == 2){
								moves += "U2 ";
							}
							else if(numUTurns == 3) {
								moves += "U' ";
							}
							if(yRotations == 1) {
								moves+="y' ";
							}
							else if(yRotations == 2){
								moves += "y2 ";
							}
							else if(yRotations == 3) {
								moves += "y ";
							}

							//Insert the cubie
							int numSexyMoves = 0;
							while(!cornerInserted(2, 0, 2)){ 
								performMoves("R U R' U'"); numSexyMoves++;
							}
							if(numSexyMoves == 5) {
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
				CornerCubie tempCubie = (CornerCubie)cubiePos[2][0][2];
				if(tempCubie.isWhiteCorner()) {
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
		CornerCubie tempCubie;
		
		//Figure out whether the corner cubie is even a white corner
		if(cubiePos[2][0][0].isCornerCubie()) {
			tempCubie = (CornerCubie)cubiePos[2][0][0];
			if(!tempCubie.isWhiteCorner()) {
				return false;
			}
		}

		//If the cubie is a white corner, figure out whether the white sticker is facing up
		CubieColor[] tempColors = cubiePos[2][0][0].getColors();
		for(int i = 0; i<3; i++) {
			if(tempColors[i].getColor() == 'W' && tempColors[i].getDir() == 'U') {
				whiteUp = true; break;
			}
		}

		//based on whether white is up, check accordingly if the corner is above appropriate slot
		if(whiteUp) {
			CubieColor[] nonWhites = new CubieColor[2];
			int j = 0;
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getColor() != 'W') {
					nonWhites[j] = tempColors[i]; j++;
				}
			}
			for(int i = 0; i<2 && whiteUp; i++) {
				if(tempColors[i].getDir() == 'R' && tempColors[i].getColor() == cubiePos[1][0][1].getColors()[0].getColor() ||
				   tempColors[i].getDir() == 'F' && tempColors[i].getColor() == cubiePos[2][1][1].getColors()[0].getColor())
					return true;
			}
		}
		else {
			for(int i = 0; i<3; i++) {
				if(tempColors[i].getDir() == 'R' && tempColors[i].getColor() == cubiePos[2][1][1].getColors()[0].getColor() ||
				   tempColors[i].getDir() == 'F' && tempColors[i].getColor() == cubiePos[1][0][1].getColors()[0].getColor()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks whether the corner at (2, 0, 2) is solved.
	 * @param x, y, z
	 * @return true if corner is solved, else false
	 */
	public boolean cornerInserted(int x, int y, int z) {
		if(cubiePos[x][y][z].isCornerCubie()) {
			CubieColor[] tempColors = cubiePos[x][y][z].getColors();
			for(int i = 0; i<tempColors.length; i++) {
				//Check bottom, front, and right colors against respective centers
				if(tempColors[i].getDir() == 'D' && tempColors[i].getColor() != cubiePos[1][1][2].getColors()[0].getColor() ||
						tempColors[i].getDir() == 'F' && tempColors[i].getColor() != cubiePos[1][0][1].getColors()[0].getColor() ||
						tempColors[i].getDir() == 'R' && tempColors[i].getColor() != cubiePos[2][1][1].getColors()[0].getColor())
					return false;
			}
		}
		return true;
	}

	/**
	 * Outputs the position, colors, and respective directions of colors of every cubie making up the cube
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
	 * Test whether the white cross has been made successfully
	 * @return
	 */
	public boolean testCross() {
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				if(cubiePos[i][j][2].isEdgeCubie()) {
					//If at least one of the colors is not white and facing down, return false
					if( !(cubiePos[i][j][2].getColors()[0].getColor() == 'W' && cubiePos[i][j][2].getColors()[0].getDir() == 'D') &&
							!(cubiePos[i][j][2].getColors()[1].getColor() == 'W' && cubiePos[i][j][2].getColors()[1].getDir() == 'D') ) {
						return false;
					}
				}
			}
		}
		return true;
	}


}
