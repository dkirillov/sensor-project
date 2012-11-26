import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class BarGraph {
	String bartop = ""+
			"<html>\n"+
	  "<head>\n"+
	    "<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>\n"+
	    "<script type=\"text/javascript\">\n"+
	      "google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});\n"+
	      "google.setOnLoadCallback(drawChart);\n"+
	      "function drawChart() {\n"+
	        "var data = google.visualization.arrayToDataTable([\n"+
	          "['Time', 'ARA', 'RSRMA','RSRMA\\''],\n";
	
	String barbottom = ""+
			"]);\n"+
	        "var options = {\n"+
	          "title: 'Algorithm Performance for n = ',\n"+
	          "hAxis: {title: 'Rounds', titleTextStyle: {color: 'red'}}\n"+
	        "};\n"+

	        "var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));\n"+
	        "chart.draw(data, options);\n"+
	      "}\n"+
	    "</script>\n"+
	 "</head>\n"+
	  "<body>\n"+
	    "<div id=\"chart_div\" style=\"width: 900px; height: 500px;\"></div>\n"+
	  "</body>\n"+
	"</html>\n";
	
	BufferedWriter bargraph;
	
	public BarGraph(){
		//write the bar graph
		FileWriter barstream;
		try {
			barstream = new FileWriter("bargraph.html");
			bargraph = new BufferedWriter(barstream);
			bargraph.write(bartop);
			//bargraph.write(middle);
			bargraph.write(barbottom);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				bargraph.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
    
	public static void main(String[] args){
		
	}
}