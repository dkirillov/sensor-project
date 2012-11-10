/**
 * @author Danil Kirillov, Darryl Hill, Wesley Lawrence.
 */
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SpeedDialog extends JPanel{
	JSlider sensorSpeed;
	JTextField sSpeed;
	int speed;
	int lower = 0;
	int upper = 25;
	MainThread game;
	JFrame frame;
	
	public SpeedDialog(final MainThread game){
		//super(game.frame);
		this.frame = game.wC;
		this.game = game;
		//setSize (x,y);
		//JButton OK, cancel;
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(layout);
		setLocation(frame.getX()+frame.getWidth(),frame.getY());
		
		speed = 25 -game.speed;
		
		//x label
		constraints.gridx = 0; constraints.gridy = 0;
		constraints.gridwidth = 3; constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1; constraints.weighty = 1;
		constraints.insets = new Insets(5, 10, 0, 0);
		JLabel label = new JLabel("Sensor speed ("+lower+" - "+ upper+  ")  :");
		layout.setConstraints(label, constraints);
		add(label);

		//x position
		sensorSpeed = new JSlider(JSlider.HORIZONTAL, lower, upper,speed);
		constraints.gridx = 1; constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.insets = new Insets(5, 5, 0, 10);
		layout.setConstraints(sensorSpeed, constraints);
		add(sensorSpeed);

		//x text
		sSpeed = new JTextField(5);
		sSpeed.setText(String.valueOf(sensorSpeed.getValue()));
		sSpeed.setEditable(false);
		constraints.gridx=0; constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(5, 10, 0, 0);
		layout.setConstraints(sSpeed, constraints);
		add(sSpeed);

	
		
		sensorSpeed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				sSpeed.setText(String.valueOf(source.getValue()));
				if (!source.getValueIsAdjusting()){
					speed = source.getValue();
					game.speed = 25-speed;
				}
			}
		});
		
		setVisible(true);
	}

	public void OKButtonClicked(){
		//Debug.debug("Setting speed at: " + speed, "speed");
		//dispose();
	}
	
	public void cancelButtonClicked(){
		//dispose();
	}
}