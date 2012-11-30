import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;


public class MakeSectorGraph {
	int n = 40;
	
	String top = "<html>\n"+
  "<head>\n"+
    "<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>\n"+
    "<script type=\"text/javascript\">\n"+
      "google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});\n"+
      "google.setOnLoadCallback(drawChart);\n"+
      "function drawChart() {\n"+
        "var data = google.visualization.arrayToDataTable([\n"+
          "['Time', 'ARA', 'RSRMA','RSRMA\\''],\n";
	
	//for testing, soon deprecated
	String middle = ""+
	          "['2004',  1000,      400, 200],\n"+
	          "['2005',  1170,      460, 100],\n"+
	          "['2006',  660,       1120, 400],\n"+
	          "['2007',  1030,      540, 588]\n";
	
	String bottom1 = ""+
			 "]);\n"+

		        "var options = {\n"+
		          "title: 'Algorithm Performance for n = ";
	String bottom2 = "',\n"+
		          "hAxis: {title: 'Rounds', titleTextStyle: {color: 'red'}},\n"+
		          "vAxis: {title: 'Percentage of Sensors Discovered', titleTextStyle: {color: 'red'}}\n"+

		        "};\n"+

		        "var chart = new google.visualization.LineChart(document.getElementById('chart_div'));\n"+
		        "chart.draw(data, options);\n"+
		      "}\n"+
		    "</script>\n"+
		  "</head>\n"+
		  "<body>\n"+
		    "<div id=\"chart_div\" style=\"width: 900px; height: 500px;\"></div>\n"+
		  "</body>\n"+
		"</html>\n";
	
	
	BufferedWriter graph;
	
	public MakeSectorGraph(){
		
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
		String middle = null;
		try {
			middle = makeMiddle();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			// Create file 
			FileWriter fstream = new FileWriter("graph"+n+".html");
			graph = new BufferedWriter(fstream);
			graph.write(top);
			graph.write(middle);
			graph.write(bottom1);
			//writing in the number of sensors in the title
			graph.write(Integer.toString(n));
			graph.write(bottom2);
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}finally{
			try {
				graph.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public String makeMiddle() throws IOException{
		StringBuffer middle = new StringBuffer();
		/*
		 * 	String middle = ""+
	          "['2004',  1000,      400, 200],\n"+
	          "['2005',  1170,      460, 100],\n"+
	          "['2006',  660,       1120, 400],\n"+
	          "['2007',  1030,      540, 588]\n";
	          
	          This is your target
		 */
		FileReader ARAfile = null;
		FileReader RSRMAfile = null;
		FileReader RSRMApfile = null;

		try {
			ARAfile = new FileReader("statARA"+n+".txt");
			RSRMAfile = new FileReader("statRSRMA"+n+".txt");
			RSRMApfile= new FileReader("statRSRMA'"+n+".txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader ARA = new BufferedReader(ARAfile);
		BufferedReader RSRMA = new BufferedReader(RSRMAfile);
		BufferedReader RSRMAp = new BufferedReader(RSRMApfile);
		int count = 0;
		String str;
		boolean moreARA = true, moreRSRMA = true, moreRSRMAp = true;
		while (true){
			count ++;
			middle.append("["+count+",");
			/*
			 * The stats files are not always the same length.
			 * If there are no more lines presumably that one had
			 * discovered all the sensors, so we assign it 1
			 */
			//ARA
			if (moreARA){
				str = ARA.readLine();
				if (str == null){
					moreARA = false;
					str = "0";
				}
				
			}else{
				str = "0";
			}
			middle.append(str+",");
			
			//RSRMA
			if (moreRSRMA){
				str = RSRMA.readLine();
				if (str == null){
					moreRSRMA = false;
					str = "0";
				}
			}else{
				str = "0";
			}
			middle.append(str+",");
			
			//RSRMAp
			if (moreRSRMAp){
				str = RSRMAp.readLine();
				if (str == null){
					moreRSRMAp = false;
					str = "0";
				}
			}else{
				str = "0";
			}
			middle.append(str+"],\n");
			if ((!moreARA)&&(!moreRSRMA)&&(!moreRSRMAp)){
				break;
			}
		}
		//delete the last comma
		middle.deleteCharAt(middle.length()-2);
		return middle.toString();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MakeSectorGraph();

	}

}
