import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sensor {
	private int sectors;
	private int current_sector;
	private Point p;
	private int wait_time;
	private int max_wait_time;
	private Color c; // Delete later.
	//Maybe do a matrix like described in algorithms, facebook example...
	List <Neighbour> neighbours;

	public Sensor(int x, int y) {
		neighbours = new ArrayList<Neighbour>();
		sectors = (new Random().nextInt(9)) + 3;
		//max_wait_time = (new Random().nextInt(990)) + 10;
		max_wait_time = 100;
		current_sector = new Random().nextInt(sectors);
		wait_time = 1;
		p = new Point(x, y);
		// p = new Point(200,200);
		c = new Color(new Random().nextInt(255), new Random().nextInt(255),
				new Random().nextInt(255), 50);
	}

	public void update() {
		wait_time--;
		if (wait_time == 0) {
			// System.out.println("currenct_sector: " +
			// currenct_sector+" sectors: " + sectors);
			current_sector += current_sector >= sectors - 1 ? ((sectors - 1) * -1)
					: 1;
			wait_time = max_wait_time;
		}
	}

	public void draw(Graphics gfx) {
		int multi_deg = (360) / sectors;

		gfx.setColor(c);
		gfx.fillArc(p.x - 55, p.y - 55, 115, 115, multi_deg * current_sector,
				multi_deg);

		gfx.setColor(Color.BLACK);
		gfx.fillOval(p.x, p.y, 5, 5);
		gfx.setColor(new Color(0, 0, 0, 25));
		gfx.drawOval(p.x - 55, p.y - 55, 115, 115);
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

	public boolean inRange(Point p2) {
		return (p2.x >= p.x - 53 && p2.x <= p.x + 53)
				&& (p2.y >= p.y - 53 && p2.y < p.y + 53);
	}

	public int inSector(Point p2) {
		double d_x = (p2.x - p.x);
		double d_y = (p2.y - p.y) * -1;
		//System.out.println("d_x: " + d_x + " d_y: " + d_y);
		double degree = Math.atan(Math.abs(d_x / d_y)) * (180.0 / Math.PI);

		//System.out.println("computed degree: " + degree);

		if(d_x>0&&d_y>0){
			degree = (90-degree);
		}else if (d_x < 0 && d_y > 0) {
			degree += 90;
		} else if (d_x < 0 && d_y < 0) {
			degree = 180 + (90-degree);
		} else if (d_x > 0 && d_y < 0) {
			degree += 270;
		}

		//System.out.println("modded degree: " + degree);
		//System.out.println("sector chunks: " + (360.0 / sectors));
		int s = (int) (degree / (360.0 / sectors));

		return s;
	}
	public int currentSector(){
		return current_sector;
	}
	public Point getPoint() {
		return p;
	}
	public List<Neighbour> getNeighbours(){
		return neighbours;
	}
	
	public void addNeighbour(Neighbour n){
		neighbours.add(n);	
	}	
}
