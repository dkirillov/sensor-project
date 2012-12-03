/**
 * @author	Danil Kirillov, Darryl Hill, Wesley Lawrence.
 */
public class ARAlgorithm extends RotationAlgorithm {

	/**
	 * The constructor. THe passed in sensor is the sensor that this algorithm is
	 * responsible for.
	 * @param s	The sensor that the algorithm is responsible for.
	 */
	public ARAlgorithm(Sensor s) {
		super(s);
		d = sensor.getDelay();
		outer_loop = d;
	}

	/**
	 * Updates the algorithm, potentially turning the sensor.
	 */
	public void update() {
		// Send message to neighbor(s);
		// Listen for messages from neighbor(s) (if any);
		
		outer_loop--;
		
		if (outer_loop == 0){
			// Rotate antenna beam one sector counter-clockwise;
			sensor.update();
			updated = true;
			outer_loop = d;
		}		
	}
}
