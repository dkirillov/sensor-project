
public abstract class SensorProjectMain {

	public static void main(String args[]) {
		
		if (args.length == 0) {
			new WindowClass();
		}
		
		else {
			if (args.length != 4) {
				System.err.println("Incorrect number of arguments");
				System.exit(1);
			}
			
			int sensorCount = -1;
			int testCount = -1;
			
			for (int i = 0; i < args.length; i+=2) {
				String key = args[i];
				if (key.equals("-s")) {
					sensorCount = Integer.parseInt(args[i+1]);
				}
				else if (key.equals("-t")) {
					testCount = Integer.parseInt(args[i+1]);
				}
				else {
					System.err.println("Error in arguments: don't recognize key: " + key);
					System.exit(1);
				}
			}
			
			if (sensorCount < 3) {
				System.err.println("Error in arguments: sensorCount less than 3");
				System.exit(1);
			}
			else if (testCount < 0) {
				System.err.println("Error in arguments: testCount less than 0");
				System.exit(1);
			}
			
			runTests(sensorCount, testCount);
		}
	}
	
	public static void runTests(int sensorCount, int testCount) {
		
		//Create mainthread
			//init for first time
		MainThread mT = new MainThread(null);
		mT.speed = 10;
		mT.round = 0;
		mT.k = 3;						//3 to 12
		mT.range = 50;
		mT.algorithm = Algo.ARA;
		
		for (int i = 1; i <= testCount; ++i) {
			
			System.out.println("-----------------");
			System.out.println("------Test " + i + "------");
			System.out.println("-----------------");
			
			mT.initialize(sensorCount);
			
			//run all current test
			for (Algo algo : Algo.values()) {
				//if (algo.equals(Algo.RSRMAP)) continue;
				//if (algo.equals(Algo.ARA)) continue;
				System.out.println("----Algo " + algo + "----");
				mT.algorithm = algo;
				for (int k = 3; k < 13; k++) {
					System.out.println("----K " + k + "----");
					mT.k = k;
					mT.round = 0;
					mT.possibleConnections = 0;
					mT.currentConnections = 0;
					mT.setKeepRunning(true);
					mT.initalizeWithOutGeneratingSensors(i);
					while (mT.isKeepRunning()) {
						mT.update(true);
					}
				}
			}
		}
	}
}
