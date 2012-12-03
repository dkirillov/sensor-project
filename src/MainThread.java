/**
 * @author	Danil Kirillov, Darryl Hill, Wesley Lawrence.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * The MainThread, responsible for the running of this application.
 */
public class MainThread {

	private boolean keepRunning = false;
	boolean restart = false;
	WindowClass wC;
	private Sensor[] sensors;
	protected Vector<Neighbour> neighbours;
	private List<RotationAlgorithm> runningAlgoThreads;
	int speed = 100;
	int numSensors = 3;
	int round = 0;
	int k = 4;
	int possibleConnections = 0;
	int currentConnections = 0;
	public int range = 50;
	private long startTime;
	private long endTime;
	private boolean firstTime;
	private boolean resetTime;
	boolean redraw = true;
	boolean gui = true;
	private int numberOfColours;
	private final List<Integer> PRIME_NUMBERS = Arrays.asList(2, 3, 5, 7, 11, 13, 17,
			19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89,
			97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157,
			163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229,
			233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307,
			311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383,
			389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461,
			463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557,
			563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631,
			641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719,
			727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811,
			821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887,
			907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991,
			997);
	int width = 600;
	int height = 440;
	LogFile log;

	Algo algorithm = Algo.ARA;

	/**
	 * The constructor for the MainThread, 
	 * takes in the WindowClass which will be used as a GUI
	 * for this thread.  
	 * @param wC	The GUI window for this thread.
	 */
	public MainThread(WindowClass wC) {
		this.wC = wC;
		restart = true;
		log = new LogFile();
	}

	/**
	 * Initializes everything, based on the number of sensors passed in.
	 * @param numSensors	The number of sensors the current configuration will have.
	 */
	public void initialize(int numSensors){
		round = 0;
		possibleConnections = 0;
		currentConnections = 0;
		this.numSensors = numSensors;
		sensors = new Sensor[numSensors];
		runningAlgoThreads = new ArrayList<RotationAlgorithm>(numSensors);
		neighbours = new Vector<Neighbour>();

		Random random = new Random();
		Vector<PlacementPoint> validPlacements = new Vector<PlacementPoint>();
		int xPlace = SensorBoard.BOARD_WIDTH/2;
		int yPlace = SensorBoard.BOARD_HEIGHT/2;
		sensors[0] = new Sensor(0, xPlace, yPlace, range, k);

		int radiusRange = (int) (range * 0.9);
		double startingRadian = (2 * Math.PI) * (random.nextInt(361) / 360);
		int numberOfPoints = random.nextInt(6) + 3;	// 3 to 8
		double radianIncrement = (2 * Math.PI) / numberOfPoints;

		double radianValue;
		int xValue;
		int yValue;
		for (int i = 0; i < numberOfPoints; i++) {
			radianValue = startingRadian + (radianIncrement * i);
			xValue = (int) (radiusRange * (Math.cos(radianValue)));
			yValue = (int) (radiusRange * (Math.sin(radianValue)));
			validPlacements.add(new PlacementPoint(xPlace + xValue, yPlace + yValue, radianValue));
		}
	
		for (int x = 1; x < sensors.length; x++) {
			PlacementPoint choice = validPlacements.remove(random.nextInt(validPlacements.size()));
			sensors[x] = new Sensor(x, choice.x, choice.y, range, k);
			//Get placements
			//find opposite point
			numberOfPoints = random.nextInt(4) + 2;	// 2 to 5
			radianIncrement = Math.PI / (numberOfPoints + 1); //For n points, there are n + 1 spaces

			for (int i = 1; i < numberOfPoints + 1; i++) {
				radianValue = (choice.radianFacing - (Math.PI / 2)) + (radianIncrement * i);
				xValue = (int) (radiusRange * (Math.cos(radianValue)));
				yValue = (int) (radiusRange * (Math.sin(radianValue)));
				validPlacements.add(new PlacementPoint(choice.x + xValue, choice.y + yValue, radianValue));
			}
		}
		
		initalizeWithOutGeneratingSensors(0);
		
		firstTime = true;
		resetTime = true;
	}

	/**
	 * Continues the initialization process, by recording the neighbours, 
	 * assigning sensors to each algorithm, and opens up log/stat file output. 
	 * @param testNumber	The current test number that this is being initialized for.
	 */
	public void initalizeWithOutGeneratingSensors(int testNumber) {
		log.close();
		
		for (Sensor curSensor : sensors) {
			curSensor.clearNeighbours();
		}
		
		neighbours.clear();
		runningAlgoThreads.clear();
		
		for (int i = 0; i < sensors.length; i++) {
			record_neighbours(i);
		}
		
		String algo = null;
		switch (algorithm){
		default:
		case ARA:
			algo = "ARA";
			break;
		case RSRMA:	
			algo = "RSRMA";
			break;
		case RSRMAP:
			algo = "RSRMA\'";
			break;	
		}
		
		for (int i = 0; i < sensors.length; i++) {			
			switch (algorithm){
			case ARA:
				runningAlgoThreads.add(new ARAlgorithm(sensors[i]));
				break;
			case RSRMA:
				runningAlgoThreads.add(new RSRMAlgorithm(sensors[i]));
				break;
			case RSRMAP:
				runningAlgoThreads.add(new RSRMAlgorithmPrime(sensors[i]));
				break;	
			}
		}
		log.open(testNumber, algo, numSensors, k);
		
		String output = "There " + (possibleConnections<=1?"is":"are") + " " + possibleConnections + " possible connection"
				+ (possibleConnections<=1?"":"s") + ".\n";
		if (wC != null) wC.output(output);
		log.logWrite(output);
		
		output = "There "+(numberOfColours<=1?"is":"are")+" " + numberOfColours+" colour"+(numberOfColours<=1?"":"s")+".\n";
		if (wC != null) wC.output(output);
		log.logWrite(output);
		
		if (wC != null && redraw) wC.sB.draw(wC.sB.getGraphics(), sensors, neighbours);
		
		firstTime = true;
		resetTime = true;
	}

