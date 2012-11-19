import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class OutputDialog extends JFrame {

	private static final long serialVersionUID = -3235644261772098381L;
	
	JTextArea out;
	JScrollPane scroll;
	
	public OutputDialog(JFrame parent){
		super();
		setSize(400,400);
		this.setLocation(parent.getX()+parent.getWidth()+5, parent.getY());
		out = new JTextArea();
		out.setEditable(false);
		scroll = new JScrollPane(out);
		add(scroll);
		setVisible(true);
	}
	
	public void setText(String text){
		out.setText(text);
		out.setCaretPosition(out.getDocument().getLength());
	}
	
	public void append(String text){
		out.append(text);
		out.setCaretPosition(out.getDocument().getLength());
	}
}
