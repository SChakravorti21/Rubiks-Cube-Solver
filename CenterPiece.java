
public class CenterPiece extends Cubie{

	private CubieColor[] colors = new CubieColor[1];
	
	/**
	 * Constructs the CenterPiece object
	 * @param xPos, yPos, zPos; Assign location of CenterPiece
	 * @param color1, dir1; Assign color and direction of CenterPiece
	 */
	public CenterPiece(int xPos, int yPos, int zPos, char color1, char dir1) {
		super(xPos, yPos, zPos);
		colors[0] = new CubieColor(color1, dir1);
	}
	
	/**
	 * @return Whether CenterPiece is a corner cubie (it is false by default)
	 */
	public boolean isCornerCubie() {
		return false;
	}
	/**
	 * @return Whether CenterPiece is an edge cubie (it is false by default)
	 */
	public boolean isEdgeCubie() {
		return false;
	}
	
	/**
	 * @return Color and direction of color of the CenterPiece
	 */
	public CubieColor[] getColors() {
		return colors;
	}
	
	/**
	 * @return Changes the color associated with the CenterPiece
	 */
	public void setColors(CubieColor[] newcolors) {
		colors = newcolors;
	}
}
