public class RSRMAlgorithmPrime extends RSRMAGeneral{	
	public RSRMAlgorithmPrime(Sensor s) {
		super(s);
		d = sensor.getDelay();
		k = sensor.getSectors();
	}
}
