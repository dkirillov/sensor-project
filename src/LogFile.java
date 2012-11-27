import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class LogFile {

	BufferedWriter logOut;
	BufferedWriter statOut;
	boolean open = false;
	int logged = 0;
	
	
	public LogFile(){
		
	}
	
	public void open(int testNumber, String algo, int sensors, int k){
		logged ++;
		try{
			// Create file 
			String fs = System.getProperty("file.separator");
			FileWriter fstream = new FileWriter("logfiles"+fs+"Log__S-"+sensors+"_T-"+testNumber+"_A-"+algo+"_K-"+k+".txt");
			//new FileWriter("logfiles"+fs+"Log"+testNumber+"-sectors="+k+"-sensors="+sensors+algo+".txt");
			//SensorCount - TestNumber - Algo - SectorCount
			
			logOut = new BufferedWriter(fstream);
			fstream = new FileWriter("statfiles"+fs+"Stat__S-"+sensors+"_T-"+testNumber+"_A-"+algo+"_K"+k+".txt");
			//new FileWriter("statfiles"+fs+"statk="+k+"n="+sensors+algo+".txt");
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
