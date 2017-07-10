import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Arrays;

public class CubeDisplayer extends JFrame implements ActionListener{
	//Auto-generated ID
	private static final long serialVersionUID = -3198702237161500498L;
	CubePainter cubePainter; //The JPanel that will handle painting and user input
	JMenuBar menuBar;
	JMenu modes;
	JMenuItem colorSelection, scramble;
	//JMenuItem colorSelection, scramble;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {				
				new CubeDisplayer();
			}
		});	
	}


	/**
	 * Creates a new CubeDisplayer and initializes it with a new CubePainter for the user
	 * to interact with.
	 */
	public CubeDisplayer() {
		setTitle("Cube Displayer");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setResizable(false);

		menuBar = new JMenuBar();
		modes = new CubeMenu("Mode Selection", this);
		colorSelection = new JMenuItem("Color Selection Mode");
		scramble = new JMenuItem("Text Scramble Mode");
		modes.add(colorSelection);
		modes.add(scramble);
		menuBar.add(modes);
		setJMenuBar(menuBar);

		//Create a new CubePainter JPanel
		cubePainter = new CubePainter();
		add(cubePainter);
		cubePainter.updateElements();
		cubePainter.repaint();
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == colorSelection) {
			cubePainter.setInSolution(false);
			cubePainter.changeMode(CubePainter.COLOR_SELECTION);
			cubePainter.updateElements();
			cubePainter.repaint();
		}
		if(e.getSource() == scramble) {
			cubePainter.setInSolution(true);
			cubePainter.changeMode(CubePainter.TEXT_SCRAMBLE);
			cubePainter.updateElements();
			cubePainter.repaint();
		}
	}
}

class CubePainter extends JPanel implements ActionListener, ChangeListener {
	//Auto-generated ID
	private static final long serialVersionUID = -8879300942801280752L;

	//Buttons to start and stop animation; to reset the scramble based on text field
	private JButton start, stop, applyScramble;
	private JButton resetCubeInputs, setInputs;
	private JButton[] colorInputs;
	private CubeButton[][] cubeInputs;
	//Slider to control animation speed
	private JSlider animSpeed;
	//Text field to allow user to input a custom scramble different from the default scramble
	private JTextField inputScramble;
	private JComboBox<String> sideChoser;
	//Timer to control delay between animation of moves
	public Timer frameTimer;

	//Stroke for bold outline along edges of cubie colors
	private final static BasicStroke s = new BasicStroke(5.0f, BasicStroke.CAP_BUTT, 
			BasicStroke.JOIN_MITER, 10.0f);
	private final static Font font = new Font("Monospace", Font.BOLD, 35);
	//Standard frame rate delay
	public final static int DELAY = 1500;
	private final int CUBIE_SIZE = 50;

	//Dictates the mode in which the solution is occurring
	private String mode = new String();
	public final static String TEXT_SCRAMBLE = "Text Scramble";
	public final static String COLOR_SELECTION = "Color Selection";
	private char colorSelected;
	private char sideChosen;
	private char[][][] colorsInputted;
	//Whether a solution is currently being displayed
	private boolean inSolution;

	private Cube cube = new Cube();
	//Default scramble
	private String scramble = new String("F2 D' B U' D L2 B2 R B L' B2 L2 B2 D' R2 F2 D' R2 U' "),
			sunflower = new String(), whiteCross = new String(),
			whiteCorners = new String(), secondLayer = new String(), 
			yellowCross = new String(), OLL = new String(), PLL = new String();
	private String movesToPerform = new String(), movesPerformed = new String();

	/*
	 * Respective stages of the solution w.r.t the phase variable
	 * 0 = sunflower
	 * 1 = whiteCross
	 * 2 = whiteCorners
	 * 3 = secondLayer
	 * 4 = yellowCross
	 * 5 = OLL
	 * 6 = PLL
	 * The phase is updated in updatePhase() to reflect the stage at which the solution is
	 */
	private int phase = 0;
	//Helps keep track of moves to perform, and allows for painting of moves
	private int movesIndex = 0;

