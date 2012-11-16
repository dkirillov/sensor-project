import java.util.Random;


public class RSRMAlgorithm extends RSRMAGeneral{

	public void run() {
		while(keepRunning){
			boolean selectBit = (new Random(System.currentTimeMillis())).nextBoolean();
			Debug.debug("Just checking in RSRMAlgorithm.");
			if(selectBit){
				//Mech1
				//Should be k,k
				mech1(0,0);
			}else{
				//Mech0
				mech0(0,0);
			}
			Debug.debug("");
		}
	}
}
