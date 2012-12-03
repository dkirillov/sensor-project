import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class MainThread {

	private boolean keepRunning = false;
	boolean restart = false;
	WindowClass wC;
	private Sensor[] sensors;
	protected Vector<Neighbour> neighbours;
	//private RotationAlgorithm[] algoThreads;
	private List<RotationAlgorithm> runningAlgoThreads;
	private List<RotationAlgorithm> stoppedAlgoThreads;		//Do we even need this?
	int speed = 2000;
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

	public MainThread(WindowClass wC) {
		this.wC = wC;
		restart = true;
		//Debug.setDebug(true);
		log = new LogFile();
	}

	public void initialize(int numSensors){
		round = 0;
		possibleConnections = 0;
		currentConnections = 0;
		this.numSensors = numSensors; //= Integer.parseInt(wC.numSensors.getText());
		sensors = new Sensor[numSensors];
		runningAlgoThreads = new ArrayList<RotationAlgorithm>(numSensors);
		stoppedAlgoThreads = new ArrayList<RotationAlgorithm>(numSensors);
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
	
		//Debug.debug("Created sensor " + 0);

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
			//Debug.debug("Created sensor " + x);
		}
		
		initalizeWithOutGeneratingSensors(0);
		
		firstTime = true;
		resetTime = true;
	}

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
			
			//Debug.debug("Created algorithm " + algo + " for sensor " + i);
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

	private void record_neighbours(int n){
		ArrayList<Integer> primeNumbers = new ArrayList<Integer>(PRIME_NUMBERS);
		sensors[n].changeSectorCount(k);
		int numC=1;
		for (int x = 0; x < n; x++) {
			if (sensors[x].inRange(sensors[n].getPoint())) {
				Neighbour newNeighbour = new Neighbour(sensors[x], sensors[n]);
				neighbours.add(newNeighbour);
				//Debug.debug("Delay: "+sensors[x].getDelay());
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
		//Debug.debug("Record Neighbours for sensor " + n + " |> Delay: "+sensors[n].getDelay());
	}

	public void update(boolean shouldDraw) {
		if(firstTime){
			startTime = System.currentTimeMillis();
			if (wC != null) wC.output("Start time: "+startTime+"\n");
			firstTime = false;
		}
		round ++; //This is now a more accurate representation.
		int connections = 0;
		int size = runningAlgoThreads.size();
		for (int x = 0; x < size; x++) {
			runningAlgoThreads.get(x).update();
			if(!runningAlgoThreads.get(x).hasRemainingNeighbours()){
				stoppedAlgoThreads.add(runningAlgoThreads.get(x));
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
			//writeOutputs(""+(connections - currentConnections)+ " connections made in round "+round+"\n");
			currentConnections = connections;
			//writeOutputs(""+(possibleConnections-currentConnections)+" connections left to be made\n");
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
			//Sensor sensor1 = stoppedAlgoThreads.remove(stoppedAlgoThreads.size()).sensor;
			//Sensor sensor2 = stoppedAlgoThreads.remove(stoppedAlgoThreads.size()).sensor;
			//System.out.println("Sensor1 sector: "+sensor1.currentSector());
			//System.out.println("Sensor2 sector: "+sensor2.currentSector());
			/*for (int i = 0; i < sensors.length; i ++){
				System.out.printf("Sensor %d delay: %d sector: %d\n", i, 
						sensors[i].getDelay(), sensors[i].currentSector());
			}*/

		}
		if(shouldDraw && gui && wC != null){
			wC.sB.draw(wC.sB.getGraphics(), sensors, neighbours);
		}
	}

	protected void writeOutputs(String toWrite) {
		if (wC != null) wC.output(toWrite);
		else System.out.print(toWrite);
		log.logWrite(toWrite);
	}
	
	public void runAsThread() {

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
					while (isKeepRunning()) {
						long start = System.currentTimeMillis();
						//delay(speed);						
						update(true);
						long end = System.currentTimeMillis();
						if((end-start)<speed){
							//Under 2000ms, sleep the difference.
							//This 2000ms should be changed to something on the slider...
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

	public void stop() {
		setKeepRunning(false);
	}


	public void delay(int speed){
		try {
			Thread.sleep(speed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean isKeepRunning() {
		return keepRunning;
	}

	protected void setKeepRunning(boolean keepRunning) {
		this.keepRunning = keepRunning;
	}
}
