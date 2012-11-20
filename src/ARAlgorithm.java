import java.math.BigInteger;
import java.util.Random;


public class ARAlgorithm extends RotationAlgorithm {

	public ARAlgorithm(Sensor s) {
		super(s);
		//Better prime?
		d = BigInteger.probablePrime(3, new Random(System.currentTimeMillis())).intValue();
		outer_loop = 0;
	}

	public void update() {
		// Send message to neighbor(s);
		// Listen for messages from neighbor(s) (if any);
		
		if (outer_loop == 0){
			// Rotate antenna beam one sector counter-clockwise;
			sensor.update();
			outer_loop = d;
		}
		outer_loop--;
		Debug.debug("Running ARAlgorithm.");
	
	}
}
