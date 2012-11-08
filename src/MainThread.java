import java.util.List;
import java.util.Random;

public class MainThread {
	private boolean keepRunning = true;
	WindowClass wC;
	private Sensor[] sensors;
	int speed = 10;

	public MainThread(WindowClass wC) {
		this.wC = wC;
		int numSensors = new Random().nextInt(3)+2;
		sensors = new Sensor[numSensors];
		
		int x_c = new Random().nextInt(370) + 115;
		int y_c = new Random().nextInt(170) + 115;
		for (int x = 0; x < sensors.length; x++) {
			x_c += ((new Random().nextBoolean() ? 1 : (-1)) * new Random().nextInt(52));
			y_c += ((new Random().nextBoolean() ? 1 : (-1)) * new Random().nextInt(52));
			if(x_c+58>=485||x_c-58<0){
				x_c += (x_c-58<0)?58-x_c:(485-(x_c+58)); 
			}
			if(y_c-58<0||y_c+58>=285){
				y_c += (y_c-58<0)?58-y_c:(285-(y_c+58)); 				
			}
			sensors[x] = new Sensor(x_c, y_c);
		}
		record_neighbours();
	}

	private void record_neighbours(){
		//There should be a smarter way...
		for (int x = 0;x<sensors.length;x++){
			for(int y = 0;y<sensors.length;y++){
				if(x==y){ continue;	}			
				if (sensors[x].inRange(sensors[y].getPoint())){
					int s = sensors[x].inSector(sensors[y].getPoint());
					//System.out.println("Sector: "+s);
					//System.out.println("Sectors: "+sensors[x].sectors+"\n");
					//Neighbour					
					sensors[x].addNeighbour(new Neighbour(s,y));
				}
			} 
		}
	}
	
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
					}
				}
			}
		}
		wC.sB.draw(wC.sB.getGraphics(), sensors);
	}

	public void run() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (keepRunning) {
					update();
					delay(speed);
				}
			}

		}).run();
	}

	public void stop() {
		keepRunning = false;
	}
	
	public void delay(int speed){
		try {
			Thread.sleep(speed);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
