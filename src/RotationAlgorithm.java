
public abstract class RotationAlgorithm{
	protected int d;							//The delay, d, as talked about in the assignment.
	protected int k;
	protected Sensor sensor;
	protected int inner_loop;
	protected int outer_loop;
	boolean updated;
	public RotationAlgorithm(Sensor s){
		sensor = s;
	}
	public abstract void update();	
	public boolean hasRemainingNeighbours(){
		return sensor.getRemainingNeighbours()!=0;
	}
}
