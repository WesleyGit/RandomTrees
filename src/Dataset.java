import java.util.Arrays;


public class Dataset {

	private double[][] doubles;
	private double[][] sortedIntervals;
	private boolean[][] booleans;
	private boolean[] ads;
	
	public Dataset(double[][] doubles, boolean[][] booleans, boolean[] ads) {
		this.doubles = doubles;
		this.booleans = booleans;
		this.ads = ads;
		if (doubles.length > 0) {
			sortedIntervals = new double[doubles[0].length][doubles.length];
			for (int i = 0; i < doubles[0].length; i++) {
				for (int j = 0; j < doubles.length; j++)
					sortedIntervals[i][j] = doubles[j][i];
				Arrays.sort(sortedIntervals[i]);
			}
		}
	}
	
	public double[][] getDoubles() {
		return doubles;
	}
	
	public boolean[][] getBooleans() {
		return booleans;
	}
	
	public double[] getIntervals(int i) {
		return sortedIntervals[i];
	}
	
	public boolean[] getAds() {
		return ads;
	}
	
	public int instances() {
		return doubles.length;
	}

	public int attributes() {
		if (instances() > 0)
			return doubles[0].length + booleans[0].length;
		return 0;
	}

	public Integer reals() {
		if (instances() > 0)
			return doubles[0].length;
		return 0;
	}
}
