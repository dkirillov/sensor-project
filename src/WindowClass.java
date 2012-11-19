import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.JButton;

public class WindowClass extends JFrame implements ActionListener{

	private static final long serialVersionUID = 127373704609559805L;
	
	SensorBoard sB;
	MainThread mT;
	SpeedDialog sd;
	RangeDialog rd;
	OutputDialog od;
	JButton play, newgame, clear;
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
		makeMenu();
		setVisible(true);
		mT = new MainThread(this);
		sd = new SpeedDialog(mT);
		rd = new RangeDialog(mT);

		JPanel panel = new JPanel();
		panel.add(sd);
		panel.add(rd);
		getContentPane().add(panel, BorderLayout.NORTH);
		od = new OutputDialog(this);
		mT.run();
	}
	
	public void makeMenu(){
		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("Algorithm");
		JRadioButtonMenuItem h1 = new JRadioButtonMenuItem("ARA");
		JRadioButtonMenuItem h2 = new JRadioButtonMenuItem("RSRMA");
		JRadioButtonMenuItem h3 = new JRadioButtonMenuItem("RSRMA'");
		h1.setSelected(true);
		ButtonGroup g = new ButtonGroup();
		g.add(h1);
		g.add(h2);
		g.add(h3);
		menu.add(h1);
		menu.add(h2);
		menu.add(h3);
		mb.add(menu);
		h1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}		
		});
		h2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}		
		});
		h3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}		
		});
		
		mb.add(menu);
		setJMenuBar(mb);
	}
	
	public JPanel buttonPanel(){
		JPanel panel = new JPanel(new BorderLayout());
		play = new JButton("Start");
		play.setActionCommand("play");
		play.addActionListener(this);
		newgame = new JButton("New");
		newgame.setActionCommand("newgame");
		newgame.addActionListener(this);
		clear = new JButton("Clear Output");
		clear.setActionCommand("clear");
		clear.addActionListener(this);
		JPanel innerpanel = new JPanel();
		innerpanel.add(play);
		innerpanel.add(newgame);
		innerpanel.add(clear);
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
			play.setText("Start");
		}else if (e.getActionCommand().equals("clear")){
			od.setText("");
		}

	}
	
	public void output(String message){
		od.append(message);
	}


	public static void main(String args[]) {
		new WindowClass();
	}
}
