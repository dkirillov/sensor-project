import java.util.Random;
import java.util.Vector;

public class MainThread {
	private boolean keepRunning = false;
	boolean restart = false;
	WindowClass wC;
	private Sensor[] sensors;
	protected Vector<Neighbour> neighbours;
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
		sensors = new Sensor[Integer.parseInt(wC.numSensors.getText())];
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
			record_neighbours(x);
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
		//There should be a smarter way...
		/*for (int x = 0;x<sensors.length;x++){
			for(int y = 0;y<sensors.length;y++){
				if(x==y){ continue;	}			
				if (sensors[x].inRange(sensors[y].getPoint())){
					int s = sensors[x].inSector(sensors[y].getPoint());
					//System.out.println("Sector: "+s);
					//System.out.println("Sectors: "+sensors[x].sectors+"\n");
					//Neighbour					
					sensors[x].addNeighbour(new Neighbour(s,y));
					possibleConnections ++;
				}
			} 
		}
		//since every possible connection is recorded twice, 
		//divide by 2 to get the true value. Also do a quick
		//sanity check
		if (possibleConnections%2 ==1){
			new Exception("possible connections should be even").printStackTrace();
		}
		possibleConnections/=2;*/
	}

	public void update() {
		round ++;
		int connections = 0;
		for (int x = 0; x < sensors.length; x++) {
			sensors[x].update();
		}
		for (Neighbour n : neighbours) {
			if (n.isConnected()) connections++;
		}
		//Unfortunately, the loop above updates all the sensors, the loop below checks if connected...
		/*for (int x = 0; x < sensors.length; x++) {
			List<Neighbour> neighbour_list = sensors[x].getNeighbours();
			int n_size = neighbour_list.size();
			for (int y=0;y<n_size;y++){
				Neighbour n = neighbour_list.get(y);
				if(n.isConnected()||sensors[x].currenctSector()!=n.getSector()){continue;}

				List<Neighbour> neighbours_n_list = sensors[n.getNeighbour_num()].getNeighbours();
				int n_n_size = neighbours_n_list.size();
				for (int z=0;z<n_n_size;z++){
					if (neighbours_n_list.get(z).getNeighbour_num()!=x){continue;}

					Neighbour n_n = neighbours_n_list.get(z);

					if(n_n.getSector()==sensors[n.getNeighbour_num()].currenctSector()){
						//Duplicate connection pair!
						n.connect();
						n_n.connect();
						currentConnections++;
						connections ++;
						//wC.output(""+n.getNeighbour_num()+" connected with "+n_n.getNeighbour_num()+
						//		" at round "+round+"\n");
						//wC.output(""+(possibleConnections-currentConnections)+" connections left to be made\n");
					}
				}
			}
		}*/
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
						update();
						delay(speed);
					}
					delay(100);
					Debug.debug("here");
					if (restart){
						initialize();
						restart = false;
					}
				}
			}

		}).run();
	}

	public void stop() {
		setKeepRunning(false);
	}


	public void delay(int speed){
		try {
			Thread.sleep(speed);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isKeepRunning() {
		return keepRunning;
	}

	public void setKeepRunning(boolean keepRunning) {
		this.keepRunning = keepRunning;
	}
}
