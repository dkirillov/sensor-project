/**
 * @author	Danil Kirillov, Darryl Hill, Wesley Lawrence.
 */
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 * A class used to output debugging messages.
 */
public class Debug {
	static String[] disable = {"bc","blockdetect"};
	static HashMap<String, DebugFrame> debugFrames = new HashMap<String, DebugFrame>();
	static public boolean DEBUG = false;
	static JTextPane console;
	static boolean useConsole = false;
	
	/**
	 * Enables/Disables debugging based on the boolean passed in.
	 * @param d	Enables debugging if d is true, disables if otherwise.
	 */
	public static void setDebug(boolean d){
		DEBUG = d;
	}
	/**
	 * Sets the console that the debug messages will be output to.
	 * @param con	The console that the messages will be output to.
	 */
	public static void setConsole(JTextPane con){
		console = con;
		useConsole = true;
	}
	
	/**
	 * Displays the message passed in.
	 * @param message	Message to display.
	 */
	public static void debug(String message) {
		if (DEBUG){
			if (useConsole){
				StyledDocument doc = console.getStyledDocument();
				try {
					doc.insertString(doc.getLength(), message+"\n", null);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}else{
				System.out.println(message);
			}
		}
	}
	/**
	 * Displays the character passed in.
	 * @param c	The character to be displayed.
	 */
	public static void debug(char c){
		if (DEBUG){
			if (useConsole){
				StyledDocument doc = console.getStyledDocument();
				try {
					doc.insertString(doc.getLength(), Character.toString(c), null);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}else{
				System.out.println(c);
			}
		}
	}
	/**
	 * Displays the message on a specific debug window.
	 * @param message	Message to be displayed.
	 * @param window	The debug window on which the message will be displayed. 
	 */
	public static void debug(String message, String window){
		//disable windows we don't need to avoid clutter
		for (int i = 0; i < disable.length; i++){
			if (disable[i].equals(window)){
				return;
			}
		}
		if (!DEBUG)return;
		if (debugFrames.containsKey(window)){
			debugFrames.get(window).addMessage(message);
		}else{
			DebugFrame frame = new DebugFrame(window);
			debugFrames.put(window, frame);
			frame.addMessage(message);
		}
	}
	/**
	 * A class for a debug window.
	 */
	private static class DebugFrame extends JFrame{
		
		private static final long serialVersionUID = 7930187426916348611L;
		
		JTextArea output;
		/**
		 * Constructor for the debug window, takes in the window's name.
		 * @param name The window's name.
		 */
		public DebugFrame(String name){
			super(name);
			setLocation(400, 0);
			setSize(400,400);
			output = new JTextArea();
			JScrollPane pane = new JScrollPane(output,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			add(pane);
			setVisible(true);
			
		}
		/**
		 * Displays the message on the window.
		 * @param message	The message to be displayed.
		 */
		public void addMessage(String message){
			output.append(message+"\n");
		}
	}
}

