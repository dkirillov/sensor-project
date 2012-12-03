/**
 * @author	Danil Kirillov, Darryl Hill, Wesley Lawrence.
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class in charge of outputting to a log file. 
 */
public class LogFile {

	BufferedWriter logOut;		//Output to a log file.
	BufferedWriter statOut;		//Output to a statistics file.
	boolean open = false;		//If the output is currently opened.

	/**
	 * Opens the output files.
	 * @param testNumber	The current test that it is on.
	 * @param algo			The algorithm that the output is for.
	 * @param sensors		The number of sensors for the current output.
	 * @param k				The number of sectors per sensor.
	 */
	public void open(int testNumber, String algo, int sensors, int k){
		try{
			// Create file 
			File logdir = new File("logfiles");
			File statdir = new File("statfiles");
			logdir.mkdir();
			statdir.mkdir();
			String fs = System.getProperty("file.separator");			
			FileWriter fstream = new FileWriter("logfiles"+fs+"Log__S-"+sensors+"_T-"+testNumber+"_A-"+algo+"_K-"+k+".txt");		
			logOut = new BufferedWriter(fstream);
			fstream = new FileWriter("statfiles"+fs+"Stat__S-"+sensors+"_T-"+testNumber+"_A-"+algo+"_K"+k+".txt");
			statOut = new BufferedWriter(fstream);
			open = true;
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		
	}
	/**
	 * Writes a string to the log file.
	 * @param s	The string which will be written.
	 */
	public void logWrite(String s){
		try {
			logOut.write(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Writes the string to a statistics file.
	 * @param s	The string which will be written.
	 */
	public void statWrite(String s) {
		try {
			statOut.write(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Closes the outputs.
	 */
	public void close(){
		if (!open) return;
		try {
			logOut.close();
			statOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
