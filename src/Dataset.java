
public class Dataset {

	private double[][] doubles;
	private boolean[][] booleans;
	private boolean[] ads;
	
	public Dataset(double[][] doubles, boolean[][] booleans, boolean[] ads) {
		this.doubles = doubles;
		this.booleans = booleans;
		this.ads = ads;
	}
	
	public double[][] getDoubles() {
		return doubles;
	}
	
	public boolean[][] getBooleans() {
		return booleans;
	}
	
	public boolean[] getAds() {
		return ads;
	}
	
	public int instances() {
		return doubles.length;
	}
}
