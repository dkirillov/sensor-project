import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;


public class SensorBoard extends JPanel {
	public SensorBoard(){
		setVisible(true);
	}
	public void draw(Graphics gfx, Sensor[] sensors,List<List<Neighbour>> test){
		BufferedImage buffer = new BufferedImage(598, 438, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = buffer.getGraphics();
		
		graphics.setColor(Color.WHITE);
		graphics.fillRect(1,1,598,438);

		//Just testing if all neighbours are discovered
		graphics.setColor(Color.GREEN);
		for(int x = 0;x<sensors.length;x++){
			int start_x = sensors[x].getPoint().x+2;
			int start_y = sensors[x].getPoint().y+2;
			for(int y = 0;y<test.get(x).size();y++){
				if(!test.get(x).get(y).isConnected()){continue;}
				int end_x = sensors[test.get(x).get(y).getNeighbour_num()].getPoint().x+2;
				int end_y = sensors[test.get(x).get(y).getNeighbour_num()].getPoint().x+2;
				//System.out.println("start_x: "+start_x+" start_y: "+start_y);
				graphics.drawLine(start_x, start_y, end_x, end_y);
			}
		}		
		
		for(int x = 0 ;x <sensors.length;x++){
			sensors[x].draw(graphics);
		}
		
		Graphics2D g2 = (Graphics2D) gfx;
		g2.drawImage(buffer, 0, 0, null);
	}
}
