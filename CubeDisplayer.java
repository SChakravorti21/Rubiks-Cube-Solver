import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import javax.swing.*;
import javax.swing.event.*;

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
		setSize(700, 770);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setVisible(true);
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

		//Initialize the frame timer
		//NOTE: For whatever reason, initializing the frame timer within the CubePainter constructor does not seem to work
		cubePainter.frameTimer = new javax.swing.Timer(CubePainter.DELAY, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cubePainter.performNextMove();
				cubePainter.repaint(); //Only repaint the JPanel, not the JFrame (to preserve all interactive features)
			}
		});	
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

class CubePainter extends JPanel implements ActionListener, ChangeListener, MouseListener{
	//Auto-generated ID
	private static final long serialVersionUID = -8879300942801280752L;

	//Buttons to start and stop animation; to reset the scramble based on text field
	private JButton start, stop, applyScramble;
	private JButton[] colorInputs;
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
	private final Robot robot;

	/**
	 * Initializes all elements of the CubePainter JPanel with which the user can interact.
	 * This includes all buttons, sliders, and text fields.
	 */
	public CubePainter() {
		setLayout(null); //Allows for manually setting locations of components
		setSize(getPreferredSize());
		setVisible(true);
		inSolution = true;
		mode = TEXT_SCRAMBLE;
		colorSelected = 'R';
		try {
			robot = new Robot();
		}
		catch (AWTException e) {
			throw new RuntimeException(e);
		}

		//Initialize all buttons, sliders and text fields
		initializeComponents();
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
		
		sideChoser = new JComboBox<String>(new String[]{"Red", "Green", "Blue", "Yellow", "Orange", "White"} );
		sideChoser.setLocation(270, 100); sideChoser.setSize(100, 30);
		add(sideChoser);
		sideChoser.addActionListener(this);
		sideChoser.setVisible(false); sideChoser.setEnabled(false);
		
		colorInputs = new JButton[] {new JButton("Red"), new JButton("Green"), new JButton("Blue"),
				new JButton("Yellow"), new JButton("Orange"), new JButton("White")};
		for(int i = 0; i<6; i++) {
			colorInputs[i].setLocation(100 + CUBIE_SIZE*i + CUBIE_SIZE/2*i, 450);
			colorInputs[i].setSize(CUBIE_SIZE, CUBIE_SIZE);
			colorInputs[i].setEnabled(false); colorInputs[i].setVisible(false);
			colorInputs[i].addActionListener(this);
			add(colorInputs[i]);
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
		}
		else if(e.getSource() == sideChoser) {
			//TO-DO LATER
		}
		else {
			JButton colorInput = (JButton)e.getSource();
			colorSelected = colorInput.getText().charAt(0);
			repaint(400, 465 + CUBIE_SIZE*2, CUBIE_SIZE, CUBIE_SIZE);
			System.out.println(colorSelected);
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
		super.paintComponent(g);

		if(mode.equals(TEXT_SCRAMBLE)) {
			g.setFont(new Font("Monospace", Font.BOLD, 25));
			g.drawString("Scramble: ", 30, 70);
		}
		
		if(mode.equals(COLOR_SELECTION)) {
			if(!inSolution) {
				((Graphics2D)g).setStroke(s);
				int xVal = 100; int yVal = 450;
				g.setColor(Color.RED);
				g.fillRect(xVal, yVal, CUBIE_SIZE, CUBIE_SIZE);
				g.setColor(Color.BLACK);
				g.drawRect(xVal, yVal, CUBIE_SIZE, CUBIE_SIZE);
				
				xVal += CUBIE_SIZE*1.5;
				g.setColor(Color.GREEN);
				g.fillRect(xVal, yVal, CUBIE_SIZE, CUBIE_SIZE);
				g.setColor(Color.BLACK);
				g.drawRect(xVal, yVal, CUBIE_SIZE, CUBIE_SIZE);
				
				xVal += CUBIE_SIZE*1.5;
				g.setColor(Color.BLUE);
				g.fillRect(xVal, yVal, CUBIE_SIZE, CUBIE_SIZE);
				g.setColor(Color.BLACK);
				g.drawRect(xVal, yVal, CUBIE_SIZE, CUBIE_SIZE);
				
				xVal += CUBIE_SIZE*1.5;
				g.setColor(Color.YELLOW);
				g.fillRect(xVal, yVal, CUBIE_SIZE, CUBIE_SIZE);
				g.setColor(Color.BLACK);
				g.drawRect(xVal, yVal, CUBIE_SIZE, CUBIE_SIZE);
				
				xVal += CUBIE_SIZE*1.5;
				g.setColor(Color.ORANGE);
				g.fillRect(xVal, yVal, CUBIE_SIZE, CUBIE_SIZE);
				g.setColor(Color.BLACK);
				g.drawRect(xVal, yVal, CUBIE_SIZE, CUBIE_SIZE);
				
				xVal += CUBIE_SIZE*1.5;
				g.setColor(Color.WHITE);
				g.fillRect(xVal, yVal, CUBIE_SIZE, CUBIE_SIZE);
				g.setColor(Color.BLACK);
				g.drawRect(xVal, yVal, CUBIE_SIZE, CUBIE_SIZE);
				
				xVal = 250; yVal = 200;
				g.setColor(Color.BLACK);
				for(int i = xVal; i<xVal+CUBIE_SIZE*3; i+=CUBIE_SIZE){
					for(int j = yVal; j<yVal+CUBIE_SIZE*3; j+=CUBIE_SIZE) {
						g.drawRect(i, j, CUBIE_SIZE, CUBIE_SIZE);
					}
				}
				
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

			//Print the cube itself now
			((Graphics2D)g).setStroke(s);
			char[][][] allSets = cube.getColors();

			int xVal = 50;
			int yVal = 300;
			//Left side
			for(int i = 0; i<3; i++){
				for(int j = 0; j<3; j++) {
					g.setColor(getColor(allSets[0][i][j]));
					g.fillRect(xVal + j*CUBIE_SIZE, yVal+ i*CUBIE_SIZE, CUBIE_SIZE, CUBIE_SIZE);
				}
			}

			//Up
			xVal += CUBIE_SIZE*3;
			for(int i = 0; i<3; i++){
				for(int j = 0; j<3; j++) {
					g.setColor(getColor(allSets[1][i][j]));
					g.fillRect(xVal + j*CUBIE_SIZE, yVal+ i*CUBIE_SIZE, CUBIE_SIZE, CUBIE_SIZE);
				}
			}

			//Back
			yVal -= CUBIE_SIZE*3;
			for(int i = 0; i<3; i++){
				for(int j = 0; j<3; j++) {
					g.setColor(getColor(allSets[3][i][j]));
					g.fillRect(xVal + j*CUBIE_SIZE, yVal+ i*CUBIE_SIZE, CUBIE_SIZE, CUBIE_SIZE);
				}
			}

			//Front
			yVal += CUBIE_SIZE*6;
			for(int i = 0; i<3; i++){
				for(int j = 0; j<3; j++) {
					g.setColor(getColor(allSets[2][i][j]));
					g.fillRect(xVal + j*CUBIE_SIZE, yVal+ i*CUBIE_SIZE, CUBIE_SIZE, CUBIE_SIZE);
				}
			}

			//Right
			xVal += CUBIE_SIZE*3;
			yVal -= CUBIE_SIZE*3;
			for(int i = 0; i<3; i++){
				for(int j = 0; j<3; j++) {
					g.setColor(getColor(allSets[4][i][j]));
					g.fillRect(xVal + j*CUBIE_SIZE, yVal+ i*CUBIE_SIZE, CUBIE_SIZE, CUBIE_SIZE);
				}
			}

			//Down
			xVal += CUBIE_SIZE*3;
			for(int i = 0; i<3; i++){
				for(int j = 0; j<3; j++) {
					g.setColor(getColor(allSets[5][i][j]));
					g.fillRect(xVal + j*CUBIE_SIZE, yVal+ i*CUBIE_SIZE, CUBIE_SIZE, CUBIE_SIZE);
				}
			}


			//Now draw the outline for the pieces
			g.setColor(Color.BLACK);
			xVal = 50;
			yVal = 300;
			//Left side
			for(int i = xVal; i<xVal+CUBIE_SIZE*3; i+=CUBIE_SIZE){
				for(int j = yVal; j<yVal+CUBIE_SIZE*3; j+=CUBIE_SIZE) {
					g.drawRect(i, j, CUBIE_SIZE, CUBIE_SIZE);
				}
			}

			//Up
			xVal += CUBIE_SIZE*3;
			for(int i = xVal; i<xVal+CUBIE_SIZE*3; i+=CUBIE_SIZE){
				for(int j = yVal; j<yVal+CUBIE_SIZE*3; j+=CUBIE_SIZE) {
					g.drawRect(i, j, CUBIE_SIZE, CUBIE_SIZE);
				}
			}

			//Back
			yVal -= CUBIE_SIZE*3;
			for(int i = xVal; i<xVal+CUBIE_SIZE*3; i+=CUBIE_SIZE){
				for(int j = yVal; j<yVal+CUBIE_SIZE*3; j+=CUBIE_SIZE) {
					g.drawRect(i, j, CUBIE_SIZE, CUBIE_SIZE);
				}
			}

			//Front
			yVal += CUBIE_SIZE*6;
			for(int i = xVal; i<xVal+CUBIE_SIZE*3; i+=CUBIE_SIZE){
				for(int j = yVal; j<yVal+CUBIE_SIZE*3; j+=CUBIE_SIZE) {
					g.drawRect(i, j, CUBIE_SIZE, CUBIE_SIZE);
				}
			}

			//Left
			xVal += CUBIE_SIZE*3;
			yVal -= CUBIE_SIZE*3;
			for(int i = xVal; i<xVal+CUBIE_SIZE*3; i+=CUBIE_SIZE){
				for(int j = yVal; j<yVal+CUBIE_SIZE*3; j+=CUBIE_SIZE) {
					g.drawRect(i, j, CUBIE_SIZE, CUBIE_SIZE);
				}
			}

			//Down
			xVal += CUBIE_SIZE*3;
			for(int i = xVal; i<xVal+CUBIE_SIZE*3; i+=CUBIE_SIZE){
				for(int j = yVal; j<yVal+CUBIE_SIZE*3; j+=CUBIE_SIZE) {
					g.drawRect(i, j, CUBIE_SIZE, CUBIE_SIZE);
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
		//If the cube is being scrambled newly after intiliazing is complete and animation has begun,
		//be sure to reset all reference indexes
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
			for(int i = 0; i<6; i++) {
				colorInputs[i].setEnabled(false);
			}
			
		} else if(mode.equals(COLOR_SELECTION)) {
			if(inSolution) {
				start.setEnabled(true); 			start.setVisible(true);
				stop.setEnabled(true); 			stop.setVisible(true);
				animSpeed.setEnabled(true); 		animSpeed.setVisible(true);
				sideChoser.setVisible(false); sideChoser.setEnabled(false);
			} else if(!inSolution) {
				start.setEnabled(false); 		start.setVisible(false);
				stop.setEnabled(false); 			stop.setVisible(false);
				animSpeed.setEnabled(false); 	animSpeed.setVisible(false);
				sideChoser.setVisible(true); 	sideChoser.setEnabled(true);
				for(int i = 0; i<6; i++) {
					colorInputs[i].setVisible(true); colorInputs[i].setEnabled(true);
					colorInputs[i].setOpaque(false); 
					colorInputs[i].setContentAreaFilled(false);
					colorInputs[i].setBorderPainted(false);
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

	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(mode.equals(COLOR_SELECTION) && !inSolution) {
			if(e.getX() > 250) {
				Color color = robot.getPixelColor(e.getX(), e.getY());
				if(color.equals(Color.RED)) {
					colorSelected = 'R';
				} else if(color.equals(Color.ORANGE)) {
					colorSelected = 'O';
				} else if(color.equals(Color.GREEN)) {
					colorSelected = 'G';
				} else if(color.equals(Color.BLUE)) {
					colorSelected = 'B';
				} else if(color.equals(Color.WHITE)) {
					colorSelected = 'W';
				} else if(color.equals(Color.YELLOW)) {
					colorSelected = 'Y';
				}
				System.out.println("" + colorSelected);
			}
			
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
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