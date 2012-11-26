import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;


public class SensorBoard extends JPanel {

	private static final long serialVersionUID = 3850197110638244786L;
	public static final int BOARD_WIDTH = 550;
	public static final int BOARD_HEIGHT = 550;

	public SensorBoard(){
		setVisible(true);
	}

	public void draw(Graphics gfx, Sensor[] sensors, Vector<Neighbour> neighbours){
		BufferedImage buffer = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT-5, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = buffer.getGraphics();
		
		graphics.setColor(Color.WHITE);
		graphics.fillRect(1,1,BOARD_WIDTH,BOARD_HEIGHT);

		//Just testing if all neighbours are discovered
		graphics.setColor(Color.RED);
		for (Neighbour n : neighbours) {
			
			if(!n.isConnected()) graphics.setColor(Color.gray);
			else graphics.setColor(Color.red);
			
			Point p1 = n.getSensor1Pos();
			Point p2 = n.getSensor2Pos();
			graphics.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
		
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
