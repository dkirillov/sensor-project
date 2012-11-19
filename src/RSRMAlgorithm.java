public class RSRMAlgorithm extends RSRMAGeneral{

	public RSRMAlgorithm(Sensor s) {
		super(s);
		k = sensor.getSectors();
		d = sensor.getSectors();
	}
}
