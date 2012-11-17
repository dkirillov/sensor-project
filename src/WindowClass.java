/**
 * @author Danil Kirillov, Darryl Hill, Wesley Lawrence.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

/**
 * The main window class of the program.
 */
public class WindowClass extends JFrame {
	private SensorBoard sB;				//The sensor board to draw on.
	private MainThread mT;				//The main thread that updates everything.
	private SpeedDialog sd;				//The speed slider, adjusts how fast the program runs (sleeps in thread).

	/**
	 * Constructor, sets up the window.
	 */
	public WindowClass() {
		setSize(800, 640);
		setMinimumSize(getSize());
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mT.stop();
				System.exit(0);
			}
		});

		sB = new SensorBoard();
		sB.setSize(600, 440);
		sB.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(sB);
		
		mT = new MainThread(this);
		sd = new SpeedDialog(mT);		
		getContentPane().add(sd, BorderLayout.SOUTH);
		
		setVisible(true);
		
		mT.start();
		
	}

	/**
	 * Draws the given array of sensors on the sensor board.
	 * @param sensors The array of sensors to draw.
	 */
	public void drawOnBoard(Sensor[] sensors,int totalNeighbours){
		sB.draw(sensors,totalNeighbours);
	}

	public static void main(String args[]) {
		new WindowClass();
	}
}
