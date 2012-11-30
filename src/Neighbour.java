import java.awt.Point;

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
	protected final Sensor _sensor1;
	protected final Sensor _sensor2;
	protected boolean _sensor1Facing;
	protected boolean _sensor2Facing;
	protected boolean _connected;			//True if the sensor and the neighbour have 'connected'. False otherwise.

	/**\
	 * Constructor, initalizes everything
	 * @param sensor1Id Id of the first sensor
	 * @param sensor2Id Id of the second sensor
	 */
	public Neighbour (Sensor sensor1, Sensor sensor2){
		_sensor1 = sensor1;
		_sensor2 = sensor2;
		_sensor1Facing = false;
		_sensor2Facing = false;
		_connected = false;
	}
	
	/**
	 * For a given sensor Id, set if that sensor is facing for this neighbour
	 * @param sensorId The sensor that is facing this neighbour
	 * @param facing If the sensor is facing this neighbour
	 * @throws IllegalArgumentException thrown if this neighbour is not related to the sensorId input
	 */
	public void setFacingForSensor(int sensorId, boolean facing) 
		throws IllegalArgumentException {
		if (sensorId == _sensor1.SensorId) {
			_sensor1Facing = facing;
		}
		else if (sensorId == _sensor2.SensorId) {
			_sensor2Facing = facing;
		}
		else {
			throw new IllegalArgumentException("Sensor with ID: " + sensorId + " cannot modify this Neighbour.");
		}
		if (!_connected) {
			_connected = _sensor1Facing && _sensor2Facing;
			if(_connected){
			/*System.out.println("*********** when connected **********");
			System.out.printf("Sensor id: %d sector: %d\n",
					_sensor1.SensorId, _sensor1.currentSector());
			System.out.printf("Sensor id: %d sector: %d\n", 
					_sensor2.SensorId, _sensor2.currentSector());*/
			}
		}
	}
	
	/**
	 * Check if the sensor and the neighbour are connected.
	 * @return True if the sensor and the neighbour are connected, false otherwise. 
	 */
	public boolean isConnected(){
		return _connected;
	}
	
	public Point getSensor1Pos() { return _sensor1.getPoint(); }
	public Point getSensor2Pos() { return _sensor2.getPoint(); }
}
