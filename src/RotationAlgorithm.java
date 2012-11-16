
public abstract class RotationAlgorithm implements Runnable{
	boolean keepRunning = true;		//Keeps it running.
	int d;							//The delay, d, as talked about in the assignment.
	public abstract void run();
	public void stop(){
		keepRunning  = false;
	}	
}
