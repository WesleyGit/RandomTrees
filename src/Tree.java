import java.util.ArrayList;
import java.util.Collections;


public class Tree {

	public enum NodeType {
		Real, Boolean
	}
	
	private Dataset dataset;
	private int m;
	private Node root;
	private ArrayList<Integer> attributes;
	
	public Tree(Dataset dataset, int[] samples, int m) {
		this.dataset = dataset;
		this.m = m;
		root = new Node(samples);
		attributes = new ArrayList<Integer>();
		for (int i = 0 ; i < dataset.attributes(); i++) {
			attributes.add(i);
		}
		buildTree(root);
	}
	
	private void buildTree(Node node) {
		int pos = 0, neg = 0;
		for(int n : node.samples) {
			if (dataset.getAds()[n])
				pos++;
			else
				neg++;
		}
		double gini = giniImpurity(pos, neg);
		if (gini == 0) {
			node.leaf = true;
			node.ad = neg == 0;
		}
		else {
			Collections.shuffle(attributes);
			for (int i = 0; i < m; i++) {
				if (attributes.get(i) < dataset.realcount()) {
					
				}
				else {
					int negL = 0, posL = 0, negR = 0, posR = 0;
					for(int n : node.samples) {
						if (dataset.getBooleans()[n][i-dataset.realcount()]) {
							if (dataset.getAds()[n])
								posL++;
							else
								negL++;
						}
						else {
							if (dataset.getAds()[n])
								posR++;
							else
								negR++;
						}
					}
					double giniL = giniImpurity(posL, negL);
					double giniR = giniImpurity(posR, negR);
				}
			}
		}
	}

	private double giniImpurity(int pos, int neg) {
		return 1 - (1.0*pos)/(pos + neg) - (1.0*neg)/(pos + neg);
	}

	public class Node {
		
		private Node left;
		private Node right;
		private double threshold;
		private NodeType type;
		private int attr;
		private int[] samples;
		
		private boolean leaf;
		private boolean ad;
		
		public Node(int[] s) {
			samples = s;
		}
		
	}
	
	public boolean classify(double[] doubles, boolean[] booleans) {
		
		return false;
	}

}
