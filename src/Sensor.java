/**
 * @author Danil Kirillov, Darryl Hill, Wesley Lawrence.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Represents the sensor itself.
 */
public class Sensor {
	public final int SensorId;			//The ID of this sensor
	private int sectors;				//The number of sectors (k) it has.
	private int delay;					//The delay (d) that will be used for algorithms, prime number.
	private int current_sector;			//The current sector it's on.
	private Point p;					//The position/location of the sensor.
	private Color c; 					//The literal color of the sensor, it's sector/beam color.
	private int radius;
	private int neighboursToConnect = 0;
	
	ArrayList<ArrayList<Neighbour>> neighboursInASector;		//All it's neighbours, in sectors.

	/**
	 * Constructor for a sensor
	 * @param id		Sensor's ID
	 * @param x			X Position
	 * @param y			Y Position
	 * @param radius	Radius of the sensor
	 * @param k			Number of sectors
	 */
	public Sensor(int id, int x, int y, int radius,int k) {
		SensorId = id;
		this.radius = radius;
		changeSectorCount(k);
		p = new Point(x, y);
	}

	/**
	 * Changes the number of sectors
	 * 
	 * WARING: Erases known neighbours
	 * @param k	The new number of sectors
	 */
	public void changeSectorCount(int k) {
		sectors = k;//= (new Random(System.currentTimeMillis()+id).nextInt(10)) + 3;
		
		neighboursInASector = new ArrayList<ArrayList<Neighbour>>();
		for (int i = 0; i < sectors; i++) {
			neighboursInASector.add(new ArrayList<Neighbour>());
		}
		
		current_sector = new Random().nextInt(sectors);
	}
	
	/**
	 * Effectively rotate the sensor
	 * 
	 * Calls {@link #setNeighboursFacing(boolean)} with a false to stop facing. Clears connected neighbours, updates our sector
	 * and calls {@link #setNeighboursFacing(boolean)} with a true to face the new neighbours.
	 */
	public void update() {
		setNeighboursFacing(false);
		removeConnectedNeighbours();
		current_sector += current_sector >= sectors - 1 ? ((sectors - 1) * -1) : 1;
		setNeighboursFacing(true);
	}
	
	
	
	/**
	 * Draws the sensor.
	 * @param gfx Graphics to draw with.
	 */
	public void draw(Graphics gfx) {
		int multi_deg = (360) / sectors;	//The angle of the sensor's beam, in degrees.
		
		if (neighboursToConnect != 0) {
			gfx.setColor(c);
			gfx.fillArc(p.x - radius, p.y - radius, radius*2, radius*2, multi_deg * current_sector, multi_deg);
		}

		if (neighboursToConnect != 0) {
			gfx.setColor(Color.BLACK);
		}
		else {
			gfx.setColor(Color.green);
		}
		gfx.fillOval(p.x-2, p.y-2, 4, 4);
		
		gfx.setColor(new Color(0, 0, 0, 50));
		gfx.drawOval(p.x - radius, p.y - radius, radius*2, radius*2);
		
		if (neighboursToConnect != 0) {
			gfx.setColor(Color.BLACK);
		}
		else {
			gfx.setColor(Color.green);
		}
		//gfx.drawString(current_sector + "/" + sectors, p.x, p.y);
		//gfx.drawString("" + SensorId, p.x, p.y);
	}

	/**
	 * Checks if a point, probably a sensor, lays in range of this sensor.
	 * 
	 * @param p2 Point to check.
	 * @return True of the point lays in range, false if otherwise.
	 */
	public boolean inRange(Point p2) {
		return Math.pow(radius,2)>=(Math.pow((p2.x-p.x),2) + Math.pow((p2.y-p.y),2));
	}

	/**
	 * Determines which sector the point lays in. Returns a number between 0 and it's max sectors minus one.
	 * 
	 * @param p2 The point to check.
	 * @return Returns a number between 0 and it's max sectors minus one, or -1 if it's not in range.
	 */
	public int inSector(Point p2) {
		if (!inRange(p2)) return -1;
		
		double d_x = (p2.x - p.x);
		double d_y = (p2.y - p.y) * -1;
		double degree = (Math.atan2(d_y, d_x) * (180.0 / Math.PI));
		//double degree = (Math.atan(d_x/d_y) * (180.0 / Math.PI));
		degree = (degree+360)%360;
		//Debug.debug("degree: "+degree);

	/*	if(d_x>=0&&d_y>=0){
			degree = (90-degree);
		}else if (d_x < 0 && d_y >= 0) {
			degree += 90;
		} else if (d_x < 0 && d_y < 0) {
			degree = 180 + (90-degree);
		} else if (d_x >= 0 && d_y < 0) {
			degree += 270;
		}
*/
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
		return neighboursInASector.get(current_sector);
	}
	
	/**
	 * Adds a neighbour to the list.
	 * @param n The neighbour to add to the list.
	 */
	public void addNeighbour(Neighbour n, Sensor peer){
		int sectorForNeighbour = inSector(peer.getPoint());
		neighboursInASector.get(sectorForNeighbour).add(n);
		neighboursToConnect++;
	}
	
	/**
	 * Calls {@link #changeSectorCount(int)} with the current 
	 */
	public void clearNeighbours() {
		changeSectorCount(sectors);
	}
	
	/**
	 * Sets all the neighbours in our current sectors facing value as facing
	 * 
	 * @param facing	Whether or not we're facing
	 */
	protected void setNeighboursFacing(boolean facing) {
		for (Neighbour n : getNeighbours()) {
			n.setFacingForSensor(SensorId, facing);
		}
	}
	
	/**
	 * Looks at all the connected neighbours in the current sector, and removes them
	 */
	protected void removeConnectedNeighbours() {
		List<Neighbour> curNeighbours = getNeighbours();
		
		Iterator<Neighbour> iter = curNeighbours.iterator();
		while (iter.hasNext()) {
			Neighbour curNeighbour = iter.next();
			if (curNeighbour.isConnected()) {
				iter.remove();
				neighboursToConnect--;
			}
		}
	}
	
	/**
	 * Gets the delay of a sensor, this is the 'd' in the algorithms, and is a prime number.
	 * This is also the 'colour' of the sensor/node, both graph theory wise and literally.
	 * @return The delay of a sensor.
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * Sets the delay/colour ('d') of the sensor.
	 * @param d The new delay.
	 */
	public void setDelay(int d) {
		delay = d;
		
		//The 100 is just to make the colours pretty, could be some other number...
		Random r = new Random(delay+100);
		c = new Color(r.nextInt(255), r.nextInt(255),r.nextInt(255), 100);
	}
	
	
	/**
	 * Returning the number of neighbours left to connect, across all remaining sectors
	 * @return
	 */
	public int getRemainingNeighbours(){
		return neighboursToConnect;
	}
}