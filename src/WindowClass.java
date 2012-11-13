import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.JButton;

public class WindowClass extends JFrame implements ActionListener{

	SensorBoard sB;
	MainThread mT;
	SpeedDialog sd;
	JButton play, newgame;
	JTextField numSensors;
	JLabel message;
	boolean restart = false;

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
		

		add(buttonPanel(), BorderLayout.SOUTH);
		setVisible(true);
		mT = new MainThread(this);
		sd = new SpeedDialog(mT);
		getContentPane().add(sd, BorderLayout.NORTH);
		mT.run();
	}
	
	public JPanel buttonPanel(){
		JPanel panel = new JPanel(new BorderLayout());
		play = new JButton("Start");
		play.setActionCommand("play");
		play.addActionListener(this);
		newgame = new JButton("New");
		newgame.setActionCommand("newgame");
		newgame.addActionListener(this);
		JPanel innerpanel = new JPanel();
		innerpanel.add(play);
		innerpanel.add(newgame);
		panel.add(innerpanel, BorderLayout.SOUTH);
		JPanel centerpanel = new JPanel();
		centerpanel.setLayout(new GridLayout(0,2));
		numSensors = new JTextField("3");
		JLabel label = new JLabel("# of sensors");
		label.setHorizontalTextPosition(SwingConstants.RIGHT);

		centerpanel.add(label);
		centerpanel.add(numSensors);
		panel.add(centerpanel, BorderLayout.CENTER);
		message = new JLabel("Sensor Sim");
		panel.add(message, BorderLayout.NORTH);
		return panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("play")){
			if (!mT.isKeepRunning()){
				play.setText("Stop");
			}else{
				play.setText("Start");
			}
			mT.setKeepRunning(!mT.isKeepRunning());
			//Debug.debug("GUI: playing = "+control.playing);
			
		}else if (e.getActionCommand().equals("newgame")){
			mT.setKeepRunning(false);
			mT.restart = true;
			play.setText("Play");
		}

	}


	public static void main(String args[]) {
		new WindowClass();
	}
}
