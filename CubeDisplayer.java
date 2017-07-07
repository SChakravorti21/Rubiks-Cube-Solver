import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import javax.swing.*;
import javax.swing.event.*;

public class CubeDisplayer extends JFrame{
	//Auto-generated ID
	private static final long serialVersionUID = -3198702237161500498L;
	CubePainter cubePainter; //The JPanel that will handle painting and user input
	
	/**
	 * Creates a new CubeDisplayer and initializes it with a new CubePainter for the user
	 * to interact with.
	 */
	public CubeDisplayer() {
		setTitle("Cube Displayer");
		setLayout(new BorderLayout());
		setSize(700, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setVisible(true);
		setResizable(false);
		
		//Create a new CubePainter JPanel
		cubePainter = new CubePainter();
		add(cubePainter);
		
		//Set the JPanel as not being visible while cube is being scrambled
		//(While the cube is being scrambled, screen will show nonsensical colors, such as black)
		cubePainter.setVisible(false);
		cubePainter.resetScramble("F2 D' B U' D L2 B2 R B L' B2 L2 B2 D' R2 F2 D' R2 U' ");
		cubePainter.setVisible(true);
		
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
	
	public static void main(String[] args) {
		CubeDisplayer display = new CubeDisplayer();
	}
	
	
}

class CubePainter extends JPanel implements ActionListener, ChangeListener{
	//Auto-generated ID
	private static final long serialVersionUID = -8879300942801280752L;
	
	//Buttons to start and stop animation; to reset the scramble based on text field
	private JButton start, stop, applyScramble;
	//Slider to control animation speed
	private JSlider animSpeed;
	//Text field to allow user to input a custom scramble different from the default scramble
	private JTextField inputScramble;
	//Timer to control delay between animation of moves
	public Timer frameTimer;
	
	//Stroke for bold outline along edges of cubie colors
	private final static BasicStroke s = new BasicStroke(5.0f, BasicStroke.CAP_BUTT, 
			BasicStroke.JOIN_MITER, 10.0f);
	private final static Font font = new Font("Monospace", Font.BOLD, 35);
	//Standard frame rate delay
	public final static int DELAY = 1500;
	private boolean drawComponents = false;
	private final static int CUBIE_SIZE = 50;
	
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
		drawComponents = true;
		
		//Initialize all buttons, sliders and text fields
		start = new JButton("Start");
		start.setLocation(50, 10); start.setSize(100,20);
		add(start);
		start.addActionListener(this);
		start.setVisible(true);
		
		stop = new JButton("Stop");
		stop.setLocation(170, 10); stop.setSize(100,20);
		add(stop);
		stop.addActionListener(this);
		stop.setVisible(true);
		
		animSpeed = new JSlider(1, 10); animSpeed.setValue(1); //Slider values range from 1 to 10
		animSpeed.setMinorTickSpacing(1); animSpeed.setPaintTicks(true);
		animSpeed.setSnapToTicks(true);
		animSpeed.setLocation(500, 0); animSpeed.setSize(200, 40);
		add(animSpeed); 
		animSpeed.setVisible(true); 
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
		applyScramble.setVisible(true);
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
			setVisible(true);
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
		return new Dimension(700,900);
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
		
		//Draw the buttons, sliders, and text fields that only need to be painted once
		if(drawComponents) {
			start.paintComponents(g); stop.paintComponents(g); 
			animSpeed.paintComponents(g);
			inputScramble.paintComponents(g);
			drawComponents = false;
		}
		
		g.setFont(new Font("Monospace", Font.BOLD, 25));
		g.drawString("Scramble: ", 30, 70);
		
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