
public class EdgeCubie extends Cubie{

	private CubieColor[] colors;
	
	/**
	 * Constructs the EdgeCubie object
	 * @param xPos, yPos, zPos; Assign location of CenterPiece
	 * @param color1, dir1, color2, dir2; Assign colors and directions of colors of EdgeCubie
	 */
	public EdgeCubie(int xPos, int yPos, int zPos, char color1, char dir1, char color2, char dir2) {
		super(xPos, yPos, zPos);
		colors = new CubieColor[] {new CubieColor(color1, dir1), new CubieColor(color2, dir2)};
	}
	
	/**
	 * @return Whether EdgeCubie is a corner cubie (it is false by default)
	 */
	public boolean isCornerCubie() {
		return false;
	}
	
	/**
	 * @return Whether EdgeCubie is an edge cubie (it is true by default)
	 */
	public boolean isEdgeCubie() {
		return true;
	}
	
	/**
	 * Used to aid formation of the white cross
	 * @return For any EdgeCubie that is NOT in the E Slice, method returns the vertical slice that cubie belongs in
	 */
	public char verticalFace(int x, int y) {
		if(x == 0) return 'L';
		else if(x == 1) {
			if(y == 0) {
				return 'F';
			}
			else return 'B';
		}
		else return 'R';
	
	}
	
	/**
	 * @return Colors and directions of colors of the EdgeCubie
	 */
	public CubieColor[] getColors() {
		return colors;
	}
	
	/**
	 * @return Changes the colors associated with the EdgeCubie
	 */
	public void setColors(CubieColor[] newcolors) {
		colors = newcolors;
	}
	
}
