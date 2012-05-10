import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;


public class Forest {

	public Instance[] dataset;
	public Tree[] forest;

	public Forest(Instance[] dataset) {
		this.dataset = dataset;
	}

	public void growTrees(int treecount, int m) {
		forest = new Tree[treecount];
		Random r = new Random();
		for (int i = 0; i < treecount; i++) {
			int[] samples = new int[Main.INSTANCES];
			for (int j = 0; j < Main.INSTANCES; j++) {
				samples[j] = r.nextInt(dataset.length);
			}
			int[] attributes = new int[m];
			for (int j = 0; j < Main.ATTRIBUTES; j++) {
				
			}
			forest[i] = new Tree(dataset, samples, attributes);
		}
	}
	
	public boolean classify(Instance inst) {
		int upvotes = 0;
		for (int i = 0; i < forest.length; i++)
			if (forest[i].classify(inst))
				upvotes++;
		return upvotes >= forest.length/2.0;
	}
	
}
