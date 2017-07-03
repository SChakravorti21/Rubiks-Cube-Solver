import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import javax.swing.*;
import javax.swing.event.*;

public class CubeDisplayer extends JFrame{
	static CubePainter cubePainter;
	Timer frameTimer;
	
	public CubeDisplayer() {
		setLayout(new BorderLayout());
		setSize(700, 900);
		cubePainter = new CubePainter();
		add(cubePainter);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setVisible(true);
		setResizable(false);
		
		frameTimer = new javax.swing.Timer(100, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cubePainter.performNextMove();
				repaint();
			}
		});
		
		frameTimer.start();
	}

	public static void main(String[] args) {
		CubeDisplayer display = new CubeDisplayer();
	}
	
	public void paint(Graphics g) {
		cubePainter.paint((Graphics2D)g);
	}
}

class CubePainter extends JPanel {
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
	
	int i = 0;
	
	public CubePainter() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		setSize(getPreferredSize());
		setVisible(true);
		drawOutline = true;
		resetScramble("L R' U2 B2 L2 U2 B2 L D2 R' F2 D B F2 L' D U2 B R' U' L'");
		
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
		System.out.println(movesToPerform);
		
		cube = new Cube();
		cube.scramble(scramble);
	}
	
	public void performNextMove() {
		updatePhase();
	
		while(i<movesToPerform.length()-1 && movesToPerform.substring(i, i+1).compareTo(" ") == 0) {
			i++;
		}
		if(movesToPerform.substring(i, i+1) != " ") { 
			if(i!=movesToPerform.length()-1) {
				if(movesToPerform.substring(i+1, i+2).compareTo("2") == 0) {
					//Turning twice ex. U2
					cube.turn(movesToPerform.substring(i, i+1));
					cube.turn(movesToPerform.substring(i, i+1));
					i++;
				}
				else if(movesToPerform.substring(i+1,i+2).compareTo("'") == 0) {
					//Making a counterclockwise turn ex. U'
					cube.turn(movesToPerform.substring(i, i+2));
					i++;
				}
				else {
					//Clockwise turn
					cube.turn(movesToPerform.substring(i, i+1));
				}
			}
			else {
				//Clockwise turn
				cube.turn(movesToPerform.substring(i, i+1));
			}
		}
		i++;
	}
	
	public void updatePhase() {
		if(i >= movesToPerform.length()) {
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
			phase++; i = 0;
		}
	}
	
	
}
