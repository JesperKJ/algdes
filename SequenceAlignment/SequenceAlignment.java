import java.util.*;
import java.io.*;
import java.math.*;
import java.util.regex.Pattern;
public class SequenceAlignment {
	
	//public static ArrayList<double> Alignment(ArrayList<String> X, ArrayList<String> Y){
		//Array[x.];

		//return shortPoint; //shortPoint;
	//}
	
	static class penalties {
		public String name;
		public int[][] matrix;
	}
	
	static class species {
		public String name;
		public String protein;
	}
	
	private static penalties dataReader(String filepath) throws IOException { 
		String name = "A";
		int[][] matrix = new int[24][24];
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
					name = str.replaceAll("\\s+", "");
				if (str2 != null) {
					for (int i = 0; i < str2.split("[\\r\\n]+")[0].split("\\s+").length-1; i++) {
					matrix[j][i] = Integer.parseInt(str2.split("[\\r\\n]+")[0].split("\\s+")[i+1]); 
					}
					j++;
				}
				
			}
			
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			}
	    penalties output = new penalties();
	    output.name = name;
	    output.matrix = matrix;
	    
		return output;
		}
		
	//Define recurrence
	//Algorithm: Two strings as input, double as output
	
		private static int opt(String X, String Y, String name, int[][] matrix, int I, int J ) {
			int opt;
			int alpha;
			int delta;
			
			if (I == -1 | J == -1) {
				opt = 0;
				return opt;
			} else {
				alpha = matrix[1][1];
				delta = -4;
				opt = Math.max(alpha + opt(X, Y, name, matrix,I-1,J-1),
						Math.max(delta + opt(X, Y, name, matrix,I-1,J), 
								delta + opt(X, Y, name, matrix,I,J-1))); 
			}
			return opt;
		}
		
		private static int AbuttomUp(String X, String Y, String name, int[][] matrix) {
			int lengthx = X.length();
			int lengthy = Y.length();
			
			int[][] A = new int[lengthx + 1][lengthy + 1];
			int d = -4;
			int opt;
			int alpha = 0;
			
			for (int i = 0; i <= lengthx; i++) {
				A[i][0] = i*d;
			}
			for (int i = 0; i <= lengthy; i++) {
				A[0][i] = i*d;
			}
			System.out.println( lengthx);
			System.out.println( lengthy);
			System.out.println( X);
			System.out.println( Y);
			for (int i = 1; i <= lengthx; i++) {
				for (int j = 1; j <= lengthy; j++){
			alpha = matrix[name.indexOf(X.charAt(i-1))][name.indexOf(Y.charAt(j-1))];
			A[i][j] = Math.max(alpha + A[i-1][j-1], Math.max(d + A[i][j-1], d + A[i-1][j]));
				}
			}
			return A[lengthx][lengthy];
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
	    penalties inputData = dataReader(args[0]);// System.in);
	  //  input inputData2 = readFile(args[0]);// System.in);
		
	    ArrayList<String> names = new ArrayList<>();
		ArrayList<String> gene = new ArrayList<>();
		try {
			Scanner scan = null;
			File file = new File(args[0]);
			scan = new Scanner(file);
			while (scan.hasNextLine()) {
				final String line = scan.nextLine().trim();
				if (line.contains(">"))
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
				System.out.println(opt(gene.get(0), gene.get(1), inputData.name, inputData.matrix, I, J));
				System.out.println(AbuttomUp(gene.get(0),gene.get(1), inputData.name, inputData.matrix));
				//System.out.println(inputData.name);
	}	
	
	}



