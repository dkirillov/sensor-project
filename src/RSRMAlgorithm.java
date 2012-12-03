public class RSRMAlgorithm extends RSRMAGeneral{
	/**
	 * Constructor.
	 * 
	 * Sets the RSRMAGeneral's d and k values to sensors sectors
	 * 
	 * @param s	The sensor to manipulate
	 */
	public RSRMAlgorithm(Sensor s) {
		super(s);
		k = sensor.getSectors();
		d = sensor.getSectors();
	}
}
