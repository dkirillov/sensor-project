/**
 * @author Danil Kirillov, Darryl Hill, Wesley Lawrence.
 */
import java.util.List;
import java.util.Random;

/**
 * A class representing the main thread of the program. 
 * Contains the loop that keeps the sensors 'running'.
 */
public class MainThread {
	private boolean keepRunning = true;		//Boolean to keep the thread running, setting it to false will stop the thread.
	WindowClass wC;							//The window class which needs this thread.
	private Sensor[] sensors;				//All the sensors.
	int speed = 10;							//Used to sleeping.
	Sensor testSensor1;    //DELETE ME
	Sensor testSensor2;    //DELETE ME
	Sensor testSensor3;    //DELETE ME

	/**
	 * Constructor, initializes the sensors, then records the neighbours.
	 * @param wC The window class which will be drawn on.
	 */
	public MainThread(WindowClass wC) {
		this.wC = wC;
		//Assign random amount of sensors, for now its up to 5, minimum of 2.
		int numSensors = new Random().nextInt(3)+2;
		sensors = new Sensor[numSensors];
		
		//Places the first sensor at a random point within the panel's size.
		int x_c = new Random().nextInt(370) + 115;
		int y_c = new Random().nextInt(170) + 115;
		sensors[0] = new Sensor(x_c, y_c);
		
		for (int x = 1; x < sensors.length; x++) {
			//Each new sensor will lay in range of the previous sensor.
			x_c += ((new Random().nextBoolean() ? 1 : (-1)) * (new Random().nextInt(47)+5));
			y_c += ((new Random().nextBoolean() ? 1 : (-1)) * (new Random().nextInt(47)+5));
			//Checking that it's not out of bound.
			if(x_c+58>=485||x_c-58<0){
				x_c += (x_c-58<0)?58-x_c:(485-(x_c+58)); 
			}
			if(y_c-58<0||y_c+58>=285){
				y_c += (y_c-58<0)?58-y_c:(285-(y_c+58)); 				
			}
			sensors[x] = new Sensor(x_c, y_c);
			record_neighbours(x);
		}
		
		testSensor1 = new Sensor(115,115);
		testSensor2 = new Sensor(153,153);
		testSensor3 = new Sensor(153,77);
		Debug.setDebug(true);
		
		RSRMAlgorithm rSRMA1 = new RSRMAlgorithm(testSensor1);
		RotationAlgorithm rA1 = rSRMA1;
		Thread t1 = new Thread(rA1);
		rSRMA1.setThread(t1);
		t1.start();
		
		
		RSRMAlgorithmPrime rSRMA2 = new RSRMAlgorithmPrime(testSensor2);
		RotationAlgorithm rA2 = rSRMA2;
		Thread t2 = new Thread(rA2);
		rSRMA2.setThread(t2);
		t2.start();
		
		ARAlgorithm rSRMA3 = new ARAlgorithm(testSensor3);
		RotationAlgorithm rA3 = rSRMA3;
		Thread t3 = new Thread(rA3);
		rSRMA3.setThread(t3);
		t3.start();
		//rA.stop();
	
	}

	/**
	 * Goes up to the current sensor being initialized and updates the neighbours.
	 */
	private void record_neighbours(int n){
		for (int x = 0; x < n; x++) {
			if (sensors[x].inRange(sensors[n].getPoint())) {
				int s = sensors[x].inSector(sensors[n].getPoint());
				sensors[x].addNeighbour(new Neighbour(s, n));
				s = sensors[n].inSector(sensors[x].getPoint());
				sensors[n].addNeighbour(new Neighbour(s,x));
			}
		}
	}
	
	/**
	 * Updates all the sensors, and check if neighbours are connected.
	 */
	public void update() {
		
		for (int x = 0; x < sensors.length; x++) {
			sensors[x].update();
		}
		//Unfortunately, the loop above updates all the sensors, the loop below checks if connected...
		for (int x = 0; x < sensors.length; x++) {
			List<Neighbour> neighbour_list = sensors[x].getNeighbours();
			int n_size = neighbour_list.size();
			for (int y=0;y<n_size;y++){
				Neighbour n = neighbour_list.get(y);
				if(n.isConnected()||sensors[x].currentSector()!=n.getSector()){continue;}
				
				List<Neighbour> neighbours_n_list = sensors[n.getNeighbour_num()].getNeighbours();
				int n_n_size = neighbours_n_list.size();
				for (int z=0;z<n_n_size;z++){
					if (neighbours_n_list.get(z).getNeighbour_num()!=x){continue;}
					
					Neighbour n_n = neighbours_n_list.get(z);
					
					if(n_n.getSector()==sensors[n.getNeighbour_num()].currentSector()){
						//Duplicate connection pair!
						n.connect();
						n_n.connect();
					}
				}
			}
		}
		wC.drawOnBoard(sensors,testSensor1,testSensor2,testSensor3);
	}

	/**
	 * Starts the thread, runs it.
	 */
	public void start() {
		keepRunning = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (keepRunning) {
					update();
					delay(speed);
				}
			}

		}).start();
	}

	/**
	 * Stops the thread.
	 */
	public void stop() {
		keepRunning = false;
	}
	
	/**
	 * Delays/sleeps the thread.
	 * @param speed The number of miliseconds to sleep.
	 */
	public void delay(int speed){
		try {
			Thread.sleep(speed);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
