import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * A JFrame with a JTextArea in a scrollpane to 
 * provide a simple way to display data
 *
 */
public class OutputDialog extends JFrame {

	private static final long serialVersionUID = -3235644261772098381L;
	
	JTextArea out;
	JScrollPane scroll;
	
	/**
	 * 
	 * @param parent	the parent window that this dialog gets placed
	 * beside
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
	 * Set the text in the output window
	 * @param text	the text to be outputted
	 */
	public void setText(String text){
		out.setText(text);
		out.setCaretPosition(out.getDocument().getLength());
	}
	
	/**
	 * Append the text in the output window
	 * @param text the text to be appended
	 */
	public void append(String text){
		out.append(text);
		out.setCaretPosition(out.getDocument().getLength());
	}
}
