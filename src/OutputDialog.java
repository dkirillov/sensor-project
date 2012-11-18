

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class OutputDialog extends JFrame {

	JTextArea out;
	public OutputDialog(JFrame parent){
		super();
		setSize(400,400);
		this.setLocation(parent.getX()+parent.getWidth()+5, parent.getY());
		out = new JTextArea();
		add(new JScrollPane(out));
		setVisible(true);
	}
	
	public void setText(String text){
		out.setText(text);
	}
	
	public void append(String text){
		out.append(text);
	}
}
