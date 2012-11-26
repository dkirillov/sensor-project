public class RSRMAGeneral extends RotationAlgorithm{
	private boolean selectBit;
	public RSRMAGeneral(Sensor s) {
		super(s);
		outer_loop = 0;
		inner_loop = 0;
	}

	public void update(){
		Debug.debug("Running RSRMA. d:" + d + " k: " + k + (d == k ? "" : " -=Prime=-")+" outer_loop: "+outer_loop+" inner_loop: "+inner_loop);
		if (outer_loop == 0){
			selectBit = (Math.random() < 0.5);
		}
		if (selectBit) {
			// Mech1
			mech1(k, d);
		} else {
			// Mech0
			mech0(k, d);
		}
		outer_loop--;
	}

	public void mech0(int k, int d){
		Debug.debug("Doing Mech0");
		if (outer_loop == 0){
			outer_loop = d*k; // It's not k-1 simply because the way the loop is arranged, if it was k-1  it would be actually k-2.
		}
		// Send message to neighbor(s) in sector i;
		// Listen for messages from neighbor(s) (if any) in sector i;
		// Basically set our neighbours to facing. THIS SHOULD DELAY THE THING.

		// Rotate antenna one sector.
		sensor.update();
	}
	public void mech1(int k, int d){
		Debug.debug("Doing Mech1");		
		if (outer_loop == 0) {
			outer_loop = k*(d-1);
		}
		
		//Send message to neighbor(s) in sector i;
		//Listen for messages from neighbor(s) (if any) in sector i;
		//Basically set our neighbours to facing. THIS SHOULD DELAY THE THING.
		if(inner_loop == 0){
			//Rotate antenna one sector.
			sensor.update();			
		}
		if (inner_loop == 0){inner_loop = d-1;}		
		inner_loop--;		
	}
}
