public class ARAlgorithm extends RotationAlgorithm {

	public ARAlgorithm(Sensor s) {
		super(s);
		d = sensor.getDelay();
		outer_loop = d;
	}

	public void update() {
		// Send message to neighbor(s);
		// Listen for messages from neighbor(s) (if any);
		
		outer_loop--;
		if (outer_loop == 0){
			// Rotate antenna beam one sector counter-clockwise;
			sensor.update();
			outer_loop = d;
		}
		
		//Debug.debug("Running ARAlgorithm.");
	
	}
}
