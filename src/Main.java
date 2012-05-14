import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.Scanner;

public class Main {

	public static final String datasetfile = "ad.data"; 
	public static final int INSTANCES = 3279;
	public static final int REALS = 3;
	public static final int BOOLEANS = 1558-REALS;
	
	public static void main(String[] args) throws FileNotFoundException {
		FileReader ifile = new FileReader(datasetfile);
		Scanner scanner = new Scanner(ifile);
		long starttime = (new Date()).getTime();
		boolean[][] bools = new boolean[INSTANCES][BOOLEANS];
		double[][] reals = new double[INSTANCES][REALS];
		boolean[] ads = new boolean[INSTANCES];
		for (int i = 0; scanner.hasNextLine(); i++) {
			Scanner attscanner = new Scanner(scanner.nextLine());
			attscanner.useDelimiter(",");
			for (int j = 0; j < REALS; j++) {
				String dstring = attscanner.next();
				if (!dstring.contains("?"))
					reals[i][j] = Double.parseDouble(dstring);
				else
					reals[i][j] = -1;
			}
			for (int j = 0; j < BOOLEANS; j++)
				bools[i][j] = Boolean.parseBoolean(attscanner.next());
			ads[i] = attscanner.next() == "ad";
			System.out.println(i);
		}
		System.out.println("File reading done. Read dataset succesfully. Took "+((new Date()).getTime()-starttime)+"ms.");
		Forest forest = new Forest(new Dataset(reals, bools, ads));
	}
	

}
