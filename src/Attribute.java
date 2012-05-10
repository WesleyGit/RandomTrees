
public class Attribute {

	public static final int REAL = 1;
	public static final int BINARY = 2;
	
	public double rvalue;
	public boolean bvalue;
	public int type;
	
	public Attribute(double value, int type) {
		this.rvalue = value;
		this.type = type;
	}

	public Attribute(boolean value, int type) {
		this.bvalue = value;
		this.type = type;
	}
	
}
