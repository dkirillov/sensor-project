import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SectorDialog extends JPanel{

	private static final long serialVersionUID = -6091239265498976732L;
	
	JSlider sensorRange;
	JTextField sRange;
	int range;
	int lower = 3;
	int upper = 12;
	MainThread game;
	JFrame frame;
	
	/**
	 * Constructor
	 * @param game	The MainThread that this control modifies
	 */
	public SectorDialog(final MainThread game){
		//super(game.frame);
		this.frame = game.wC;
		this.game = game;
		//setSize (x,y);
		//JButton OK, cancel;
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(layout);
		setLocation(frame.getX()+frame.getWidth(),frame.getY());
		
		range = game.k;
		
		//x label
		constraints.gridx = 0; constraints.gridy = 0;
		constraints.gridwidth = 3; constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1; constraints.weighty = 1;
		constraints.insets = new Insets(5, 10, 0, 0);
		JLabel label = new JLabel("Number of sectors ("+lower+" - "+ upper+  ")  :");
		layout.setConstraints(label, constraints);
		add(label);

		//x position
		sensorRange = new JSlider(JSlider.HORIZONTAL, lower, upper,range);
		constraints.gridx = 1; constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.insets = new Insets(5, 5, 0, 10);
		layout.setConstraints(sensorRange, constraints);
		add(sensorRange);

		//x text
		sRange = new JTextField(5);
		sRange.setText(String.valueOf(sensorRange.getValue()));
		sRange.setEditable(false);
		constraints.gridx=0; constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(5, 10, 0, 0);
		layout.setConstraints(sRange, constraints);
		add(sRange);

	
		
		sensorRange.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				sRange.setText(String.valueOf(source.getValue()));
				if (!source.getValueIsAdjusting()){
					range = source.getValue();
					game.k = range;
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