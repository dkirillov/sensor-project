import java.util.Random;


public class RSRMAlgorithm extends RSRMAGeneral{

	public RSRMAlgorithm(Sensor s) {
		super(s);
		k = sensor.getSectors();
		d = sensor.getSectors();
	}

	/*public void run() {
		while(keepRunning){
			boolean selectBit = (new Random(System.currentTimeMillis())).nextBoolean();
			Debug.debug("Running RSRMAlgorithm.");
			int k = sensor.getSectors();
			if(selectBit){
				//Mech1
				mech1(k,k);
			}else{
				//Mech0
				mech0(k,k);
			}
		}
		Debug.debug("RSRMAlgorithm ended.");
	}*/
}
