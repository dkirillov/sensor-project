/**
 * @author Danil Kirillov, Darryl Hill, Wesley Lawrence.
 */
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * A class that serves as an output dialog for the GUI.
 */
public class OutputDialog extends JFrame {

	private static final long serialVersionUID = -3235644261772098381L;
	
	JTextArea out;
	JScrollPane scroll;
	
	/**
	 * Initializes the output dialog.
	 * @param parent	The parent of this dialog.
	 */
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
	
	/**
	 * Sets the text of the output dialog.
	 * @param text	The text that will be set.
	 */
	public void setText(String text){
		out.setText(text);
		out.setCaretPosition(out.getDocument().getLength());
	}
	
	/**
	 * Appends text to the output dialog.
	 * @param text	The text that will be appended.
	 */
	public void append(String text){
		out.append(text);
		out.setCaretPosition(out.getDocument().getLength());
	}
}
