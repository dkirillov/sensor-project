/**
 * @author Danil Kirillov, Darryl Hill, Wesley Lawrence.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the sensor itself.
 */
public class Sensor {
	public final int SensorId;			//The ID of this sensor
	private int sectors;				//The number of sectors it has.
	private int current_sector;			//The current sector it's on.
	private Point p;					//The position/location of the sensor.
	private int wait_time;				//The wait 'time' (thread ticks) before the sensor goes to the next sector, this decrements each time the thread calls on update.
	private int max_wait_time;			//The random wait time, used to reset the variable above when it hits 0.
	private Color c; 					//The literal color of the sensor, it's sector/beam color.
	private int radius;
	
	ArrayList<ArrayList<Neighbour>> neighboursInASector;		//All it's neighbours, in sectors.

	/**
	 * Constructor, initializes everything.
	 * A random wait time and color are assigned.
	 * The passed in x/y are used for it's position.
	 * 
	 * ****A RANDOM NUMBER OF SECTORS ARE ASSIGNED**** between 3 and 12, inclusive.
	 * The sensor starts on a random sector.
	 * 
	 * @param x
	 * @param y
	 */
	public Sensor(int id, int x, int y, int radius) {
		SensorId = id;
		this.radius = radius;
		sectors = (new Random().nextInt(9)) + 3;
		
		neighboursInASector = new ArrayList<ArrayList<Neighbour>>();
		for (int i = 0; i < sectors; i++) {
			neighboursInASector.add(new ArrayList<Neighbour>());
		}
		
		//max_wait_time = (new Random().nextInt(990)) + 10;
		max_wait_time = 100; 	//For now it's not random...
		current_sector = new Random().nextInt(sectors);
		wait_time = 1;
		p = new Point(x, y);
		c = new Color(new Random().nextInt(255), new Random().nextInt(255),
				new Random().nextInt(255), 50);
	}

	/**
	 * Updates the sensor.
	 * Switches the sector that it's on if the wait time is up.
	 */
	public void update() {
		wait_time--;
		if (wait_time == 0) {
			//Update neighbours
			setNeighboursFacing(false);
			//Change sector, reset wait time
			current_sector += current_sector >= sectors - 1 ? ((sectors - 1) * -1) : 1;
			wait_time = max_wait_time;
			//Update new neighbours
			setNeighboursFacing(true);
		}
	}

	/**
	 * Draws the sensor.
	 * @param gfx Graphics to draw with.
	 */
	public void draw(Graphics gfx) {
		int multi_deg = (360) / sectors;	//The angle of the sensor's beam, in degrees.

		gfx.setColor(c);
		gfx.fillArc(p.x - radius, p.y - radius, radius*2, radius*2, multi_deg * current_sector, multi_deg);

		gfx.setColor(Color.BLACK);
		gfx.fillOval(p.x-2, p.y-2, 4, 4);
		gfx.setColor(new Color(0, 0, 0, 25));
		
		gfx.drawOval(p.x - radius, p.y - radius, radius*2, radius*2);
		gfx.setColor(Color.BLACK);
		gfx.drawString(current_sector + "/" + sectors, p.x, p.y);

		/*
		 * double multi_rad = (Math.PI * 2) / sectors; gfx.setColor(Color.RED);
		 * for (int x = 0; x < sectors; x++) { int offsetX = (int)
		 * (Math.cos(multi_rad * x) * 60); int offsetY = (int)
		 * (Math.sin(multi_rad * x) * 60); System.out.println("x: " + x);
		 * System.out.println("sectors: " + sectors);
		 * System.out.println("multiples: " + multi_rad);
		 * System.out.println("offsetX: " + offsetX + "\noffsetY: " + offsetY +
		 * "\n"); gfx.drawLine(p.x + 2, p.y + 2, p.x + offsetX, p.y + offsetY);
		 * }
		 */
	}

	/**
	 * Checks if a point, probably a sensor, lays in range of this sensor.
	 * @param p2 Point to check.
	 * @return True of the point lays in range, false if otherwise.
	 */
	public boolean inRange(Point p2) {
		return Math.pow(radius,2)>=(Math.pow((p2.x-p.x),2) + Math.pow((p2.y-p.y),2));
	}

	/**
	 * Determines which sector the point lays in. Returns a number between 0 and it's max sectors minus one.
	 * @param p2 The point to check.
	 * @return Returns a number between 0 and it's max sectors minus one, or -1 if it's not in range.
	 */
	public int inSector(Point p2) {
		if (!inRange(p2)) return -1;
		
		double d_x = (p2.x - p.x);
		double d_y = (p2.y - p.y) * -1;
		double degree = Math.atan(Math.abs(d_x / d_y)) * (180.0 / Math.PI);

		if(d_x>0&&d_y>0){
			degree = (90-degree);
		}else if (d_x < 0 && d_y > 0) {
			degree += 90;
		} else if (d_x < 0 && d_y < 0) {
			degree = 180 + (90-degree);
		} else if (d_x > 0 && d_y < 0) {
			degree += 270;
		}

		int s = (int) (degree / (360.0 / sectors));

		return s;
	}
	
	/**
	 * Gets the current sector that the sensor is on.
	 * @return Current sector that the sensor is on.
	 */
	public int currentSector(){
		return current_sector;
	}
	
	/**
	 * Location/position of the sensor.
	 * @return A Point of the location/position of the sensor.
	 */
	public Point getPoint() {
		return p;
	}
	
	/**
	 * Gets a list of all the neighbours of this sensor. 
	 * @return A list of neighbours.
	 */
	public List<Neighbour> getNeighbours(){
		return neighboursInASector.get(current_sector);
	}
	
	/**
	 * Adds a neighbour to the list.
	 * @param n The neighbour to add to the list.
	 */
	public void addNeighbour(Neighbour n, Sensor peer){
		int sectorForNeighbour = inSector(peer.getPoint());
		neighboursInASector.get(sectorForNeighbour).add(n);
	}
	
	protected void setNeighboursFacing(boolean facing) {
		for (Neighbour n : getNeighbours()) {
			n.setFacingForSensor(SensorId, facing);
		}
	}
}
