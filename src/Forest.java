import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;


public class Forest {

	public Dataset dataset;
	public Tree[] forest;

	public Forest(Dataset dataset) {
		this.dataset = dataset;
	}

	public void growTrees(int treecount, int m) {
		forest = new Tree[treecount];
		Random r = new Random();
		for (int i = 0; i < treecount; i++) {
			int[] samples = new int[dataset.instances()];
			for (int j = 0; j < dataset.instances(); j++) {
				samples[j] = r.nextInt(dataset.instances());
			}
			forest[i] = new Tree(dataset, samples, m);
		}
	}
	
	public boolean classify(double[] doubles, boolean[] booleans) {
		int upvotes = 0;
		for (int i = 0; i < forest.length; i++)
			if (forest[i].classify(doubles, booleans))
				upvotes++;
		return upvotes >= forest.length/2.0;
	}
	
}
