import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Sensor {
	private int sectors;
	private int currenct_sector;
	private Point p;
	private int wait_time;
	private Color c; // Delete later.

	public Sensor(int x,int y) {
		sectors = (new Random().nextInt(9)) + 3;
		currenct_sector = new Random().nextInt(sectors);
		wait_time = 1000;
		p = new Point(x,y);
		//p = new Point(200,200);
		c = new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255),50);
	}

	public void update(){
		wait_time--;
		if (wait_time==0){
			System.out.println("currenct_sector: " + currenct_sector+" sectors: " + sectors);
			currenct_sector += currenct_sector >= sectors-1 ? ((sectors-1)*-1) : 1;
			wait_time = 1000;
		}
	}
	
	public void draw(Graphics gfx) {
		int multi_deg = (360) / sectors;
		
		gfx.setColor(c);
		gfx.fillArc(p.x - 55, p.y - 55, 115, 115, multi_deg * currenct_sector, multi_deg);

		gfx.setColor(Color.BLACK);
		gfx.fillOval(p.x, p.y, 5, 5);
		gfx.setColor(new Color(0,0,0,25));
		gfx.drawOval(p.x - 55, p.y - 55, 115, 115);

		/*
		double multi_rad = (Math.PI * 2) / sectors;
		gfx.setColor(Color.RED);
		for (int x = 0; x < sectors; x++) {
			int offsetX = (int) (Math.cos(multi_rad * x) * 60);
			int offsetY = (int) (Math.sin(multi_rad * x) * 60);
			System.out.println("x: " + x);
			System.out.println("sectors: " + sectors);
			System.out.println("multiples: " + multi_rad);
			System.out.println("offsetX: " + offsetX + "\noffsetY: " + offsetY
					+ "\n");
			gfx.drawLine(p.x + 2, p.y + 2, p.x + offsetX, p.y + offsetY);
		}*/
	}
}
