import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import javax.swing.*;
import javax.swing.event.*;

public class CubeDisplayer extends JFrame{
	CubePainter cubePainter;
	
	public CubeDisplayer() {
		setLayout(new BorderLayout());
		setSize(700, 900);
		cubePainter = new CubePainter();
		add(cubePainter);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setVisible(true);
		setResizable(false);
		
		cubePainter.setVisible(false);
		cubePainter.resetScramble("F2 D' B U' D L2 B2 R B L' B2 L2 B2 D' R2 F2 D' R2 U' ");
		
		cubePainter.frameTimer = new javax.swing.Timer(1000, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cubePainter.performNextMove();
				repaint(45, 140, 650, 465);
			}
		});
		
		cubePainter.setVisible(true);
		
		cubePainter.frameTimer.start();
	}
	
	public static void main(String[] args) {
		CubeDisplayer display = new CubeDisplayer();
	}
	
	public void paint(Graphics g) {
		cubePainter.paint((Graphics2D)g);
	}
}

class CubePainter extends JPanel implements ActionListener{
	public JButton start, stop;
	public Timer frameTimer;
	
	final static BasicStroke s = new BasicStroke(5.0f, BasicStroke.CAP_BUTT, 
			BasicStroke.JOIN_MITER, 10.0f);
	boolean drawOutline = false;
	final static int CUBIE_SIZE = 50;
	
	public Cube cube = new Cube();
	String scramble = new String(), sunflower = new String(), whiteCross = new String(),
	whiteCorners = new String(), secondLayer = new String(), yellowCross = new String(),
	OLL = new String(), PLL = new String();
	String movesToPerform = new String(sunflower);
	
	/*
	 * Respective stages w.r.t phase variable
	 * 0 = sunflower
	 * 1 = whiteCross
	 * 2 = whiteCorners
	 * 3 = secondLayer
	 * 4 = yellowCross
	 * 5 = OLL
	 * 6 = PLL
	 */
	int phase = 0;
	int movesIndex = 0;
	
	public CubePainter() {
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setSize(getPreferredSize());
		setVisible(true);
		drawOutline = true;
		
		start = new JButton("Start");
		start.setLocation(250, 10); start.setSize(100,20);
		add(start);
		start.addActionListener(this);
		start.setVisible(true);
		
		stop = new JButton("Stop");
		stop.setLocation(370, 10); stop.setSize(100,20);
		add(stop);
		stop.addActionListener(this);
		stop.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start && !frameTimer.isRunning()) 
			frameTimer.start();
		else if(e.getSource() == stop)
			frameTimer.stop();
	}
	
	public void start() {
		frameTimer.start();
	}
	public void stop() {
		frameTimer.stop();
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(700,900);
	}
	
	public void paint(Graphics2D g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.setStroke(s);
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
		
		if(drawOutline) {
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
			
			//drawOutline = false;
		}
		
	}

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
	
	public void resetScramble(String s) {
		scramble = s;
		cube.scramble(scramble);
		sunflower = cube.makeSunflower();
		whiteCross = cube.makeWhiteCross();
		whiteCorners = cube.finishWhiteLayer();
		secondLayer = cube.insertAllEdges();
		yellowCross = cube.makeYellowCross();
		OLL = cube.orientLastLayer();
		PLL = cube.permuteLastLayer();
		
		movesToPerform = sunflower;

		cube = new Cube();
		cube.scramble(scramble);
	}
	
	public void performNextMove() {
		updatePhase();
	
		while(movesIndex<movesToPerform.length()-1 && movesToPerform.substring(movesIndex, movesIndex+1).compareTo(" ") == 0) {
			movesIndex++;
		}
		if(movesToPerform.substring(movesIndex, movesIndex+1) != " ") { 
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
	}
	
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