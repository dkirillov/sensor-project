/**
 * @author Danil Kirillov, Darryl Hill, Wesley Lawrence.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JPanel;

/**
 * A class that represents the board which the sensor are on.
 * Draws all the sensors on it self as well as connection.
 */
public class SensorBoard extends JPanel {
	/**
	 * Constructor, just sets the visibility to true.
	 */
	public SensorBoard(){
		setVisible(true);
	}
	/**
	 * Draws all the sensors as well as their connections.
	 * Uses buffering while drawing.
	 * @param sensors An array of sensors to draw.
	 */
	public void draw(Sensor[] sensors){
		//Image used for buffering.
		BufferedImage buffer = new BufferedImage(598, 438, BufferedImage.TYPE_INT_RGB);
		//Graphics from the image to draw on.
		Graphics graphics = buffer.getGraphics();
		
		graphics.setColor(Color.WHITE);
		graphics.fillRect(1,1,598,438);

		//Draws red lines when the sensors are connected.
		graphics.setColor(Color.RED);
		for(int x = 0;x<sensors.length;x++){
			int start_x = sensors[x].getPoint().x+2;
			int start_y = sensors[x].getPoint().y+2;
			List<Neighbour> neighbours = sensors[x].getNeighbours();
			int size = neighbours.size();
			for(int y = 0;y<size;y++){
				if(!neighbours.get(y).isConnected()){continue;}
				int end_x = sensors[neighbours.get(y).getNeighbour_num()].getPoint().x+2;
				int end_y = sensors[neighbours.get(y).getNeighbour_num()].getPoint().y+2;
				graphics.drawLine(start_x, start_y, end_x, end_y);
			}
		}		
		
		//Calls on each sensor to draw itself.
		for(int x = 0 ;x <sensors.length;x++){
			sensors[x].draw(graphics);
		}
		
		//Gets the graphics of this JPanel to draw the buffered image on.
		Graphics2D g2 = (Graphics2D) this.getGraphics();
		if (g2 == null){
			Debug.debug("Graphics are null");
		}
		//Draws the image.
		g2.drawImage(buffer, 0, 0, null);
	}
}
