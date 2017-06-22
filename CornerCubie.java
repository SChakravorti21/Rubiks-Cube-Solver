
public class CornerCubie extends Cubie{

	/**
	 * Constructs the CornerCubie object
	 * @param xPos, yPos, zPos; Assign location of CenterPiece
	 * @param color1, dir1, color2, dir2, color3, dir3; Assign colors and directions of colors of CornerCubie
	 */
	public CornerCubie(int xPos, int yPos, int zPos, char color1, char dir1, char color2, char dir2, char color3, char dir3) {
		super(xPos, yPos, zPos, new CubieColor[] {new CubieColor(color1, dir1), new CubieColor(color2, dir2), new CubieColor(color3, dir3)});
	}
	
	/**
	 * @return Whether CornerCubie is a corner cubie (it is true by default)
	 */
	public boolean isCornerCubie() {
		return true;
	}
	
	public boolean isWhiteCorner() {
		return (colors[0].getColor()=='W'|| colors[1].getColor()=='W' || colors[2].getColor()=='W');
	}
	
	/**
	 * @return Whether CornerCubie is an edge cubie (it is false by default)
	 */
	public boolean isEdgeCubie() {
		return false;
	}
	
	
}
