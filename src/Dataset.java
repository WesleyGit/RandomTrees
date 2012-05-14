
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

	public int attributes() {
		return doubles.length + booleans.length;
	}

	public Integer realcount() {
		if (instances() > 0)
			return doubles[0].length;
		return 0;
	}
}
