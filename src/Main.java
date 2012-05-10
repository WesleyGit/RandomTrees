import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.Scanner;

public class Main {

	public static final String datasetfile = "ad.data"; 
	public static final int INSTANCES = 3279;
	public static final int ATTRIBUTES = 1558;
	
	public static void main(String[] args) throws FileNotFoundException {
		FileReader ifile = new FileReader(datasetfile);
		Scanner scanner = new Scanner(ifile);
		long starttime = (new Date()).getTime();
		Instance[] dataset = new Instance[INSTANCES];
		for (int i = 0; scanner.hasNextLine(); i++) {
			Scanner attscanner = new Scanner(scanner.nextLine());
			attscanner.useDelimiter(",");
			Attribute[] attributes = new Attribute[ATTRIBUTES];
			for (int j = 0; j < 3; j++) {
				String dstring = attscanner.next();
				if (!dstring.contains("?"))
					attributes[j] = new Attribute(Double.parseDouble(dstring), Attribute.REAL);
				else
					attributes[j] = new Attribute(-1, Attribute.REAL);
			}
			for (int j = 3; j < ATTRIBUTES; j++)
				attributes[j] = new Attribute(Boolean.parseBoolean(attscanner.next()), Attribute.BINARY);
			boolean ad = attscanner.next() == "ad";
			dataset[i] = null;//new Instance(attributes, ad);
		}
		System.out.println("File reading done. Read dataset succesfully. Took "+((new Date()).getTime()-starttime)+"ms.");
		new Forest(dataset);
	}
	

}
