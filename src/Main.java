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
		System.out.println("Reading file..");
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
			for (int j = 0; j < BOOLEANS; j++) {
				bools[i][j] = attscanner.next().equals("1");
			}
			ads[i] = attscanner.next().equals("ad.");
		}
		System.out.println("File reading done. Read dataset succesfully. Took "+((new Date()).getTime()-starttime)+"ms.");
		starttime = (new Date()).getTime();
		System.out.println("Planted seeds..");
		Dataset dataset = new Dataset(reals, bools, ads);
		Forest forest = new Forest(dataset);
		forest.growTrees(1, 500, 100);
		System.out.println("Trees fully grown! Took "+((new Date()).getTime()-starttime)+"ms.");
		starttime = (new Date()).getTime();
		System.out.println("Classifying training set..");
		int correct = 0;
		for (int i = 0; i < dataset.instances(); i++) {
			boolean res = forest.classify(dataset.getDoubles()[i], dataset.getBooleans()[i]);
			if (res == dataset.getAds()[i])
				correct++;
		}
		double percent = 100*(double)correct/dataset.instances();
		System.out.print("Classification done! "+(Math.round(percent*1000)/1000.0)+"% correct");
		System.out.println(" ("+correct+"/"+dataset.instances()+"). Took "+((new Date()).getTime()-starttime)+"ms.");
	}
	

}
