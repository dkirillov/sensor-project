import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JPanel;


public class SensorBoard extends JPanel {
	public SensorBoard(){
		setVisible(true);
	}
	public void draw(Graphics gfx, Sensor[] sensors){
		BufferedImage buffer = new BufferedImage(598, 438, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = buffer.getGraphics();
		
		graphics.setColor(Color.WHITE);
		graphics.fillRect(1,1,598,438);

		for(int x = 0 ;x <sensors.length;x++){
			sensors[x].draw(graphics);
		}
		
		Graphics2D g2 = (Graphics2D) gfx;
		g2.drawImage(buffer, 0, 0, null);
	}
}