	/**
	 * Initializes all elements of the CubePainter JPanel with which the user can interact.
	 * This includes all buttons, sliders, and text fields.
	 */
	public CubePainter() {
		setLayout(null); //Allows for manually setting locations of components
		setSize(getPreferredSize());
		setVisible(true);
		inSolution = false;
		mode = TEXT_SCRAMBLE;
		colorSelected = 'R';
		sideChosen = 'L';
		colorsInputted = new char[6][3][3];
		resetCubeInputs();

		//Initialize all buttons, sliders and text fields
		initializeComponents();
		
		//Initialize the frame timer
		frameTimer = new javax.swing.Timer(CubePainter.DELAY, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(getInSolution())
					performNextMove();
			}
		});	
	}
	
	public void resetCubeInputs() {
		for(int i = 0; i<3; i++) {
			Arrays.fill(colorsInputted[0][i], 'R');
			Arrays.fill(colorsInputted[1][i], 'Y');
			Arrays.fill(colorsInputted[2][i], 'G');
			Arrays.fill(colorsInputted[3][i], 'B');
			Arrays.fill(colorsInputted[4][i], 'O');
			Arrays.fill(colorsInputted[5][i], 'W');
		}
	}

	public void initializeComponents() {
		start = new JButton("Start");
		start.setLocation(50, 10); start.setSize(60,20);
		add(start);
		start.addActionListener(this);

		stop = new JButton("Stop");
		stop.setLocation(130, 10); stop.setSize(60,20);
		add(stop);
		stop.addActionListener(this);

		animSpeed = new JSlider(1, 10); animSpeed.setValue(1); //Slider values range from 1 to 10
		animSpeed.setMinorTickSpacing(1); animSpeed.setPaintTicks(true);
		animSpeed.setSnapToTicks(true);
		animSpeed.setLocation(500, 0); animSpeed.setSize(200, 40);
		add(animSpeed); 
		animSpeed.addChangeListener(this);

		inputScramble = new JTextField(scramble); 
		inputScramble.setLocation(170, 40); inputScramble.setSize(400, 40); 
		inputScramble.setFocusable(true); 
		inputScramble.setBorder(BorderFactory.createLineBorder(Color.black)); 
		inputScramble.setFont(new Font("Monospace", Font.BOLD, 15));
		add(inputScramble);

		applyScramble = new JButton("RESET");
		applyScramble.setLocation(590, 50); applyScramble.setSize(100,20);
		add(applyScramble);
		applyScramble.addActionListener(this); 
		
		sideChoser = new JComboBox<String>(new String[]{"Left", "Up", "Back", "Front", "Right", "Down"} );
		sideChoser.setLocation(270, 50); sideChoser.setSize(100, 30);
		add(sideChoser);
		sideChoser.addActionListener(this);
		sideChoser.setVisible(false); sideChoser.setEnabled(false);
		
		resetCubeInputs = new JButton("RESET");
		resetCubeInputs.setLocation(200, 650); resetCubeInputs.setSize(100, 30);
		add(resetCubeInputs);
		resetCubeInputs.addActionListener(this);
		resetCubeInputs.setVisible(false); resetCubeInputs.setEnabled(false);
		
		setInputs = new JButton("PROCEED");
		setInputs.setLocation(300, 650); setInputs.setSize(100, 30);
		add(setInputs);
		setInputs.addActionListener(this);
		setInputs.setVisible(false); setInputs.setEnabled(false);
		
		colorInputs = new JButton[6];
		for(int i = 0; i<6; i++) {
			colorInputs[i] = new JButton();
			switch(i) {
				case(0): colorInputs[i].setName("Red");		break;
				case(1): colorInputs[i].setName("Green"); 	break;
				case(2): colorInputs[i].setName("Blue"); 	break;
				case(3): colorInputs[i].setName("Yellow"); 	break;
				case(4): colorInputs[i].setName("Orange"); 	break;
				case(5): colorInputs[i].setName("White"); 	break;
			}
			colorInputs[i].setLocation(100 + CUBIE_SIZE*i + CUBIE_SIZE/2*i, 450);
			colorInputs[i].setSize(CUBIE_SIZE, CUBIE_SIZE);
			colorInputs[i].setEnabled(false); colorInputs[i].setVisible(false);
			colorInputs[i].addActionListener(this);
			add(colorInputs[i]);
		}
		
		cubeInputs = new CubeButton[3][3];
		for(int i = 0; i<3; i++) {
			for(int j = 0 ; j<3; j++) {
				cubeInputs[i][j] = new CubeButton(i, j);
				cubeInputs[i][j].addActionListener(this);
				cubeInputs[i][j].setLocation(250 +j*CUBIE_SIZE, 200 + i*CUBIE_SIZE);
				cubeInputs[i][j].setSize(CUBIE_SIZE, CUBIE_SIZE);
				cubeInputs[i][j].setEnabled(false); cubeInputs[i][j].setVisible(false);
				add(cubeInputs[i][j]);
			}
		}
		
	}
	/**
	 * Takes actions performed on the buttons to cause changes in the animations or resetting the cube
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) 
			start();
		else if(e.getSource() == stop)
			stop();
		else if(e.getSource() == applyScramble) {
			stop();
			//While the cube is being scrambled, screen will show nonsensical colors, such as black, so set as invisible
			setVisible(false); 
			resetScramble(inputScramble.getText());
			inSolution = true;
			updateElements();
			setVisible(true);
		} else if(e.getSource() == sideChoser) {
			sideChosen = ((String)sideChoser.getSelectedItem()).charAt(0);
		} else if(e.getSource() == resetCubeInputs) {
			resetCubeInputs();
		} else if(e.getSource() == setInputs) {
			cube.setAllColors(colorsInputted);
			resetScrambleByColorInputs();
			inSolution = true;
			updateElements();
		} else {
			JButton colorInput = (JButton)e.getSource();
			if(colorInput.getY() > 440) {
				colorSelected = colorInput.getName().charAt(0);
			}
			else {
				CubeButton colorInp = (CubeButton)e.getSource();
				colorsInputted[getIndexOfSide(sideChosen)][colorInp.getI()][colorInp.getJ()] = colorSelected;
			}
		}
	}

	/**
	 * Takes a change of input from the slider to change the frame rate accordingly
	 */
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == animSpeed) {
			frameTimer.setDelay(DELAY/animSpeed.getValue());
		}
	}

	/**
	 * Starts the frame timer
	 */
	public void start() {
		frameTimer.start();
	}

	/**
	 * Stops the frame timer
	 */
	public void stop() {
		frameTimer.stop();
	}

	/**
	 * Returns the preferred dimensions of the CubePainter as a Dimension object.
	 * @return default dimensions of CubePainter
	 */
	public Dimension getPreferredSize(){
		return new Dimension(700,770);
	}

	/**
	 * Paints the JPanel. Upon initialization, paints the buttons, sliders, and text field which
	 * the user can interact with. When repaint() is called, the main changes that will be visible
	 * are changes to the cube, moves to be performed, and moves already performed. For painting the cube, this method
	 * invokes the getColors() method from Cube to retrieve all colors, and after painting those colors,
	 * paints an outline around the cubies.
	 */
	public void paintComponent(Graphics g) {
		super.repaint();

		if(mode.equals(TEXT_SCRAMBLE)) {
			g.setFont(new Font("Monospace", Font.BOLD, 25));
			g.drawString("Scramble: ", 30, 70);
		}
		
		if(mode.equals(COLOR_SELECTION)) {
			if(!inSolution) {
				//Paint the color selection boxes
				((Graphics2D)g).setStroke(s);
				int xVal = 100; int yVal = 450;
				for(int i = 0; i<6; i++) {
					switch(i) {
						case(0): g.setColor(Color.RED); 		break;
						case(1): g.setColor(Color.GREEN); 	break;
						case(2): g.setColor(Color.BLUE); 	break;
						case(3): g.setColor(Color.YELLOW); 	break;
						case(4): g.setColor(Color.ORANGE); 	break;
						case(5): g.setColor(Color.WHITE); 	break;
					}
					g.fillRect(xVal, yVal, CUBIE_SIZE, CUBIE_SIZE);
					g.setColor(Color.BLACK);
					g.drawRect(xVal, yVal, CUBIE_SIZE, CUBIE_SIZE);
					xVal += CUBIE_SIZE*1.5;
				}
				
				//Paint the chosen cube side
				xVal = 250; yVal = 200;
				char[][] sideColors = colorsInputted[getIndexOfSide(sideChosen)];
				for(int i = 0; i<3; i++){
					for(int j = 0; j<3; j++) {
						g.setColor(getColor(sideColors[i][j]));
						g.fillRect(xVal + j*CUBIE_SIZE, yVal+ i*CUBIE_SIZE, CUBIE_SIZE, CUBIE_SIZE);
						g.setColor(Color.BLACK);
						g.drawRect(xVal + j*CUBIE_SIZE, yVal+ i*CUBIE_SIZE, CUBIE_SIZE, CUBIE_SIZE);
					}
				}
				
				g.setColor(Color.BLACK);
				String[] instr = getInstructions();
				g.drawString("Hold the cube such that " + instr[0] + " is facing up, " +
						instr[1] + " is to the back, and " + instr[2] + " is in front.",
						50, 130);
				g.drawString("Enter the top colors.",
						50, 150);
				
				//Paint the color that is selected so user is sure to paint correct color
				g.setFont(font);
				g.drawString("Selected Color:", 100, 500 + CUBIE_SIZE*2);
				g.setColor(getColor(colorSelected));
				g.fillRect(400, 465 + CUBIE_SIZE*2, CUBIE_SIZE, CUBIE_SIZE);
				g.setColor(Color.BLACK);
				g.drawRect(400, 465 + CUBIE_SIZE*2, CUBIE_SIZE, CUBIE_SIZE);
			}
		}
		
		if(inSolution) {
			g.setFont(font);
			g.setColor(Color.RED);
			g.drawString(movesPerformed, 50, 700); //Draw the moves that have already been performed

			//Draw the moves that are yet to be performed
			g.setColor(Color.BLACK);
			if(movesIndex <= movesToPerform.length()-1) { //Avoid index out of bounds error
				if(movesToPerform.substring(movesIndex).length() >= 33) {
					g.drawString(movesToPerform.substring(movesIndex, movesIndex + 33), 40, 650);
				}
				else {
					g.drawString(movesToPerform.substring(movesIndex), 40, 650);
				}
			}

			//Draw the cube itself now
			((Graphics2D)g).setStroke(s);
			char[][][] allSets = cube.getColors();
			int xVal = 50;
			int yVal = 300;
			for(int k = 0; k<6; k++) {
				switch(k) {
					case(1): xVal += CUBIE_SIZE*3;; 	break;
					case(2): yVal += CUBIE_SIZE*3; 	break;
					case(3): yVal -= CUBIE_SIZE*6;	break;
					case(4): xVal += CUBIE_SIZE*3;
							 yVal += CUBIE_SIZE*3; 	break;
					case(5): xVal += CUBIE_SIZE*3; 	break;
				}
				for(int i = 0; i<3; i++){
					for(int j = 0; j<3; j++) {
						g.setColor(getColor(allSets[k][i][j]));
						g.fillRect(xVal + j*CUBIE_SIZE, yVal+ i*CUBIE_SIZE, CUBIE_SIZE, CUBIE_SIZE);
					}
				}
			}
			
			//Draw the outline
			for(int k = 0; k<6; k++) {
				switch(k) {
					case(0): xVal = 50; yVal = 300; break;
					case(1): xVal += CUBIE_SIZE*3;; 	break;
					case(2): yVal += CUBIE_SIZE*3; 	break;
					case(3): yVal -= CUBIE_SIZE*6;	break;
					case(4): xVal += CUBIE_SIZE*3;
							 yVal += CUBIE_SIZE*3; 	break;
					case(5): xVal += CUBIE_SIZE*3; 	break;
				}
				for(int i = 0; i<3; i++){
					for(int j = 0; j<3; j++) {
						g.setColor(Color.BLACK);
						g.drawRect(xVal + j*CUBIE_SIZE, yVal+ i*CUBIE_SIZE, CUBIE_SIZE, CUBIE_SIZE);
					}
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

	private int getIndexOfSide(char side) {
		switch(side) {
			case('L'): return 0;
			case('U'): return 1;
			case('F'): return 2;
			case('B'): return 3;
			case('R'): return 4;
			case('D'): return 5;
		}
		return 6;
	}
	
	private String[] getInstructions() {
		String[] colors = new String[3];
		switch(sideChosen) {
		case('L'): 	colors[0] = "Red";
				   	colors[1] = "Yellow";
				   	colors[2] = "White"; break;
		case('U'): 	colors[0] = "Yellow";
		   		   	colors[1] = "Blue";
		   		   	colors[2] = "Green"; break;
		case('F'): 	colors[0] = "Green";
		   		   	colors[1] = "Yellow";
		   		   	colors[2] = "White"; break;
		case('B'): 	colors[0] = "Blue";
		           	colors[1] = "Yellow";
		           	colors[2] = "White"; break;
		case('R'): 	colors[0] = "Orange";
		   		   	colors[1] = "Yellow";
		   		   	colors[2] = "White"; break;
		case('D'): 	colors[0] = "White";
		    		   	colors[1] = "Green";
		    		   	colors[2] = "Blue";
		}
		return colors;
	}
	
	/**
	 * Resets the scramble that is to be applied on the cube based on the input.
	 * Determines the moves to be performed to solve the cube as well.
	 * @param s: the scramble to be applied
	 */
	public void resetScramble(String s) {
		scramble = s;
		cube = new Cube();
		cube.scramble(scramble);
		sunflower = cube.makeSunflower();
		whiteCross = cube.makeWhiteCross();
		whiteCorners = cube.finishWhiteLayer();
		secondLayer = cube.insertAllEdges();
		yellowCross = cube.makeYellowCross();
		OLL = cube.orientLastLayer();
		PLL = cube.permuteLastLayer();

		movesToPerform = sunflower;
		movesPerformed = new String();

		cube = new Cube();
		cube.scramble(scramble);
		//If the cube is being scrambled newly after initializing is complete and animation has begun,
		//be sure to reset all reference indexes
		movesIndex = 0; phase = 0;
		repaint();
	}
	
	public void resetScrambleByColorInputs() {
		Cube cube2 = new Cube();
		cube2.setAllColors(colorsInputted);
		sunflower = cube2.makeSunflower();
		whiteCross = cube2.makeWhiteCross();
		whiteCorners = cube2.finishWhiteLayer();
		secondLayer = cube2.insertAllEdges();
		yellowCross = cube2.makeYellowCross();
		OLL = cube2.orientLastLayer();
		PLL = cube2.permuteLastLayer();

		movesToPerform = sunflower;
		movesPerformed = new String();
		
		movesIndex = 0; phase = 0;
		repaint();
	}
	
	/**
	 * for using color input
	 */
	public void resetFromColorInput() {
		
	}

	/**
	 * After updating the phase (if necessary), performs the next move in the String movesToPerform 
	 * and updates movesPerformed.
	 */
	public void performNextMove() {
		updatePhase();

		//Get to a character that is not a space
		while(movesIndex<movesToPerform.length()-1 && movesToPerform.substring(movesIndex, movesIndex+1).compareTo(" ") == 0) {
			movesIndex++;
		}
		//Same logic as in Cube class's performMoves() method
		if(movesToPerform.length()>0 && movesToPerform.substring(movesIndex, movesIndex+1) != " ") { 
			if(movesIndex!=movesToPerform.length()-1) {
				if(movesToPerform.substring(movesIndex+1, movesIndex+2).compareTo("2") == 0) {
					//Turning twice ex. U2
					cube.turn(movesToPerform.substring(movesIndex, movesIndex+1));
					cube.turn(movesToPerform.substring(movesIndex, movesIndex+1));
					movesIndex++;
				}
				else if(movesToPerform.substring(movesIndex+1,movesIndex+2).compareTo("'") == 0) {
					//Making a counterclockwise turn ex. U'
					cube.turn(movesToPerform.substring(movesIndex, movesIndex+2));
					movesIndex++;
				}
				else {
					//Clockwise turn
					cube.turn(movesToPerform.substring(movesIndex, movesIndex+1));
				}
			}
			else {
				//Clockwise turn
				cube.turn(movesToPerform.substring(movesIndex, movesIndex+1));
			}
		}
		movesIndex++;
		//Append the moves performed onto the end of movesPerformed
		if(movesToPerform.length()>0) {
			movesPerformed = movesToPerform.substring(0, movesIndex);
		}
		//Ensure that movesPerformed does not overflow out of the graphical interface
		if(movesPerformed.length() >= 35) {
			movesPerformed = movesPerformed.substring(movesPerformed.length()-33);
		}
	}

	
	public void updateElements() {
		if(mode.equals(TEXT_SCRAMBLE)) {
			if(inSolution) {
				start.setEnabled(true); 			start.setVisible(true);
				stop.setEnabled(true); 			stop.setVisible(true);
				animSpeed.setEnabled(true); 		animSpeed.setVisible(true);
			} else if(!inSolution) {
				start.setEnabled(false); 		start.setVisible(false);
				stop.setEnabled(false); 			stop.setVisible(false);
				animSpeed.setEnabled(false); 	animSpeed.setVisible(false);
			}
			applyScramble.setEnabled(true); 	applyScramble.setVisible(true);
			inputScramble.setEnabled(true); 	inputScramble.setVisible(true);
			sideChoser.setVisible(false); 	sideChoser.setEnabled(false);
			resetCubeInputs.setVisible(false);resetCubeInputs.setEnabled(false);
			setInputs.setVisible(false);		setInputs.setEnabled(false);
			for(int i = 0; i<6; i++) {
				colorInputs[i].setEnabled(false);
			}
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					cubeInputs[i][j].setEnabled(false);
				}
			}
			
		} else if(mode.equals(COLOR_SELECTION)) {
			if(inSolution) {
				start.setEnabled(true); 			start.setVisible(true);
				stop.setEnabled(true); 			stop.setVisible(true);
				animSpeed.setEnabled(true); 		animSpeed.setVisible(true);
				sideChoser.setVisible(false); 	sideChoser.setEnabled(false);
				resetCubeInputs.setVisible(false);resetCubeInputs.setEnabled(false);
				setInputs.setVisible(false);		setInputs.setEnabled(false);
			} else if(!inSolution) {
				start.setEnabled(false); 		start.setVisible(false);
				stop.setEnabled(false); 			stop.setVisible(false);
				animSpeed.setEnabled(false); 	animSpeed.setVisible(false);
				sideChoser.setVisible(true); 	sideChoser.setEnabled(true);
				resetCubeInputs.setVisible(true);resetCubeInputs.setEnabled(true);
				setInputs.setVisible(true);		setInputs.setEnabled(true);
				for(int i = 0; i<6; i++) {
					colorInputs[i].setVisible(true); 
					colorInputs[i].setEnabled(true);
					colorInputs[i].setOpaque(false); 
					colorInputs[i].setContentAreaFilled(false);
					colorInputs[i].setBorderPainted(false);
				}
				for(int i = 0; i<3; i++) {
					for(int j = 0; j<3; j++) {
						cubeInputs[i][j].setVisible(true); 
						cubeInputs[i][j].setEnabled(true);
						cubeInputs[i][j].setOpaque(false); 
						cubeInputs[i][j].setContentAreaFilled(false);
						cubeInputs[i][j].setBorderPainted(false);
					}
				}
			}
			applyScramble.setEnabled(false);	applyScramble.setVisible(false);
			inputScramble.setEnabled(false);	inputScramble.setVisible(false);
		}
	}

	public void changeMode(String str) {
		mode = str;
	}

	public void setInSolution(boolean inSoln) {
		inSolution = inSoln;
	}
	
	public boolean getInSolution() {
		return inSolution;
	}

	/**
	 * Updates the current phase of the solution as necessary
	 * Respective stages of the solution w.r.t the phase variable
	 * 0 = sunflower		 	1 = whiteCross		2 = whiteCorners		3 = secondLayer
	 * 4 = yellowCross		5 = OLL				6 = PLL
	 */
	public void updatePhase() {
		if(movesIndex >= movesToPerform.length()) {
			switch(phase) {
			case 0: 
				movesToPerform = whiteCross; break;
			case 1: 
				movesToPerform = whiteCorners; break;
			case 2:
				movesToPerform = secondLayer; break;
			case 3:
				movesToPerform = yellowCross; break;
			case 4:
				movesToPerform = OLL; break;
			case 5:
				movesToPerform = PLL; break;
			case 6:
				movesToPerform = " "; phase--;
			}
			phase++; movesIndex = 0;
		}
	}

	
}

class CubeMenu extends JMenu {
	//Auto-generated ID
	private static final long serialVersionUID = 7730471936826831950L;
	ActionListener actionListener;

	public CubeMenu(String name, ActionListener listener) {
		super(name);
		actionListener = listener;
	}

	public JMenuItem add(JMenuItem item) {
		item.addActionListener(actionListener);
		return super.add(item);
	}
}

class CubeButton extends JButton {
	//Auto-generated ID
	private static final long serialVersionUID = 88125897501752956L;
	
	int i;
	int j;
	
	public CubeButton(int newI, int newJ) {
		super();
		i = newI;
		j = newJ;
	}
	
	public int getI() {
		return i;
	}
	
	public int getJ() {
		return j;
	}
}