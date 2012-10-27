import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainThread {
	private boolean keepRunning = true;
	WindowClass wC;
	private Sensor[] sensors;
	//Might need another one to keep track of the ones really connected...
	private List<List<Neighbour>> sensors_neighbour; // Actually... maybe store it in sensor?

	public MainThread(WindowClass wC) {
		this.wC = wC;
		int numSensors = new Random().nextInt(3)+2;
		sensors = new Sensor[numSensors];
		sensors_neighbour = new ArrayList<List<Neighbour>>(numSensors);
		
		int x_c = new Random().nextInt(370) + 115;
		int y_c = new Random().nextInt(170) + 115;
		for (int x = 0; x < sensors.length; x++) {
			x_c = x_c + ((new Random().nextBoolean() ? 1 : (-1)) * new Random().nextInt(52));
			y_c = y_c + ((new Random().nextBoolean() ? 1 : (-1)) * new Random().nextInt(52));
			sensors[x] = new Sensor(x_c, y_c);
		}
		record_neighbours();
	}

	private void record_neighbours(){
		//There should be a smarter way...
		for (int x = 0;x<sensors.length;x++){
			sensors_neighbour.add(new LinkedList<Neighbour>());
			for(int y = 0;y<sensors.length;y++){
				if(x==y){ continue;	}			
				if (sensors[x].inRange(sensors[y].getPoint())){
					int s = sensors[x].inSector(sensors[y].getPoint());
					//System.out.println("Sector: "+s);
					//System.out.println("Sectors: "+sensors[x].sectors+"\n");
					//Neighbour					
					sensors_neighbour.get(x).add(new Neighbour(s,y));
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
			List<Neighbour> neighbour_list = sensors_neighbour.get(x);
			int n_size = neighbour_list.size();
			for (int y=0;y<n_size;y++){
				Neighbour n = neighbour_list.get(y);
				if(n.isConnected()||sensors[x].currenctSector()!=n.getSector()){continue;}
				
				List<Neighbour> neighbours_n_list = sensors_neighbour.get(n.getNeighbour_num());
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
		
		wC.sB.draw(wC.getContentPane().getGraphics(), sensors,sensors_neighbour);
	}

	public void run() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (keepRunning) {
					update();
				}
			}

		}).run();
	}

	public void stop() {
		keepRunning = false;
	}
}
