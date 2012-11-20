
public abstract class RotationAlgorithm{
	protected boolean keepRunning = true;		//Keeps it running.
	protected int d;							//The delay, d, as talked about in the assignment.
	protected int k;
	protected Sensor sensor;
	protected int inner_loop;
	protected int outer_loop;
	public RotationAlgorithm(Sensor s){
		sensor = s;
	}
	public abstract void update();
	public void stop(){
		keepRunning  = false;
	}	
}
