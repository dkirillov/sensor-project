import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainThread {
	private boolean keepRunning = true;
	WindowClass wC;
	private Sensor[] sensors;
	private List<List<Neighbour>> sensors_neighbour;//Might need another one to keep track of the ones really connected...

	public MainThread(WindowClass wC) {
		this.wC = wC;
		int numSensors = 2;//new Random().nextInt(18)+2;
		sensors = new Sensor[numSensors];
		sensors_neighbour = new ArrayList<List<Neighbour>>(numSensors);
		
		int x_c = new Random().nextInt(370) + 115;
		int y_c = new Random().nextInt(170) + 115;
		for (int x = 0; x < sensors.length; x++) {
			x_c = x_c + ((new Random().nextBoolean() ? 1 : (-1)) * new Random().nextInt(53));
			y_c = y_c + ((new Random().nextBoolean() ? 1 : (-1)) * new Random().nextInt(53));
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
					//Neighbour
					//Mathematically, there's a hypotinues, based on that an angle can be calculated from that angle find in which multiple sector it falls in...
					
					sensors_neighbour.get(x).add(new Neighbour(0,y));
				}
			}
		}
	}
	
	public void update() {
		for (int x = 0; x < sensors.length; x++) {
			sensors[x].update();
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
