import java.math.BigInteger;
import java.util.Random;


public class RSRMAlgorithmPrime extends RSRMAGeneral{	
	public RSRMAlgorithmPrime(Sensor s) {
		super(s);
		//Better prime?
		d = BigInteger.probablePrime(6, new Random(System.currentTimeMillis())).intValue();
		k = sensor.getSectors();
	}
}
