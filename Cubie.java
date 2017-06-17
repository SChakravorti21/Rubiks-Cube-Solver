;
public abstract class Cubie {

	//Store x, y, and z positions of a cubie
	private int x;
	private int y;
	private int z;
	
	/**
	 * Constructs a Cubie object
	 * Sets the location of the cubie
	 * <param> xPos, yPos, zPos </param>
	 */
	public Cubie (int xPos, int yPos, int zPos) {
		x = xPos;
		y = yPos;
		z = zPos;
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
	
	public abstract CubieColor[] getColors();
	public abstract void setColors(CubieColor[] newcolors);
	public abstract boolean isCornerCubie();
	public abstract boolean isEdgeCubie();
}
