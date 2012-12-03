public class RSRMAlgorithmPrime extends RSRMAGeneral{
	/**
	 * Constructor.
	 * 
	 * Sets the RSRMAGeneral's d and k values to sensors delay and sectors respectively
	 * 
	 * @param s	The sensor to manipulate
	 */
	public RSRMAlgorithmPrime(Sensor s) {
		super(s);
		d = sensor.getDelay();
		k = sensor.getSectors();
	}
}
