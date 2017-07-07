;
public class Cubie {

	//Store x, y, and z positions of a cubie
	private int x;
	private int y;
	private int z;
	private boolean corner;
	private boolean edge;
	//Store the set of colors associated with a cubie; accessible to all subclasses
	protected CubieColor[] colors;
	
	/**
	 * Constructs a Cubie object
	 * Sets the location of the cubie
	 * <param> xPos, yPos, zPos </param>
	 */
	public Cubie (int xPos, int yPos, int zPos, CubieColor[] ncolors, boolean isCorner,
			boolean isEdge) {
		x = xPos;
		y = yPos;
		z = zPos;
		corner = isCorner;
		edge = isEdge;
		colors = ncolors;
	}
	
	/**
	 * @return x location of cubie
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return y location of cubie
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * @return z location of cubie
	 */
	public int getZ() {
		return z;
	}
	
	/**
	 * Finds and returns the direction of a particular color on any type of cubie
	 * @param color </param> The color for which the direction is being found
	 * @return the direction of the color on the corresponding cubie ('A' if color is not on cubie)
	 */
	public char getDirOfColor(char color) {
		for(int i = 0; i<colors.length; i++) {
			if(colors[i].getColor() == color)
				return colors[i].getDir();
		}
		return 'A';
	}
	
	/**
	 * Finds and returns the color in a particular direction on any type of cubie
	 * @param dir </param> The direction for which the color is being found
	 * @return the direction of the color on the corresponding cubie ('A' if cubie does not have a color in direction dir)
	 */
	public char getColorOfDir(char dir) {
		for(int i = 0; i<colors.length; i++) {
			if(colors[i].getDir() == dir)
				return colors[i].getColor();
		}
		return 'A';
	}
	
	/**
	 * @return the colors and their respective directions of the Cubie
	 */
	public CubieColor[] getColors() {
		return colors;
	}
	
	/**
	 * @return Changes the colors associated with the Cubie
	 */
	public void setColors(CubieColor[] newcolors) {
		this.colors = newcolors;
	}
	
	/**
	 * Returns whether the cubie is a corner cubie
	 * @return whether corner cubie
	 */
	public boolean isCornerCubie() {
		return corner;
	}
	
	/**
	 * Returns whether the cubie is an edge cubie
	 * @return whether edge cubie
	 */
	public boolean isEdgeCubie() {
		return edge;
	}
	
	/**
	 * Used to aid formation of the white cross
	 * @return For any EdgeCubie that is NOT in the E Slice, method returns the vertical slice that cubie belongs in
	 */
	public char verticalFace(int x, int y) {
		if(edge) {
			if(x == 0) return 'L';
			else if(x == 1) {
				if(y == 0) {
					return 'F';
				}
				else return 'B';
			}
			else return 'R';
		}
		return 'A';
	
	}
	
	/**
	 * If the cubie is a corner cubie, method returns whether the cubie is a white corner
	 * Returns false if cubie is not a corner cubie
	 * @return whether corner cubie
	 */
	public boolean isWhiteCorner() {
		if(corner) {
			return (colors[0].getColor()=='W'|| colors[1].getColor()=='W' || colors[2].getColor()=='W');
		}
		return false;
	}
}
