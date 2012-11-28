import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class LogFile {

	BufferedWriter logOut;
	BufferedWriter statOut;
	boolean open = false;
	int logged = 0;
	
	
	public LogFile(){
		
	}
	
	public void open(String algo, int sensors, int k){
		open(algo, sensors, k, -1);
	}
	
	public void open(String algo, int sensors, int k, int id){
		logged ++;
		String s;
		if (id==-1){
			s = "";
		}else{
			s = Integer.toString(id);
		}
		try{
			// Create file 
			File logdir = new File("logfiles");
			File statdir = new File("statfiles");
			logdir.mkdir();
			statdir.mkdir();
			String fs = System.getProperty("file.separator");
			FileWriter fstream = new FileWriter("logfiles"+fs+"log"+s+"k="+k+"n="+sensors+algo+".txt");
			logOut = new BufferedWriter(fstream);
			fstream = new FileWriter("statfiles"+fs+"stat"+s+"k="+k+"n="+sensors+algo+".txt");
			statOut = new BufferedWriter(fstream);
			open = true;
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		
	}
	
	public void logWrite(String s){
		try {
			logOut.write(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void statWrite(String s) {
		try {
			statOut.write(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
