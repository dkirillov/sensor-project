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
	private int sectors;				//The number of sectors it has.
	private int current_sector;			//The current sector it's on.
	private Point p;					//The position/location of the sensor.
	private int wait_time;				//The wait 'time' (thread ticks) before the sensor goes to the next sector, this decrements each time the thread calls on update.
	private int max_wait_time;			//The random wait time, used to reset the variable above when it hits 0.
	private Color c; 					//The literal color of the sensor, it's sector/beam color.
	//Maybe do a matrix like described in algorithms, facebook example...
	List <Neighbour> neighbours;		//All it's neighbours, other sensors the fall in range.

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
	public Sensor(int x, int y) {
		neighbours = new ArrayList<Neighbour>();
		sectors = (new Random().nextInt(9)) + 3;
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
			current_sector += current_sector >= sectors - 1 ? ((sectors - 1) * -1) : 1;
			wait_time = max_wait_time;
		}
	}
	public void update(boolean override) {
		if (override) {
			current_sector += current_sector >= sectors - 1 ? ((sectors - 1) * -1) : 1;
			wait_time = max_wait_time;
		}
	}

	/**
	 * Draws the sensor.
	 * @param gfx Graphics to draw with.
	 */
	public void draw(Graphics gfx) {
		int multi_deg = (360) / sectors;	//The angle of the sensor's beam, in degrees.

		gfx.setColor(c);
		gfx.fillArc(p.x - 55, p.y - 55, 115, 115, multi_deg * current_sector, multi_deg);

		gfx.setColor(Color.BLACK);
		gfx.fillOval(p.x, p.y, 5, 5);
		gfx.setColor(new Color(0, 0, 0, 25));
		gfx.drawOval(p.x - 55, p.y - 55, 115, 115);
		
		//This was for visual debugging... but not sure if it looks good with our without.
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
		return (p2.x >= p.x - 53 && p2.x <= p.x + 53)
				&& (p2.y >= p.y - 53 && p2.y < p.y + 53);
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
	 * Returns the number of sectors that the sensor has.
	 * @return The number of sectors that the sensor has.
	 */
	public int getSectors() {
		return sectors;
	}
	
	/**
	 * Gets a list of all the neighbours of this sensor. 
	 * @return A list of neighbours.
	 */
	public List<Neighbour> getNeighbours(){
		return neighbours;
	}
	
	/**
	 * Adds a neighbour to the list.
	 * @param n The neighbour to add to the list.
	 */
	public void addNeighbour(Neighbour n){
		neighbours.add(n);	
	}	
}
