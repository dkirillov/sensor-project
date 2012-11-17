import java.util.Random;


public class RSRMAGeneral extends RotationAlgorithm{
	protected int d;
	protected int k;
	public RSRMAGeneral(Sensor s) {
		super(s);
	}

	public void run(){
		while(keepRunning){
			boolean selectBit = (new Random(System.currentTimeMillis())).nextBoolean();
			Debug.debug("Running RSRMA. d:"+d+" k: "+k+(d==k?"":" -=Prime=-"));
			int k = sensor.getSectors();
			if(selectBit){
				//Mech1
				mech1(k,d);
			}else{
				//Mech0
				mech0(k,d);
			}
		}
		Debug.debug("RSRMA d:"+d+" k: "+k+" ended.");
	}

	public void mech0(int k, int d){
		Debug.debug("Doing Mech0");	
		for(int j = 1; j <= d; j++){
			for(int i = 0; i <= k-1 ; i++){
				//Send message to neighbor(s) in sector i;
				//Listen for messages from neighbor(s) (if any) in sector i;
				//Basically set our neighbours to facing. THIS SHOULD DELAY THE THING.
				//OH GOD DOES THIS LOOK WRONG...
				sleep();
				//Rotate antenna one sector.
				sensor.update(true);
			}
		}
	}
	public void mech1(int k, int d){
		Debug.debug("Doing Mech1");		
		for(int j = 1; j <= k-1; j++){
			for(int i = 0; i <= d ; i++){
				//Send message to neighbor(s) in sector i;
				//Listen for messages from neighbor(s) (if any) in sector i;
				//Basically set our neighbours to facing. THIS SHOULD DELAY THE THING.
				//OH GOD DOES THIS LOOK WRONG...
				sleep();
			}
			//Rotate antenna one sector.
			sensor.update(true);
		}
	}
}
