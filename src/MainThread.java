import java.util.Random;


public class MainThread {
	private boolean keepRunning = true;
	WindowClass wC;
	Sensor[] sensors;
	
	public MainThread(WindowClass wC){
		this.wC = wC;
		//sensors = new Sensor[new Random().nextInt(10)+1];
		sensors = new Sensor[2];
		int x_c = new Random().nextInt(370)+115;
		int y_c = new Random().nextInt(170)+115;
		for(int x = 0 ;x <sensors.length;x++){
			x_c = new Random().nextInt(55)+x_c;
			y_c = new Random().nextInt(55)+y_c;
			sensors[x] = new Sensor(x_c,y_c);		
		}
	}
	
	public void update(){
		for(int x = 0 ;x <sensors.length;x++){
			sensors[x].update();
		}
		wC.sB.draw(wC.getContentPane().getGraphics(),sensors);
	}
	
	public void run(){
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(keepRunning){
					update();
				}
			}
			
		}).run();
	}
	public void stop(){
		keepRunning = false;
	}
}
