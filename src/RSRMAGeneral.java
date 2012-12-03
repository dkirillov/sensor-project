/**
 * @author Danil Kirillov, Darryl Hill, Wesley Lawrence.
 */
/**
 * A class that is the generalization of the random algorithms RSRMA and RSRMA'.
 * The only distinction between the two algorithms is that RSRMA passes in k's 
 * to the mech's, while RSRMA' passes in the appropriate k and d.
 */
public class RSRMAGeneral extends RotationAlgorithm{
	private boolean selectBit;
	/**
	 * The constructor, takes in the sensor that this
	 * algorithm is responsible for.
	 * @param s	The Sensor that it is responsible for.
	 */
	public RSRMAGeneral(Sensor s) {
		super(s);
		outer_loop = 1;
		inner_loop = 1;
	}

	/**
	 * Updates the algorithm, decides on which mech to use 
	 * and executes it.
	 */
	public void update(){
		outer_loop--;
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
	}

	/**
	 * Mechanism 1, for the random algorithms RSRMA and RSRMA'.
	 * Takes in the number of sectors and the delay which is a prime.
	 * The loops are done by setting the outer_loop variable to the 
	 * number of times that this mech will run. Because mech1 contained 
	 * nested for loops which would essentially run the mech d*(k-1) times;     
	 * @param k	The number of sectors that the sensor has.
	 * @param d	The delay, the sensor's prime.
	 */
	public void mech0(int k, int d){
		if (outer_loop == 0){
			outer_loop = d*(k-1); // It's not k-1 simply because the way the loop is arranged, if it was k-1  it would be actually k-2.
		}
		// Send message to neighbor(s) in sector i;
		// Listen for messages from neighbor(s) (if any) in sector i;
		// Basically set our neighbours to facing. THIS SHOULD DELAY THE THING.

		// Rotate antenna one sector.
		sensor.update();
	}
	/**
	 * Mechanism 2, for the random algorithms RSRMA and RSRMA'.
	 * Takes in the number of sectors and the delay which is a prime.
	 * The loops are done by using the outer_loop and inner_loop variables.
	 * The outer_loop represents the number of times this mech will run.
	 * The inner_loop represents the delay between the updates.
	 * @param k	The number of sectors that the sensor has.
	 * @param d	The delay, the sensor's prime.
	 */
	public void mech1(int k, int d){
		if (outer_loop == 0) {
			outer_loop = k*d;
		}
		
		//Send message to neighbor(s) in sector i;
		//Listen for messages from neighbor(s) (if any) in sector i;
		//Basically set our neighbours to facing. THIS SHOULD DELAY THE THING.
		inner_loop--;
		if(inner_loop == 0){
			//Rotate antenna one sector.
			sensor.update();
			inner_loop = d;
		}				
	}
	
}
