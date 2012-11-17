
public abstract class RotationAlgorithm implements Runnable{
	protected boolean keepRunning = true;		//Keeps it running.
	protected int d;							//The delay, d, as talked about in the assignment.
	protected Thread thread;
	protected Sensor sensor;
	private static int sleep_time;
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
	
	public static void setSleepTime(int x){
		sleep_time = x;
	}
	
	@SuppressWarnings({ "static-access" })
	protected void sleep(){
		try {
			//OH GOD DOES THIS LOOK WRONG...
			thread.sleep(sleep_time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
