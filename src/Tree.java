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
	private int maxdepth;
	
	public Tree(Dataset dataset, int[] samples, int m, int maxdepth) {
		this.dataset = dataset;
		this.m = m;
		this.maxdepth = maxdepth;
		root = new Node(samples);
		attributes = new ArrayList<Integer>();
		for (int i = 0 ; i < dataset.attributes(); i++) {
			attributes.add(i);
		}
		root.depth = 0;
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
		double entropy = entropy(pos, neg);
		if (entropy == 0) {
			node.leaf = true;
			node.ad = neg == 0;
		}
		else if (node.depth == maxdepth) {
			node.leaf = true;
			int count = 0;
			for(int n : node.samples) {
				if (dataset.getAds()[n])
					count++;
			}
			node.ad = count*2 > node.samples.length;
		}
		else {
			Collections.shuffle(attributes);
			int bestAtr = Integer.MIN_VALUE;
			int sizeL = 0;
			int sizeR = 0;
			double bestv = Double.NEGATIVE_INFINITY;
			double bestIG = Double.NEGATIVE_INFINITY;
			for (int i = 0; i < m; i++) {
				int at = attributes.get(i);
				if (at < dataset.reals()) {
					int sizeLLocal = 0;
					int sizeRLocal = 0;
					double bestAtrv = 0;
					double bestAtrIG = Double.NEGATIVE_INFINITY;
					double[] intervals = dataset.getIntervals(at);
					for (int j = intervals.length-1; j >= 0; j--) {
						int negL = 0, posL = 0, negR = 0, posR = 0;
						for(int n : node.samples) {
							if (dataset.getDoubles()[n][at] != -1) {
								if (dataset.getDoubles()[n][at] <= intervals[j]) {
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
						}
						double entropyL = entropy(posL, negL);
						double entropyR = entropy(posR, negR);
						double IG = entropy - ((posL + negL) * entropyL + (posR + negR) * entropyR) / (pos + neg);
						if (IG > bestAtrIG) {
							bestAtrv = intervals[j];
							bestAtrIG = IG;
							sizeLLocal = posL + negL; 
							sizeRLocal = posR + negR;
						}
					}
					if (bestAtrIG > bestIG) {
						bestv = bestAtrv;
						bestAtr = at;
						bestIG = bestAtrIG;
						sizeL = sizeLLocal;
						sizeR = sizeRLocal;
					}
				}
				else {
					int negL = 0, posL = 0, negR = 0, posR = 0;
					for(int n : node.samples) {
						if (dataset.getBooleans()[n][at-dataset.reals()]) {
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
					double entropyL = entropy(posL, negL);
					double entropyR = entropy(posR, negR);
					double IG = entropy - ((posL + negL) * entropyL + (posR + negR) * entropyR) / (pos + neg);
					if (IG > bestIG) {
						bestAtr = at;
						bestIG = IG;
						sizeL = posL + negL;
						sizeR = posR + negR;
					}
				}
			}
			node.attr = bestAtr;
			node.type = node.attr < dataset.reals() ? NodeType.Real : NodeType.Boolean;
			node.threshold = bestv;
			int[] samplesL = new int[sizeL];
			int[] samplesR = new int[sizeR];
			int l = 0, r = 0;
			if (node.type == NodeType.Boolean) {
				for(int n : node.samples) {
					if (dataset.getBooleans()[n][node.attr-dataset.reals()]) {
						samplesL[l] = n;
						l++;
					}
					else {
						samplesR[r] = n;
						r++;
					}
				}
			}
			else {
				for(int n : node.samples) {
					if (dataset.getDoubles()[n][node.attr] != -1) {
						if (dataset.getDoubles()[n][node.attr] <= node.threshold) {
							samplesL[l] = n;
							l++;
						}
						else {
							samplesR[r] = n;
							r++;
						}
					}
				}
			}
			node.left = new Node(samplesL);
			node.left.depth = node.depth + 1;
			node.right = new Node(samplesR);
			node.right.depth = node.depth + 1;
			buildTree(node.left);
			buildTree(node.right);
		}
	}

	private static final double LOG2 = Math.log(2);
	
	public static double entropy(double pos, double neg) {
		if (pos + neg == 0)
			return 0;
		return -1 * (neg / (pos + neg)) * Math.log(neg / (pos + neg)) / LOG2 +
				-1 * (pos / (pos + neg)) * Math.log(pos / (pos + neg)) / LOG2;
	}
	
	/*private double giniImpurity(int pos, int neg) {
		return 1 - (1.0*pos)/(pos + neg) - (1.0*neg)/(pos + neg);
	}*/

	public class Node {
		
		private Node left;
		private Node right;
		private double threshold;
		private NodeType type;
		private int attr;
		private int[] samples;
		private int depth;
		
		private boolean leaf;
		private boolean ad;
		
		public Node(int[] s) {
			samples = s;
		}
		
	}
	
	public boolean classify(double[] doubles, boolean[] booleans) {
		return classifyRec(doubles, booleans, root);
	}

	private boolean classifyRec(double[] doubles, boolean[] booleans, Node node) {
		if (node.leaf)
			return node.ad;
		if (node.type == NodeType.Boolean)
			if (booleans[node.attr-dataset.reals()])
				return classifyRec(doubles, booleans, node.left);
			else
				return classifyRec(doubles, booleans, node.right);
		else
			if (doubles[node.attr] != -1)
				if (doubles[node.attr] <= node.threshold)
					return classifyRec(doubles, booleans, node.left);
				else
					return classifyRec(doubles, booleans, node.right);
			else
				return classifyRec(doubles, booleans, node.left) || classifyRec(doubles, booleans, node.right);
	}

}
