

public class Neighbour {
	private int sector;
	private int neighbour_num;
	private boolean connected;

	public Neighbour (int s, int n_n){
		this.sector = s;
		this.neighbour_num = n_n;
		connected = false;
	}
	
	public int getSector() {
		return sector;
	}
	public int getNeighbour_num() {
		return neighbour_num;
	}
	
	public void connect(){
		connected = true;
	}
	public boolean isConnected(){
		return connected;
	}
}
