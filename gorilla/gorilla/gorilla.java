import java.util.*;
import java.io.*;
import java.math.*;
import java.util.regex.Pattern;
public class gorilla {
	
	//public static ArrayList<double> Alignment(ArrayList<String> X, ArrayList<String> Y){
		//Array[x.];

		//return shortPoint; //shortPoint;
	//}
	
	static class scoring {
		public String[] names;
		public String[][] matrix;
	}
	/*
	static class input {
		String name;
		String gene;
		
		public input(String name, String gene) {
			this.name = name;
			this.gene = gene;
		}
	}
*/
	private static scoring dataReader(String filepath) throws IOException {
		String[] names = new String[24];
		String[][] matrix = new String[24][24];
		try {
			Scanner scan = null;
			File file = new File("BLOSUM62.txt");
			scan = new Scanner(file);
			Pattern pattern = Pattern.compile("(  [A-Z])+|  //*");
			Pattern pattern2 = Pattern.compile("(\\s+-?\\d+)+");
			int j = 0;
			//Pattern pattern = Pattern.compile("[A-Z]");
			while (scan.hasNextLine()) {
				if (scan.nextLine().contains("Cluster Percentage"))
					break;
			}
			while (scan.hasNext()) {
				String str;
				String str2;
				final String line = scan.nextLine().trim();
				str =  scan.findInLine(pattern);
				str2 = scan.findInLine(pattern2);
				if (str != null)
					for (int i = 0; i < str.split("\\s+").length-1; i++)
					names[i] = str.split("\\s+")[i+1];
				if (str2 != null) {
					for (int i = 0; i < str2.split("[\\r\\n]+")[0].split("\\s+").length-1; i++) {
					matrix[j][i] = str2.split("[\\r\\n]+")[0].split("\\s+")[i+1];
					}
					j++;
				}
				
			}
			
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			}
	    scoring output = new scoring();
	    output.names = names;
	    output.matrix = matrix;
	    
		return output;
		}
	/*
	private static scoring readFile(String filepath) throws IOException {
		ArrayList<String> names = new ArrayList<>();
		ArrayList<String> gene = new ArrayList<>();
		try {
			Scanner scan = null;
			File file = new File(filepath);
			scan = new Scanner(file);
			while (scan.hasNextLine()) {
				final String line = scan.nextLine().trim();
				if (line.contains("<"))
					names.add(line);
				else
					gene.add(line);
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	    input output = new input();
	    output.names = names;
	    output.gene = gene;
	    
		return output;
		}
	*/
	
	//Define recurrence
	//Algorithm: Two strings as input, double as output
	
		private static int opt(String X, String Y, String[] names, String[][] matrix, int I, int J ) {
			int opt;
			int alpha;
			int delta;
			
			if (I == -1 | J == -1) {
				opt = 0;
				return opt;
			} else {
				alpha = Integer.parseInt(matrix[1][1]);
				delta = -4;
				opt = Math.max(alpha + opt(X, Y, names, matrix,I-1,J-1),
						Math.max(delta + opt(X, Y, names, matrix,I-1,J), 
								delta + opt(X, Y, names, matrix,I,J-1))); 
			}
			return opt;
		}
	
	//Algorithm: Two strings as input, double as output
	
	private static int alignment(String X, String Y) {
		int max = Math.max(X.length(), Y.length());
		String[][] A = new String[2][max];
		for (int i = 0; i < X.length(); i++)
			A[1][i] = X.split("(?!^)")[i];
		for (int i = 0; i < X.length(); i++)
			A[2][i] = Y.split("(?!^)")[i];
		
		
		return A.length;
	}
	
	
	
	public static void main(String[] args) throws IOException {
	    scoring inputData = dataReader(args[0]);// System.in);
	  //  input inputData2 = readFile(args[0]);// System.in);
		
	    ArrayList<String> names = new ArrayList<>();
		ArrayList<String> gene = new ArrayList<>();
		try {
			Scanner scan = null;
			File file = new File(args[0]);
			scan = new Scanner(file);
			while (scan.hasNextLine()) {
				final String line = scan.nextLine().trim();
				if (line.contains("<"))
					names.add(line);
				else
					gene.add(line);
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	    
		for (int i = 0 ; i < names.size() ; i++) 
			System.out.print(names.get(i) + " ");
			System.out.println();	
			
			for (int i = 0 ; i < gene.size() ; i++) 
				System.out.print(gene.get(i) + " ");
				System.out.println();	
		
		/*
		for (int i = 0 ; i < inputData.names.length ; i++) 
			System.out.print(inputData.names[i] + " ");
			System.out.println();	
			
		for (int i = 0 ; i < inputData.matrix.length ; i++) {
			for (int j = 0; j < inputData.matrix.length; j++)
			System.out.print(inputData.matrix[i][j] + " ");
			System.out.println();
		}
		*/
				
		//Algorithm: Two strings as input, double as output
				
				int I = 2;
				int J = 2;
				System.out.println(opt(gene.get(0), gene.get(1), inputData.names, inputData.matrix, I, J));
	}	
	
	}


