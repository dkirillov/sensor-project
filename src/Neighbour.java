/**
 * @author Danil Kirillov, Darryl Hill, Wesley Lawrence.
 */

/**
 * A class representing a neighbour relationship between two sensors.
 * Sensors are neighbours when they fall in range of each other.
 * The neighbour class is stored within a sensor and contains information about a neighbour of it.
 * The two are connected when they have had their sectors face each other at least once.
 */
public class Neighbour {
	private int sector;					//The sector which the neighbour lays on.
	private int neighbour_num;			//The neighbour's number, relative to the list stored in the MainThread.
	private boolean connected;			//True if the sensor and the neighbour have 'connected'. False otherwise.

	/**
	 * Constructor, initializes everything.
	 * @param s The sector which the neighbour lays on.
	 * @param n_n The neighbour's number, relative to the list in the MainThread.
	 */
	public Neighbour (int s, int n_n){
		this.sector = s;
		this.neighbour_num = n_n;
		connected = false;
	}
	
	/**
	 * Gets the sector which the neighbour lays on.
	 * @return The sector which the neighbour lays on.
	 */
	public int getSector() {
		return sector;
	}
	/**
	 * Gets the neighbour's number.
	 * @return Neighbour's number.
	 */
	public int getNeighbour_num() {
		return neighbour_num;
	}
	
	/**
	 * 'Connect' the sensor and the neighour.
	 */
	public void connect(){
		connected = true;
	}
	/**
	 * Check if the sensor and the neighbour are connected.
	 * @return True if the sensor and the neighbour are connected, false otherwise. 
	 */
	public boolean isConnected(){
		return connected;
	}
}
