import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;


public class SensorBoard extends JPanel {
	public SensorBoard(){
		setVisible(true);
	}
	public void draw(Graphics gfx, Sensor[] sensors, Vector<Neighbour> neighbours){
		BufferedImage buffer = new BufferedImage(598, 438, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = buffer.getGraphics();
		
		graphics.setColor(Color.WHITE);
		graphics.fillRect(1,1,598,438);

		//Just testing if all neighbours are discovered
		graphics.setColor(Color.RED);
		for (Neighbour n : neighbours) {
			
			if(!n.isConnected()) continue;
			
			Point p1 = n.getSensor1Pos();
			Point p2 = n.getSensor2Pos();
			graphics.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
		/*graphics.setColor(Color.RED);
		for(int x = 0;x<sensors.length;x++){
			int start_x = sensors[x].getPoint().x;
			int start_y = sensors[x].getPoint().y;
			List<Neighbour> neighbours = sensors[x].getNeighbours();
			int size = neighbours.size();
			for(int y = 0;y<size;y++){
				if(!neighbours.get(y).isConnected()){continue;}
				int end_x = sensors[neighbours.get(y).getNeighbour_num()].getPoint().x;
				int end_y = sensors[neighbours.get(y).getNeighbour_num()].getPoint().y;
				//System.out.println("start_x: "+start_x+" start_y: "+start_y);
				graphics.drawLine(start_x, start_y, end_x, end_y);
			}
		}*/
		
		for(int x = 0 ;x <sensors.length;x++){
			sensors[x].draw(graphics);
		}
		
		Graphics2D g2 = (Graphics2D) gfx;
		if (g2 == null){
			Debug.debug("Graphics are null");
		}
		g2.drawImage(buffer, 0, 0, null);
	}
}
