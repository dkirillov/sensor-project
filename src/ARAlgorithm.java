import java.math.BigInteger;
import java.util.Random;


public class ARAlgorithm extends RotationAlgorithm {
	private int d;

	public ARAlgorithm(Sensor s) {
		super(s);
		//Better prime?
		d = BigInteger.probablePrime(3, new Random(System.currentTimeMillis())).intValue();
	}

	public void run() {
		while(keepRunning){
			Debug.debug("Running ARAlgorithm.");
			for (int i = 0; i<=d-1; i++){
				//Send message to neighbor(s);
				//Listen for messages from neighbor(s) (if any);
				sleep();
			}
			//Rotate antenna beam one sector counter-clockwise;
			sensor.update(true);
		}
	}
}
