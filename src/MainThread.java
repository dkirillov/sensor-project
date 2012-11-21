import java.util.Random;
import java.util.Vector;

public class MainThread {

	private boolean keepRunning = false;
	boolean restart = false;
	WindowClass wC;
	private Sensor[] sensors;
	protected Vector<Neighbour> neighbours;
	private RotationAlgorithm[] algoThreads;
	int speed = 10;
	int numSensors = 3;
	int round = 0;
	int possibleConnections = 0;
	int currentConnections = 0;
	public int range = 50;
	long startTime;
	long endTime;
	boolean firstTime;
	boolean resetTime;
	

	public MainThread(WindowClass wC) {
		this.wC = wC;
		restart = true;
//		Debug.setDebug(true);
	}

	private void initialize(){
		round = 0;
		possibleConnections = 0;
		currentConnections = 0;
		numSensors = Integer.parseInt(wC.numSensors.getText());
		sensors = new Sensor[numSensors];
		algoThreads = new RotationAlgorithm[numSensors];
		neighbours = new Vector<Neighbour>();
		int x_c = new Random().nextInt(SensorBoard.BOARD_WIDTH - 2*range) + range;
		int y_c = new Random().nextInt(SensorBoard.BOARD_HEIGHT - 2*range) + range;
		int k = (new Random(System.currentTimeMillis()).nextInt(10)) + 3;
		for (int x = 0; x < sensors.length; x++) {
			int value = ((new Random().nextBoolean() ? 1 : (-1)) * new Random().nextInt(range));
			x_c += value;
			//ensure it is in the proper range, pythagorean again
			value = (int) Math.sqrt(range*range - value*value); 
			y_c += ((new Random().nextBoolean() ? 1 : (-1)) * new Random().nextInt(value));
			if(x_c+range>=SensorBoard.BOARD_WIDTH||x_c-range<0){
				x_c += (x_c-range<0)?range-x_c:(SensorBoard.BOARD_WIDTH-(x_c+range)); 
			}
			if(y_c-range<0||y_c+range>=SensorBoard.BOARD_HEIGHT){
				y_c += (y_c-range<0)?range-y_c:(SensorBoard.BOARD_WIDTH-(y_c+range)); 				
			}

			sensors[x] = new Sensor(x, x_c, y_c, range,k);

			int ranAlgo = new Random(System.currentTimeMillis()+x).nextInt(300);
			if(ranAlgo >= 0 && ranAlgo <= 99){
				algoThreads[x]  = new RSRMAlgorithm(sensors[x]);			
			}else if (ranAlgo >= 100 && ranAlgo <= 199){
				algoThreads[x] = new RSRMAlgorithmPrime(sensors[x]);	
			}else {
				algoThreads[x] = new ARAlgorithm(sensors[x]);
			}
			Debug.debug("ranAlgo: "+ranAlgo);

			record_neighbours(x);
		}
		firstTime = true;
		resetTime = true;
		wC.output("There are " + possibleConnections+" possible connections.\n");
	}

	private void record_neighbours(int n){
		for (int x = 0; x < n; x++) {
			if (sensors[x].inRange(sensors[n].getPoint())) {
				Neighbour newNeighbour = new Neighbour(sensors[x], sensors[n]);
				neighbours.add(newNeighbour);
				sensors[x].addNeighbour(newNeighbour, sensors[n]);
				sensors[n].addNeighbour(newNeighbour, sensors[x]);
				possibleConnections++;
			}
		}
	}

	public void update() {
		if(firstTime){
			startTime = System.currentTimeMillis();
			wC.output("Start time: "+startTime+"\n");
			firstTime = false;
		}
		round ++; //This is now a more accurate representation.
		int connections = 0;
		for (int x = 0; x < algoThreads.length; x++) {
			algoThreads[x].update();
		}
		for (Neighbour n : neighbours) {
			if (n.isConnected()) connections++;
		}
		if ((connections - currentConnections)>0){
			wC.output(""+(connections - currentConnections)+ " connections made in round "+round+"\n");
			currentConnections = connections;
			wC.output(""+(possibleConnections-currentConnections)+" connections left to be made\n\n");
		}
		if (resetTime&&possibleConnections-currentConnections == 0){
			endTime = System.currentTimeMillis();
			wC.output("End time: "+endTime+"\n");
			wC.output("Ran for: "+(endTime-startTime)+"ms\n");	
			resetTime = false;
		}
		wC.sB.draw(wC.sB.getGraphics(), sensors, neighbours);
	}

	public void run() {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
					while (isKeepRunning()) {
						float start = System.currentTimeMillis();
						//delay(speed);						
						update();
						float end = System.currentTimeMillis();
						if((end-start)<speed){
							//Under 2000ms, sleep the difference.
							//This 2000ms should be changed to something on the slider...
							Debug.debug("Sleeping: "+(speed-(end-start)));
							delay((int)(speed-(end-start)));
						}
					}
					if (restart){
						initialize();
						restart = false;
					}
				}
			}

		}).start();
	}

	public void stop() {
		setKeepRunning(false);
		for (int x = 0; x < algoThreads.length; x++) {
			algoThreads[x].stop();
		}
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
