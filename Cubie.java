;
public abstract class Cubie {

	//Store x, y, and z positions of a cubie
	private int x;
	private int y;
	private int z;
	//Store the set of colors associated with a cubie; accessible to all subclasses
	protected CubieColor[] colors;
	
	/**
	 * Constructs a Cubie object
	 * Sets the location of the cubie
	 * <param> xPos, yPos, zPos </param>
	 */
	public Cubie (int xPos, int yPos, int zPos, CubieColor[] ncolors) {
		x = xPos;
		y = yPos;
		z = zPos;
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
	
	public abstract boolean isCornerCubie();
	public abstract boolean isEdgeCubie();
}
