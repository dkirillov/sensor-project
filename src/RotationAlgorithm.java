
public abstract class RotationAlgorithm implements Runnable{
	boolean keepRunning = true;		//Keeps it running.
	int d;							//The delay, d, as talked about in the assignment.
	Thread thread;
	Sensor sensor;
	public RotationAlgorithm(Sensor s){
		sensor = s;
	}
	public void setThread(Thread t){
		thread = t;
	}
	public abstract void run();
	public void stop(){
		keepRunning  = false;
	}	
	
	@SuppressWarnings({ "static-access" })
	protected void sleep(){
		try {
			//OH GOD DOES THIS LOOK WRONG...
			thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
