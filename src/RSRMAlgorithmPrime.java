import java.math.BigInteger;
import java.util.Random;


public class RSRMAlgorithmPrime extends RSRMAGeneral{
	private int d;
	
	public RSRMAlgorithmPrime(Sensor s) {
		super(s);
		//Better prime?
		d = BigInteger.probablePrime(6, new Random(System.currentTimeMillis())).intValue();
	}

	public void run() {
		while(keepRunning){
			boolean selectBit = (new Random(System.currentTimeMillis())).nextBoolean();
			Debug.debug("Running RSRMAlgorithmPrime. d:"+d);
			int k = sensor.getSectors();
			if(selectBit){
				//Mech1
				mech1(k,d);
			}else{
				//Mech0
				mech0(k,d);
			}
		}
	}
}
