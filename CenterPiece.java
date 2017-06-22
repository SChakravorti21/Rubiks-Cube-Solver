
public class CenterPiece extends Cubie{
	
	/**
	 * Constructs the CenterPiece object
	 * @param xPos, yPos, zPos; Assign location of CenterPiece
	 * @param color1, dir1; Assign color and direction of CenterPiece
	 */
	public CenterPiece(int xPos, int yPos, int zPos, char color1, char dir1) {
		super(xPos, yPos, zPos, new CubieColor[] {new CubieColor(color1, dir1)});
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
	
}
