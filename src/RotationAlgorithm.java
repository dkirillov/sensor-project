/**
 * @author Danil Kirillov, Darryl Hill, Wesley Lawrence.
 */
/**
 * An abstract class for the rotation algorithms.
 * It is abstracted so that the MainThread could use
 * it's concrete implementations without knowing which 
 * specific one it is.
 */
public abstract class RotationAlgorithm{
	protected int d;							//The delay, d, as talked about in the assignment.
	protected int k;
	protected Sensor sensor;
	protected int inner_loop;
	protected int outer_loop;
	boolean updated;
	/**
	 * The constructor, takes in the sensor that this
	 * algorithm is responsible for.
	 * @param s	The Sensor that it is responsible for.
	 */
	public RotationAlgorithm(Sensor s){
		sensor = s;
	}
	/**
	 * An update method that all the sub-classes will implement.
	 */
	public abstract void update();	
	/**
	 * Checks if there are any remaining neighbours that need to be connected.
	 * @return	Returns true if there's a neighbour(s) that is not connected.
	 */
	public boolean hasRemainingNeighbours(){
		return sensor.getRemainingNeighbours()!=0;
	}
}
