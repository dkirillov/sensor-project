/**
 * @author Danil Kirillov, Darryl Hill, Wesley Lawrence.
 */
/**
 * A class used in the determination of where the sensor is placed.
 */
public class PlacementPoint {

	public int x;
	public int y;
	public double radianFacing;

	/**
	 * The constructor, taking in the X and Y position of this point
	 * along with the radian of which angle it's facing.
	 * @param x				The X coordinate.
	 * @param y				The Y coordinate.
	 * @param radianFacing	The angle in that the point is facing.
	 */
	public PlacementPoint(int x, int y, double radianFacing) {
		this.x = x;
		this.y = y;
		this.radianFacing = radianFacing;
	}
	
}
