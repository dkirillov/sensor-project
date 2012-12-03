
public abstract class SensorProjectMain {

	/**
	 * The entry into our program
	 * 
	 * If no arguments are passed, the GUI is started up. If arguments are passed, we initialize testing based on arguments
	 * -t for number of tests and -s for the number of sensors to test with
	 * 
	 * @param args Command line arguments
	 */
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
	
	/**
	 * Helper function to manage running multiple tests
	 * @param sensorCount	The number of sensors to test with
	 * @param testCount		The number of tests to run
	 */
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
				runTestsOnKValue(mT, 3, i);
				runTestsOnKValue(mT, 7, i);
				runTestsOnKValue(mT, 12, i);
			}
		}
	}
	/**
	 * Helper function for running tests given k values
	 * @param mT			The {@link MainThread} to run the test on
	 * @param sectorAmount	The number of sectors
	 * @param testNum		The current test number
	 */
	public static void runTestsOnKValue(MainThread mT, int sectorAmount, int testNum){
			System.out.println("----K " + sectorAmount + "----");
			mT.k = sectorAmount;
			mT.round = 0;
			mT.possibleConnections = 0;
			mT.currentConnections = 0;
			mT.setKeepRunning(true);
			mT.initalizeWithOutGeneratingSensors(testNum);
			while (mT.isKeepRunning()) {
				mT.update(true);
			}
	}
}