	/**
	 * After a Sensor is placed, this method is called.
	 * This method records the neighbours for the Sensor
	 * that has just been placed. Goes through all the 
	 * previous sensors and checks if the Sensor that has
	 * just been placed is a neighbour for them.
	 * @param n	The index of the last placed Sensor.
	 */
	private void record_neighbours(int n){
		ArrayList<Integer> primeNumbers = new ArrayList<Integer>(PRIME_NUMBERS);
		sensors[n].changeSectorCount(k);
		int numC=1;
		for (int x = 0; x < n; x++) {
			if (sensors[x].inRange(sensors[n].getPoint())) {
				Neighbour newNeighbour = new Neighbour(sensors[x], sensors[n]);
				neighbours.add(newNeighbour);
				primeNumbers.remove(new Integer(sensors[x].getDelay()));
				numC++;
				sensors[x].addNeighbour(newNeighbour, sensors[n]);
				sensors[n].addNeighbour(newNeighbour, sensors[x]);
				possibleConnections++;
			}
		}
		numberOfColours = numberOfColours<numC?numC:numberOfColours;
		while (primeNumbers.get(0)<k){
			primeNumbers.remove(0);
		}
		sensors[n].setDelay(primeNumbers.get(0));
	}

	/**
	 * This is the update for the MainThread.
	 * Updates all the algorithms, and removes and that have been finished.
	 * Checks all the neighbours to see if new connections are made.
	 * Outputs to log/stat files.
	 * And re-draws the sensors on the GUI if needed.
	 * @param shouldDraw	Re-draws the sensors if true, otherwise doesn't.
	 */
	public void update(boolean shouldDraw) {
		if(firstTime){
			startTime = System.currentTimeMillis();
			if (wC != null) wC.output("Start time: "+startTime+"\n");
			firstTime = false;
		}
		round++; 
		int connections = 0;
		int size = runningAlgoThreads.size();
		for (int x = 0; x < size; x++) {
			runningAlgoThreads.get(x).update();
			if(!runningAlgoThreads.get(x).hasRemainingNeighbours()){
				runningAlgoThreads.remove(x);
				size--;
				x--;
			}
		}
		for (Neighbour n : neighbours) {
			if (n.isConnected()) connections++;
		}
		log.statWrite(""+(((double)connections)/possibleConnections)+"\n");
		if ((connections - currentConnections)>0){
			writeOutputs(""+(connections - currentConnections)+ " connections made in round "+round+"\n");
			currentConnections = connections;
			writeOutputs(""+(possibleConnections-currentConnections)+" connections left to be made\n");
		}
		if (resetTime&&possibleConnections-currentConnections == 0){
			endTime = System.currentTimeMillis();
			writeOutputs("End time: "+endTime+"\n");
			writeOutputs("Finished in " + round + " rounds\n");
			writeOutputs("Ran for: "+(endTime-startTime)+"ms\n");	
			resetTime = false;
			setKeepRunning(false);

			restart = true;
			redraw = false;
			log.close();
			if (wC != null) wC.play.setText("Start");

		}
		if(shouldDraw && gui && wC != null){
			wC.sB.draw(wC.sB.getGraphics(), sensors, neighbours);
		}
	}

	/**
	 * Writes a message to the output window.
	 * @param toWrite	The message to write.
	 */
	protected void writeOutputs(String toWrite) {
		if (wC != null) wC.output(toWrite);
		else System.out.print(toWrite);
		log.logWrite(toWrite);
	}
	
	/**
	 * Runs this class as a thread.
	 * Essentially, spawns a Java thread that will
	 * keep calling update or re-initializing.
	 */
	public void runAsThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
					while (isKeepRunning()) {
						long start = System.currentTimeMillis();
						update(true);
						long end = System.currentTimeMillis();
						if((end-start)<speed){
							Debug.debug("Sleeping: "+((speed)-(end-start)));
							delay((int)((speed)-(end-start)));
						}
					}
					if (restart){
						initialize(Integer.parseInt(wC.numSensors.getText()));
						restart = false;
					}
				}
			}

		}).start();
	}

	/**
	 * Stops the thread.
	 */
	public void stop() {
		setKeepRunning(false);
	}

	/**
	 * Delays/Sleeps the thread.
	 * @param miliseconds	The number of seconds to sleep.
	 */
	public void delay(int miliseconds){
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Is the thread running?
	 * @return Returns true if the thread is running, false if otherwise.
	 */
	public boolean isKeepRunning() {
		return keepRunning;
	}

	/**
	 * Sets the keepRunning variable that causes the thread to run or stop.
	 * @param keepRunning	The new value for this variable.
	 */
	protected void setKeepRunning(boolean keepRunning) {
		this.keepRunning = keepRunning;
	}
}
