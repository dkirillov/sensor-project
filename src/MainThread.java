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
	int width = 600;
	int height = 440;
	

	public MainThread(WindowClass wC) {
		this.wC = wC;
		restart = true;
	}

	private void initialize(){
		round = 0;
		possibleConnections = 0;
		currentConnections = 0;
		numSensors = Integer.parseInt(wC.numSensors.getText());
		sensors = new Sensor[numSensors];
		algoThreads = new RotationAlgorithm[numSensors];
		neighbours = new Vector<Neighbour>();
		int x_c = new Random().nextInt(width - 2*range) + range;
		int y_c = new Random().nextInt(height - 2*range) + range;
		for (int x = 0; x < sensors.length; x++) {
			int value = ((new Random().nextBoolean() ? 1 : (-1)) * new Random().nextInt(range));
			x_c += value;
			//ensure it is in the proper range, pythagorean again
			value = (int) Math.sqrt(range*range - value*value); 
			y_c += ((new Random().nextBoolean() ? 1 : (-1)) * new Random().nextInt(value));
			if(x_c+range>=width||x_c-range<0){
				x_c += (x_c-range<0)?range-x_c:(width-(x_c+range)); 
			}
			if(y_c-range<0||y_c+range>=height){
				y_c += (y_c-range<0)?range-y_c:(height-(y_c+range)); 				
			}

			sensors[x] = new Sensor(x, x_c, y_c, range);

			int ranAlgo = new Random(System.currentTimeMillis()).nextInt(300);
			if(ranAlgo >= 0 && ranAlgo <= 99){
				algoThreads[x]  = new RSRMAlgorithm(sensors[x]);			
			}else if (ranAlgo >= 100 && ranAlgo <= 299){
				algoThreads[x] = new RSRMAlgorithmPrime(sensors[x]);	
			}else {
				algoThreads[x] = new ARAlgorithm(sensors[x]);
			}
			Debug.debug("ranAlgo: "+ranAlgo);

			record_neighbours(x);
		}
	
		for (int x = 0; x < algoThreads.length; x++) {
			Thread temp = new Thread(algoThreads[x]);
			algoThreads[x].setThread(temp);
			temp.start();
		}
	
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
		round ++;
		int connections = 0;
		/*for (int x = 0; x < sensors.length; x++) {
			sensors[x].update();
		}*/
		for (Neighbour n : neighbours) {
			if (n.isConnected()) connections++;
		}
		if ((connections - currentConnections)>0){
			wC.output(""+(connections - currentConnections)+ " connections made in round "+round+"\n");
			currentConnections = connections;
			wC.output(""+(possibleConnections-currentConnections)+" connections left to be made\n\n");
		}
		wC.sB.draw(wC.sB.getGraphics(), sensors, neighbours);
	}

	public void run() {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
					while (isKeepRunning()) {
						RotationAlgorithm.setSleepTime(speed);
						update();
					}
					//delay(100);
					Debug.debug("here");
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
