import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;




public class MakeAverage {
	
	public static void main(String args[]){
		try {
			new MakeAverage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	int n = 0;
	String k, tests;
	int numtests = 0;
	
	public MakeAverage() throws IOException{
		String s = (String)JOptionPane.showInputDialog(
				null,
				"How many sensors?",
				"Customized Dialog",
				JOptionPane.PLAIN_MESSAGE,
				null,null, null);

		if ((s == "")||(s==null)){
			System.exit(0);
		}
		n = Integer.parseInt(s);
		if (n == 0){
			System.exit(0);
		}

		k = (String)JOptionPane.showInputDialog(
				null,
				"k value?",
				"Customized Dialog",
				JOptionPane.PLAIN_MESSAGE,
				null,null, null);

		tests = (String)JOptionPane.showInputDialog(
				null,
				"Number of tests?",
				"Customized Dialog",
				JOptionPane.PLAIN_MESSAGE,
				null,null, null);
		doit("ARA");
		doit("RSRMA");
		doit("RSRMA'");
		
		new MakeGraph(n, k);

	}

	public void doit(String algo) throws IOException{
		
		numtests = Integer.parseInt(tests);
		numtests --;
		String fs = System.getProperty("file.separator");
		String pre = "statfiles"+fs;
		String preav = "average"+fs;
		FileReader ARAfile[] = new FileReader[numtests];
		BufferedReader ARAreader[] = new BufferedReader[numtests];
		FileWriter ARAoutput = null;
		boolean moreARA[] = new boolean[numtests];


		try {
			for (int i = 0; i < numtests; i++){
				ARAfile[i] = new FileReader(pre+"Stat__S-"+n+"_T-"+(i+1)+"_A-"+algo+"_K"+k+".txt");
				moreARA[i] = true;
			}
			ARAoutput = new FileWriter(preav+"Stat__S-"+n+"_AVERAGE_A-"+algo+"_K"+k+".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < numtests; i++){
			ARAreader[i] = new BufferedReader(ARAfile[i]);
		}
		BufferedWriter ARAwriter = new BufferedWriter(ARAoutput);
		String read = null;
		while(true){
			double total =0;
			for (int i = 0; i < numtests; i ++){
				if (!moreARA[i]){
					read = "1.0";
				}else{
					read  = ARAreader[i].readLine();
					if (read == null){
						moreARA[i] = false;
						read = "1.0";
					}
				}
				total += Double.parseDouble(read);
			}
			total /= numtests;
			ARAwriter.write(Double.toString(total)+"\n");
			boolean check = false;
			for (int i = 0; i < numtests; i ++){
				if (moreARA[i])check = true;
			}
			if (!check) break;
		}
		for (int i = 0; i < numtests; i ++){
			ARAreader[i].close();
		}
		ARAwriter.close();
	}
}
