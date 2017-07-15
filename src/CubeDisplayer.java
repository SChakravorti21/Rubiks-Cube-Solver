
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Copyright 2017, Shoumyo Chakravorti, All rights reserved.
 * <p>
 * Licensed under the MIT License.
 * <p>
 * The CubeDisplayer class extends JFrame, allowing for the display of a CubePainter JPanel which
 * the user can interact with. The CubeDisplayer class allows for toggling between solution modes.
 * 
 * @author Shoumyo Chakravorti
 * @version 2.0
 */
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
		setResizable(false);
		setIgnoreRepaint(true);

		menuBar = new JMenuBar();
		modes = new JMenu("Mode Selection");
		colorSelection = new JMenuItem("Color Selection Mode");
		scramble = new JMenuItem("Text Scramble Mode");
		modes.add(colorSelection);
		modes.add(scramble);
		colorSelection.addActionListener(this);
		scramble.addActionListener(this);
		menuBar.add(modes);
		setJMenuBar(menuBar);

		//Create a new CubePainter JPanel
		cubePainter = new CubePainter();
		add(cubePainter);
		cubePainter.setVisible(true);
		cubePainter.setEnabled(true);
		
		menuBar.setVisible(true);
		setVisible(true);
		this.repaint();
		
		
	}

	/**
	 * Toggles between color selection mode and text scramble mode in the cubePainter instance.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == colorSelection) {
			cubePainter.setInSolution(false);
			cubePainter.updateMode(CubePainter.COLOR_SELECTION);
		}
		else if(e.getSource() == scramble) {
			cubePainter.setInSolution(true);
			cubePainter.updateMode(CubePainter.TEXT_SCRAMBLE);
		}
	}
}

