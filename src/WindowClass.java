import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

public class WindowClass extends JFrame {

	SensorBoard sB;
	MainThread mT;

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
		getContentPane().add(sB);

		setVisible(true);
		mT = new MainThread(this);
		mT.run();
	}

	public static void main(String args[]) {
		new WindowClass();
	}
}
