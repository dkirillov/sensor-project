import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

public class WindowClass extends JFrame {

	SensorBoard sB;
	MainThread mT;
	SpeedDialog sd;

	public WindowClass() {
		setLayout(null);
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
		getContentPane().add(sd, BorderLayout.NORTH);
		setVisible(true);
		mT.run();
		
		

	}

	public static void main(String args[]) {
		new WindowClass();
	}
}
